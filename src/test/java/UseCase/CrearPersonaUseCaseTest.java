package UseCase;


import Input.CrearPersonaInput;
import excepcion.ExceptionPersona;
import excepcion.PersonaExisteException;
import model.Persona;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ouput.GuardarPersona;
import usecase.CrearPersonaUseCase;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearPersonaUseCaseTest {

    @Mock
    private GuardarPersona guardarPersona;



    @Test
    void CrearPersona_Success() {
        CrearPersonaUseCase personaUseCase = new CrearPersonaUseCase(guardarPersona);
        when(guardarPersona.validarPersona("43611353")).thenReturn(false); // No existe
        when(guardarPersona.guardarPersona(any(Persona.class))).thenReturn(true); // Guardado exitoso


        boolean resultado = personaUseCase.crearPersona(
                "Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                "43611353",
                180.0f,
                80.5f
        );
        Assertions.assertTrue(resultado);

    }

    @Test
    void CrearPersona_Duplicada() {
        CrearPersonaUseCase personaUseCase = new CrearPersonaUseCase(guardarPersona);
        when(guardarPersona.validarPersona("43611353")).thenReturn(true); // Ya existe

        PersonaExisteException exception = Assertions.assertThrows(PersonaExisteException.class, () -> {
            personaUseCase.crearPersona(
                    "Juan",
                    "Perez",
                    LocalDate.of(2000, 1, 1),
                    "43611353",
                    180.0f,
                    80.5f
            );
        });

        Assertions.assertEquals("La persona ya existe", exception.getMessage());

        verify(guardarPersona).validarPersona("43611353");
        verify(guardarPersona, never()).guardarPersona(any(Persona.class));
    }

    @Test
    void CrearPersona_ErrorGuardar() {
        CrearPersonaUseCase personaUseCase = new CrearPersonaUseCase(guardarPersona);
        when(guardarPersona.validarPersona("43611353")).thenReturn(false); // No existe
        when(guardarPersona.guardarPersona(any(Persona.class))).thenReturn(false); // Error al guardar

        PersonaExisteException exception = Assertions.assertThrows(PersonaExisteException.class, () -> {
            personaUseCase.crearPersona(
                    "Juan",
                    "Perez",
                    LocalDate.of(2000, 1, 1),
                    "43611353",
                    180.0f,
                    80.5f
            );
        });
        Assertions.assertEquals("Error al guardar la persona", exception.getMessage());
        verify(guardarPersona).validarPersona("43611353");
        verify(guardarPersona).guardarPersona(any(Persona.class));
    }

    @Test
    public void CrearPersona_DatosInvalidos(){
        CrearPersonaUseCase personaUseCase = new CrearPersonaUseCase(guardarPersona);

        Assertions.assertThrows(ExceptionPersona.class, () ->
                personaUseCase.crearPersona("", "Perez", LocalDate.of(2000, 1, 1), "43611353", 180.0f, 80.5f)
        );
        Assertions.assertThrows(ExceptionPersona.class, () ->
                personaUseCase.crearPersona("Juan", "", LocalDate.of(2000, 1, 1), "43611353", 180.0f, 80.5f)
        );
        Assertions.assertThrows(ExceptionPersona.class, () ->
                personaUseCase.crearPersona("Juan", "Perez", LocalDate.of(2025, 1, 1), "43611353", 180.0f, 80.5f)
        );
        Assertions.assertThrows(ExceptionPersona.class, () ->
                personaUseCase.crearPersona("Juan", "Perez", LocalDate.of(2000, 1, 1), "", 180.0f, 80.5f)
        );
        Assertions.assertThrows(ExceptionPersona.class, () ->
                personaUseCase.crearPersona("Juan", "Perez", LocalDate.of(2000, 1, 1), "43611353", -180.0f, 80.5f)
        );
        Assertions.assertThrows(ExceptionPersona.class, () ->
                personaUseCase.crearPersona("Juan", "Perez", LocalDate.of(2000, 1, 1), "43611353", 180.0f, -80.5f)
        );

        verify(guardarPersona, never()).validarPersona(any());
        verify(guardarPersona, never()).guardarPersona(any());
    }
}