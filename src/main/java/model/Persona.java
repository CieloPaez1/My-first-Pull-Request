package model;

import excepcion.ExceptionPersona;

import java.time.LocalDate;

public class Persona {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String dni;
    private float altura;
    private float peso;

    private Persona(String nombre,String apellido,LocalDate fechaNacimiento, String dni,float altura,float peso) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.dni = dni;
        this.altura = altura;
        this.peso = peso;
    }

    public static Persona factory(String nombre, String apellido, LocalDate fechaNacimiento, String dni, float altura, float peso) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ExceptionPersona("El nombre no puede ser nulo o vacío");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ExceptionPersona("El apellido no puede ser nulo o vacío");
        }
        if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
            throw new ExceptionPersona("La fecha de nacimiento no puede ser nula o en el futuro");
        }
        if (dni == null || dni.isEmpty()) {
            throw new ExceptionPersona("El DNI no puede ser nulo o vacío");
        }
        if (altura <= 0) {
            throw new ExceptionPersona("La altura debe ser un valor positivo");
        }
        if (peso <= 0) {
            throw new ExceptionPersona("El peso debe ser un valor positivo");
        }
        return new Persona(nombre, apellido, fechaNacimiento, dni, altura, peso);

    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {

        return apellido;

    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public String getDni() { return dni;
    }
    public float getAltura() {
        return altura;
    }
    public float getPeso() {
        return peso;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Persona persona = (Persona) obj;
        return dni.equals(persona.dni);
    }
}