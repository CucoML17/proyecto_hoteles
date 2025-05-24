package carlos.mejia.proyectohoteles.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Reserva;
import carlos.mejia.proyectohoteles.repository.ReservaRepository;
import jakarta.transaction.Transactional;

@Service
public class ReservaServiceJpa implements ReservaService {

	@Autowired
	ReservaRepository reservaRepository;
	
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;

	@Override
	public int guardarReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		Reserva reservatmp = reservaRepository.save(reserva);
        //retorna el id generado automático, esto será útil para las relaciones de DETALLEVENTA.
        return reservatmp.getId();
	}

	@Override
	public List<Reserva> todasReservas() {
		// TODO Auto-generated method stub
		return reservaRepository.findAll();
	}

	@Override
	public List<Reserva> todasReservasFecha(Integer idHabitacion, Date fechaIni, Date fechaFin) {
		return reservaRepository
                .findByIdReservaHabitacion_IdAndFechainicioLessThanAndFechafinGreaterThan(idHabitacion, fechaIni, fechaFin);
	}

	@Override
	public Reserva buscarReservaId(Integer idReserva) {
		Optional<Reserva> optional = reservaRepository.findById(idReserva);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

	@Override
	public List<Reserva> todasReservasFechaMante(Integer idHabitacion, Date fecha) {
		// TODO Auto-generated method stub
		return reservaRepository
	            .findByIdReservaHabitacion_IdAndFechainicioLessThanEqualAndFechafinGreaterThanEqual(idHabitacion, fecha, fecha);
	}

	@Override
    public boolean tieneReservasEnFecha(Integer idHabitacion, Date fecha) {
	    List<Reserva> reservas = reservaRepository
	            .findByIdReservaHabitacion_IdAndFechainicioLessThanEqualAndFechafinGreaterThanEqual(idHabitacion, fecha, fecha);
	    return !reservas.isEmpty();
    }
	
	@Override
    public Reserva buscarPorCodigoReserva(String codigoReserva) {
        return reservaRepository.findByCodigoReserva(codigoReserva);
    }	
	
    @Override
    public List<Reserva> filtrarReservas(String codigoReserva, String fechaInicioStr, String fechaFinStr, Double precioMinimo, Double precioMaximo, String nombreCliente) {
        if (codigoReserva != null && !codigoReserva.isEmpty()) {
            Reserva reserva = reservaRepository.findByCodigoReserva(codigoReserva);
            return reserva != null ? List.of(reserva) : List.of();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;
        Date fechaFin = null;

        try {
            if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
                fechaInicio = sdf.parse(fechaInicioStr);
            }
            if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
                fechaFin = sdf.parse(fechaFinStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return List.of(); // Devolver lista vacía en caso de error de formato de fecha
        }

        if ((fechaInicio != null && fechaFin != null) && fechaInicio.after(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
        if (precioMinimo != null && precioMaximo != null && precioMinimo > precioMaximo) {
            throw new IllegalArgumentException("El precio mínimo no puede ser mayor que el precio máximo.");
        }

        if (fechaInicio != null && fechaFin != null && precioMinimo != null && precioMaximo != null && nombreCliente != null && !nombreCliente.isEmpty()) {
            return reservaRepository.findByFechainicioBetweenAndPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(fechaInicio, fechaFin, precioMinimo, precioMaximo, nombreCliente);
        } else if (fechaInicio != null && fechaFin != null && precioMinimo != null && precioMaximo != null) {
            return reservaRepository.findByFechainicioBetweenAndPrecioTotalBetween(fechaInicio, fechaFin, precioMinimo, precioMaximo);
        } else if (fechaInicio != null && fechaFin != null && nombreCliente != null && !nombreCliente.isEmpty()) {
            return reservaRepository.findByFechainicioBetweenAndIdReservaCliente_NombreContainingIgnoreCase(fechaInicio, fechaFin, nombreCliente);
        } else if (precioMinimo != null && precioMaximo != null && nombreCliente != null && !nombreCliente.isEmpty()) {
            return reservaRepository.findByPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(precioMinimo, precioMaximo, nombreCliente);
        } else if (fechaInicio != null && fechaFin != null) {
            return reservaRepository.findByFechainicioBetween(fechaInicio, fechaFin);
        } else if (precioMinimo != null && precioMaximo != null) {
            return reservaRepository.findByPrecioTotalBetween(precioMinimo, precioMaximo);
        } else if (nombreCliente != null && !nombreCliente.isEmpty()) {
            return reservaRepository.findByIdReservaCliente_NombreContainingIgnoreCase(nombreCliente);
        } else {
            return reservaRepository.findAll();
        }
    }
    
    @Override
    public List<Reserva> buscarReservasPorHabitacion(Integer habitacionId) {
        return reservaRepository.findByIdReservaHabitacion_Id(habitacionId);
    }

    @Override
    public Reserva buscarReservaPorCodigoReservaYHabitacion(String codigoReserva, Integer habitacionId) {
        return reservaRepository.findByCodigoReservaAndIdReservaHabitacion_Id(codigoReserva, habitacionId);
    }

    @Override
    public List<Reserva> filtrarReservasPorHabitacion(
            Integer habitacionId,
            String filtrarPorCodigoReserva,
            String codigoReserva,
            String fechaInicioStr,
            String fechaFinStr,
            Double precioMinimo,
            Double precioMaximo,
            String filtrarPorNombreCliente,
            String nombreCliente
    ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;
        Date fechaFin = null;

        try {
            if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
                fechaInicio = sdf.parse(fechaInicioStr);
            }
            if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
                fechaFin = sdf.parse(fechaFinStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return reservaRepository.findByIdReservaHabitacion_Id(habitacionId);
        }

        if (filtrarPorCodigoReserva != null && filtrarPorCodigoReserva.equals("on") && codigoReserva != null && !codigoReserva.isEmpty()) {
            Reserva reserva = reservaRepository.findByIdReservaHabitacion_IdAndCodigoReserva(habitacionId, codigoReserva);
            return reserva != null ? List.of(reserva) : List.of();
        } else if (fechaInicio != null && fechaFin != null && precioMinimo != null && precioMaximo != null && filtrarPorNombreCliente != null && filtrarPorNombreCliente.equals("on") && nombreCliente != null && !nombreCliente.isEmpty()) {
            return reservaRepository.findByIdReservaHabitacion_IdAndFechainicioBetweenAndPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(habitacionId, fechaInicio, fechaFin, precioMinimo, precioMaximo, nombreCliente);
        } else if (fechaInicio != null && fechaFin != null && precioMinimo != null && precioMaximo != null) {
            return reservaRepository.findByIdReservaHabitacion_IdAndFechainicioBetweenAndPrecioTotalBetween(habitacionId, fechaInicio, fechaFin, precioMinimo, precioMaximo);
        } else if (fechaInicio != null && fechaFin != null && filtrarPorNombreCliente != null && filtrarPorNombreCliente.equals("on") && nombreCliente != null && !nombreCliente.isEmpty()) {
            return reservaRepository.findByIdReservaHabitacion_IdAndFechainicioBetweenAndIdReservaCliente_NombreContainingIgnoreCase(habitacionId, fechaInicio, fechaFin, nombreCliente);
        } else if (precioMinimo != null && precioMaximo != null && filtrarPorNombreCliente != null && filtrarPorNombreCliente.equals("on") && nombreCliente != null && !nombreCliente.isEmpty()) {
            return reservaRepository.findByIdReservaHabitacion_IdAndPrecioTotalBetweenAndIdReservaCliente_NombreContainingIgnoreCase(habitacionId, precioMinimo, precioMaximo, nombreCliente);
        } else if (fechaInicio != null && fechaFin != null) {
            return reservaRepository.findByIdReservaHabitacion_IdAndFechainicioBetween(habitacionId, fechaInicio, fechaFin);
        } else if (precioMinimo != null && precioMaximo != null) {
            return reservaRepository.findByIdReservaHabitacion_IdAndPrecioTotalBetween(habitacionId, precioMinimo, precioMaximo);
        } else if (filtrarPorNombreCliente != null && filtrarPorNombreCliente.equals("on") && nombreCliente != null && !nombreCliente.isEmpty()) {
            return reservaRepository.findByIdReservaHabitacion_IdAndIdReservaCliente_NombreContainingIgnoreCase(habitacionId, nombreCliente);
        } else {
            return reservaRepository.findByIdReservaHabitacion_Id(habitacionId);
        }
    }
    
    //Para las gráficas
    @Override
    @Transactional
    public Map<String, Long> contarReservasPorEstado(Integer hotelId) {
        List<Reserva> todasLasReservas = reservaRepository.findAll();
        List<Reserva> reservasFiltradas = new ArrayList<>();

        if (hotelId != null) {
            for (Reserva reserva : todasLasReservas) {
                if (reserva.getIdReservaHabitacion() != null &&
                    reserva.getIdReservaHabitacion().getIdHotelHabitacion() != null &&
                    reserva.getIdReservaHabitacion().getIdHotelHabitacion().getId().equals(hotelId)) {
                    reservasFiltradas.add(reserva);
                }
            }
        } else {
            reservasFiltradas = todasLasReservas;
        }

        Map<String, Long> conteoPorEstado = new HashMap<>();
        conteoPorEstado.put("Reservado", 0L);
        conteoPorEstado.put("Proceso", 0L);
        conteoPorEstado.put("Terminado", 0L);
        conteoPorEstado.put("Cancelado", 0L);

        for (Reserva reserva : reservasFiltradas) {
            String estado = reserva.getEstado();
            conteoPorEstado.put(estado, conteoPorEstado.getOrDefault(estado, 0L) + 1);
        }

        return conteoPorEstado;
    }
    
    @Override
    @Transactional
    public Map<String, Long> contarReservasPorFechaInicio(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Reserva> reservasEnRango = reservaRepository.findByFechainicioBetween(
                java.sql.Date.valueOf(fechaInicio), java.sql.Date.valueOf(fechaFin)
        );

        Map<String, Long> conteoPorDia = reservasEnRango.stream()
                .collect(Collectors.groupingBy(
                        reserva -> {
                            // Convertir java.util.Date a Instant y luego a LocalDate
                            return reserva.getFechainicio().toInstant()
                                    .atZone(java.time.ZoneId.systemDefault())
                                    .toLocalDate()
                                    .format(dateFormatter);
                        },
                        Collectors.counting()
                ));

        // Asegurar que todos los días en el rango tengan una entrada (conteo 0 si no hay reservas)
        LocalDate current = fechaInicio;
        while (!current.isAfter(fechaFin)) {
            conteoPorDia.putIfAbsent(dateFormatter.format(current), 0L);
            current = current.plusDays(1);
        }

        return conteoPorDia.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        () -> new HashMap<String, Long>()
                ));
    }
    
    
    @Override
    public List<Reserva> findReservasSolapadas(Integer habitacionId, Date fechaInicio, Date fechaFin) {
        List<Reserva> reservasSolapadas = reservaRepository.findByIdReservaHabitacion_IdAndFechainicioLessThanEqualAndFechafinGreaterThanEqual(
                habitacionId, fechaFin, fechaInicio);
        reservasSolapadas.addAll(reservaRepository.findByIdReservaHabitacion_IdAndFechainicioGreaterThanEqualAndFechainicioLessThanEqual(
                habitacionId, fechaInicio, fechaFin));
        reservasSolapadas.addAll(reservaRepository.findByIdReservaHabitacion_IdAndFechafinGreaterThanEqualAndFechafinLessThanEqual(
                habitacionId, fechaInicio, fechaFin));
        
        reservasSolapadas.removeIf(reserva -> reserva.getEstado().equalsIgnoreCase("Terminado") || reserva.getEstado().equalsIgnoreCase("Cancelado"));
        
        return reservasSolapadas;
    }
    
    
    //Para los historiales de reserva:
    @Override
    public List<Reserva> obtenerHistorialReservasCliente(Integer clienteId) {
        return reservaRepository.findByIdReservaCliente_IdAndEstadoIn(clienteId, Arrays.asList("Terminado", "Cancelado"));
    }
    
    @Override
    public List<Reserva> obtenerReservasPendientesCliente(Integer clienteId) {
        return reservaRepository.findByIdReservaCliente_IdAndEstadoIn(clienteId, Arrays.asList("Reservado", "Proceso"));
    }   
    
    
    //Filtrado de reservas
    @Override
    public List<Reserva> obtenerHistorialReservasClientePorEstados(Integer clienteId, List<String> estados) {
        return reservaRepository.findByIdReservaCliente_IdAndEstadoIn(clienteId, estados);
    }

    @Override
    public List<Reserva> filtrarHistorialReservasPorHotel(Integer clienteId, List<String> estados, String nombreHotel) {
        return reservaRepository.findByIdReservaCliente_IdAndEstadoInAndIdReservaHabitacion_IdHotelHabitacion_NombreContainingIgnoreCase(clienteId, estados, nombreHotel);
    }

    @Override
    public List<Reserva> filtrarHistorialReservasPorHabitacion(Integer clienteId, List<String> estados, Integer numeroHabitacion) {
        return reservaRepository.findByIdReservaCliente_IdAndEstadoInAndIdReservaHabitacion_NumeroHabitacion(clienteId, estados, numeroHabitacion);
    }

    @Override
    public List<Reserva> filtrarHistorialReservasPorPrecio(Integer clienteId, List<String> estados, Double precioMinimo, Double precioMaximo, String orden) {
        if ("asc".equalsIgnoreCase(orden)) {
            return reservaRepository.findByIdReservaCliente_IdAndEstadoInAndPrecioTotalBetweenOrderByPrecioTotalAsc(clienteId, estados, precioMinimo, precioMaximo);
        } else if ("desc".equalsIgnoreCase(orden)) {
            return reservaRepository.findByIdReservaCliente_IdAndEstadoInAndPrecioTotalBetweenOrderByPrecioTotalDesc(clienteId, estados, precioMinimo, precioMaximo);
        } else {
            return reservaRepository.findByIdReservaCliente_IdAndEstadoInAndPrecioTotalBetween(clienteId, estados, precioMinimo, precioMaximo);
        }
    }

    @Override
    public List<Reserva> filtrarHistorialReservasPorFecha(Integer clienteId, List<String> estados, Date fechaInicio, Date fechaFin, String orden) {
        if ("asc".equalsIgnoreCase(orden)) {
            return reservaRepository.findByIdReservaCliente_IdAndEstadoInAndFechainicioBetweenOrderByFechainicioAsc(clienteId, estados, fechaInicio, fechaFin);
        } else if ("desc".equalsIgnoreCase(orden)) {
            return reservaRepository.findByIdReservaCliente_IdAndEstadoInAndFechainicioBetweenOrderByFechainicioDesc(clienteId, estados, fechaInicio, fechaFin);
        } else {
            return reservaRepository.findByIdReservaCliente_IdAndEstadoInAndFechainicioBetween(clienteId, estados, fechaInicio, fechaFin);
        }
    }
    
    @Override
    public List<Reserva> filtrarHistorialReservasPorCodigo(Integer clienteId, List<String> estados, String codigoReserva) {
        return reservaRepository.findByIdReservaCliente_IdAndEstadoInAndCodigoReserva(clienteId, estados, codigoReserva);
    }
    
}
