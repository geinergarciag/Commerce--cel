package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Carrito implements Serializable {

    private ArrayList<ItemCarrito> items;

    public Carrito() {
        this.items = new ArrayList<>();
    }

    public ArrayList<ItemCarrito> getItems() { return items; }

    public void agregarProducto(Producto producto, int cantidad) throws Exception {
        if (cantidad <= 0)
            throw new Exception("La cantidad debe ser mayor a 0.");
        if (!producto.hayStock())
            throw new Exception("'" + producto.getNombre() + "' esta agotado.");
        if (cantidad > producto.getStock())
            throw new Exception("Solo hay " + producto.getStock() +
                    " unidades de '" + producto.getNombre() + "'.");

        // Si el producto ya esta en el carrito, solo sumamos la cantidad
        for (int i = 0; i < items.size(); i++) {
            ItemCarrito item = items.get(i);
            if (item.getProducto().getId() == producto.getId()) {
                int nuevaCantidad = item.getCantidad() + cantidad;
                if (nuevaCantidad > producto.getStock())
                    throw new Exception("No puede agregar mas de " +
                            producto.getStock() + " unidades en total.");
                item.setCantidad(nuevaCantidad);
                return;
            }
        }

        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(int productoId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProducto().getId() == productoId) {
                items.remove(i);
                return;
            }
        }
    }

    public void modificarCantidad(int productoId, int nuevaCantidad) throws Exception {
        for (int i = 0; i < items.size(); i++) {
            ItemCarrito item = items.get(i);
            if (item.getProducto().getId() == productoId) {
                if (nuevaCantidad <= 0) {
                    eliminarProducto(productoId);
                } else if (nuevaCantidad > item.getProducto().getStock()) {
                    throw new Exception("Stock insuficiente. Disponible: " +
                            item.getProducto().getStock());
                } else {
                    item.setCantidad(nuevaCantidad);
                }
                return;
            }
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total = total + items.get(i).getSubtotal();
        }
        return total;
    }

    public void vaciar() { items.clear(); }

    public boolean estaVacio() { return items.isEmpty(); }

    public int getTotalUnidades() {
        int total = 0;
        for (int i = 0; i < items.size(); i++) {
            total = total + items.get(i).getCantidad();
        }
        return total;
    }
}
