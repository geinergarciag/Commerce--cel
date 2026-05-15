package modelo;

import pago.Pago;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Pedido implements Serializable {

    // Contador compartido por todos los pedidos para generar IDs unicos
    private static int contadorId = 1;

    private int                    id;
    private String                 clienteNombre;
    private ArrayList<ItemCarrito> items;
    private double                 total;
    private String                 estado;
    private Date                   fechaPedido;
    private String                 comprobantePago;
    private String                 tipoPago;

    public Pedido(String clienteNombre, ArrayList<ItemCarrito> items, double total) {
        this.id            = contadorId++;
        this.clienteNombre = clienteNombre;
        this.total         = total;
        this.estado        = "Pendiente";
        this.fechaPedido   = new Date();
        this.items         = new ArrayList<>(items); // copia para no modificar el carrito
    }

    // Reduce stock, procesa pago y confirma el pedido
    public boolean confirmar(Pago pago) throws Exception {
        try {
            for (int i = 0; i < items.size(); i++) {
                ItemCarrito item = items.get(i);
                item.getProducto().reducirStock(item.getCantidad());
            }
        } catch (Exception e) {
            this.estado = "Fallido";
            throw e;
        }

        if (pago.procesarPago(total)) {
            this.comprobantePago = pago.obtenerComprobante();
            this.tipoPago        = pago.getTipoPago();
            this.estado          = "Confirmado";
            return true;
        } else {
            // Pago rechazado: devolver el stock
            for (int i = 0; i < items.size(); i++) {
                ItemCarrito item = items.get(i);
                item.getProducto().aumentarStock(item.getCantidad());
            }
            this.estado = "Pago fallido";
            return false;
        }
    }

    public void cancelar() {
        if (estado.equals("Confirmado")) {
            for (int i = 0; i < items.size(); i++) {
                ItemCarrito item = items.get(i);
                item.getProducto().aumentarStock(item.getCantidad());
            }
        }
        this.estado = "Cancelado";
    }

    public int                   getId()              { return id; }
    public String                getClienteNombre()   { return clienteNombre; }
    public ArrayList<ItemCarrito> getItems()          { return items; }
    public double                getTotal()           { return total; }
    public String                getEstado()          { return estado; }
    public Date                  getFechaPedido()     { return fechaPedido; }
    public String                getComprobantePago() { return comprobantePago; }
    public String                getTipoPago()        { return tipoPago; }

    public String getFechaFormateada() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fechaPedido);
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " | " + getFechaFormateada() +
               " | $" + String.format("%,.0f", total) + " | " + estado;
    }
}
