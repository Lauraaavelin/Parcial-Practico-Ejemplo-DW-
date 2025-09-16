package co.edu.uniandes.dse.parcial1.entities;

import java.sql.Date;
import java.time.LocalDateTime;

import co.edu.uniandes.dse.parcial1.podam.DateStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Long presupuesto;
    @Temporal(value = TemporalType.TIMESTAMP)
    @PodamStrategyValue(DateStrategy.class)
    private LocalDateTime fechaConcierto;
    private Integer capacidad;

    @ManyToOne
    private EstadioEntity estadio;
}
