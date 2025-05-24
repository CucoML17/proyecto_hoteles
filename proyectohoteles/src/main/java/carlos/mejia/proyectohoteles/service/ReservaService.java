package carlos.mejia.proyectohoteles.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import carlos.mejia.proyectohoteles.model.Reserva;

public interface ReservaService {
	int guardarReserva(Reserva reserva);
	
	List<Reserva> todasReservas();
	
	List<Reserva> todasReservasFecha(Integer idHabitacion, Date fechaIni, Date fechaFin);
	
	List<Reserva> todasReservasFechaMante(Integer idHabitacion, Date fecha);
	
	Reserva buscarReservaId(Integer idReserva);
	
	boolean tieneReservasEnFecha(Integer idHabitacion, Date fecha);
	
	Reserva buscarPorCodigoReserva(String codigoReserva);
	
	List<Reserva> filtrarReservas(String codigoReserva, String fechaInicioStr, String fechaFinStr, Double precioMinimo, Double precioMaximo, String nombreCliente);
	
	
    List<Reserva> buscarReservasPorHabitacion(Integer habitacionId);
    Reserva buscarReservaPorCodigoReservaYHabitacion(String codigoReserva, Integer habitacionId);
    List<Reserva> filtrarReservasPorHabitacion(
            Integer habitacionId,
            String filtrarPorCodigoReserva,
            String codigoReserva,
            String fechaInicioStr,
            String fechaFinStr,
            Double precioMinimo,
            Double precioMaximo,
            String filtrarPorNombreCliente,
            String nombreCliente
    );
    //Para las gráficas
    
    
    Map<String, Long> contarReservasPorEstado(Integer hotelId); 
    
    //Para las fechas
    Map<String, Long> contarReservasPorFechaInicio(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<Reserva> findReservasSolapadas(Integer habitacionId, Date fechaInicio, Date fechaFin);
    
    //Para los historiales
    List<Reserva> obtenerHistorialReservasCliente(Integer clienteId); 
    
    List<Reserva> obtenerReservasPendientesCliente(Integer clienteId);    
    
    //Filtrado de las reservas
    
    List<Reserva> obtenerHistorialReservasClientePorEstados(Integer clienteId, List<String> estados);

    List<Reserva> filtrarHistorialReservasPorHotel(Integer clienteId, List<String> estados, String nombreHotel);

    List<Reserva> filtrarHistorialReservasPorHabitacion(Integer clienteId, List<String> estados, Integer numeroHabitacion);

    List<Reserva> filtrarHistorialReservasPorPrecio(Integer clienteId, List<String> estados, Double precioMinimo, Double precioMaximo, String orden);

    List<Reserva> filtrarHistorialReservasPorFecha(Integer clienteId, List<String> estados, Date fechaInicio, Date fechaFin, String orden);
    
    List<Reserva> filtrarHistorialReservasPorCodigo(Integer clienteId, List<String> estados, String codigoReserva);

}
