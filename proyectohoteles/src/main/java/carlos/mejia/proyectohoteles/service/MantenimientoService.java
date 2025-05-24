package carlos.mejia.proyectohoteles.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import carlos.mejia.proyectohoteles.model.Mantenimiento;

public interface MantenimientoService {
	int guardarMantenimiento(Mantenimiento mantenimiento);
	
	List<Mantenimiento> todosMantes();
	
	Mantenimiento buscarMantenimientoID(Integer idMante);
	
	List<Mantenimiento> filtrarMantenimientos(Integer idHotel, Integer numeroHabitacion, String fechaInicioStr, String fechaFinStr);
	
	Map<String, Long> contarMantenimientosPorEstado(Integer hotelId);
	
	List<Mantenimiento> findMantenimientosSolapados(Integer habitacionId, Date fechaInicio, Date fechaFin);
	
	List<Mantenimiento> findMantenimientoEnFecha(Integer habitacionId, Date fecha);	
	
}
