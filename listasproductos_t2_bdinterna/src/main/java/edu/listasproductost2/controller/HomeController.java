package edu.listasproductost2.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.listasproductost2.model.Categoria;
import edu.listasproductost2.model.Perfil;
import edu.listasproductost2.model.Producto;
import edu.listasproductost2.model.Usuario;
import edu.listasproductost2.service.CategoriaService;
import edu.listasproductost2.service.IUsuarioService;
import edu.listasproductost2.service.PerfilService;
import edu.listasproductost2.service.ProductoService;



@Controller
public class HomeController {
	
	@Autowired
	private ProductoService serviceProducto;
	
	@Autowired
	private CategoriaService serviceCategoria;
	
	@Autowired
	private IUsuarioService serviceUsuario;
	
	@Autowired
	private PerfilService servicePerfil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;	
	
	@GetMapping("/")
	public String mostrarHome(@RequestParam(value = "errorAcceso", required = false) String errorAcceso, Model model) {
		
	    if (errorAcceso != null) {
	        model.addAttribute("mensajeAccesoDenegado", "Acceso denegado. No tiene los permisos necesarios.");
	    }		
		
		List<Categoria> listaC = serviceCategoria.buscarTodasCates();
		List<Producto> lista = serviceProducto.buscarDestacadoDisponible();
		
		model.addAttribute("productoP", lista);		
		model.addAttribute("listaC", listaC);
		
		
		if(serviceUsuario.buscarTodosUsuarios().size()==0) {
			//Crear los Perfiles
			Perfil p1 = new Perfil();
			p1.setPerfil("ADMINISTRADOR");
			servicePerfil.guardarPerfil(p1);
			
			Perfil p2 = new Perfil();
			p2.setPerfil("SUPERVISOR");
			servicePerfil.guardarPerfil(p2);
			
			Perfil p3 = new Perfil();
			p3.setPerfil("USUARIO");
			servicePerfil.guardarPerfil(p3);
			
			Perfil p4 = new Perfil();
			p4.setPerfil("CONSULTA");
			servicePerfil.guardarPerfil(p4);			
			
			Usuario userJefe = new Usuario();
			userJefe.setNombre("Administrador desarrollador");
			userJefe.setStatus(1);
			userJefe.setFechaRegistro(new Date());
			userJefe.setUsername("cuco");
			userJefe.setEmail("bambino7576@gmai.com");
		    
		    
		    //Guardar la contraseña encriptada
		    String passwordEncriptado = passwordEncoder.encode("123");
		    userJefe.setPassword(passwordEncriptado);	    
		    
		    //Para el perfil de CONSULTA
			Perfil perUsuario = new Perfil();
			perUsuario = servicePerfil.buscarPorIdPerfil(1);
			
			
			userJefe.agregarPerfilUsuario(perUsuario);	    

		    serviceUsuario.guardarU(userJefe);
		    System.out.println("Patrón creado");			
			
			
			
		}
		
		return "home"; //El nombre del archivo, no es que vaya a mostrar la palabra home, sino que buscará un hmtl con ese nombre, sino que renderiza ¿ok?
		
	}
	
	
	

	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Producto> lista = serviceProducto.buscarTodos();
		model.addAttribute("productoP", lista);
		return "tabla";
	}
	
	@GetMapping("categorias/nuevaCate")
	public String mostrarNuevaCate(Model model) {

		return "categorias/nuevaCate";
	}	
	
	
    @PostMapping("/buscar")
    public String buscarProductos(Model model, @RequestParam("busquedaProd") String busquedaProd,
                                  @RequestParam("busquedaCate") String busquedaCate) {

    	
    	List<Categoria> listaC = serviceCategoria.buscarTodasCates();
    	
    	model.addAttribute("listaC", listaC);
    	
        List<Producto> listaProducto;
        int buscaCa = Integer.parseInt(busquedaCate);
        System.out.println(buscaCa);
        if (buscaCa!=0) {
        	if(busquedaProd.isEmpty()==false) {
        		listaProducto = serviceProducto.buscarPorCateNombre(buscaCa, busquedaProd);
        		model.addAttribute("mensajeExito", "Producto buscado por: " + busquedaProd + "<br>" +
        		"Categoría buscada por: " + serviceCategoria.buscarPorIDCat(buscaCa).getNombre());
        	}else {
        		
        		listaProducto = serviceProducto.buscarPorCate(buscaCa);
        		model.addAttribute("mensajeExito", "Categoría buscada por: " + serviceCategoria.buscarPorIDCat(buscaCa).getNombre());
        	}
        }else {
        	if(busquedaProd.isEmpty()) {
        		listaProducto = serviceProducto.buscarDestacadoDisponible();
        		
        	}else {
        		listaProducto = serviceProducto.buscarPorNombre(busquedaProd);
        		model.addAttribute("mensajeExito", "Producto buscado por: " + busquedaProd);
                		        		
        	}
        	
        }

        model.addAttribute("productoP", listaProducto);
        return "home";
    }	
    
    
    
    
	@GetMapping("/registrarUsuario")
	public String abrirRegistroUsuario(Model model, Usuario usuario) {
		
		List<Perfil> listaPerfil = servicePerfil.buscarTodosPerfiles();
		model.addAttribute("listaPerfil", listaPerfil);		
		
		return "usuario/formUsuario";		
		
	}	
	
	
	@PostMapping("/guardarUsuario")
	public String guardar(Usuario usuario, BindingResult result, Model model, RedirectAttributes redirectAttributes){

	    if (result.hasErrors()) {
	        for (ObjectError error : result.getAllErrors()) {
	            System.out.println("Ocurrio un error: " + error.getDefaultMessage());
	        }

			List<Perfil> listaPerfil = servicePerfil.buscarTodosPerfiles();
			model.addAttribute("listaPerfil", listaPerfil);	
	        return "usuario/formUsuario";
	    }

	    //Para la fecha actual del sistema
	    usuario.setFechaRegistro(new Date());
	    
	    //Guardar la contraseña encriptada
	    String passwordEncriptado = passwordEncoder.encode(usuario.getPassword());
	    usuario.setPassword(passwordEncriptado);	    
	    
	    //Para el perfil de CONSULTA
		Perfil perUsuario = new Perfil();
		perUsuario.setId(4);
		
		//Para status
		usuario.setStatus(1);
		
		usuario.agregarPerfilUsuario(perUsuario);	    

	    serviceUsuario.guardarU(usuario);
	    System.out.println(usuario);

	    redirectAttributes.addFlashAttribute("mensajeExitoUsuario", "Usuario agregado con éxito");
	    return "redirect:/";
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
		
    
	
	
	@GetMapping("/registrarUsuarioExtra") // Nuevo mapping para el formulario extra
	public String abrirRegistroUsuarioExtra(Model model, Usuario usuario) {
		List<Perfil> listaPerfil = servicePerfil.buscarTodosPerfiles();
		model.addAttribute("listaPerfil", listaPerfil);	
		
		return "usuario/formUsuarioExtra";
	}


	@PostMapping("/guardarUsuarioExtra")
	public String guardarUsuarioExtra(Usuario usuario, BindingResult result, Model model, RedirectAttributes redirectAttributes,
	                      @RequestParam(value = "perfilesSeleccionados", required = false) String perfiles) {

	    System.out.println("--- Inicio del método guardarUsuarioExtra ---");
	    System.out.println("Usuario recibido del formulario: " + usuario);
	    System.out.println("Cadena de perfiles seleccionados: " + perfiles);

	    if (result.hasErrors()) {
	        System.out.println("¡Errores de validación encontrados!");
	        for (ObjectError error : result.getAllErrors()) {
	            System.out.println("Error: " + error.getDefaultMessage());
	        }
	        List<Perfil> listaPerfil = servicePerfil.buscarTodosPerfiles();
	        model.addAttribute("listaPerfil", listaPerfil);
	        System.out.println("Lista de perfiles para el formulario (en caso de error): " + listaPerfil);
	        System.out.println("--- Fin del método guardarUsuarioExtra (con errores) ---");
	        return "usuario/formUsuarioExtra";
	    }

	    //Para la fecha actual del sistema
	    usuario.setFechaRegistro(new Date());

	    //Encriptar la contraseña
	    String passwordEncriptado = passwordEncoder.encode(usuario.getPassword());
	    usuario.setPassword(passwordEncriptado);

	    //Asignar los perfiles seleccionados
	    if (perfiles != null && !perfiles.isEmpty()) {
	        List<Integer> perfilesIds = Arrays.stream(perfiles.split(","))
	                                         .map(Integer::parseInt)
	                                         .collect(Collectors.toList());
	        System.out.println("IDs de perfiles a procesar: " + perfilesIds);
	        for (Integer perfilId : perfilesIds) {
	            System.out.println("Intentando buscar perfil con ID: " + perfilId);
	            Perfil perfil = servicePerfil.buscarPorIdPerfil(perfilId);
	            if (perfil != null) {
	                System.out.println("Perfil encontrado: " + perfil);
	                usuario.agregarPerfilUsuario(perfil);
	                System.out.println("Perfil agregado al usuario.");
	            } else {
	                System.out.println("¡Advertencia! No se encontró el perfil con ID: " + perfilId);
	            }
	        }
	    } else {
	        System.out.println("No se recibieron perfiles seleccionados.");
	    }

	    //Para status
	    usuario.setStatus(1);

	    serviceUsuario.guardarU(usuario);
	    System.out.println("Usuario guardado (después de agregar perfiles): " + usuario);
	    System.out.println("--- Fin del método guardarUsuarioExtra (éxito) ---");

	    redirectAttributes.addFlashAttribute("mensajeExitoUsuario", "Usuario agregado con éxito");
	    return "redirect:/";
	}
}
