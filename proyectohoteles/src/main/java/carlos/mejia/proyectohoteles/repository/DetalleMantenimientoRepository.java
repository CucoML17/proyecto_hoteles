package carlos.mejia.proyectohoteles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carlos.mejia.proyectohoteles.model.DetalleMantenimiento;
import carlos.mejia.proyectohoteles.model.DetalleMantenimientoId;



public interface DetalleMantenimientoRepository extends JpaRepository<DetalleMantenimiento, DetalleMantenimientoId> {
	 void deleteById_Idmantenimiento(Integer idMantenimiento);
}
