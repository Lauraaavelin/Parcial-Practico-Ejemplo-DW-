package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

    // Complete
    // solo vamos a ver la creacion de estadio, no la de conciertos porque no nos dicen lo contrario en el eniunciado
    
    // siempre el repositorio de la clase
    @Autowired
    private EstadioRepository estadioRepository;
    @Transactional
    public EstadioEntity createEstadio(EstadioEntity e) throws Exception {
        log.info("Inicia proceso de creación del estadio");
        // falta implementar
        if(e.getCapacidad()<=1000 && e.getCapacidad()>1000000 ){
            throw new IllegalArgumentException("La capacidad del estadio debe estar entre 1.000 y 1.000.000");
        }

        if(e.getPrecioAlquiler()<100000){
            throw new IllegalArgumentException("El precio del alquiler debe ser mayor a 100.000 dolares");
        }

        if(e.getNombre().length()<3){
            throw new IllegalArgumentException("El nombre del estadio debe tener al menos 3 caracteres");
        }

        log.info("Termina proceso de creación del estadio");
        return estadioRepository.save(e);
    }


}
