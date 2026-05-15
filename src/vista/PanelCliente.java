package vista;

import sistema.SistemaEcommerce;
import modelo.*;
import pago.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCliente extends JPanel {

    private VentanaPrincipal ventana;
    private SistemaEcommerce sistema;

    private PanelCatalogo     panelCatalogo;
    private JTable            tablaCarrito, tablaHistorial;
    private DefaultTableModel modeloCarrito, modeloHistorial;
    private JLabel            lblTotal, lblBienvenido, lblEstado;
    private JTabbedPane       pestanas;

    public PanelCliente(VentanaPrincipal ventana, SistemaEcommerce sistema) {
        this.ventana = ventana;
        this.sistema = sistema;
        setLayout(new BorderLayout());
        setBackground(VentanaPrincipal.FONDO);
        add(construirHeader(), BorderLayout.NORTH);

        pestanas = new JTabbedPane(JTabbedPane.TOP);
        pestanas.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Pestaña 1: catalogo de tarjetas
        panelCatalogo = new PanelCatalogo(sistema, new PanelCatalogo.OnCarritoActualizado() {
            @Override
            public void actualizar() {
                actualizarTablaCarrito();
                actualizarTituloCarrito();
            }
        });
        pestanas.addTab("  Catalogo  ",    panelCatalogo);
        pestanas.addTab("  Carrito  ",     construirCarrito());
        pestanas.addTab("  Mis Pedidos  ", construirHistorial());
        add(pestanas, BorderLayout.CENTER);
        add(construirPie(), BorderLayout.SOUTH);
    }

    private JPanel construirHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(VentanaPrincipal.NEGRO);

        JPanel barraRoja = new JPanel();
        barraRoja.setBackground(VentanaPrincipal.ROJO);
        barraRoja.setPreferredSize(new Dimension(0, 4));
        h.add(barraRoja, BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(VentanaPrincipal.NEGRO);
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        izq.setBackground(VentanaPrincipal.NEGRO);
        JLabel lbl1 = new JLabel("Shop"); lbl1.setFont(new Font("Segoe UI", Font.BOLD, 20)); lbl1.setForeground(Color.WHITE);
        JLabel lbl2 = new JLabel("Phone"); lbl2.setFont(new Font("Segoe UI", Font.BOLD, 20)); lbl2.setForeground(VentanaPrincipal.ROJO);
        lblBienvenido = new JLabel("");
        lblBienvenido.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblBienvenido.setForeground(new Color(150, 150, 150));
        izq.add(lbl1); izq.add(lbl2); izq.add(Box.createHorizontalStrut(6)); izq.add(lblBienvenido);

        JButton btnSalir = VentanaPrincipal.crearBoton("Cerrar sesion", VentanaPrincipal.ROJO);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { sistema.logout(); ventana.mostrarLogin(); }
        });

        contenido.add(izq, BorderLayout.WEST);
        contenido.add(btnSalir, BorderLayout.EAST);
        h.add(contenido, BorderLayout.CENTER);
        return h;
    }

    private JPanel construirCarrito() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(VentanaPrincipal.FONDO);
        p.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        String[] cols = {"ID", "Nombre", "Precio", "Cantidad", "Subtotal"};
        modeloCarrito = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaCarrito = tablaEstilizada(modeloCarrito);
        tablaCarrito.getColumnModel().getColumn(0).setPreferredWidth(45);
        tablaCarrito.getColumnModel().getColumn(1).setPreferredWidth(280);
        tablaCarrito.getColumnModel().getColumn(2).setPreferredWidth(110);
        tablaCarrito.getColumnModel().getColumn(3).setPreferredWidth(75);
        tablaCarrito.getColumnModel().getColumn(4).setPreferredWidth(115);

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 8));
        izq.setBackground(VentanaPrincipal.FONDO);
        JButton btnElim   = VentanaPrincipal.crearBoton("Eliminar", VentanaPrincipal.ROJO);
        JButton btnVaciar = VentanaPrincipal.crearBoton("Vaciar carrito", new Color(130, 130, 130));
        btnElim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaCarrito.getSelectedRow();
                if (fila < 0) { aviso("Seleccione un item."); return; }
                int id = (int) modeloCarrito.getValueAt(fila, 0);
                try { sistema.eliminarDelCarrito(id); actualizarTablaCarrito(); actualizarTituloCarrito(); }
                catch (Exception ex) { error(ex.getMessage()); }
            }
        });
        btnVaciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sistema.esCliente()) {
                    ((Cliente) sistema.getUsuarioActual()).getCarrito().vaciar();
                    actualizarTablaCarrito(); actualizarTituloCarrito();
                }
            }
        });
        izq.add(btnElim); izq.add(btnVaciar);

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        der.setBackground(VentanaPrincipal.FONDO);
        lblTotal = new JLabel("Total: $0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTotal.setForeground(VentanaPrincipal.NEGRO);
        JButton btnPagar = VentanaPrincipal.crearBoton("PAGAR AHORA", VentanaPrincipal.ROJO);
        btnPagar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnPagar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { ejecutarCompra(); }
        });
        der.add(lblTotal); der.add(btnPagar);

        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(VentanaPrincipal.FONDO);
        barra.add(izq, BorderLayout.WEST); barra.add(der, BorderLayout.EAST);

        p.add(scroll(tablaCarrito), BorderLayout.CENTER);
        p.add(barra, BorderLayout.SOUTH);
        return p;
    }

    private JPanel construirHistorial() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(VentanaPrincipal.FONDO);
        p.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        String[] cols = {"# Pedido", "Fecha", "Total", "Estado", "Metodo Pago"};
        modeloHistorial = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaHistorial = tablaEstilizada(modeloHistorial);

        JButton btnDet = VentanaPrincipal.crearBoton("Ver detalle", VentanaPrincipal.NEGRO);
        btnDet.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { mostrarDetalle(); }
        });
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        barra.setBackground(VentanaPrincipal.FONDO);
        barra.add(btnDet);

        p.add(scroll(tablaHistorial), BorderLayout.CENTER);
        p.add(barra, BorderLayout.SOUTH);
        return p;
    }

    private JPanel construirPie() {
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 6));
        pie.setBackground(VentanaPrincipal.NEGRO);
        lblEstado = new JLabel("Datos guardados automaticamente en datos/");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstado.setForeground(new Color(100, 100, 100));
        pie.add(lblEstado);
        return pie;
    }

    private void ejecutarCompra() {
        if (!sistema.esCliente()) return;
        Cliente cliente = (Cliente) sistema.getUsuarioActual();
        if (cliente.getCarrito().estaVacio()) { aviso("El carrito esta vacio."); return; }
        double total = cliente.getCarrito().calcularTotal();
        String[] ops = {"Efectivo", "Tarjeta Debito", "Tarjeta Credito"};
        int sel = JOptionPane.showOptionDialog(this,
                "Total:  $" + fmt(total) + "\n\nMetodo de pago:",
                "Confirmar compra", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, ops, ops[0]);
        if (sel < 0) return;
        Pago pago;
        try {
            if (sel == 0) {
                String ent = JOptionPane.showInputDialog(this,
                        "Total: $" + fmt(total) + "\nMonto recibido:", "Efectivo", JOptionPane.QUESTION_MESSAGE);
                if (ent == null) return;
                double pagado = Double.parseDouble(ent.replaceAll("[^0-9.]", ""));
                if (pagado < total) { error("Monto insuficiente."); return; }
                pago = new PagoEfectivo(pagado);
            } else {
                JTextField n = new JTextField("4111111111111111");
                JTextField t = new JTextField(cliente.getNombre());
                JPanel f = new JPanel(new GridLayout(4, 1, 4, 4));
                f.add(new JLabel("Numero de tarjeta:")); f.add(n);
                f.add(new JLabel("Titular:"));           f.add(t);
                if (JOptionPane.showConfirmDialog(this, f, "Datos tarjeta",
                        JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
                pago = new PagoTarjeta(n.getText().trim(), t.getText().trim(),
                        sel == 1 ? "Debito" : "Credito");
            }
            Pedido pedido = sistema.realizarPedido(pago);
            JOptionPane.showMessageDialog(this,
                    "Compra exitosa!\n\n" + pedido.getComprobantePago() + "\n\nGracias, " + cliente.getNombre() + "!",
                    "Pago aprobado", JOptionPane.INFORMATION_MESSAGE);
            panelCatalogo.cargar();
            actualizarTablaCarrito(); actualizarTablaHistorial(); actualizarTituloCarrito();
            lblEstado.setText("  Compra guardada — datos/usuarios.dat y datos/productos.dat");
            lblEstado.setForeground(VentanaPrincipal.ROJO_CLARO);
        } catch (NumberFormatException e) {
            error("Ingresa un monto valido.");
        } catch (Exception e) { error(e.getMessage()); }
    }

    private void mostrarDetalle() {
        int fila = tablaHistorial.getSelectedRow();
        if (fila < 0) { aviso("Selecciona un pedido."); return; }
        int pedidoId = (int) modeloHistorial.getValueAt(fila, 0);
        Cliente c = (Cliente) sistema.getUsuarioActual();
        for (int i = 0; i < c.getHistorialPedidos().size(); i++) {
            Pedido p = c.getHistorialPedidos().get(i);
            if (p.getId() == pedidoId) {
                StringBuilder sb = new StringBuilder();
                sb.append("====== PEDIDO #").append(p.getId()).append(" ======\n");
                sb.append("Cliente : ").append(p.getClienteNombre()).append("\n");
                sb.append("Fecha   : ").append(p.getFechaFormateada()).append("\n");
                sb.append("Estado  : ").append(p.getEstado()).append("\n\n");
                sb.append("PRODUCTOS:\n------------------------------\n");
                for (int j = 0; j < p.getItems().size(); j++) sb.append("  ").append(p.getItems().get(j)).append("\n");
                sb.append("------------------------------\nTOTAL: $").append(fmt(p.getTotal())).append("\n\n");
                if (p.getComprobantePago() != null) sb.append(p.getComprobantePago());
                JTextArea area = new JTextArea(sb.toString());
                area.setFont(new Font("Monospaced", Font.PLAIN, 13));
                area.setEditable(false);
                JScrollPane sp = new JScrollPane(area);
                sp.setPreferredSize(new Dimension(460, 360));
                JOptionPane.showMessageDialog(this, sp, "Detalle #" + pedidoId, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }

    public void actualizar() {
        if (sistema.getUsuarioActual() != null)
            lblBienvenido.setText("·  Hola, " + sistema.getUsuarioActual().getNombre());
        panelCatalogo.cargar();
        actualizarTablaCarrito(); actualizarTablaHistorial(); actualizarTituloCarrito();
    }

    private void actualizarTablaCarrito() {
        modeloCarrito.setRowCount(0);
        if (!sistema.esCliente()) return;
        Cliente c = (Cliente) sistema.getUsuarioActual();
        double total = 0;
        for (int i = 0; i < c.getCarrito().getItems().size(); i++) {
            ItemCarrito item = c.getCarrito().getItems().get(i);
            modeloCarrito.addRow(new Object[]{
                item.getProducto().getId(), item.getProducto().getNombre(),
                "$" + fmt(item.getProducto().getPrecio()), item.getCantidad(), "$" + fmt(item.getSubtotal())
            });
            total += item.getSubtotal();
        }
        lblTotal.setText("Total: $" + fmt(total));
    }

    private void actualizarTablaHistorial() {
        modeloHistorial.setRowCount(0);
        if (!sistema.esCliente()) return;
        Cliente c = (Cliente) sistema.getUsuarioActual();
        java.util.ArrayList<Pedido> pedidos = c.getHistorialPedidos();
        for (int i = pedidos.size() - 1; i >= 0; i--) {
            Pedido p = pedidos.get(i);
            modeloHistorial.addRow(new Object[]{
                p.getId(), p.getFechaFormateada(), "$" + fmt(p.getTotal()),
                p.getEstado(), p.getTipoPago() != null ? p.getTipoPago() : "-"
            });
        }
    }

    private void actualizarTituloCarrito() {
        if (!sistema.esCliente()) return;
        int u = ((Cliente) sistema.getUsuarioActual()).getCarrito().getTotalUnidades();
        pestanas.setTitleAt(1, u > 0 ? "  Carrito (" + u + ")  " : "  Carrito  ");
    }

    private JTable tablaEstilizada(DefaultTableModel m) {
        JTable t = new JTable(m) {
            @Override
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row))
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                return c;
            }
        };
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setRowHeight(30);
        t.setShowVerticalLines(false);
        t.setGridColor(new Color(235, 235, 235));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.getTableHeader().setBackground(VentanaPrincipal.NEGRO);
        t.getTableHeader().setForeground(Color.WHITE);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setSelectionBackground(new Color(255, 235, 235));
        t.setSelectionForeground(VentanaPrincipal.NEGRO);
        return t;
    }

    private JScrollPane scroll(JTable t) {
        JScrollPane s = new JScrollPane(t);
        s.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        s.getViewport().setBackground(Color.WHITE);
        return s;
    }

    private String fmt(double v) { return String.format("%,.0f", v); }
    private void aviso(String m) { JOptionPane.showMessageDialog(this, m, "Aviso", JOptionPane.WARNING_MESSAGE); }
    private void error(String m) { JOptionPane.showMessageDialog(this, m, "Error", JOptionPane.ERROR_MESSAGE); }
}
