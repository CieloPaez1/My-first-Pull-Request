package ouput;

import model.Persona;

public interface GuardarPersona {
    boolean guardarPersona(Persona persona);
    boolean validarPersona(String documento);

}
