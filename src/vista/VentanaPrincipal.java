package vista;

import sistema.SistemaEcommerce;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private SistemaEcommerce sistema;
    private CardLayout       cardLayout;
    private JPanel           panelContenedor;
    private PanelLogin       panelLogin;
    private PanelCliente     panelCliente;
    private PanelAdmin       panelAdmin;

    // ── Paleta negro + rojo ───────────────────────────────────────────────────
    public static final Color NEGRO       = new Color(18, 18, 18);
    public static final Color NEGRO_SUAVE = new Color(33, 33, 33);
    public static final Color NEGRO_CARD  = new Color(45, 45, 45);
    public static final Color ROJO        = new Color(198, 40, 40);
    public static final Color ROJO_CLARO  = new Color(229, 57, 53);
    public static final Color FONDO       = new Color(245, 245, 245);
    public static final Color BLANCO      = Color.WHITE;
    public static final Color VERDE       = new Color(27, 94, 32);
    public static final Color GRIS_TEXTO  = new Color(90, 90, 90);

    // Aliases para que el resto del codigo no cambie
    public static final Color COLOR_PRIMARIO = ROJO;
    public static final Color COLOR_ACENTO   = ROJO_CLARO;
    public static final Color COLOR_FONDO    = FONDO;
    public static final Color COLOR_EXITO    = VERDE;
    public static final Color COLOR_PELIGRO  = ROJO;

    public VentanaPrincipal() {
        sistema = new SistemaEcommerce();
        setTitle("Shop Phone");
        setSize(1080, 720);
        setMinimumSize(new Dimension(920, 620));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);

        cardLayout      = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(FONDO);

        panelLogin   = new PanelLogin(this, sistema);
        panelCliente = new PanelCliente(this, sistema);
        panelAdmin   = new PanelAdmin(this, sistema);

        panelContenedor.add(panelLogin,   "login");
        panelContenedor.add(panelCliente, "cliente");
        panelContenedor.add(panelAdmin,   "admin");
        add(panelContenedor);

        mostrarLogin();
    }

    public void mostrarLogin() {
        panelLogin.limpiar();
        cardLayout.show(panelContenedor, "login");
        setTitle("Shop Phone");
    }

    public void mostrarPanelCliente() {
        panelCliente.actualizar();
        cardLayout.show(panelContenedor, "cliente");
        setTitle("Shop Phone - " + sistema.getUsuarioActual().getNombre());
    }

    public void mostrarPanelAdmin() {
        panelAdmin.actualizar();
        cardLayout.show(panelContenedor, "admin");
        setTitle("Shop Phone - Administracion");
    }

    public SistemaEcommerce getSistema() { return sistema; }

    public static JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(BLANCO);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(9, 20, 9, 20));
        btn.setOpaque(true);
        return btn;
    }

    public static JTextField crearCampo() {
        JTextField f = new JTextField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        f.setBackground(new Color(250, 250, 250));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        return f;
    }

    public static JPasswordField crearCampoPassword() {
        JPasswordField f = new JPasswordField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        f.setBackground(new Color(250, 250, 250));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        return f;
    }

    public static JLabel crearEtiquetaCampo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(GRIS_TEXTO);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }
}
