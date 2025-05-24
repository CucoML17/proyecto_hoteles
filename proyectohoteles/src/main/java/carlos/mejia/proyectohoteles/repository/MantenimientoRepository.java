package carlos.mejia.proyectohoteles.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import carlos.mejia.proyectohoteles.model.Mantenimiento;



public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Integer>{

	
    List<Mantenimiento> findByidMantenimientoHabitacion_IdHotelHabitacion_Id(Integer idHotel);

    List<Mantenimiento> findByidMantenimientoHabitacion_NumeroHabitacion(Integer numeroHabitacion);

    List<Mantenimiento> findByFechaBetween(Date fechaInicio, Date fechaFin);

    List<Mantenimiento> findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndIdMantenimientoHabitacion_NumeroHabitacion(Integer idHotel, Integer numeroHabitacion);

    List<Mantenimiento> findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndFechaBetween(Integer idHotel, Date fechaInicio, Date fechaFin);

    List<Mantenimiento> findByidMantenimientoHabitacion_NumeroHabitacionAndFechaBetween(Integer numeroHabitacion, Date fechaInicio, Date fechaFin);

    List<Mantenimiento> findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndIdMantenimientoHabitacion_NumeroHabitacionAndFechaBetween(Integer idHotel, Integer numeroHabitacion, Date fechaInicio, Date fechaFin);

    //Consultas para rangos abiertos (solo inicio o solo fin)
    List<Mantenimiento> findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndFechaGreaterThanEqual(Integer idHotel, Date fechaInicio);
    List<Mantenimiento> findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndFechaLessThanEqual(Integer idHotel, Date fechaFin);

    List<Mantenimiento> findByidMantenimientoHabitacion_NumeroHabitacionAndFechaGreaterThanEqual(Integer numeroHabitacion, Date fechaInicio);
    List<Mantenimiento> findByidMantenimientoHabitacion_NumeroHabitacionAndFechaLessThanEqual(Integer numeroHabitacion, Date fechaFin);

    List<Mantenimiento> findByFechaGreaterThanEqual(Date fechaInicio);
    List<Mantenimiento> findByFechaLessThanEqual(Date fechaFin);

    List<Mantenimiento> findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndIdMantenimientoHabitacion_NumeroHabitacionAndFechaGreaterThanEqual(Integer idHotel, Integer numeroHabitacion, Date fechaInicio);
    List<Mantenimiento> findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndIdMantenimientoHabitacion_NumeroHabitacionAndFechaLessThanEqual(Integer idHotel, Integer numeroHabitacion, Date fechaFin);
    
    List<Mantenimiento> findByidMantenimientoHabitacion_IdAndFechaBetween(Integer habitacionId, Date fechaInicio, Date fechaFin);
    List<Mantenimiento> findByidMantenimientoHabitacion_IdAndFecha(Integer habitacionId, Date fecha);
	
}
