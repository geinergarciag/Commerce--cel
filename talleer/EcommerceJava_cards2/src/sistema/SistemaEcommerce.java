package sistema;

import datos.GestorDatos;
import modelo.*;
import pago.Pago;
import java.util.ArrayList;

public class SistemaEcommerce {

    private ArrayList<Usuario>  usuarios;
    private ArrayList<Producto> productos;
    private Usuario             usuarioActual;
    private GestorDatos         gestorDatos;
    private int                 siguienteIdProducto;

    public SistemaEcommerce() {
        this.gestorDatos = new GestorDatos();
        this.usuarios    = gestorDatos.cargarUsuarios();
        this.productos   = gestorDatos.cargarProductos();

        if (usuarios.isEmpty()) {
            usuarios.add(new Administrador("ADM001", "Administrador",
                    "admin@tienda.com", "admin123", "TOTAL"));
            gestorDatos.guardarUsuarios(usuarios);
        }

        if (productos.isEmpty()) {
            cargarCelularesEjemplo();
        }

        siguienteIdProducto = 1;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() >= siguienteIdProducto)
                siguienteIdProducto = productos.get(i).getId() + 1;
        }
    }

    private void cargarCelularesEjemplo() {
        productos.add(new Producto(1, "Samsung Galaxy S24 Ultra",
                "6.8\" QHD+, Snapdragon 8 Gen 3, 12GB RAM, S Pen incluido",
                5200000, 8, "Gama Alta",
                "https://fdn2.gsmarena.com/vv/pics/samsung/samsung-galaxy-s24-ultra-5g-1.jpg"));

        productos.add(new Producto(2, "iPhone 15 Pro Max",
                "6.7\" Super Retina XDR, chip A17 Pro, 256GB, titanio",
                6800000, 6, "Gama Alta",
                "https://fdn2.gsmarena.com/vv/pics/apple/apple-iphone-15-pro-max-1.jpg"));

        productos.add(new Producto(3, "Xiaomi 14 Pro",
                "6.73\" AMOLED 120Hz, Snapdragon 8 Gen 3, Leica 50MP",
                4100000, 5, "Gama Alta",
                "https://fdn2.gsmarena.com/vv/pics/xiaomi/xiaomi-14-pro-1.jpg"));

        productos.add(new Producto(4, "Samsung Galaxy A55",
                "6.6\" Super AMOLED, 8GB RAM, triple camara 50MP, IP67",
                1850000, 15, "Gama Media",
                "https://fdn2.gsmarena.com/vv/pics/samsung/samsung-galaxy-a55-1.jpg"));

        productos.add(new Producto(5, "Motorola Edge 50 Pro",
                "6.7\" pOLED 144Hz, 12GB RAM, carga 125W",
                2200000, 12, "Gama Media",
                "https://fdn2.gsmarena.com/vv/pics/motorola/motorola-edge-50-pro-1.jpg"));

        productos.add(new Producto(6, "Xiaomi Redmi Note 13 Pro",
                "6.67\" AMOLED 120Hz, 12GB RAM, camara 200MP",
                1380000, 20, "Gama Media",
                "https://fdn2.gsmarena.com/vv/pics/xiaomi/xiaomi-redmi-note-13-pro-1.jpg"));

        productos.add(new Producto(7, "Samsung Galaxy A15",
                "6.5\" Super AMOLED, 4GB RAM, bateria 5000mAh",
                780000, 25, "Gama Baja",
                "https://fdn2.gsmarena.com/vv/pics/samsung/samsung-galaxy-a15-1.jpg"));

        productos.add(new Producto(8, "Motorola Moto G54",
                "6.5\" IPS LCD 120Hz, 8GB RAM, bateria 5000mAh",
                850000, 18, "Gama Baja",
                "https://fdn2.gsmarena.com/vv/pics/motorola/motorola-moto-g54-1.jpg"));

        productos.add(new Producto(9, "Cargador USB-C 65W GaN",
                "Carga rapida universal, compatible Samsung, Xiaomi, iPhone",
                95000, 40, "Accesorios",
                "https://http2.mlstatic.com/D_NQ_NP_714633-MCO71763611714_092023-O.webp"));

        productos.add(new Producto(10, "Samsung Galaxy Buds2 Pro",
                "ANC activo, Hi-Fi 24bit, IPX7, hasta 8h de bateria",
                650000, 22, "Accesorios",
                "https://fdn2.gsmarena.com/vv/pics/samsung/samsung-galaxy-buds2-pro-1.jpg"));

        siguienteIdProducto = 11;
        gestorDatos.guardarProductos(productos);
    }

    // ── Autenticacion ─────────────────────────────────────────────────────────

    public Usuario login(String email, String contrasena) throws Exception {
        if (email == null || email.trim().isEmpty())
            throw new Exception("El correo electronico es obligatorio.");
        if (contrasena == null || contrasena.isEmpty())
            throw new Exception("La contrasena es obligatoria.");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getEmail().equalsIgnoreCase(email.trim())) {
                if (u.verificarContrasena(contrasena)) { usuarioActual = u; return u; }
                else throw new Exception("Contrasena incorrecta.");
            }
        }
        throw new Exception("No existe una cuenta con el correo: " + email.trim());
    }

    public void logout() { usuarioActual = null; }

    public Cliente registrarCliente(String nombre, String email,
                                    String contrasena, String direccion,
                                    String telefono) throws Exception {
        if (nombre.trim().isEmpty() || email.trim().isEmpty() || contrasena.trim().isEmpty())
            throw new Exception("Nombre, correo y contrasena son obligatorios.");
        if (!email.contains("@")) throw new Exception("El correo no es valido.");
        if (contrasena.length() < 4) throw new Exception("La contrasena debe tener al menos 4 caracteres.");
        for (int i = 0; i < usuarios.size(); i++)
            if (usuarios.get(i).getEmail().equalsIgnoreCase(email.trim()))
                throw new Exception("Ya existe una cuenta con ese correo.");
        String id = "CLI" + String.format("%03d", usuarios.size() + 1);
        Cliente c = new Cliente(id, nombre.trim(), email.trim().toLowerCase(),
                contrasena, direccion.trim(), telefono.trim());
        usuarios.add(c);
        gestorDatos.guardarUsuarios(usuarios);
        return c;
    }

    // ── CRUD productos ────────────────────────────────────────────────────────

    public void agregarProducto(String nombre, String descripcion, double precio,
                                 int stock, String categoria, String imagenUrl) throws Exception {
        verificarAdmin();
        if (nombre.trim().isEmpty()) throw new Exception("El nombre es obligatorio.");
        if (precio <= 0)             throw new Exception("El precio debe ser mayor a $0.");
        if (stock < 0)               throw new Exception("El stock no puede ser negativo.");
        productos.add(new Producto(siguienteIdProducto++, nombre.trim(), descripcion.trim(),
                precio, stock, categoria.trim(), imagenUrl.trim()));
        gestorDatos.guardarProductos(productos);
    }

    public void actualizarProducto(int id, String nombre, String descripcion, double precio,
                                    int stock, String categoria, String imagenUrl) throws Exception {
        verificarAdmin();
        Producto p = buscarProductoPorId(id);
        if (p == null) throw new Exception("Producto no encontrado.");
        p.setNombre(nombre.trim()); p.setDescripcion(descripcion.trim());
        p.setPrecio(precio); p.setStock(stock); p.setCategoria(categoria.trim());
        p.setImagenUrl(imagenUrl.trim());
        gestorDatos.guardarProductos(productos);
    }

    public void eliminarProducto(int id) throws Exception {
        verificarAdmin();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == id) { productos.remove(i); gestorDatos.guardarProductos(productos); return; }
        }
        throw new Exception("Producto no encontrado.");
    }

    // ── Carrito ───────────────────────────────────────────────────────────────

    public void agregarAlCarrito(int productoId, int cantidad) throws Exception {
        Cliente c = getClienteActual();
        Producto p = buscarProductoPorId(productoId);
        if (p == null) throw new Exception("Producto no encontrado.");
        c.getCarrito().agregarProducto(p, cantidad);
    }

    public void eliminarDelCarrito(int productoId) throws Exception {
        getClienteActual().getCarrito().eliminarProducto(productoId);
    }

    public void modificarCantidadCarrito(int productoId, int nuevaCantidad) throws Exception {
        getClienteActual().getCarrito().modificarCantidad(productoId, nuevaCantidad);
    }

    // ── Pedidos ───────────────────────────────────────────────────────────────

    public Pedido realizarPedido(Pago metodoPago) throws Exception {
        Cliente cliente = getClienteActual();
        Carrito carrito = cliente.getCarrito();
        if (carrito.estaVacio()) throw new Exception("El carrito esta vacio.");
        Pedido pedido = new Pedido(cliente.getNombre(), carrito.getItems(), carrito.calcularTotal());
        if (!pedido.confirmar(metodoPago)) throw new Exception("El pago fue rechazado.");
        cliente.agregarPedido(pedido);
        carrito.vaciar();
        gestorDatos.guardarProductos(productos);
        gestorDatos.guardarUsuarios(usuarios);
        return pedido;
    }

    // ── Auxiliares ────────────────────────────────────────────────────────────

    private void verificarAdmin() throws Exception {
        if (!(usuarioActual instanceof Administrador))
            throw new Exception("Acceso denegado.");
    }

    private Cliente getClienteActual() throws Exception {
        if (!(usuarioActual instanceof Cliente))
            throw new Exception("Debe iniciar sesion como cliente.");
        return (Cliente) usuarioActual;
    }

    public Producto buscarProductoPorId(int id) {
        for (int i = 0; i < productos.size(); i++)
            if (productos.get(i).getId() == id) return productos.get(i);
        return null;
    }

    public ArrayList<Producto> getProductos()    { return productos; }
    public ArrayList<Usuario>  getUsuarios()     { return usuarios; }
    public Usuario             getUsuarioActual(){ return usuarioActual; }
    public boolean esAdmin()   { return usuarioActual instanceof Administrador; }
    public boolean esCliente() { return usuarioActual instanceof Cliente; }
}
