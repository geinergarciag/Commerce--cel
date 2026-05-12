package vista;

import modelo.Producto;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class PanelTarjetaProducto extends JPanel {

    public interface OnAgregarListener {
        void onAgregar(Producto producto);
    }

    public PanelTarjetaProducto(Producto producto, OnAgregarListener listener) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(210, 200));
        setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(18, 18, 18, 18)));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        // Efecto hover
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                setBorder(new CompoundBorder(
                        new LineBorder(VentanaPrincipal.ROJO, 2),
                        new EmptyBorder(17, 17, 17, 17)));
            }
            @Override public void mouseExited(MouseEvent e) {
                setBorder(new CompoundBorder(
                        new LineBorder(new Color(230, 230, 230), 1),
                        new EmptyBorder(18, 18, 18, 18)));
            }
        });

        // ── Contenido ─────────────────────────────────────────────────────────
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(Color.WHITE);

        // Badge categoria
        JLabel lblCat = new JLabel(" " + producto.getCategoria().toUpperCase() + " ");
        lblCat.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblCat.setForeground(Color.WHITE);
        lblCat.setBackground(colorCategoria(producto.getCategoria()));
        lblCat.setOpaque(true);
        lblCat.setBorder(BorderFactory.createEmptyBorder(3, 7, 3, 7));
        lblCat.setAlignmentX(LEFT_ALIGNMENT);

        // Nombre (puede ser largo, se hace wrap con html)
        JLabel lblNombre = new JLabel("<html>" + producto.getNombre() + "</html>");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombre.setForeground(new Color(20, 20, 20));
        lblNombre.setAlignmentX(LEFT_ALIGNMENT);

        // Precio
        JLabel lblPrecio = new JLabel("$" + String.format("%,.0f", producto.getPrecio()));
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPrecio.setForeground(VentanaPrincipal.NEGRO);
        lblPrecio.setAlignmentX(LEFT_ALIGNMENT);

        // Stock
        JLabel lblStock = new JLabel(producto.hayStock() ? "Stock: " + producto.getStock() : "Agotado");
        lblStock.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStock.setForeground(producto.hayStock() ? new Color(46, 125, 50) : VentanaPrincipal.ROJO);
        lblStock.setAlignmentX(LEFT_ALIGNMENT);

        // Boton
        JButton btnAgregar = new JButton(producto.hayStock() ? "+ Agregar" : "Agotado");
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAgregar.setBackground(producto.hayStock() ? VentanaPrincipal.ROJO : new Color(200, 200, 200));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setEnabled(producto.hayStock());
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.setAlignmentX(LEFT_ALIGNMENT);
        btnAgregar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        btnAgregar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if (listener != null) listener.onAgregar(producto);
            }
        });

        contenido.add(lblCat);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(lblNombre);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(lblPrecio);
        contenido.add(Box.createVerticalStrut(4));
        contenido.add(lblStock);
        contenido.add(Box.createVerticalGlue());
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(btnAgregar);

        add(contenido, BorderLayout.CENTER);
    }

    private Color colorCategoria(String cat) {
        if (cat.equals("Gama Alta"))  return new Color(183, 28, 28);
        if (cat.equals("Gama Media")) return new Color(33, 33, 33);
        if (cat.equals("Gama Baja"))  return new Color(90, 90, 90);
        return new Color(120, 120, 120);
    }
}
