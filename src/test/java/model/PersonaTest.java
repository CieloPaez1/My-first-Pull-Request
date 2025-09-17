package model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class PersonaTest {
    @Test
    public void create_AtributosCorrectos_Success() {
        Persona nuevaPersona = Persona.create("Juan",
                "Perez",
                LocalDate.of(2000,1,1),
                "12.345.678",
                180.0f,
                80.5f);

        Assertions.assertNotNull(nuevaPersona);
        Assertions.assertEquals("Perez", nuevaPersona.getApellido());
    }
    @Test
    public void create_AtributosIncorrectos_ThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Persona.create("", "Perez", LocalDate.of(2000,1,1), "12.345.678", 180.0f, 80.5f);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Persona.create("Juan", "", LocalDate.of(2000,1,1), "12.345.678", 180.0f, 80.5f);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Persona.create("Juan", "Perez", null, "12.345.678", 180.0f, 80.5f);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Persona.create("Juan", "Perez", LocalDate.of(2000,1,1), "", 180.0f, 80.5f);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Persona.create("Juan", "Perez", LocalDate.of(2000,1,1), "12.345.678", -180.0f, 80.5f);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Persona.create("Juan", "Perez", LocalDate.of(2000,1,1), "12.345.678", 180.0f, -80.5f);
        });
    }

}
