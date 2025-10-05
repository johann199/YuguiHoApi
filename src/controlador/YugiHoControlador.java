package controlador;
import gui.YugiHoGui;
import Duelo.Monster;
import gui.YugiHoGui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class YugiHoControlador {

        private final YugiHoGui vista;
        private Monster jugador;
        private Monster maquina;
        private ArrayList<Monster> cartasJugador = new ArrayList<>();
        private ArrayList<Monster> cartasMaquina = new ArrayList<>();

        public YugiHoControlador(YugiHoGui vista) {
            this.vista = vista;

            vista.getBtnCartasMaquina().addActionListener(e -> generarCartaMaquina());
            vista.getBtnMisCartas().addActionListener(e -> generarCartaJugador());
            vista.getBtnDuelo().addActionListener(e -> duelo());
        }
        public  void generarCartaMaquina (){
//            int random = makeMoster();
//            Monster monsterObtenido = search(random);
//            if (monsterObtenido != null) {
//                cartasMaquina.add(monsterObtenido);
//                maquina = monsterObtenido;
//                //extraerData(monsterObtenido, 1);
//            }
        }

        public void generarCartaJugador(){
//            int random = makeMoster();
//                Monster monsterObtenido = search(random);
//                if (monsterObtenido != null) {
//                    cartasJugador.add(monsterObtenido);
//                    jugador = monsterObtenido;
//                    extraerData(monsterObtenido, 1);
//                }
//            }
        }

        public void cartas(){
            if (jugador != null && maquina != null) {
               duelo();
           } else {
                JOptionPane.showMessageDialog(null, "Se necesitan Selecionar las cartas");
           }
        }

        public void duelo(){
//            Monster cardsJugador, cardsMaquina;
//            int player, machine;
//            if (cardsJugador.getAtk() > cardsMaquina.getDef()){
//                cardsJugador = jugador;
//                cardsMaquina = maquina;
//                player = 1;
//                machine = 2;
//                JOptionPane.showMessageDialog(null, jugador.getName() + " Ataca primero");
//            } else if(cardsJugador.getDef() < cardsMaquina.getAtk()){
//                cardsJugador = maquina;
//                cardsMaquina = jugador;
//                player = 2;
//                machine = 1;
//                JOptionPane.showMessageDialog(null, maquina.getName() + " Ataca primero");
//            } else {
//                Random random = new Random();
//                if(random.nextBoolean()){
//                    cardsJugador = jugador;
//                    cardsMaquina = maquina;
//                    player = 1;
//                    machine = 2;
//                } else{
//                    cardsJugador = maquina;
//                    cardsMaquina = jugador;
//                    player = 2;
//                    machine = 1;
//                }
//                JOptionPane.showMessageDialog(null,"Mismo nivel de ataque" + jugador.getName() + " Ataca primero");
//            }
//            batalla(cardsJugador, cardsMaquina, player, machine);
//
        }
        public void batalla(Monster atacante, Monster defensor, int player, int machine){
            int atk = atacante.getDef();
            int def = defensor.getDef();
            int turno = 1;

        }

    }

