package Input;

import excepcion.PersonaExisteException;

import java.time.LocalDate;

public interface CrearPersonaInput {
   boolean crearPersona(String nombre,String apellido, LocalDate fechaNacimiento, String dni, float altura, float peso) ;

}
