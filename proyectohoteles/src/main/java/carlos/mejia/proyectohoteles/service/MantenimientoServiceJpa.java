package carlos.mejia.proyectohoteles.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Mantenimiento;
import carlos.mejia.proyectohoteles.repository.MantenimientoRepository;
import jakarta.transaction.Transactional;

@Service
public class MantenimientoServiceJpa implements MantenimientoService{

	
	@Autowired 
	MantenimientoRepository mantenimientoRepository;
	
	@Override
	public int guardarMantenimiento(Mantenimiento mantenimiento) {
		// TODO Auto-generated method stub
		Mantenimiento mantenimientotmp = mantenimientoRepository.save(mantenimiento);
        //retorna el id generado automático, esto será útil para las relaciones de DETALLEVENTA.
        return mantenimientotmp.getId();
	}

	@Override
	public List<Mantenimiento> todosMantes() {
		// TODO Auto-generated method stub
		return mantenimientoRepository.findAll();
	}

	@Override
	public Mantenimiento buscarMantenimientoID(Integer idMante) {
		Optional<Mantenimiento> optional = mantenimientoRepository.findById(idMante);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}
	
	@Override
    public List<Mantenimiento> filtrarMantenimientos(Integer idHotel, Integer numeroHabitacion, String fechaInicioStr, String fechaFinStr) {
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
            return List.of(); // Devolver lista vacía en caso de error de formato
        }

        if (idHotel != null && numeroHabitacion != null && fechaInicio != null && fechaFin != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndIdMantenimientoHabitacion_NumeroHabitacionAndFechaBetween(idHotel, numeroHabitacion, fechaInicio, fechaFin);
        } else if (idHotel != null && numeroHabitacion != null && fechaInicio != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndIdMantenimientoHabitacion_NumeroHabitacionAndFechaGreaterThanEqual(idHotel, numeroHabitacion, fechaInicio);
        } else if (idHotel != null && numeroHabitacion != null && fechaFin != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndIdMantenimientoHabitacion_NumeroHabitacionAndFechaLessThanEqual(idHotel, numeroHabitacion, fechaFin);
        } else if (idHotel != null && numeroHabitacion != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndIdMantenimientoHabitacion_NumeroHabitacion(idHotel, numeroHabitacion);
        } else if (idHotel != null && fechaInicio != null && fechaFin != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndFechaBetween(idHotel, fechaInicio, fechaFin);
        } else if (numeroHabitacion != null && fechaInicio != null && fechaFin != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_NumeroHabitacionAndFechaBetween(numeroHabitacion, fechaInicio, fechaFin);
        } else if (idHotel != null && fechaInicio != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndFechaGreaterThanEqual(idHotel, fechaInicio);
        } else if (idHotel != null && fechaFin != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_IdHotelHabitacion_IdAndFechaLessThanEqual(idHotel, fechaFin);
        } else if (numeroHabitacion != null && fechaInicio != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_NumeroHabitacionAndFechaGreaterThanEqual(numeroHabitacion, fechaInicio);
        } else if (numeroHabitacion != null && fechaFin != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_NumeroHabitacionAndFechaLessThanEqual(numeroHabitacion, fechaFin);
        } else if (fechaInicio != null && fechaFin != null) {
            return mantenimientoRepository.findByFechaBetween(fechaInicio, fechaFin);
        } else if (idHotel != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_IdHotelHabitacion_Id(idHotel);
        } else if (numeroHabitacion != null) {
            return mantenimientoRepository.findByidMantenimientoHabitacion_NumeroHabitacion(numeroHabitacion);
        } else if (fechaInicio != null) {
            return mantenimientoRepository.findByFechaGreaterThanEqual(fechaInicio);
        } else if (fechaFin != null) {
            return mantenimientoRepository.findByFechaLessThanEqual(fechaFin);
        } else {
            return mantenimientoRepository.findAll();
        }
    }

	
    @Override
    @Transactional
    public Map<String, Long> contarMantenimientosPorEstado(Integer hotelId) {
        List<Mantenimiento> todosLosMantenimientos = mantenimientoRepository.findAll();
        List<Mantenimiento> mantenimientosFiltrados = new ArrayList<>();

        if (hotelId != null) {
            for (Mantenimiento mantenimiento : todosLosMantenimientos) {
                if (mantenimiento.getIdMantenimientoHabitacion() != null &&
                    mantenimiento.getIdMantenimientoHabitacion().getIdHotelHabitacion() != null &&
                    mantenimiento.getIdMantenimientoHabitacion().getIdHotelHabitacion().getId().equals(hotelId)) {
                    mantenimientosFiltrados.add(mantenimiento);
                }
            }
        } else {
            mantenimientosFiltrados = todosLosMantenimientos;
        }

        Map<String, Long> conteoPorEstado = new HashMap<>();
        conteoPorEstado.put("Programada", 0L);
        conteoPorEstado.put("Proceso", 0L);
        conteoPorEstado.put("Terminada", 0L);
        conteoPorEstado.put("Cancelada", 0L);

        for (Mantenimiento mantenimiento : mantenimientosFiltrados) {
            String estado = mantenimiento.getEstado();
            conteoPorEstado.put(estado, conteoPorEstado.getOrDefault(estado, 0L) + 1);
        }

        return conteoPorEstado;
    }	
    
    @Override
    public List<Mantenimiento> findMantenimientosSolapados(Integer habitacionId, Date fechaInicio, Date fechaFin) {
        return mantenimientoRepository.findByidMantenimientoHabitacion_IdAndFechaBetween(habitacionId, fechaInicio, fechaFin);
    }
    
    @Override
    public List<Mantenimiento> findMantenimientoEnFecha(Integer habitacionId, Date fecha) {
        return mantenimientoRepository.findByidMantenimientoHabitacion_IdAndFecha(habitacionId, fecha);
    }    

}
