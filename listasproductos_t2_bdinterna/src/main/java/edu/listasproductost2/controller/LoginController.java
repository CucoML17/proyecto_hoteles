package edu.listasproductost2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	
    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login/loginForm";
    }

    
    
    @GetMapping("/logout")
    public String mostrarPaginaConfirmarLogout() {
        return "login/logoutConfirm";
    }

    @GetMapping("/salido")
    public String mostrarPaginaLogoutExito(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajeSalida", "Ha cerrado su sesión con éxito, ¡hasta pronto!");

        return "redirect:/";
    }
    
    @GetMapping("/nopermiso")
    public String accesoDenegado(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajeAccesoDenegado", "Acceso denegado. No tiene los permisos necesarios.");
        return "redirect:/";
    }
    
}
