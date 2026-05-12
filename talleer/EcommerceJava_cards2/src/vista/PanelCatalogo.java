package vista;

import sistema.SistemaEcommerce;
import modelo.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelCatalogo extends JPanel {

    private SistemaEcommerce sistema;
    private OnCarritoActualizado carritoListener;
    private JPanel       grilla;
    private JTextField   txtBuscar;
    private JComboBox<String> cbCategoria;
    private JLabel       lblContador;

    public interface OnCarritoActualizado {
        void actualizar();
    }

    public PanelCatalogo(SistemaEcommerce sistema, OnCarritoActualizado listener) {
        this.sistema         = sistema;
        this.carritoListener = listener;
        setLayout(new BorderLayout());
        setBackground(VentanaPrincipal.FONDO);
        add(construirBarra(), BorderLayout.NORTH);
        add(construirGrilla(), BorderLayout.CENTER);
    }

    private JPanel construirBarra() {
        JPanel barra = new JPanel(new BorderLayout(10, 0));
        barra.setBackground(VentanaPrincipal.FONDO);
        barra.setBorder(BorderFactory.createEmptyBorder(14, 14, 8, 14));

        txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) { filtrar(); }
        });

        cbCategoria = new JComboBox<>(new String[]{
            "Todas", "Gama Alta", "Gama Media", "Gama Baja", "Accesorios"
        });
        cbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbCategoria.setPreferredSize(new Dimension(155, 36));
        cbCategoria.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { filtrar(); }
        });

        lblContador = new JLabel("");
        lblContador.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblContador.setForeground(new Color(160, 160, 160));

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        der.setBackground(VentanaPrincipal.FONDO);
        JLabel lbl = new JLabel("Categoria:");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        der.add(lbl); der.add(cbCategoria); der.add(lblContador);

        barra.add(txtBuscar, BorderLayout.CENTER);
        barra.add(der,       BorderLayout.EAST);
        return barra;
    }

    private JScrollPane construirGrilla() {
        grilla = new JPanel(new WrapLayout(FlowLayout.LEFT, 12, 12));
        grilla.setBackground(VentanaPrincipal.FONDO);
        grilla.setBorder(BorderFactory.createEmptyBorder(4, 10, 14, 10));

        JScrollPane scroll = new JScrollPane(grilla);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getViewport().setBackground(VentanaPrincipal.FONDO);
        return scroll;
    }

    public void cargar() { filtrar(); }

    private void filtrar() {
        String texto = txtBuscar.getText().toLowerCase().trim();
        String cat   = (String) cbCategoria.getSelectedItem();

        ArrayList<Producto> todos     = sistema.getProductos();
        ArrayList<Producto> filtrados = new ArrayList<>();
        for (int i = 0; i < todos.size(); i++) {
            Producto p = todos.get(i);
            boolean matchTexto = texto.isEmpty()
                    || p.getNombre().toLowerCase().contains(texto)
                    || p.getDescripcion().toLowerCase().contains(texto);
            boolean matchCat = cat.equals("Todas") || p.getCategoria().equals(cat);
            if (matchTexto && matchCat) filtrados.add(p);
        }

        grilla.removeAll();
        for (int i = 0; i < filtrados.size(); i++) {
            final Producto prod = filtrados.get(i);
            grilla.add(new PanelTarjetaProducto(prod, new PanelTarjetaProducto.OnAgregarListener() {
                @Override public void onAgregar(Producto producto) { agregarAlCarrito(producto); }
            }));
        }

        if (filtrados.isEmpty()) {
            JLabel lbl = new JLabel("No se encontraron productos.");
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lbl.setForeground(new Color(180, 180, 180));
            grilla.add(lbl);
        }

        lblContador.setText(filtrados.size() + " producto" + (filtrados.size() != 1 ? "s" : ""));
        grilla.revalidate();
        grilla.repaint();
    }

    private void agregarAlCarrito(Producto producto) {
        SpinnerNumberModel modelo = new SpinnerNumberModel(1, 1, producto.getStock(), 1);
        JSpinner spinner = new JSpinner(modelo);
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPanel msg = new JPanel(new BorderLayout(8, 0));
        msg.add(new JLabel("Cantidad:"), BorderLayout.WEST);
        msg.add(spinner, BorderLayout.CENTER);
        int res = JOptionPane.showConfirmDialog(this, msg,
                producto.getNombre(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res != JOptionPane.OK_OPTION) return;
        try {
            sistema.agregarAlCarrito(producto.getId(), (int) spinner.getValue());
            if (carritoListener != null) carritoListener.actualizar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
