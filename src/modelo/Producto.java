package modelo;

import java.io.Serializable;

// Serializable permite guardar este objeto en un archivo .dat
public class Producto implements Serializable {

    private int    id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int    stock;
    private String categoria;
    private String imagenUrl; // URL de la foto del producto

    public Producto(int id, String nombre, String descripcion,
                    double precio, int stock, String categoria, String imagenUrl) {
        this.id          = id;
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.precio      = precio;
        this.stock       = stock;
        this.categoria   = categoria;
        this.imagenUrl   = imagenUrl;
    }

    public int    getId()          { return id; }
    public String getNombre()      { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio()      { return precio; }
    public int    getStock()       { return stock; }
    public String getCategoria()   { return categoria; }
    public String getImagenUrl()   { return imagenUrl; }

    public void setNombre(String nombre)       { this.nombre      = nombre; }
    public void setDescripcion(String desc)    { this.descripcion = desc; }
    public void setPrecio(double precio)       { this.precio      = precio; }
    public void setStock(int stock)            { this.stock       = stock; }
    public void setCategoria(String categoria) { this.categoria   = categoria; }
    public void setImagenUrl(String url)       { this.imagenUrl   = url; }

    public void reducirStock(int cantidad) throws Exception {
        if (cantidad > stock)
            throw new Exception("Stock insuficiente de '" + nombre +
                    "'. Disponible: " + stock + ", solicitado: " + cantidad);
        this.stock = this.stock - cantidad;
    }

    public void aumentarStock(int cantidad) { this.stock = this.stock + cantidad; }
    public boolean hayStock()               { return stock > 0; }

    @Override
    public String toString() {
        return nombre + " | $" + String.format("%,.0f", precio) + " | Stock: " + stock;
    }
}
