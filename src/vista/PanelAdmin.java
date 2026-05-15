package vista;

import sistema.SistemaEcommerce;
import modelo.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAdmin extends JPanel {

    private VentanaPrincipal ventana;
    private SistemaEcommerce sistema;

    private JTable            tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtDescripcion, txtPrecio, txtStock, txtCategoria, txtImagenUrl;
    private JLabel     lblFormTitulo, lblEstado;
    private int        idEditando = -1;

    public PanelAdmin(VentanaPrincipal ventana, SistemaEcommerce sistema) {
        this.ventana = ventana;
        this.sistema = sistema;
        setLayout(new BorderLayout());
        setBackground(VentanaPrincipal.FONDO);
        add(construirHeader(), BorderLayout.NORTH);
        JPanel centro = new JPanel(new BorderLayout(12, 0));
        centro.setBackground(VentanaPrincipal.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(14, 14, 8, 14));
        centro.add(construirTabla(),      BorderLayout.CENTER);
        centro.add(construirFormulario(), BorderLayout.EAST);
        add(centro, BorderLayout.CENTER);
        add(construirPie(), BorderLayout.SOUTH);
    }

    private JPanel construirHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(VentanaPrincipal.NEGRO);
        h.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Barra roja delgada arriba del todo
        JPanel barraRoja = new JPanel();
        barraRoja.setBackground(VentanaPrincipal.ROJO);
        barraRoja.setPreferredSize(new Dimension(0, 4));
        h.add(barraRoja, BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(VentanaPrincipal.NEGRO);
        contenido.setBorder(BorderFactory.createEmptyBorder(14, 22, 14, 22));

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        izq.setBackground(VentanaPrincipal.NEGRO);

        JLabel lblPS = new JLabel("Shop");
        lblPS.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPS.setForeground(VentanaPrincipal.BLANCO);

        JLabel lblStore = new JLabel("Phone");
        lblStore.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblStore.setForeground(VentanaPrincipal.ROJO);

        JLabel sep = new JLabel("|");
        sep.setForeground(new Color(60, 60, 60));
        sep.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        JLabel lblSec = new JLabel("Panel Administrador");
        lblSec.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSec.setForeground(new Color(160, 160, 160));

        izq.add(lblPS); izq.add(lblStore); izq.add(sep); izq.add(lblSec);

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        der.setBackground(VentanaPrincipal.NEGRO);
        if (sistema.getUsuarioActual() != null) {
            JLabel lblUser = new JLabel(sistema.getUsuarioActual().getNombre());
            lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblUser.setForeground(new Color(140, 140, 140));
            der.add(lblUser);
        }
        JButton btnSalir = VentanaPrincipal.crearBoton("Cerrar sesion", VentanaPrincipal.ROJO);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { sistema.logout(); ventana.mostrarLogin(); }
        });
        der.add(btnSalir);

        contenido.add(izq, BorderLayout.WEST);
        contenido.add(der, BorderLayout.EAST);
        h.add(contenido, BorderLayout.CENTER);
        return h;
    }

    private JPanel construirTabla() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(VentanaPrincipal.FONDO);

        JLabel titulo = new JLabel("Catalogo de Celulares");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(VentanaPrincipal.NEGRO);

        String[] cols = {"ID", "Nombre", "Precio", "Stock", "Categoria"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla) {
            // Filas alternadas para mejor lectura
            @Override
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                }
                return c;
            }
        };
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(30);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(235, 235, 235));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(VentanaPrincipal.NEGRO);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tabla.setSelectionBackground(new Color(255, 235, 235));
        tabla.setSelectionForeground(VentanaPrincipal.NEGRO);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(210);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(110);

        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = tabla.getSelectedRow();
                    if (fila >= 0) cargarEnFormulario(fila);
                }
            }
        });

        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        barra.setBackground(VentanaPrincipal.FONDO);
        JButton btnNuevo = VentanaPrincipal.crearBoton("+ Nuevo celular", VentanaPrincipal.NEGRO);
        JButton btnElim  = VentanaPrincipal.crearBoton("Eliminar", VentanaPrincipal.ROJO);
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { limpiarFormulario(); }
        });
        btnElim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { eliminarSeleccionado(); }
        });
        barra.add(btnNuevo); barra.add(btnElim);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scroll.getViewport().setBackground(Color.WHITE);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll,  BorderLayout.CENTER);
        panel.add(barra,   BorderLayout.SOUTH);
        return panel;
    }

    private JPanel construirFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(VentanaPrincipal.BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(22, 22, 22, 22)));
        panel.setPreferredSize(new Dimension(290, 0));

        // Barra roja izquierda decorativa simulada con titulo
        lblFormTitulo = new JLabel("Nuevo Celular");
        lblFormTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblFormTitulo.setForeground(VentanaPrincipal.ROJO);
        lblFormTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(240, 240, 240));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        txtNombre      = campo(); txtDescripcion = campo();
        txtPrecio      = campo(); txtStock       = campo();
        txtCategoria   = campo(); txtImagenUrl   = campo();

        JButton btnGuardar = VentanaPrincipal.crearBoton("GUARDAR", VentanaPrincipal.ROJO);
        btnGuardar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { guardar(); }
        });

        JButton btnLimpiar = VentanaPrincipal.crearBoton("Limpiar", new Color(160, 160, 160));
        btnLimpiar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnLimpiar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { limpiarFormulario(); }
        });

        panel.add(lblFormTitulo); panel.add(Box.createVerticalStrut(8));
        panel.add(sep);           panel.add(Box.createVerticalStrut(16));
        agregarCampo(panel, "Nombre *",       txtNombre);
        agregarCampo(panel, "Descripcion",    txtDescripcion);
        agregarCampo(panel, "Precio *",       txtPrecio);
        agregarCampo(panel, "Stock *",        txtStock);
        agregarCampo(panel, "Categoria",      txtCategoria);
        agregarCampo(panel, "URL Imagen",     txtImagenUrl);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnGuardar); panel.add(Box.createVerticalStrut(8));
        panel.add(btnLimpiar);
        return panel;
    }

    private JPanel construirPie() {
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 6));
        pie.setBackground(VentanaPrincipal.NEGRO);
        lblEstado = new JLabel("Datos guardados automaticamente en datos/productos.dat");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstado.setForeground(new Color(100, 100, 100));
        pie.add(lblEstado);
        return pie;
    }

    private void cargarEnFormulario(int fila) {
        idEditando = (int) modeloTabla.getValueAt(fila, 0);
        Producto p = sistema.buscarProductoPorId(idEditando);
        if (p == null) return;
        lblFormTitulo.setText("Editando producto");
        txtNombre.setText(p.getNombre()); txtDescripcion.setText(p.getDescripcion());
        txtPrecio.setText(String.valueOf((long) p.getPrecio()));
        txtStock.setText(String.valueOf(p.getStock())); txtCategoria.setText(p.getCategoria());
        txtImagenUrl.setText(p.getImagenUrl() != null ? p.getImagenUrl() : "");
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) { aviso("El nombre es obligatorio."); return; }
            double precio = Double.parseDouble(txtPrecio.getText().trim().replace(",", ""));
            int    stock  = Integer.parseInt(txtStock.getText().trim());
            String desc   = txtDescripcion.getText().trim();
            String cat    = txtCategoria.getText().trim();
            String imgUrl = txtImagenUrl.getText().trim();
            if (idEditando == -1) sistema.agregarProducto(nombre, desc, precio, stock, cat, imgUrl);
            else                  sistema.actualizarProducto(idEditando, nombre, desc, precio, stock, cat, imgUrl);
            ok(idEditando == -1 ? "Celular agregado." : "Celular actualizado.");
            lblEstado.setText("  Guardado — datos/productos.dat");
            lblEstado.setForeground(VentanaPrincipal.ROJO_CLARO);
            limpiarFormulario(); actualizarTabla();
        } catch (NumberFormatException e) {
            aviso("Precio y stock deben ser numeros.");
        } catch (Exception e) { error(e.getMessage()); }
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { aviso("Seleccione un celular."); return; }
        int    id     = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);
        int conf = JOptionPane.showConfirmDialog(this,
                "Eliminar \"" + nombre + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) return;
        try {
            sistema.eliminarProducto(id);
            limpiarFormulario(); actualizarTabla();
            lblEstado.setText("  Guardado — datos/productos.dat");
            lblEstado.setForeground(VentanaPrincipal.ROJO_CLARO);
        } catch (Exception e) { error(e.getMessage()); }
    }

    private void limpiarFormulario() {
        idEditando = -1;
        lblFormTitulo.setText("Nuevo Celular");
        txtNombre.setText(""); txtDescripcion.setText(""); txtPrecio.setText("");
        txtStock.setText(""); txtCategoria.setText(""); txtImagenUrl.setText(""); tabla.clearSelection();
    }

    public void actualizar() { actualizarTabla(); limpiarFormulario(); }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (int i = 0; i < sistema.getProductos().size(); i++) {
            Producto p = sistema.getProductos().get(i);
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), "$" + String.format("%,.0f", p.getPrecio()),
                p.getStock(), p.getCategoria()
            });
        }
    }

    private void agregarCampo(JPanel p, String lbl, JComponent campo) {
        JLabel l = new JLabel(lbl);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(VentanaPrincipal.GRIS_TEXTO);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l); p.add(Box.createVerticalStrut(4)); p.add(campo);
        p.add(Box.createVerticalStrut(11));
    }

    private JTextField campo() {
        JTextField f = new JTextField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        return f;
    }

    private void aviso(String m) { JOptionPane.showMessageDialog(this, m, "Aviso",  JOptionPane.WARNING_MESSAGE); }
    private void ok(String m)    { JOptionPane.showMessageDialog(this, m, "Listo",  JOptionPane.INFORMATION_MESSAGE); }
    private void error(String m) { JOptionPane.showMessageDialog(this, m, "Error",  JOptionPane.ERROR_MESSAGE); }
}
