package co.edu.uniandes.dse.parcial1.entities;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private Long precioAlquiler;
    private Integer capacidad;
    
  
    @PodamExclude
    @OneToMany(mappedBy = "estadio",  orphanRemoval = true) // cuando se borra un estadio, se borran sus conciertos yn pra crear aun concierto debe exitir un estadio ya creado, el esdaio ´puede tener su lista de conciertos vacia 
    private List<ConciertoEntity> concierto;
}
