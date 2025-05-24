package carlos.mejia.proyectohoteles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Telefono;
import jakarta.transaction.Transactional;

public interface TelefonoRepository extends JpaRepository<Telefono, Integer>{

	//List<Telefono> findByIdHotelTelefono(int idHotel);
    @Transactional
    void deleteByIdHotelTelefono(Hotel hotel);
}
