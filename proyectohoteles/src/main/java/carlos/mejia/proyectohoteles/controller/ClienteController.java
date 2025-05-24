package carlos.mejia.proyectohoteles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import carlos.mejia.proyectohoteles.model.Cliente;
import carlos.mejia.proyectohoteles.model.Usuario;
import carlos.mejia.proyectohoteles.service.ClienteService;
import carlos.mejia.proyectohoteles.service.UsuarioService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	UsuarioService usuarioService;	
	
	@GetMapping("/admin/listaClientes")
	public String mostrarLista(Model model) {
		//Para los empleados 
		List<Cliente> lista = clienteService.todosClientes();
		model.addAttribute("listaClientes", lista);
		
		
		
		return "admin/cliente/panelAdminClientes";
		
	}
	
	//Para ver más
	@GetMapping("/admin/verMas/{id}")
	public String verDetalle(@PathVariable("id") int idCliente, Model model) {
		
		Cliente cliente = clienteService.buscarClienteID(idCliente);
	
		model.addAttribute("cliente", cliente);
		//model.addAttribute("listaHabitaciones", hotel.getHabitaciones());
		
		model.addAttribute("titulo", "Detalles del cliente " + cliente.getNombre());
		
		return "admin/cliente/detalleCliente";
	}    

	//Cambiar de estado
	@GetMapping("/admin/cambiarEstado/{id}")
	public String cambiarEstadoCliente(@PathVariable("id") int idCliente, RedirectAttributes redirectAttributes) {

		Cliente cliente = clienteService.buscarClienteID(idCliente);

		int estadoCliente = cliente.getEstado();
		int nuevoEstadoCliente;

		if (estadoCliente == 1) {
			nuevoEstadoCliente = 0;
		} else {
			nuevoEstadoCliente = 1;
		}

		cliente.setEstado(nuevoEstadoCliente);
		clienteService.guardarCliente(cliente);

		// Obtener el usuario asociado al cliente
		Usuario usuarioCliente = cliente.getIdClienteUser();
		if (usuarioCliente != null) {
			System.out.println("Cambiando estado del usuario del cliente...");
			usuarioCliente.setEstatus(nuevoEstadoCliente);
			usuarioService.guardarUsuario(usuarioCliente);
		}

		redirectAttributes.addFlashAttribute("mensajeExito", "" + cliente.getNombre() + " ha cambiado de estado.");

		return "redirect:/cliente/admin/listaClientes";
	}
	
	
	
	//Filtrar clientes
	@PostMapping("/admin/filtrarClientes")
    public String filtrarClientes(
            @RequestParam(value = "campoFiltro", required = false) String campoFiltro,
            @RequestParam(value = "activarFiltro", required = false) String activarFiltro,
            @RequestParam(value = "valorFiltro", required = false) String valorFiltro,
            Model model
    ) {
        List<Cliente> listaClientes = clienteService.filtrarClientes(
                (activarFiltro != null && activarFiltro.equals("on")) ? campoFiltro : null,
                (activarFiltro != null && activarFiltro.equals("on")) ? valorFiltro : null
        );

        model.addAttribute("listaClientes", listaClientes);

        if (activarFiltro != null && activarFiltro.equals("on") && valorFiltro != null && !valorFiltro.isEmpty()) {
            String mensajeFiltro = "Se filtró por: ";
            switch (campoFiltro.toLowerCase()) {
                case "id":
                    mensajeFiltro += "Clave de cliente = " + valorFiltro;
                    break;
                case "nombre":
                    mensajeFiltro += "Nombre que contiene: " + valorFiltro;
                    break;
                case "apellido":
                    mensajeFiltro += "Apellido que contiene: " + valorFiltro;
                    break;
                default:
                    mensajeFiltro += "Criterio desconocido (" + campoFiltro + ") con valor: " + valorFiltro;
                    break;
            }
            model.addAttribute("mensajeFiltro", mensajeFiltro);
        } else {
            model.addAttribute("mensajeFiltro", "Mostrando todos los clientes.");
        }

        return "admin/cliente/panelAdminClientes"; // Ajusta el nombre de tu página HTML
    }
    
    
    
    
}
