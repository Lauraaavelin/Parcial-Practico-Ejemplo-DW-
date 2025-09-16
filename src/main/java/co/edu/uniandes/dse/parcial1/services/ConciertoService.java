package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    // Complete Crear un concierto 
    // no incluto el repositorioo de Estadio porque lo estoy creando por aparte no en conjunto con el concierto
    
    @Autowired
    private ConciertoRepository conciertoRepository;

    @Transactional
    public ConciertoEntity createConcierto(ConciertoEntity cv) throws Exception {
        log.info("Inicia proceso de creación del concierto");
        if (cv.getFechaConcierto().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha del concierto debe ser futura");
        }

        if (cv.getCapacidad()<10){
            throw new IllegalArgumentException("La capacidad del concierto debe ser mayor a 10");
        }

        if(cv.getPresupuesto()<1000){
            throw new IllegalArgumentException("El presupuesto del concierto debe ser mayor a 1000");
        }

        return conciertoRepository.save(cv);

    
    }
    
    


}
