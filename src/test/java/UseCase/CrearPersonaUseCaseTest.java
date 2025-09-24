package UseCase;


import Input.CrearPersonaInput;
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
        Persona miPersona = Persona.factory("Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                "43611353",
                180.0f,
                80.5f);
        CrearPersonaUseCase personaUseCase = new CrearPersonaUseCase(guardarPersona);
        when(guardarPersona.validarPersona(miPersona.getDni())).thenReturn(false); // No existe
        when(guardarPersona.guardarPersona(miPersona)).thenReturn(true); // Guardado exitoso


        boolean resultado = personaUseCase.crearPersona(miPersona.getNombre(),
                miPersona.getApellido(),
                miPersona.getFechaNacimiento(),
                miPersona.getDni(),
                miPersona.getAltura(),
                miPersona.getPeso());
        Assertions.assertTrue(resultado);

    }

    @Test
    void CrearPersona_Duplicada() {
        Persona miPersona = Persona.factory("Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                "43611353",
                180.0f,
                80.5f);
        CrearPersonaUseCase personaUseCase = new CrearPersonaUseCase(guardarPersona);
        when(guardarPersona.validarPersona("43611353")).thenReturn(true); // Ya existe

        PersonaExisteException exception = Assertions.assertThrows(PersonaExisteException.class, () -> {
            personaUseCase.crearPersona(miPersona.getNombre(),
                    miPersona.getApellido(),
                    miPersona.getFechaNacimiento(),
                    miPersona.getDni(),
                    miPersona.getAltura(),
                    miPersona.getPeso());
        });

        Assertions.assertEquals("La persona ya existe", exception.getMessage());

        verify(guardarPersona).validarPersona("43611353");
        verify(guardarPersona, never()).guardarPersona(any(Persona.class));
    }

    @Test
    void CrearPersona_ErrorGuardar() {
        Persona miPersona = Persona.factory("Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                "43611353",
                180.0f,
                80.5f);
        CrearPersonaUseCase persona = new CrearPersonaUseCase(guardarPersona);
        when(guardarPersona.validarPersona("43611353")).thenReturn(false); // No existe
        when(guardarPersona.guardarPersona(any(Persona.class))).thenReturn(false); // Error al guardar

        PersonaExisteException exception = Assertions.assertThrows(PersonaExisteException.class, () -> {
            persona.crearPersona(miPersona.getNombre(),
                    miPersona.getApellido(),
                    miPersona.getFechaNacimiento(),
                    miPersona.getDni(),
                    miPersona.getAltura(),
                    miPersona.getPeso());
        });

        Assertions.assertEquals("Error al guardar la persona", exception.getMessage());

        verify(guardarPersona).validarPersona("43611353");
        verify(guardarPersona).guardarPersona(any(Persona.class));
    }

    @Test
    public void CrearPersona_DatosInvalidos(){
        CrearPersonaInput persona=new CrearPersonaUseCase(guardarPersona);

        verify(guardarPersona, never()).validarPersona(any());
        verify(guardarPersona, never()).guardarPersona(any());

        Assertions.assertThrows(RuntimeException.class, ()->{
            persona.crearPersona("  ", "Perez", LocalDate.of(2000,1,1), "43611353", 180.0f, 80.5f);
        });

    }
}