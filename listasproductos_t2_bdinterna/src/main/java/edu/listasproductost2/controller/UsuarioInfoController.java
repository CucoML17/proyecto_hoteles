package edu.listasproductost2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.listasproductost2.model.Perfil;
import edu.listasproductost2.model.Usuario;
import edu.listasproductost2.service.IUsuarioService;

@ControllerAdvice
public class UsuarioInfoController {
    @Autowired
    private IUsuarioService usuarioService;

    @ModelAttribute("nombreUsuario")
    public String getNombreUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            String username = authentication.getName();
            Usuario usuario = usuarioService.encontrarUsuario(username);
            if (usuario != null) {
                return usuario.getNombre();
            }
        }
        return null;
    }
    
    @ModelAttribute("perfilesUsuario")
    public List<String> getPerfilesUsuarioDesdeUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            String username = authentication.getName();
            Usuario usuario = usuarioService.encontrarUsuario(username);
            if (usuario != null && usuario.getPerfiles() != null) {
                return usuario.getPerfiles().stream()
                        .map(Perfil::getPerfil) 
                        .collect(Collectors.toList());
            }
        }
        return null;
    }

}
