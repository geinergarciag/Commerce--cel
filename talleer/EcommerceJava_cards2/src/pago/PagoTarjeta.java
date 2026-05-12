package pago;

import java.io.Serializable;

public class PagoTarjeta implements Pago, Serializable {

    private String numeroTarjeta;
    private String titular;
    private String tipoTarjeta;
    private String codigoAprobacion;

    public PagoTarjeta(String numeroTarjeta, String titular, String tipoTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        this.titular       = titular;
        this.tipoTarjeta   = tipoTarjeta;
    }

    @Override
    public boolean procesarPago(double monto) {
        // En un sistema real aqui se conectaria con la pasarela de pago
        this.codigoAprobacion = "APR-" + System.currentTimeMillis();
        return true;
    }

    @Override
    public String obtenerComprobante() {
        String ultimos4 = numeroTarjeta.length() >= 4
                ? "**** **** **** " + numeroTarjeta.substring(numeroTarjeta.length() - 4)
                : "****";
        return "====== COMPROBANTE - " + tipoTarjeta.toUpperCase() + " ======\n" +
               "  Titular:      " + titular          + "\n" +
               "  Tarjeta:      " + ultimos4         + "\n" +
               "  Tipo:         " + tipoTarjeta      + "\n" +
               "  Aprobacion:   " + codigoAprobacion + "\n" +
               "====================================";
    }

    @Override
    public String getTipoPago() { return "Tarjeta " + tipoTarjeta; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public String getTitular()       { return titular; }
    public String getTipoTarjeta()   { return tipoTarjeta; }
}
