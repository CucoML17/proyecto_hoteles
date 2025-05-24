package carlos.mejia.proyectohoteles.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.service.HotelService;
import carlos.mejia.proyectohoteles.service.MantenimientoService;
import carlos.mejia.proyectohoteles.service.ReservaService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private MantenimientoService mantenimientoService;
    
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;    

	@GetMapping("/menuPrincipal")
	public String menuPrinci(@RequestParam(value = "errorAcceso", required = false) String errorAcceso, 
	Model model) {
		
	    if (errorAcceso != null) {
	        model.addAttribute("mensajeAccesoDenegado", "Acceso denegado. No tiene los permisos necesarios.");
	    }	

		//Para las gráficas
        List<Hotel> hoteles = hotelService.todosHoteles();
        model.addAttribute("hoteles", hoteles);

        //Reservas
        Map<String, Long> reservasPorEstado = reservaService.contarReservasPorEstado(null); // Null para todas las reservas
        model.addAttribute("reservasPorEstado", reservasPorEstado);
        
        //Mantenimientos
        Map<String, Long> mantenimientosPorEstado = mantenimientoService.contarMantenimientosPorEstado(null);
        model.addAttribute("mantenimientosPorEstado", mantenimientosPorEstado);
		
        //Rango de fechas por defecto para la gráfica de reservas por día
        LocalDate hoy = LocalDate.now();
        LocalDate fechaInicioDefault = hoy.minusDays(4);
        LocalDate fechaFinDefault = hoy.plusDays(4);
        model.addAttribute("fechaInicioDefault", dateFormatter.format(fechaInicioDefault));
        model.addAttribute("fechaFinDefault", dateFormatter.format(fechaFinDefault));

        Map<String, Long> reservasPorDia = reservaService.contarReservasPorFechaInicio(fechaInicioDefault, fechaFinDefault);
        model.addAttribute("reservasPorDia", reservasPorDia);
        model.addAttribute("errorRangoFechas", null);        
        
        
		return "admin/panelAdmin";
	}		
	
	
	//Para las gráficas
    @PostMapping("/dona/filtrarReservas")
    public String filtrarDashboard(@RequestParam(value = "hotelId", required = false) Integer hotelId, Model model) {
        List<Hotel> hoteles = hotelService.todosHoteles();
        model.addAttribute("hoteles", hoteles);
        model.addAttribute("hotelSeleccionadoId", hotelId); // Para mantener la selección en el desplegable

        Map<String, Long> reservasPorEstadoFiltrado = reservaService.contarReservasPorEstado(hotelId);
        model.addAttribute("reservasPorEstado", reservasPorEstadoFiltrado);

        return "admin/panelAdmin";
    }	
    
    @PostMapping("/pastel/filtrarMantenimientos")
    public String filtrarMantenimientos(@RequestParam(value = "hotelIdMantenimiento", required = false) Integer hotelIdMantenimiento, Model model) {
        List<Hotel> hoteles = hotelService.todosHoteles();
        model.addAttribute("hoteles", hoteles);
        model.addAttribute("hotelSeleccionadoMantenimientoId", hotelIdMantenimiento); // Para mantener la selección en el desplegable de mantenimientos

        Map<String, Long> reservasPorEstado = reservaService.contarReservasPorEstado(null); // Resetear las reservas al filtrar mantenimientos
        model.addAttribute("reservasPorEstado", reservasPorEstado);

        Map<String, Long> mantenimientosPorEstadoFiltrado = mantenimientoService.contarMantenimientosPorEstado(hotelIdMantenimiento);
        model.addAttribute("mantenimientosPorEstado", mantenimientosPorEstadoFiltrado);

        return "admin/panelAdmin";
    }    
    
    @PostMapping("/fechas/filtrarReservasPorFecha")
    public String filtrarReservasPorFecha(@RequestParam("fechaInicio") String fechaInicioStr,
                                           @RequestParam("fechaFin") String fechaFinStr,
                                           Model model) {
        List<Hotel> hoteles = hotelService.todosHoteles();
        model.addAttribute("hoteles", hoteles);

        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, dateFormatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, dateFormatter);

        if (ChronoUnit.DAYS.between(fechaInicio, fechaFin) > 8) {
            model.addAttribute("errorRangoFechas", "El rango de fechas no puede ser mayor a 8 días.");
            // Volver a cargar los datos por defecto para los otros gráficos
            model.addAttribute("reservasPorEstado", reservaService.contarReservasPorEstado(null));
            model.addAttribute("mantenimientosPorEstado", mantenimientoService.contarMantenimientosPorEstado(null));
            model.addAttribute("fechaInicioDefault", dateFormatter.format(LocalDate.now().minusDays(4)));
            model.addAttribute("fechaFinDefault", dateFormatter.format(LocalDate.now().plusDays(4)));
            model.addAttribute("reservasPorDia", reservaService.contarReservasPorFechaInicio(LocalDate.now().minusDays(4), LocalDate.now().plusDays(4)));
        } else {
            model.addAttribute("errorRangoFechas", null);
            model.addAttribute("fechaInicioDefault", fechaInicioStr);
            model.addAttribute("fechaFinDefault", fechaFinStr);
            model.addAttribute("reservasPorEstado", reservaService.contarReservasPorEstado(null));
            model.addAttribute("mantenimientosPorEstado", mantenimientoService.contarMantenimientosPorEstado(null));
            Map<String, Long> reservasPorDiaFiltradas = reservaService.contarReservasPorFechaInicio(fechaInicio, fechaFin);
            model.addAttribute("reservasPorDia", reservasPorDiaFiltradas);
        }

        return "admin/panelAdmin";
    }    
    

}
