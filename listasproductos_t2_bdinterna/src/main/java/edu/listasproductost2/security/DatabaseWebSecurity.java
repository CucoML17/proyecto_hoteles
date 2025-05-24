package edu.listasproductost2.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity {
	
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
	
	//Filtros
	@Bean
	public SecurityFilterChain filtroUsuarios(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		
		//Los recursos estáticos no requieren autenticación
		.requestMatchers ("/css/**", "/images/**", "/tinymce/**", "/imagenesCate/**", "/imagenesProdu/**", "/registrarUsuario", "/guardarUsuario", "/logout").permitAll()
		
		//Las vistas públicas no requieren autenticación
		.requestMatchers ("/buscar", "/", "/registrarUsuario", "/producto/listaP" , "/detalle", "/search", "/productos/view**").permitAll()
		
		//Para CONSULTA:
		.requestMatchers("/tabla", "/categorias/mostrarInicio", "/vendedor/listaV" ,"/vendedor/ver/**", "/producto/ver/**").hasAnyAuthority("CONSULTA", "SUPERVISOR", "ADMINISTRADOR")

		//Para SUPERVISOR:
		.requestMatchers("/venta/listaV", "/venta/verDetalle/**","/cliente/listaC","/usuario/listaUsuarios", "/venta/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR")
						
		//Para ADMINISTRADOR
		.requestMatchers("/**").hasAnyAuthority("ADMINISTRADOR")
		
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
            .logoutUrl("/logout") // La URL para iniciar el proceso de salida
            .logoutSuccessUrl("/salido") // Redirige a /salido después del logout exitoso
            .permitAll()
		//Para que cuando no tenga acceso no caiga la página:
        .and()
        .exceptionHandling()
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.sendRedirect("/?errorAcceso");
            });
		
		
		
		return http.build();		
		
		
		
	}

}
