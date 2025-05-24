package carlos.mejia.proyectohoteles.service;

import java.util.List;

import carlos.mejia.proyectohoteles.model.DetalleMantenimiento;
import carlos.mejia.proyectohoteles.model.DetalleMantenimientoId;


public interface DetalleMantenimientoService {
	void guardarDetalleMantenimiento(DetalleMantenimiento detalleMantenimiento);
	
	List<DetalleMantenimiento> todosDatellesMantes();
	
	DetalleMantenimiento buscarPorId (DetalleMantenimientoId id);

	void eliminarDetalleMantenimiento(DetalleMantenimientoId detalleId);
	
	void eliminarPorIdMantenimiento(Integer idMantenimiento);
	
}
