package carlos.mejia.proyectohoteles.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAccessDeniedHandler  implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Authentication authentication = (Authentication) request.getUserPrincipal();

        if (authentication != null) {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            if (roles.contains("Administrador")) {
                response.sendRedirect("/admin/menuPrincipal?errorAcceso"); 
            } else if (roles.contains("Recepcionista")) {
                response.sendRedirect("/admin/menuPrincipal?errorAcceso"); 
            } else if (roles.contains("Mantenimiento")) {
                response.sendRedirect("/admin/menuPrincipal?errorAcceso"); 
            } else if (roles.contains("Cliente")) {
                response.sendRedirect("/?errorAcceso"); 
            } else {
                response.sendRedirect("/?errorAcceso"); 
            }
        } else {
            response.sendRedirect("/login"); //Si no hay usuario autenticado
        }
    }
}
