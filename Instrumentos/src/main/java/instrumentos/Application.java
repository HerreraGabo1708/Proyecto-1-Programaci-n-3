package instrumentos;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        window = new JFrame();
        window.setContentPane(new JTabbedPane());

        instrumentos.presentation.tipos.Model tiposModel= new instrumentos.presentation.tipos.Model();
        instrumentos.presentation.tipos.View tiposView = new instrumentos.presentation.tipos.View();
        tiposController = new instrumentos.presentation.tipos.Controller(tiposView,tiposModel);

        instrumentos.Intrumentos.ViewIntrumentos intrumentosView = new instrumentos.Intrumentos.ViewIntrumentos();
        instrumentos.Calibraciones.ViewCalibraciones calibracionesView = new instrumentos.Calibraciones.ViewCalibraciones();
        instrumentos.AcercaDe.AcercaDe acercaDeView = new instrumentos.AcercaDe.AcercaDe();

        window.getContentPane().add("Tipos de Instrumento",tiposView.getPanel());
        window.getContentPane().add("Instrumentos",intrumentosView.getPrincipal());
        window.getContentPane().add("Calibraciones",calibracionesView.getPrincipal());
        window.getContentPane().add("Acerca de...",acercaDeView.getPrincipal());

        window.setSize(900,450);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Application.class.getResource("presentation/icons/icon.png"))).getImage());
        window.setTitle("SILAB: Sistema de Laboratorio Industrial");
        window.setVisible(true);
    }

    public static instrumentos.presentation.tipos.Controller tiposController;

    public static JFrame window;
    public static int MODE_EDIT=1;
    public static int MODE_CREATE=2;
}
