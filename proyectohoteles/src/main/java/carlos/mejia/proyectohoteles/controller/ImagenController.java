package carlos.mejia.proyectohoteles.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imagenes")
public class ImagenController {

    private final String DIRECTORIO_IMAGENES_HOTEL = "/app/imagenesHotel/hoteles/";
    
    private final String DIRECTORIO_IMAGENES_HABITACION = "/app/imagenesHotel/habitaciones/";
    
    private final String DIRECTORIO_IMAGENES_EMPLEADO = "/app/imagenesHotel/empleados/";
    
    private final String DIRECTORIO_IMAGENES_CLIENTE = "/app/imagenesHotel/clientes/";

    @GetMapping("/hoteles/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreImagen) {
        try {
            Path rutaImagen = Paths.get(DIRECTORIO_IMAGENES_HOTEL).resolve(nombreImagen);
            Resource recurso = new UrlResource(rutaImagen.toUri());

            if (recurso.exists() || recurso.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
                        .body(recurso);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/habitaciones/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagenHabi(@PathVariable String nombreImagen) {
        try {
            Path rutaImagen = Paths.get(DIRECTORIO_IMAGENES_HABITACION).resolve(nombreImagen);
            Resource recurso = new UrlResource(rutaImagen.toUri());

            if (recurso.exists() || recurso.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
                        .body(recurso);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }    
    
    @GetMapping("/empleados/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagenEmpleado(@PathVariable String nombreImagen) {
        try {
            Path rutaImagen = Paths.get(DIRECTORIO_IMAGENES_EMPLEADO).resolve(nombreImagen);
            Resource recurso = new UrlResource(rutaImagen.toUri());

            if (recurso.exists() || recurso.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
                        .body(recurso);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }      
    
    @GetMapping("/clientes/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagenCliente(@PathVariable String nombreImagen) {
        try {
            Path rutaImagen = Paths.get(DIRECTORIO_IMAGENES_CLIENTE).resolve(nombreImagen);
            Resource recurso = new UrlResource(rutaImagen.toUri());

            if (recurso.exists() || recurso.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
                        .body(recurso);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }       
}
