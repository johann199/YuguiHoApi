import controlador.YugiHoControlador;
import gui.YugiHoGui;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            YugiHoGui vista = new YugiHoGui();
            YugiHoControlador controlador = new YugiHoControlador(vista);
            vista.setVisible(true);
        });

    }
}