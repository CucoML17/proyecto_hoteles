package carlos.mejia.proyectohoteles.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity {
	
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	
		
	
	@Bean
	UserDetailsManager usuarios(DataSource dataSource) {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		
		//Para lo de uno propio
		users.setUsersByUsernameQuery("SELECT username, password, estatus FROM usuario WHERE username=?;");
		users.setAuthoritiesByUsernameQuery("SELECT usuario.username, perfil.perfil FROM usuario INNER JOIN usuarioperfil ON usuario.id = usuarioperfil.id_usuario INNER JOIN perfil ON usuarioperfil.id_perfil = perfil.id WHERE username=?;");
		
		return users;
	}	
	@Bean
	public SecurityFilterChain filtroUsuarios(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		
		//Los recursos estáticos no requieren autenticación
		.requestMatchers ("/css/**", "/img/**", "/tinymce/**", "/imagenes/**", "/cliente/user/registrarUsuario/**", "/cliente/user/guardar", "/logout").permitAll()
		
		//Las vistas públicas no requieren autenticación
		.requestMatchers ("/buscar", "/", "/registrarUsuario", "/producto/listaP" , "/detalle", "/search", "/productos/view**",
				"/user/reservar/selecHotel/**", "/user/reservar/buscarSelecHotel", "/user/reservar/selecHabitacion/**",
				"/user/reservar/buscarListaHabitaciones", "/sobreNosotros").permitAll()
		
		//Para Clientes:
		.requestMatchers("/cliente/user/**", "/user/reservar/**").hasAnyAuthority("Cliente")
		
		//Los 3 tienen el permiso de mínimo ver el menú principal de administración
		.requestMatchers("/admin/**").hasAnyAuthority("Recepcionista", "Mantenimiento", "Administrador")

		//Para Recepcionista:
		.requestMatchers("/cliente/admin/**",
				"/habitacion/admin/listaHabitaciones/**", "/habitacion/admin/buscarListaHabitaciones",
				"/habitacion/admin/verMas/**",
				
				"/hotel/admin/listaHoteles", "/hotel/admin/buscar", "/hotel/admin/verMas/**",
				
				"/reserva/admin/**").hasAnyAuthority("Recepcionista", "Administrador")
		
						
		//Para Mantenimiento/Limpieza
		.requestMatchers("/mantenimiento/admin/**").hasAnyAuthority("Mantenimiento", "Administrador")
		
		//Para ADMINISTRADOR
		.requestMatchers("/cliente/admin/**", "/empleado/admin/**", "/habitacion/admin/**",
				"/hotel/admin/**", "/mantenimiento/admin/**", "/reserva/admin/**").hasAnyAuthority("Administrador")		
		
		//Todas las demás URLs de la Aplicación requieren autenticación
        .anyRequest().authenticated()
        .and()
        .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/")
            .failureUrl("/login?error")
            .permitAll()
        .and()
        .logout()
            .logoutUrl("/logout") //El mapeo para preguntar la salida
            .logoutSuccessUrl("/salido") 
            .permitAll()
		//Para que cuando no tenga acceso no caiga la página:
        .and()
        .exceptionHandling()
        	.accessDeniedHandler(accessDeniedHandler);
		
		
		
		return http.build();		
		
		
		
	}
	
	
	
}
