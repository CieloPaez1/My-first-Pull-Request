package model;

import excepcion.ExceptionPersona;
import org.testng.annotations.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;



public class PersonaTest {
    @Test
    public void create_AtributosCorrectos_Success() {
        Persona nuevaPersona = Persona.factory("Juan",
                "Perez",
                LocalDate.of(2000,1,1),
                "12.345.678",
                180.0f,
                80.5f);

        Assertions.assertNotNull(nuevaPersona);
        Assertions.assertEquals("Perez", nuevaPersona.getApellido());
        Assertions.assertEquals("Juan", nuevaPersona.getNombre());
        Assertions.assertEquals(LocalDate.of(2000,1,1), nuevaPersona.getFechaNacimiento());
        Assertions.assertEquals("12.345.678", nuevaPersona.getDni());
        Assertions.assertEquals(180.0f, nuevaPersona.getAltura());
        Assertions.assertEquals(80.5f, nuevaPersona.getPeso());

    }

    @Test
    public void FactoryFalse(){
        Assertions.assertThrows(ExceptionPersona.class, () -> {Persona.factory("", "Perez", LocalDate.of(2000,1,1), "12.345.678", 180.0f, 80.5f);});
        Assertions.assertThrows(ExceptionPersona.class, () -> {Persona.factory("Juan", "", LocalDate.of(2000,1,1), "12.345.678", 180.0f, 80.5f);});
        Assertions.assertThrows(ExceptionPersona.class, () -> {Persona.factory("Juan", "Perez", null, "12.345.678", 180.0f, 80.5f);});
        Assertions.assertThrows(ExceptionPersona.class, () -> {Persona.factory("Juan", "Perez", LocalDate.of(2000,1,1), "", 180.0f, 80.5f);});
        Assertions.assertThrows(ExceptionPersona.class, () -> {Persona.factory("Juan", "Perez", LocalDate.of(2000,1,1), "12.345.678", -180.0f, 80.5f);});
        Assertions.assertThrows(ExceptionPersona.class, () -> {Persona.factory("Juan", "Perez", LocalDate.of(2000,1,1), "12.345.678", 180.0f, -80.5f);});

    }
    @Test
    public void FactoryFechaFutura_ThrowsException() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        Assertions.assertThrows(ExceptionPersona.class, () ->
                Persona.factory("Juan", "Perez", futureDate, "12345678", 180f, 80f)
        );
    }





}