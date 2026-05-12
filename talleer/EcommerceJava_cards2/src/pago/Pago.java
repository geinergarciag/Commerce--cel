package pago;

// Interface: define el contrato que deben cumplir todos los metodos de pago
public interface Pago {
    boolean procesarPago(double monto);
    String  obtenerComprobante();
    String  getTipoPago();
}
