package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class ConciertoEstadioService {

    // Complete
// Agregar un concierto a un estadio 
    @Autowired
    private EstadioRepository estadioRepo;
    @Autowired
    private ConciertoRepository conciertoRepo;

    @Transactional
    public EstadioEntity addConciertoEstadio(Long conciertoId, Long estadioId) throws Exception {
        log.info("Inicia proceso de agregar un concierto al estadio");
        // falta implementar
        Optional<ConciertoEntity> conciertoEntity= conciertoRepo.findById(conciertoId);
        Optional<EstadioEntity> estadioEntity= estadioRepo.findById(estadioId);
         // si no existe el concierto o el estadio lanzo excepcion para eso miro si es empty 
        if(conciertoEntity.isEmpty()){
            throw new IllegalArgumentException("El concierto con id "+conciertoId+" no existe");
        }
        if(estadioEntity.isEmpty()){
            throw new IllegalArgumentException("El estadio con id "+estadioId+" no existe");
        }
        if(conciertoEntity.get().getCapacidad()>estadioEntity.get().getCapacidad()){
            throw new IllegalArgumentException("La capacidad del concierto no puede ser mayor a la del estadio");
        }
        if ( conciertoEntity.get().getPresupuesto()> (estadioEntity.get().getPrecioAlquiler())){
            throw new IllegalArgumentException("El presupuesto del concierto no puede ser menor al precio de alquiler del estadio");
        }

       LocalDateTime fecha_concierto_Anterior = estadioEntity.get().getConcierto().getLast().getFechaConcierto();
        LocalDateTime fecha_concierto_nuevo = conciertoEntity.get().getFechaConcierto();

    Duration diferencia = Duration.between(fecha_concierto_Anterior, fecha_concierto_nuevo);
    if(diferencia.toDays()<2){
        throw new IllegalArgumentException("Debe haber al menos 2 dias de diferencia entre conciertos en el mismo estadio");}

        
    conciertoEntity.get().setEstadio(estadioEntity.get());
    estadioEntity.get().getConcierto().add(conciertoEntity.get());
        
        log.info("Termina proceso de agregar un concierto al estadio");
        return estadioEntity.get();
    }
        

}
