package vista;

import sistema.SistemaEcommerce;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelLogin extends JPanel {

    private VentanaPrincipal ventana;
    private SistemaEcommerce sistema;

    private JTextField     txtEmail;
    private JPasswordField txtContrasena;
    private JLabel         lblError;

    private JTextField     txtNombre;
    private JTextField     txtEmailReg;
    private JPasswordField txtContrasenaReg;
    private JTextField     txtDireccion;
    private JTextField     txtTelefono;
    private JLabel         lblErrorReg;

    private CardLayout cardInterno;
    private JPanel     contenedorInterno;

    public PanelLogin(VentanaPrincipal ventana, SistemaEcommerce sistema) {
        this.ventana = ventana;
        this.sistema = sistema;
        setLayout(new BorderLayout());
        setBackground(VentanaPrincipal.NEGRO);

        cardInterno       = new CardLayout();
        contenedorInterno = new JPanel(cardInterno);
        contenedorInterno.setBackground(VentanaPrincipal.NEGRO);
        contenedorInterno.add(construirLogin(),    "login");
        contenedorInterno.add(construirRegistro(), "registro");
        add(contenedorInterno);
    }

    // ── LOGIN: panel izquierdo oscuro + panel derecho blanco ──────────────────
    private JPanel construirLogin() {
        JPanel raiz = new JPanel(new GridLayout(1, 2));
        raiz.add(construirPanelIzquierdo());
        raiz.add(construirFormularioLogin());
        return raiz;
    }

    // Panel izquierdo: fondo negro con nombre de la app y descripcion
    private JPanel construirPanelIzquierdo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(VentanaPrincipal.NEGRO);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        // Relleno superior para centrar verticalmente
        panel.add(Box.createVerticalGlue());

        // Rectangulo rojo decorativo
        JPanel lineaRoja = new JPanel();
        lineaRoja.setBackground(VentanaPrincipal.ROJO);
        lineaRoja.setMaximumSize(new Dimension(60, 5));
        lineaRoja.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblApp = new JLabel("Phone");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 52));
        lblApp.setForeground(VentanaPrincipal.BLANCO);
        lblApp.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblStore = new JLabel("Store");
        lblStore.setFont(new Font("Segoe UI", Font.BOLD, 52));
        lblStore.setForeground(VentanaPrincipal.ROJO);
        lblStore.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDesc = new JLabel("<html>Los mejores celulares<br>al mejor precio.</html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDesc.setForeground(new Color(180, 180, 180));
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Linea separadora
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(60, 60, 60));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JLabel lblCategorias = new JLabel("Gama Alta  ·  Gama Media  ·  Accesorios");
        lblCategorias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCategorias.setForeground(new Color(120, 120, 120));
        lblCategorias.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lineaRoja);
        panel.add(Box.createVerticalStrut(18));
        panel.add(lblApp);
        panel.add(lblStore);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblDesc);
        panel.add(Box.createVerticalStrut(24));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(14));
        panel.add(lblCategorias);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    // Panel derecho: formulario blanco
    private JPanel construirFormularioLogin() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(VentanaPrincipal.BLANCO);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(VentanaPrincipal.BLANCO);
        form.setBorder(BorderFactory.createEmptyBorder(0, 55, 0, 55));
        form.setPreferredSize(new Dimension(400, 420));

        JLabel lblTitulo = new JLabel("Iniciar sesion");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(VentanaPrincipal.NEGRO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Ingresa tus credenciales");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(150, 150, 150));
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtEmail      = VentanaPrincipal.crearCampo();
        txtContrasena = VentanaPrincipal.crearCampoPassword();
        txtContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { ejecutarLogin(); }
        });

        lblError = new JLabel(" ");
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblError.setForeground(VentanaPrincipal.ROJO);
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnLogin = VentanaPrincipal.crearBoton("INGRESAR", VentanaPrincipal.ROJO);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { ejecutarLogin(); }
        });

        // Separador visual "o"
        JPanel filaSep = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        filaSep.setBackground(VentanaPrincipal.BLANCO);
        filaSep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        JLabel lblO = new JLabel("¿No tienes cuenta?");
        lblO.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblO.setForeground(new Color(150, 150, 150));
        JButton btnIrReg = linkBtn("Registrate");
        btnIrReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cardInterno.show(contenedorInterno, "registro"); }
        });
        filaSep.add(lblO); filaSep.add(btnIrReg);

        JLabel lblHint = new JLabel("Demo: admin@tienda.com / admin123");
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblHint.setForeground(new Color(200, 200, 200));
        lblHint.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(lblTitulo);
        form.add(Box.createVerticalStrut(4));
        form.add(lblSub);
        form.add(Box.createVerticalStrut(32));
        form.add(VentanaPrincipal.crearEtiquetaCampo("CORREO ELECTRONICO"));
        form.add(Box.createVerticalStrut(6)); form.add(txtEmail);
        form.add(Box.createVerticalStrut(18));
        form.add(VentanaPrincipal.crearEtiquetaCampo("CONTRASENA"));
        form.add(Box.createVerticalStrut(6)); form.add(txtContrasena);
        form.add(Box.createVerticalStrut(6)); form.add(lblError);
        form.add(Box.createVerticalStrut(18)); form.add(btnLogin);
        form.add(Box.createVerticalStrut(16)); form.add(filaSep);
        form.add(Box.createVerticalStrut(20)); form.add(lblHint);

        outer.add(form);
        return outer;
    }

    // ── REGISTRO ──────────────────────────────────────────────────────────────
    private JPanel construirRegistro() {
        JPanel raiz = new JPanel(new GridLayout(1, 2));
        raiz.add(construirPanelIzquierdoReg());
        raiz.add(construirFormularioRegistro());
        return raiz;
    }

    private JPanel construirPanelIzquierdoReg() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(VentanaPrincipal.NEGRO_SUAVE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        panel.add(Box.createVerticalGlue());

        JLabel lbl1 = new JLabel("Crea tu");
        lbl1.setFont(new Font("Segoe UI", Font.PLAIN, 38));
        lbl1.setForeground(new Color(200, 200, 200));
        lbl1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl2 = new JLabel("cuenta");
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lbl2.setForeground(VentanaPrincipal.BLANCO);
        lbl2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl3 = new JLabel("gratis.");
        lbl3.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lbl3.setForeground(VentanaPrincipal.ROJO);
        lbl3.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("<html><br>Accede al catalogo completo<br>y compra desde donde estes.</html>");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(140, 140, 140));
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl1); panel.add(lbl2); panel.add(lbl3);
        panel.add(Box.createVerticalStrut(10)); panel.add(lblSub);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel construirFormularioRegistro() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(VentanaPrincipal.BLANCO);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(VentanaPrincipal.BLANCO);
        form.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        form.setPreferredSize(new Dimension(400, 520));

        JLabel lblTitulo = new JLabel("Registro");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(VentanaPrincipal.NEGRO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNombre        = VentanaPrincipal.crearCampo();
        txtEmailReg      = VentanaPrincipal.crearCampo();
        txtContrasenaReg = VentanaPrincipal.crearCampoPassword();
        txtDireccion     = VentanaPrincipal.crearCampo();
        txtTelefono      = VentanaPrincipal.crearCampo();

        lblErrorReg = new JLabel(" ");
        lblErrorReg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblErrorReg.setForeground(VentanaPrincipal.ROJO);
        lblErrorReg.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnReg = VentanaPrincipal.crearBoton("CREAR CUENTA", VentanaPrincipal.NEGRO);
        btnReg.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnReg.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { ejecutarRegistro(); }
        });

        JButton btnVolver = linkBtn("← Volver al login");
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cardInterno.show(contenedorInterno, "login"); }
        });

        form.add(lblTitulo); form.add(Box.createVerticalStrut(22));
        agregarCampo(form, "NOMBRE COMPLETO *",       txtNombre);
        agregarCampo(form, "CORREO ELECTRONICO *",    txtEmailReg);
        agregarCampo(form, "CONTRASENA * (min. 4)",   txtContrasenaReg);
        agregarCampo(form, "DIRECCION DE ENTREGA",    txtDireccion);
        agregarCampo(form, "TELEFONO",                txtTelefono);
        form.add(Box.createVerticalStrut(4)); form.add(lblErrorReg);
        form.add(Box.createVerticalStrut(14)); form.add(btnReg);
        form.add(Box.createVerticalStrut(10)); form.add(btnVolver);

        outer.add(form);
        return outer;
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo) {
        panel.add(VentanaPrincipal.crearEtiquetaCampo(etiqueta));
        panel.add(Box.createVerticalStrut(5));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(12));
    }

    private void ejecutarLogin() {
        String email = txtEmail.getText().trim();
        String pass  = new String(txtContrasena.getPassword());
        try {
            sistema.login(email, pass);
            lblError.setText(" ");
            if (sistema.esAdmin()) ventana.mostrarPanelAdmin();
            else                   ventana.mostrarPanelCliente();
        } catch (Exception ex) {
            lblError.setText(ex.getMessage());
        }
    }

    private void ejecutarRegistro() {
        try {
            sistema.registrarCliente(
                txtNombre.getText(), txtEmailReg.getText(),
                new String(txtContrasenaReg.getPassword()),
                txtDireccion.getText(), txtTelefono.getText());
            JOptionPane.showMessageDialog(this,
                    "Cuenta creada exitosamente. Ya puedes iniciar sesion.",
                    "Listo", JOptionPane.INFORMATION_MESSAGE);
            limpiarRegistro();
            cardInterno.show(contenedorInterno, "login");
        } catch (Exception ex) {
            lblErrorReg.setText(ex.getMessage());
        }
    }

    public void limpiar() {
        txtEmail.setText(""); txtContrasena.setText(""); lblError.setText(" ");
        limpiarRegistro();
        cardInterno.show(contenedorInterno, "login");
    }

    private void limpiarRegistro() {
        txtNombre.setText(""); txtEmailReg.setText(""); txtContrasenaReg.setText("");
        txtDireccion.setText(""); txtTelefono.setText(""); lblErrorReg.setText(" ");
    }

    private JButton linkBtn(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setForeground(VentanaPrincipal.ROJO);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setFocusPainted(false);
        return b;
    }
}
