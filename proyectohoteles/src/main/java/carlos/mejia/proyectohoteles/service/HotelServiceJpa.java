package carlos.mejia.proyectohoteles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.repository.HotelRepository;




@Service
public class HotelServiceJpa implements HotelService {

	@Autowired
	HotelRepository hotelRepo;	
	
	@Override
	public int guardarHotel(Hotel hotel) {
		// TODO Auto-generated method stub
		Hotel hoteltmp = hotelRepo.save(hotel);
        //retorna el id generado automático, esto será útil para las relaciones de DETALLEVENTA.
        return hoteltmp.getId();
	}

	@Override
	public List<Hotel> todosHoteles() {
		// TODO Auto-generated method stub
		return hotelRepo.findAll();
	}

	@Override
	public List<Hotel> hotelesActivos() {
		// TODO Auto-generated method stub
		return hotelRepo.findByEstado(1);
	}

	@Override
	public Hotel buscarHotelID(Integer idHotel) {
		Optional<Hotel> optional = hotelRepo.findById(idHotel);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}


	@Override
	public List<Hotel> filtrarHotelPor(String filtro, String orden, String valor) {
        Sort sort = null;
        if (orden != null && filtro != null) {
            sort = Sort.by(filtro);
            if (orden.equalsIgnoreCase("desc")) {
                sort = sort.descending();
            }
        }
        
        if (filtro != null && valor != null && !valor.isEmpty()) {
            switch (filtro.toLowerCase()) {
                case "nombre":
                    return hotelRepo.findByNombreContainingIgnoreCase(valor, sort);
                case "direccion":
                    return hotelRepo.findByDireccionContainingIgnoreCase(valor, sort);
                case "estado":
                    int estado;
                    
                    if(valor.equalsIgnoreCase("activo")) {
                    	estado = 1;
                    }else {
                    	
                    	estado=0;
                    }
                    sort = Sort.by("id");
                    if (orden.equalsIgnoreCase("desc")) {
                        sort = sort.descending();
                    }
                    return hotelRepo.findByEstado(estado);
                    
                default:
                    return hotelRepo.findAll(sort); // Si el filtro no coincide, devolvemos todo con el orden (si existe)
            }
        } else {
            return hotelRepo.findAll(sort); // Si no hay filtro, devolvemos todo con el orden (si existe)
        }        
        
        
        
        
        
		
	}

	@Override
	public List<Hotel> filtrarHotelPrecioPromedio(double min, double max, String orden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hotel> filtrarHotelPorEstado(String orden, String estado) {
		Sort sort = null;
        int estadoNum;
        
        if(estado.equalsIgnoreCase("activo")) {
        	estadoNum = 1;
        }else {
        	
        	estadoNum=0;
        }

        return hotelRepo.findByEstado(estadoNum);		
		
	}

	@Override
	public List<Hotel> filtrarHotelPorPrecioPromedio(double precioMin, double precioMax, String orden) {
        Sort sort = null;
        if (orden != null) {
            sort = Sort.by("precioPromedio");
            if (orden.equalsIgnoreCase("desc")) {
                sort = sort.descending();
            }
        }
        
        return hotelRepo.findByPrecioPromedioBetween(precioMin, precioMax, sort);
        
	}
	
	
    @Override
    public Hotel buscarHotelMasEconomico() {
        List<Hotel> hotelesOrdenados = hotelRepo.findAll(Sort.by(Sort.Direction.ASC, "precioPromedio"));
        return hotelesOrdenados.isEmpty() ? null : hotelesOrdenados.get(0);
    }

    @Override
    public List<Hotel> buscarOtrosHotelesEconomicos() {
        List<Hotel> hotelesOrdenados = hotelRepo.findAll(Sort.by(Sort.Direction.ASC, "precioPromedio"));
        if (hotelesOrdenados.size() <= 1) {
            return List.of(); // No hay suficientes hoteles
        }
        return hotelesOrdenados.subList(1, Math.min(4, hotelesOrdenados.size())); // Obtener los siguientes 3 o menos
    }

	@Override
	public List<Hotel> todosHotelesEstado(int estado) {
		
		return hotelRepo.findByEstado(1);
	}	
    @Override
    public List<Hotel> filtrarHotelesActivosPorPrecioPromedio(double precioMin, double precioMax, String orden) {
        Sort sort = construirSort("precioPromedio", orden);
        return hotelRepo.findByEstadoAndPrecioPromedioBetween(1, precioMin, precioMax, sort);
    }

    private Sort construirSort(String filtro, String orden) {
        Sort sort = null;
        if (orden != null && filtro != null) {
            sort = Sort.by(filtro);
            if (orden.equalsIgnoreCase("desc")) {
                sort = sort.descending();
            }
        } else if (filtro != null) {
            sort = Sort.by(filtro); // Aplicar ordenamiento por el filtro aunque el orden sea null
        }
        return sort;
    }
	

}
