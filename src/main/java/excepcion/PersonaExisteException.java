package excepcion;

public class PersonaExisteException extends RuntimeException {
  public PersonaExisteException(String message) {
    super(message);
  }
}
