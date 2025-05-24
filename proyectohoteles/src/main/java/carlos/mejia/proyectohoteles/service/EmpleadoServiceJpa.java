package carlos.mejia.proyectohoteles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Empleado;
import carlos.mejia.proyectohoteles.repository.EmpleadoRepository;





@Service
public class EmpleadoServiceJpa implements EmpleadoService {
	
	@Autowired
	EmpleadoRepository empleadoRepository;

	@Override
	public int guardarEmpleado(Empleado empleado) {
		Empleado empleadoTmp = empleadoRepository.save(empleado);
       
        return empleadoTmp.getId();
	}

	@Override
	public List<Empleado> todosEmpleados() {
		// TODO Auto-generated method stub
		return empleadoRepository.findAll();
	}

	@Override
	public List<Empleado> todosEmpleadosActivos() {
		// TODO Auto-generated method stub
		return empleadoRepository.findByEstado(1);
	}

	@Override
	public Empleado buscarEmpleadoID(Integer idEmpleado) {
		Optional<Empleado> optional = empleadoRepository.findById(idEmpleado);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

    public List<Empleado> filtrarEmpleados(Integer idHotel, String campoFiltro, String valorFiltro, Integer rolId) {
        Sort sort = Sort.unsorted();

        if (idHotel != null && campoFiltro != null && !campoFiltro.isEmpty() && valorFiltro != null && !valorFiltro.isEmpty() && rolId != null) {
            switch (campoFiltro.toLowerCase()) {
                case "rfc":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndRfcContainingIgnoreCaseAndRoles_Id(idHotel, valorFiltro, rolId, sort);
                case "nombre":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndNombreContainingIgnoreCaseAndRoles_Id(idHotel, valorFiltro, rolId, sort);
                case "apellido":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndApellidoContainingIgnoreCaseAndRoles_Id(idHotel, valorFiltro, rolId, sort);
                case "telefono":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndTelefonoContainingAndRoles_Id(idHotel, valorFiltro, rolId, sort);
                default:
                    return empleadoRepository.findAll(); // Manejar caso no reconocido
            }
        } else if (idHotel != null && campoFiltro != null && !campoFiltro.isEmpty() && valorFiltro != null && !valorFiltro.isEmpty()) {
            switch (campoFiltro.toLowerCase()) {
                case "rfc":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndRfcContainingIgnoreCase(idHotel, valorFiltro, sort);
                case "nombre":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndNombreContainingIgnoreCase(idHotel, valorFiltro, sort);
                case "apellido":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndApellidoContainingIgnoreCase(idHotel, valorFiltro, sort);
                case "telefono":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndTelefonoContaining(idHotel, valorFiltro, sort);
                default:
                    return empleadoRepository.findByIdHotelEmpleado_Id(idHotel, sort);
            }
        } else if (idHotel != null && rolId != null) {
            return empleadoRepository.findByIdHotelEmpleado_IdAndRoles_Id(idHotel, rolId, sort);
        } else if (campoFiltro != null && !campoFiltro.isEmpty() && valorFiltro != null && !valorFiltro.isEmpty() && rolId != null) {
            switch (campoFiltro.toLowerCase()) {
                case "rfc":
                    return empleadoRepository.findByRfcContainingIgnoreCaseAndRoles_Id(valorFiltro, rolId, sort);
                case "nombre":
                    return empleadoRepository.findByNombreContainingIgnoreCaseAndRoles_Id(valorFiltro, rolId, sort);
                case "apellido":
                    return empleadoRepository.findByApellidoContainingIgnoreCaseAndRoles_Id(valorFiltro, rolId, sort);
                case "telefono":
                    return empleadoRepository.findByTelefonoContainingAndRoles_Id(valorFiltro, rolId, sort);
                default:
                    return empleadoRepository.findByRoles_Id(rolId, sort);
            }
        } else if (idHotel != null) {
            return empleadoRepository.findByIdHotelEmpleado_Id(idHotel, sort);
        } else if (campoFiltro != null && !campoFiltro.isEmpty() && valorFiltro != null && !valorFiltro.isEmpty()) {
            switch (campoFiltro.toLowerCase()) {
                case "rfc":
                    return empleadoRepository.findByRfcContainingIgnoreCase(valorFiltro, sort);
                case "nombre":
                    return empleadoRepository.findByNombreContainingIgnoreCase(valorFiltro, sort);
                case "apellido":
                    return empleadoRepository.findByApellidoContainingIgnoreCase(valorFiltro, sort);
                case "telefono":
                    return empleadoRepository.findByTelefonoContaining(valorFiltro, sort);
                default:
                    return empleadoRepository.findAll(); // Manejar caso no reconocido
            }
        } else if (rolId != null) {
            return empleadoRepository.findByRoles_Id(rolId, sort);
        } else {
            return empleadoRepository.findAll();
        }
    }

    @Override
    public List<Empleado> filtrarEmpleadosEstado(Integer idHotel, String campoFiltro, String valorFiltro, Integer rolId, Integer estado) {
        Sort sort = Sort.unsorted();

        if (idHotel != null && campoFiltro != null && !campoFiltro.isEmpty() && valorFiltro != null && !valorFiltro.isEmpty() && rolId != null && estado != null) {
            switch (campoFiltro.toLowerCase()) {
                case "rfc":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndRfcContainingIgnoreCaseAndRoles_IdAndEstado(idHotel, valorFiltro, rolId, estado, sort);
                case "nombre":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndNombreContainingIgnoreCaseAndRoles_IdAndEstado(idHotel, valorFiltro, rolId, estado, sort);
                case "apellido":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndApellidoContainingIgnoreCaseAndRoles_IdAndEstado(idHotel, valorFiltro, rolId, estado, sort);
                case "telefono":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndTelefonoContainingAndRoles_IdAndEstado(idHotel, valorFiltro, rolId, estado, sort);
                default:
                    return empleadoRepository.findAll(); // Manejar caso no reconocido
            }
        } else if (idHotel != null && campoFiltro != null && !campoFiltro.isEmpty() && valorFiltro != null && !valorFiltro.isEmpty() && estado != null) {
            switch (campoFiltro.toLowerCase()) {
                case "rfc":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndRfcContainingIgnoreCaseAndEstado(idHotel, valorFiltro, estado, sort);
                case "nombre":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndNombreContainingIgnoreCaseAndEstado(idHotel, valorFiltro, estado, sort);
                case "apellido":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndApellidoContainingIgnoreCaseAndEstado(idHotel, valorFiltro, estado, sort);
                case "telefono":
                    return empleadoRepository.findByIdHotelEmpleado_IdAndTelefonoContainingAndEstado(idHotel, valorFiltro, estado, sort);
                default:
                    return empleadoRepository.findByIdHotelEmpleado_IdAndEstado(idHotel, estado, sort);
            }
        } else if (idHotel != null && rolId != null && estado != null) {
            return empleadoRepository.findByIdHotelEmpleado_IdAndRoles_IdAndEstado(idHotel, rolId, estado, sort);
        } else if (campoFiltro != null && !campoFiltro.isEmpty() && valorFiltro != null && !valorFiltro.isEmpty() && rolId != null && estado != null) {
            switch (campoFiltro.toLowerCase()) {
                case "rfc":
                    return empleadoRepository.findByRfcContainingIgnoreCaseAndRoles_IdAndEstado(valorFiltro, rolId, estado, sort);
                case "nombre":
                    return empleadoRepository.findByNombreContainingIgnoreCaseAndRoles_IdAndEstado(valorFiltro, rolId, estado, sort);
                case "apellido":
                    return empleadoRepository.findByApellidoContainingIgnoreCaseAndRoles_IdAndEstado(valorFiltro, rolId, estado, sort);
                case "telefono":
                    return empleadoRepository.findByTelefonoContainingAndRoles_IdAndEstado(valorFiltro, rolId, estado, sort);
                default:
                    return empleadoRepository.findByRoles_IdAndEstado(rolId, estado, sort);
            }
        } else if (idHotel != null && estado != null) {
        	System.out.println("Dolor");
            return empleadoRepository.findByIdHotelEmpleado_IdAndEstado(idHotel, estado, sort);
        } else if (campoFiltro != null && !campoFiltro.isEmpty() && valorFiltro != null && !valorFiltro.isEmpty() && estado != null) {
            switch (campoFiltro.toLowerCase()) {
                case "rfc":
                    return empleadoRepository.findByRfcContainingIgnoreCaseAndEstado(valorFiltro, estado, sort);
                case "nombre":
                    return empleadoRepository.findByNombreContainingIgnoreCaseAndEstado(valorFiltro, estado, sort);
                case "apellido":
                    return empleadoRepository.findByApellidoContainingIgnoreCaseAndEstado(valorFiltro, estado, sort);
                case "telefono":
                    return empleadoRepository.findByTelefonoContainingAndEstado(valorFiltro, estado, sort);
                default:
                    return empleadoRepository.findAll(); // Manejar caso no reconocido
            }
        } else if (rolId != null && estado != null) {
            return empleadoRepository.findByRoles_IdAndEstado(rolId, estado, sort);
        } else if (estado != null) {
            // Caso de filtro solo por estado
        	return empleadoRepository.findAll();
        } else {
            return empleadoRepository.findAll();
        }
    }

}
