package gui;

import javax.swing.*;

public class YugiHoGui extends  JFrame{
    private JButton btnDuelo;
    private JLabel Imagen1;
    private JLabel Imagen2;
    private JButton btnCartasMaquina;
    private JButton Selectbutton1;
    private JButton SelectButton2;
    private JButton SelectButton3;
    private JLabel CardPlayer1;
    private JLabel CardPlayer2;
    private JLabel CardPlayer3;
    private JLabel CardMaquina3;
    private JLabel CardMaquina2;
    private JLabel CardMaquina1;
    private JButton btnMisCartas;
    private JButton selecionarCartaMaquinaButton;
    private JPanel panelPrincipal;


    public YugiHoGui() {
        

    }
    
    public static void verFrame() {
        JFrame frame = new JFrame("YugiHoGUi");
        frame.setContentPane(new YugiHoGui().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public JPanel getPanelPrincipal(){
        return panelPrincipal;
    }
    
    public JButton getBtnDuelo(){return btnDuelo; }
    public JButton getBtnCartasMaquina(){ return  btnCartasMaquina; }
    public JButton getBtnMisCartas(){ return btnMisCartas; }


}
