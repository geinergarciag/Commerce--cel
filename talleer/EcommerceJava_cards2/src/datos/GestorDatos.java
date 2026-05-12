package datos;

import modelo.Producto;
import modelo.Usuario;
import java.io.*;
import java.util.ArrayList;

/**
 * Maneja la persistencia con archivos .dat usando serializacion de Java.
 * Los datos se guardan automaticamente en la carpeta datos/ y sobreviven
 * al cierre del programa.
 */
public class GestorDatos {

    private static final String CARPETA   = "datos" + File.separator;
    private static final String USUARIOS  = CARPETA + "usuarios.dat";
    private static final String PRODUCTOS = CARPETA + "productos.dat";

    public GestorDatos() {
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Usuario> cargarUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USUARIOS))) {
            return (ArrayList<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // primera ejecucion, archivo no existe aun
        } catch (Exception e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarUsuarios(ArrayList<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USUARIOS))) {
            oos.writeObject(usuarios);
            System.out.println("[Auto-guardado] " + USUARIOS + " (" + usuarios.size() + " registros)");
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Producto> cargarProductos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRODUCTOS))) {
            return (ArrayList<Producto>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarProductos(ArrayList<Producto> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCTOS))) {
            oos.writeObject(productos);
            System.out.println("[Auto-guardado] " + PRODUCTOS + " (" + productos.size() + " registros)");
        } catch (IOException e) {
            System.err.println("Error al guardar productos: " + e.getMessage());
        }
    }
}
