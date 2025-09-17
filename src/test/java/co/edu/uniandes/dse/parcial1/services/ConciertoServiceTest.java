package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.services.ConciertoEstadioService;
import co.edu.uniandes.dse.parcial1.services.ConciertoService;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
@DataJpaTest
@Transactional
@Import(ConciertoService.class)
public class ConciertoServiceTest {

    @Autowired
    private ConciertoService conciertoService;

    @Autowired
	private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ConciertoEntity> conciertoList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}


    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ConciertoEntity entity = factory.manufacturePojo(ConciertoEntity.class);
            entityManager.persist(entity);
            conciertoList.add(entity);
        }
    }

    @Test
    void testCreateConcierto() throws Exception {
        ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
        
        newEntity.setFechaConcierto(LocalDateTime.now().plusMonths(1));
        newEntity.setCapacidad(100);
        newEntity.setPresupuesto(2000L);
        ConciertoEntity result = conciertoService.createConcierto(newEntity);
        assertNotNull(result);

        ConciertoEntity entity = entityManager.find(ConciertoEntity.class, result.getId());

        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getFechaConcierto(), entity.getFechaConcierto());
        assertEquals(newEntity.getCapacidad(), entity.getCapacidad());
        assertEquals(newEntity.getPresupuesto(), entity.getPresupuesto());
    }

    @Test
    void testCreateConciertoInvalidFecha() {
        
        ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
        newEntity.setFechaConcierto(LocalDateTime.now().minusDays(1)); 
        newEntity.setCapacidad(100);
        newEntity.setPresupuesto(2000L);

        assertThrows(IllegalArgumentException.class, () -> {
            conciertoService.createConcierto(newEntity);
        });
    }

    @Test
    void testCreateConciertoInvalidAforo() {
        ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
        newEntity.setFechaConcierto(LocalDateTime.now().plusMonths(1));
        newEntity.setCapacidad(5);
        newEntity.setPresupuesto(2000L);    
        assertThrows(IllegalArgumentException.class, () -> {
            conciertoService.createConcierto(newEntity);
        });
    }
    
    @Test
    void testCreateConciertoInvalidPresupuesto() {
        ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
        newEntity.setFechaConcierto(LocalDateTime.now().plusMonths(1));
        newEntity.setCapacidad(100);
        newEntity.setPresupuesto(500L);    
        assertThrows(IllegalArgumentException.class, () -> {
            conciertoService.createConcierto(newEntity);
        });
    }


}