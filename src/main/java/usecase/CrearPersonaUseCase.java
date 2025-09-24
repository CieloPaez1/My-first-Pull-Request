package usecase;

import Input.CrearPersonaInput;
import excepcion.PersonaExisteException;
import model.Persona;
import ouput.GuardarPersona;

import java.time.LocalDate;


public class CrearPersonaUseCase implements CrearPersonaInput {
    private final GuardarPersona guardarPersona;

    public CrearPersonaUseCase(GuardarPersona guardarPersona) {
        this.guardarPersona = guardarPersona;
    }


    @Override
    public boolean crearPersona(String nombre, String apellido, LocalDate fechaNacimiento, String dni, float altura, float peso) {

        Persona persona = Persona.factory(nombre, apellido, fechaNacimiento, dni, altura, peso);
        if (guardarPersona.validarPersona(persona.getDni())) {
            throw new PersonaExisteException("La persona ya existe");
        }
        if (!guardarPersona.guardarPersona(persona)) {
            throw new PersonaExisteException("Error al guardar la persona");
        }


        return true;
    }}