package modelo;

import java.io.Serializable;

public class Administrador extends Usuario implements Serializable {

    private String nivelAcceso;

    public Administrador(String id, String nombre, String email,
                         String contrasena, String nivelAcceso) {
        super(id, nombre, email, contrasena);
        this.nivelAcceso = nivelAcceso;
    }

    @Override
    public String getTipoUsuario() { return "Administrador"; }

    public String getNivelAcceso()           { return nivelAcceso; }
    public void   setNivelAcceso(String niv) { this.nivelAcceso = niv; }
}
