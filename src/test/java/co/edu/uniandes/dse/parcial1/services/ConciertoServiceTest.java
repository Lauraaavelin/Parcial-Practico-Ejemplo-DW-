package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

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

@DataJpaTest // crear la base de datos que se usara en las pruebas 
@Transactional
@Import(ConciertoEstadioService.class) // importar el servicio que se va a probar
public class ConciertoServiceTest {
    @Autowired
    private ConciertoService conciertoService;

    @Autowired
    private TestEntityManager entityManager;
   

    private PodamFactory factory = new PodamFactoryImpl();

  


    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from CVEntidad").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from DatosContactoEntidad").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EducacionEntidad").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            entityManager.persist(factory.manufacturePojo(ConciertoEntity.class));
        }
    }

    @Test
    void testCreateConcierto() throws Exception {
        ConciertoEntity concierto = factory.manufacturePojo(ConciertoEntity.class);
        concierto.setCapacidad(20);
        concierto.setPresupuesto(2000L);
        concierto.setFechaConcierto(LocalDateTime.now().plusDays(1));
        ConciertoEntity result = conciertoService.createConcierto(concierto);
        assertDoesNotThrow(() -> {
            conciertoService.createConcierto(concierto);
        });
        
        ConciertoEntity entity = entityManager.find(ConciertoEntity.class, result.getId());
        assertNotNull(entity);
        assertEquals(result.getCapacidad(), entity.getCapacidad());
        assertEquals(result.getPresupuesto(), entity.getPresupuesto());
        assertEquals(result.getFechaConcierto(), entity.getFechaConcierto());
        assertEquals(result.getNombre(), entity.getNombre());
        
    }

}
