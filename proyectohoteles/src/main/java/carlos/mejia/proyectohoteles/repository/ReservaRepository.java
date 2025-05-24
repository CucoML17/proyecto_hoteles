package carlos.mejia.proyectohoteles.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import carlos.mejia.proyectohoteles.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer>{

    List<Reserva> findByIdReservaHabitacion_IdAndFechainicioLessThanEqualAndFechafinGreaterThanEqual(
            Integer habitacionId, Date fechaRev, Date fechaRev2 //Se usa la misma fecha para ambos extremos, es una búsqueda cerrada
    );

    //Buscar todas las reservas para una habitación específica
    List<Reserva> findByIdReservaHabitacion_IdAndFechainicioBetween(
            Integer habitacionId, Date fechaIni, Date fechaFin
    );

    //Buscar todas las reservas para una habitación específica
    List<Reserva> findByIdReservaHabitacion_IdAndFechafinBetween(
            Integer habitacionId, Date fechaIni, Date fechaFin
    );

    //Buscar todas las reservas para una habitación específica    
    List<Reserva> findByIdReservaHabitacion_IdAndFechainicioLessThanAndFechafinGreaterThan(
            Integer habitacionId, Date fechaIni, Date fechaFin
    );

    //Una combinación más completa
    List<Reserva> findByIdReservaHabitacion_IdAndFechainicioLessThanAndFechafinGreaterThanEqual(
            Integer habitacionId, Date fechaFinRango, Date fechaIniRango
    );

    List<Reserva> findByIdReservaHabitacion_IdAndFechafinGreaterThanAndFechainicioLessThanEqual(
            Integer habitacionId, Date fechaIniRango, Date fechaFinRango
    );	
    
    
    
    //----------
    Reserva findByCodigoReserva(String codigoReserva);
    
    List<Reserva> findByFechainicioBetween(Date fechaInicio, Date fechaFin);

    List<Reserva> findByPrecioTotalBetween(double precioMinimo, double precioMaximo); // Cambiado a double

    List<Reserva> findByIdReservaCliente_NombreContainingIgnoreCase(String nombreCliente);

    List<Reserva> findByCodigoReservaAndFechainicioBetween(String codigoReserva, Date fechaInicio, Date fechaFin);

    List<Reserva> findByCodigoReservaAndPrecioTotalBetween(String codigoReserva, double precioMinimo, double precioMaximo); // Cambiado a double

    List<Reserva> findByCodigoReservaAndIdReservaCliente_NombreContainingIgnoreCase(String codigoReserva, String nombreCliente);

    List<Reserva> findByFechainicioBetweenAndPrecioTotalBetween(Date fechaInicio, Date fechaFin, double precioMinimo, double precioMaximo); // Cambiado a double

    List<Reserva> findByFechainicioBetweenAndIdReservaCliente_NombreContainingIgnoreCase(Date fechaInicio, Date fechaFin, String nombreCliente);

    List<Reserva> findByPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(double precioMinimo, double precioMaximo, String nombreCliente); // Cambiado a double

    List<Reserva> findByCodigoReservaAndFechainicioBetweenAndPrecioTotalBetween(String codigoReserva, Date fechaInicio, Date fechaFin, double precioMinimo, double precioMaximo); // Cambiado a double

    List<Reserva> findByCodigoReservaAndFechainicioBetweenAndIdReservaCliente_NombreContainingIgnoreCase(String codigoReserva, Date fechaInicio, Date fechaFin, String nombreCliente);

    List<Reserva> findByCodigoReservaAndPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(String codigoReserva, double precioMinimo, double precioMaximo, String nombreCliente); // Cambiado a double

    List<Reserva> findByFechainicioBetweenAndPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(Date fechaInicio, Date fechaFin, double precioMinimo, double precioMaximo, String nombreCliente); // Cambiado a double

    List<Reserva> findByCodigoReservaAndFechainicioBetweenAndPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(String codigoReserva, Date fechaInicio, Date fechaFin, double precioMinimo, double precioMaximo, String nombreCliente); // Cambiado a double

    //Consultas para rangos abiertos de precios
    List<Reserva> findByPrecioTotalGreaterThanEqual(double precioMinimo);
    List<Reserva> findByPrecioTotalLessThanEqual(double precioMaximo);
    List<Reserva> findByPrecioTotalGreaterThanEqualAndIdReservaCliente_NombreContainingIgnoreCase(double precioMinimo, String nombreCliente);
    List<Reserva> findByPrecioTotalLessThanEqualAndIdReservaCliente_NombreContainingIgnoreCase(double precioMaximo, String nombreCliente);
    
    
    
    
    List<Reserva> findByIdReservaHabitacion_Id(Integer habitacionId);

    Reserva findByIdReservaHabitacion_IdAndCodigoReserva(Integer habitacionId, String codigoReserva);

    

    List<Reserva> findByIdReservaHabitacion_IdAndPrecioTotalBetween(Integer habitacionId, double precioMinimo, double precioMaximo);

    List<Reserva> findByIdReservaHabitacion_IdAndIdReservaCliente_NombreContainingIgnoreCase(Integer habitacionId, String nombreCliente);

    List<Reserva> findByIdReservaHabitacion_IdAndFechainicioBetweenAndPrecioTotalBetween(Integer habitacionId, Date fechaInicio, Date fechaFin, double precioMinimo, double precioMaximo);

    List<Reserva> findByIdReservaHabitacion_IdAndFechainicioBetweenAndIdReservaCliente_NombreContainingIgnoreCase(Integer habitacionId, Date fechaInicio, Date fechaFin, String nombreCliente);

    List<Reserva> findByIdReservaHabitacion_IdAndPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(Integer habitacionId, double precioMinimo, double precioMaximo, String nombreCliente);

    List<Reserva> findByIdReservaHabitacion_IdAndFechainicioBetweenAndPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(Integer habitacionId, Date fechaInicio, Date fechaFin, double precioMinimo, double precioMaximo, String nombreCliente);

    
    Reserva findByCodigoReservaAndIdReservaHabitacion_Id(String codigoReserva, Integer habitacionId);    
    
    
    //Los métodos para validar que no haya solapamiento de reservas cuando se va a hacer una reserva
    List<Reserva> findByIdReservaHabitacion_IdAndFechainicioGreaterThanEqualAndFechainicioLessThanEqual(Integer habitacionId, Date fechaInicio, Date fechaFin);

    List<Reserva> findByIdReservaHabitacion_IdAndFechafinGreaterThanEqualAndFechafinLessThanEqual(Integer habitacionId, Date fechaInicio, Date fechaFin);    
    
    
    //Para los historiales
    List<Reserva> findByIdReservaCliente_IdAndEstadoIn(Integer clienteId, List<String> estados);
   
    //Filtrado del historial de reservas del cliente
    List<Reserva> findByIdReservaCliente_IdAndEstadoInAndIdReservaHabitacion_IdHotelHabitacion_NombreContainingIgnoreCase(Integer clienteId, List<String> estados, String nombreHotel);

    List<Reserva> findByIdReservaCliente_IdAndEstadoInAndIdReservaHabitacion_NumeroHabitacion(Integer clienteId, List<String> estados, Integer numeroHabitacion);

    List<Reserva> findByIdReservaCliente_IdAndEstadoInAndPrecioTotalBetween(Integer clienteId, List<String> estados, Double precioMinimo, Double precioMaximo);

    List<Reserva> findByIdReservaCliente_IdAndEstadoInAndPrecioTotalBetweenOrderByPrecioTotalAsc(Integer clienteId, List<String> estados, Double precioMinimo, Double precioMaximo);

    List<Reserva> findByIdReservaCliente_IdAndEstadoInAndPrecioTotalBetweenOrderByPrecioTotalDesc(Integer clienteId, List<String> estados, Double precioMinimo, Double precioMaximo);

    List<Reserva> findByIdReservaCliente_IdAndEstadoInAndFechainicioBetween(Integer clienteId, List<String> estados, Date fechaInicio, Date fechaFin);

    List<Reserva> findByIdReservaCliente_IdAndEstadoInAndFechainicioBetweenOrderByFechainicioAsc(Integer clienteId, List<String> estados, Date fechaInicio, Date fechaFin);

    List<Reserva> findByIdReservaCliente_IdAndEstadoInAndFechainicioBetweenOrderByFechainicioDesc(Integer clienteId, List<String> estados, Date fechaInicio, Date fechaFin);
    
    List<Reserva> findByIdReservaCliente_IdAndEstadoInAndCodigoReserva(Integer clienteId, List<String> estados, String codigoReserva);    
}
