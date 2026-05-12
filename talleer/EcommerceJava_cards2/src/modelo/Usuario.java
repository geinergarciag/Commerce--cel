package modelo;

import java.io.Serializable;

// Clase abstracta: no se puede instanciar directamente, solo Cliente o Administrador
public abstract class Usuario implements Serializable {

    private String id;
    private String nombre;
    private String email;
    private String contrasena;

    public Usuario(String id, String nombre, String email, String contrasena) {
        this.id         = id;
        this.nombre     = nombre;
        this.email      = email;
        this.contrasena = contrasena;
    }

    public String getId()         { return id; }
    public String getNombre()     { return nombre; }
    public String getEmail()      { return email; }
    public String getContrasena() { return contrasena; }

    public void setNombre(String nombre)         { this.nombre     = nombre; }
    public void setEmail(String email)           { this.email      = email; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    // Cada subclase implementa este metodo: Cliente -> "Cliente", Admin -> "Administrador"
    public abstract String getTipoUsuario();

    public boolean verificarContrasena(String contrasena) {
        return this.contrasena.equals(contrasena);
    }

    @Override
    public String toString() {
        return "[" + getTipoUsuario() + "] " + nombre + " | " + email;
    }
}
