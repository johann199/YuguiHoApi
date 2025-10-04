package Duelo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class YugiHoGui {
    private JButton DueloButton;
    private JLabel Imagen1;
    private JLabel Imagen2;
    private JButton CartasMaquinaButton;
    private JButton Selectbutton1;
    private JButton SelectButton2;
    private JButton SelectButton3;
    private JLabel CardPlayer1;
    private JLabel CardPlayer2;
    private JLabel CardPlayer3;
    private JLabel CardMaquina3;
    private JLabel CardMaquina2;
    private JLabel CardMaquina1;
    private JButton MisCartasButton;
    private JButton selecionarCartaMaquinaButton;
    private Monster jugador;
    private Monster maquina;
    private ArrayList<Monster> cartasJugador = new ArrayList<>();
    private ArrayList<Monster> cartasMaquina = new ArrayList<>();

    public YugiHoGui() {
        CartasMaquinaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int random = makeMoster();
                Monster monsterObtenido = search(random);
                if (monsterObtenido != null) {
                    cartasMaquina.add(monsterObtenido);
                    maquina = monsterObtenido;
                    extraerData(monsterObtenido, 1);
                }
            }
        });

        MisCartasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int random = makeMoster();
                Monster monsterObtenido = search(random);
                if (monsterObtenido != null) {
                    cartasJugador.add(monsterObtenido);
                    jugador = monsterObtenido;
                    extraerData(monsterObtenido, 1);
                }
            }
        });
        DueloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (jugador != null && maquina != null) {
                    duelo();
                } else {
                    JOptionPane.showMessageDialog(null, "Se necesitan Selecionar las cartas");
                }
            }
        });
    }
    private void duelo (){
        Monster cardsJugador, cardsMaquina;
        int player, machine;
        if (cardsJugador.getAtk() > cardsMaquina.getDef()){
            cardsJugador = jugador;
            cardsMaquina = maquina;
            player = 1;
            machine = 2;
            JOptionPane.showMessageDialog(null, jugador.getName() + " Ataca primero");
        } else if(cardsJugador.getDef() < cardsMaquina.getAtk()){
            cardsJugador = maquina;
            cardsMaquina = jugador;
            player = 2;
            machine = 1;
            JOptionPane.showMessageDialog(null, maquina.getName() + " Ataca primero");
        } else {
            Random random = new Random();
            if(random.nextBoolean()){
                cardsJugador = jugador;
                cardsMaquina = maquina;
                player = 1;
                machine = 2;
            } else{
                cardsJugador = maquina;
                cardsMaquina = jugador;
                player = 2;
                machine = 1;
            }
            JOptionPane.showMessageDialog(null,"Mismo nivel de ataque" + jugador.getName() + " Ataca primero");
        }
        batalla(cardsJugador, cardsMaquina, player, machine);
    }

    private  void batalla(Monster atacante, Monster defensor, int player, int machine){
        int atk = atacante.getDef();
        int def = defensor.getDef();
        int turno = 1;


    }




}
