package pago;

import java.io.Serializable;

public class PagoEfectivo implements Pago, Serializable {

    private double montoPagado;
    private double vuelto;
    private double montoTotal;

    public PagoEfectivo(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    @Override
    public boolean procesarPago(double monto) {
        this.montoTotal = monto;
        if (montoPagado >= monto) {
            this.vuelto = montoPagado - monto;
            return true;
        }
        return false;
    }

    @Override
    public String obtenerComprobante() {
        return "====== COMPROBANTE - EFECTIVO ======\n" +
               "  Monto a pagar:  $" + String.format("%,.0f", montoTotal)  + "\n" +
               "  Pago recibido:  $" + String.format("%,.0f", montoPagado) + "\n" +
               "  Vuelto:         $" + String.format("%,.0f", vuelto)      + "\n" +
               "====================================";
    }

    @Override
    public String getTipoPago() { return "Efectivo"; }

    public double getVuelto() { return vuelto; }
}
