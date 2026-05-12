package modelo;

import java.io.Serializable;

public class ItemCarrito implements Serializable {

    private Producto producto;
    private int      cantidad;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto()      { return producto; }
    public int      getCantidad()      { return cantidad; }
    public void     setCantidad(int c) { this.cantidad = c; }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return producto.getNombre() + " x" + cantidad +
               " = $" + String.format("%,.0f", getSubtotal());
    }
}
