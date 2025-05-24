package carlos.mejia.proyectohoteles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.DetalleMantenimiento;
import carlos.mejia.proyectohoteles.model.DetalleMantenimientoId;
import carlos.mejia.proyectohoteles.repository.DetalleMantenimientoRepository;
import jakarta.transaction.Transactional;

@Service
public class DetalleMantenimientoServiceJpa implements DetalleMantenimientoService{
	@Autowired 
	DetalleMantenimientoRepository detalleMantenimientoRepository;
	
	@Override
	public void guardarDetalleMantenimiento(DetalleMantenimiento detalleMantenimiento) {
		DetalleMantenimiento detalleMantenimientotmp = detalleMantenimientoRepository.save(detalleMantenimiento);
        //retorna el id generado automático, esto será útil para las relaciones de DETALLEVENTA.
        
	}

	@Override
	public List<DetalleMantenimiento> todosDatellesMantes() {
		// TODO Auto-generated method stub
		return detalleMantenimientoRepository.findAll();
	}

	@Override
    public DetalleMantenimiento buscarPorId(DetalleMantenimientoId id) {
		Optional<DetalleMantenimiento> optional = detalleMantenimientoRepository.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;		

    }

	@Override
    public void eliminarDetalleMantenimiento(DetalleMantenimientoId id) {
        detalleMantenimientoRepository.deleteById(id);
    }

	@Override
	@Transactional
	public void eliminarPorIdMantenimiento(Integer idMantenimiento) {
		// TODO Auto-generated method stub
		detalleMantenimientoRepository.deleteById_Idmantenimiento(idMantenimiento);
	}
	
	

}
