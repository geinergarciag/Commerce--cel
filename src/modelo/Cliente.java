package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Cliente extends Usuario implements Serializable {

    private String            direccion;
    private String            telefono;
    private Carrito           carrito;
    private ArrayList<Pedido> historialPedidos;

    public Cliente(String id, String nombre, String email,
                   String contrasena, String direccion, String telefono) {
        super(id, nombre, email, contrasena);
        this.direccion        = direccion;
        this.telefono         = telefono;
        this.carrito          = new Carrito();
        this.historialPedidos = new ArrayList<>();
    }

    @Override
    public String getTipoUsuario() { return "Cliente"; }

    public String            getDireccion()        { return direccion; }
    public String            getTelefono()         { return telefono; }
    public Carrito           getCarrito()          { return carrito; }
    public ArrayList<Pedido> getHistorialPedidos() { return historialPedidos; }

    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono)   { this.telefono  = telefono; }

    public void agregarPedido(Pedido pedido) {
        historialPedidos.add(pedido);
    }
}
