package carlos.mejia.proyectohoteles.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import carlos.mejia.proyectohoteles.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

	List<Empleado> findByEstado(int estado);

	List<Empleado> findByIdHotelEmpleado_Id(Integer idHotel, Sort sort);

	List<Empleado> findByRfcContainingIgnoreCase(String rfc, Sort sort);

	List<Empleado> findByNombreContainingIgnoreCase(String nombre, Sort sort);

	List<Empleado> findByApellidoContainingIgnoreCase(String apellido, Sort sort);

	List<Empleado> findByTelefonoContaining(String telefono, Sort sort);

	List<Empleado> findByRoles_Id(Integer rolId, Sort sort);

	List<Empleado> findByIdHotelEmpleado_IdAndRfcContainingIgnoreCase(Integer idHotel, String rfc, Sort sort);

	List<Empleado> findByIdHotelEmpleado_IdAndNombreContainingIgnoreCase(Integer idHotel, String nombre, Sort sort);

	List<Empleado> findByIdHotelEmpleado_IdAndApellidoContainingIgnoreCase(Integer idHotel, String apellido, Sort sort);

	List<Empleado> findByIdHotelEmpleado_IdAndTelefonoContaining(Integer idHotel, String telefono, Sort sort);

	List<Empleado> findByIdHotelEmpleado_IdAndRoles_Id(Integer idHotel, Integer rolId, Sort sort);

	List<Empleado> findByRfcContainingIgnoreCaseAndRoles_Id(String rfc, Integer rolId, Sort sort);

	List<Empleado> findByNombreContainingIgnoreCaseAndRoles_Id(String nombre, Integer rolId, Sort sort);

	List<Empleado> findByApellidoContainingIgnoreCaseAndRoles_Id(String apellido, Integer rolId, Sort sort);

	List<Empleado> findByTelefonoContainingAndRoles_Id(String telefono, Integer rolId, Sort sort);

	List<Empleado> findByIdHotelEmpleado_IdAndRfcContainingIgnoreCaseAndRoles_Id(Integer idHotel, String rfc,
			Integer rolId, Sort sort);

	List<Empleado> findByIdHotelEmpleado_IdAndNombreContainingIgnoreCaseAndRoles_Id(Integer idHotel, String nombre,
			Integer rolId, Sort sort);

	List<Empleado> findByIdHotelEmpleado_IdAndApellidoContainingIgnoreCaseAndRoles_Id(Integer idHotel, String apellido,
			Integer rolId, Sort sort);

	List<Empleado> findByIdHotelEmpleado_IdAndTelefonoContainingAndRoles_Id(Integer idHotel, String telefono,
			Integer rolId, Sort sort);
	
	//Ahora con estado
    List<Empleado> findByIdHotelEmpleado_IdAndEstado(Integer idHotel, int estado, Sort sort);
    List<Empleado> findByRfcContainingIgnoreCaseAndEstado(String rfc, int estado, Sort sort);
    List<Empleado> findByNombreContainingIgnoreCaseAndEstado(String nombre, int estado, Sort sort);
    List<Empleado> findByApellidoContainingIgnoreCaseAndEstado(String apellido, int estado, Sort sort);
    List<Empleado> findByTelefonoContainingAndEstado(String telefono, int estado, Sort sort);
    List<Empleado> findByRoles_IdAndEstado(Integer rolId, int estado, Sort sort);

    List<Empleado> findByIdHotelEmpleado_IdAndRfcContainingIgnoreCaseAndEstado(Integer idHotel, String rfc, int estado, Sort sort);
    List<Empleado> findByIdHotelEmpleado_IdAndNombreContainingIgnoreCaseAndEstado(Integer idHotel, String nombre, int estado, Sort sort);
    List<Empleado> findByIdHotelEmpleado_IdAndApellidoContainingIgnoreCaseAndEstado(Integer idHotel, String apellido, int estado, Sort sort);
    List<Empleado> findByIdHotelEmpleado_IdAndTelefonoContainingAndEstado(Integer idHotel, String telefono, int estado, Sort sort);
    List<Empleado> findByIdHotelEmpleado_IdAndRoles_IdAndEstado(Integer idHotel, Integer rolId, int estado, Sort sort);

    List<Empleado> findByRfcContainingIgnoreCaseAndRoles_IdAndEstado(String rfc, Integer rolId, int estado, Sort sort);
    List<Empleado> findByNombreContainingIgnoreCaseAndRoles_IdAndEstado(String nombre, Integer rolId, int estado, Sort sort);
    List<Empleado> findByApellidoContainingIgnoreCaseAndRoles_IdAndEstado(String apellido, Integer rolId, int estado, Sort sort);
    List<Empleado> findByTelefonoContainingAndRoles_IdAndEstado(String telefono, Integer rolId, int estado, Sort sort);

    List<Empleado> findByIdHotelEmpleado_IdAndRfcContainingIgnoreCaseAndRoles_IdAndEstado(Integer idHotel, String rfc, Integer rolId, int estado, Sort sort);
    List<Empleado> findByIdHotelEmpleado_IdAndNombreContainingIgnoreCaseAndRoles_IdAndEstado(Integer idHotel, String nombre, Integer rolId, int estado, Sort sort);
    List<Empleado> findByIdHotelEmpleado_IdAndApellidoContainingIgnoreCaseAndRoles_IdAndEstado(Integer idHotel, String apellido, Integer rolId, int estado, Sort sort);
    List<Empleado> findByIdHotelEmpleado_IdAndTelefonoContainingAndRoles_IdAndEstado(Integer idHotel, String telefono, Integer rolId, int estado, Sort sort);	
}
