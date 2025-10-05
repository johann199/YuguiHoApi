package controlador;
import gui.YugiHoGui;
import modelo.Monster;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class YugiHoControlador {

        private final YugiHoGui vista;
        private Monster jugador;
        private Monster maquina;
        private ArrayList<Monster> cartasJugador = new ArrayList<>();
        private ArrayList<Monster> cartasMaquina = new ArrayList<>();
        private int puntosJugador = 0;
        private int puntosMaquina = 0;
        private boolean iniciaJuego = false;

        private int maximo_cartas = 3;
        private int maximo_rondas_ganar = 2;


        public YugiHoControlador(YugiHoGui vista) {
            this.vista = vista;

            vista.getBtnCartasMaquina().addActionListener(e -> generarCartaMaquina());
            vista.getBtnMisCartas().addActionListener(e -> generarCartaJugador());
            vista.getBtnDuelo().addActionListener(e -> cartas());
            vista.getSelectbutton1().addActionListener(e -> seleccionarCarta(0));
            vista.getSelectButton2().addActionListener(e -> seleccionarCarta(1));
            vista.getSelectButton3().addActionListener(e -> seleccionarCarta(2));

        }
        public  void generarCartaMaquina (){
            if (cartasJugador.isEmpty()){
                JOptionPane.showMessageDialog(vista, "No hay cartas jugador");
                return;
            }
            System.out.println("Evento cartas maquina");
            cartasMaquina.clear();

            for(int i = 0; i < maximo_cartas; i++){
                int random = makeMoster();
                Monster monster = search(random);
                if (monster != null){
                    cartasMaquina.add(monster);
                    mostrarCartasMaquina(monster, i);
                }
            }
            puntosJugador = 0;
            puntosMaquina = 0;
            iniciaJuego = true;
        }

        public void generarCartaJugador(){
            cartasJugador.clear();
            for(int i = 0; i < maximo_cartas; i++){
                int random = makeMoster();
                Monster monster = search(random);
                if(monster != null){
                    cartasJugador.add(monster);
                    mostrarCartaJugador(monster, i);
                }
            }
            //JOptionPane.showMessageDialog(vista, "Inicia el juego, puedes seleccionar tus cartas como jugador");

        }

        public void cartas(){
        if (cartasJugador.isEmpty() && cartasMaquina.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Se necesitan Generar las cartas");
       } else {
            JOptionPane.showMessageDialog(null, "Se necesitan Selecionar las cartas");
       }
    }

    // Seleciona las cartas del jugador

    public  void seleccionarCarta(int indice){
            if(!iniciaJuego){
                JOptionPane.showMessageDialog(vista, "Debes generar tus cartas");
                return;
            }

            if(indice >= cartasJugador.size() || cartasJugador.get(indice) == null){
                JOptionPane.showMessageDialog(vista, "esta carta ya fue seleccionada");
                return;
            }

            Monster cardJugador = cartasJugador.get(indice);

            // para que la maquine tome una carta aleactorea
            ArrayList<Integer> indicesDisponibles = new ArrayList<>();
            for(int i = 0; i < cartasMaquina.size(); i++ ){
                if(cartasMaquina.get(i) != null){
                    indicesDisponibles.add(i);
                }
            }
            if(indicesDisponibles.isEmpty()){
                JOptionPane.showMessageDialog(vista, "No hay cartas disponibles");
                return;
            }
            Random random = new Random();
            int indiceMaquina = indicesDisponibles.get(random.nextInt(indicesDisponibles.size()));
            Monster cardMaquina = cartasMaquina.get(indiceMaquina);
            // cartas que van a pelear
            JOptionPane.showMessageDialog(vista, String.format("Tu carta: %s (ATK:%d DEF:%d)\nVS\nCarta enemiga: %s (ATK:%d DEF:%d)",
                    cardJugador.getName(), cardJugador.getAtk(), cardJugador.getDef(),
                    cardMaquina.getName(), cardMaquina.getAtk(), cardMaquina.getDef()));

            batalla(cardJugador, cardMaquina,indice , indiceMaquina);


    }



    public void batalla(Monster cardJugador, Monster cardMaquina, int iJugador, int imachine){
            int atkJugador = cardJugador.getAtk();
            int atkMaquina = cardMaquina.getAtk();

            if(atkJugador > atkMaquina){
                puntosJugador ++;
                JOptionPane.showMessageDialog(vista, "El ataque de" + atkJugador + "es mayor al de:" + atkMaquina + "\n" + "Jugador Gana la Ronda 1");
                cartasMaquina.set(imachine, null);
                ocultarCartaMaquina(imachine);
            } else if (atkJugador < atkMaquina) {
                puntosMaquina ++;
                JOptionPane.showMessageDialog(vista, "El ataque de" + atkMaquina + "es mayor al de:" + atkJugador + "\n" + "Maquina Gana la Ronda 1");
                cartasJugador.set(iJugador, null);
                ocultarCartaJugador(iJugador);
            } else {
                JOptionPane.showMessageDialog(vista, "Empate" + "\n" + "No hay puntos por ganar, ambas cartas destruidas");
                cartasMaquina.set(imachine, null);
                cartasJugador.set(iJugador, null);
                ocultarCartaMaquina(imachine);
                ocultarCartaJugador(iJugador);
            }

            anunciarGanador();
    }

    public void anunciarGanador(){
            if(puntosJugador >= maximo_rondas_ganar){
                JOptionPane.showMessageDialog(vista, "Jugador Gana El Duelo" + "\n" + "Resultado Final"  + "\n" + puntosJugador + " - " + puntosMaquina);
                restar();
            } else if ( puntosMaquina >= maximo_rondas_ganar) {
                JOptionPane.showMessageDialog(vista, "Maquina Gana El Duelo" + "\n" + "Resultado Final"  + "\n" + puntosJugador + " - " + puntosMaquina);
                restar();
            }
    }

    public void restar(){
            cartasJugador.clear();
            cartasMaquina.clear();
            puntosJugador = 0;
            puntosMaquina = 0;
            iniciaJuego = false;
    }

    public void mostrarCartaJugador(Monster monster,int posicion) {
        String textoTemp = String.format(
                "<html><center>🎴<br>Cargando...</center></html>"
        );

        JLabel label = null;
        switch (posicion) {
            case 0:
                label = vista.getCardPlayer1();
                break;
            case 1:
                label = vista.getCardPlayer2();
                break;
            case 2:
                label = vista.getCardPlayer3();
                break;
        }

        if (label != null) {
            label.setText(textoTemp);
            label.setIcon(null);

            JLabel labelFinal = label;
            new Thread(() -> {
                try {
                    URL url = new URL(monster.getUrlImagen());
                    Image image = ImageIO.read(url);
                    Image redimension = image.getScaledInstance(150, 220, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(redimension);

                    String texto = String.format(
                            "<html><center><b>%s</b><br>ATK: %d / DEF: %d</center></html>",
                            monster.getName(),
                            monster.getAtk(),
                            monster.getDef()
                    );

                    SwingUtilities.invokeLater(() -> {
                        labelFinal.setIcon(imageIcon);
                        labelFinal.setText(texto);
                        labelFinal.setHorizontalTextPosition(JLabel.CENTER);
                        labelFinal.setVerticalTextPosition(JLabel.BOTTOM);
                    });

                } catch (Exception e) {
                    System.out.println("Error al cargar imagen: " + e.getMessage());
                    SwingUtilities.invokeLater(() -> {
                        labelFinal.setText(String.format(
                                "<html><center><b>%s</b><br>ATK:%d | DEF:%d</center></html>",
                                monster.getName(), monster.getAtk(), monster.getDef()
                        ));
                    });
                }
            }).start();


        }
    }

    public void mostrarCartasMaquina(Monster monster, int posicion) {
        String cartaOculta = "<html><center>🎴<br><b>CARTA OCULTA</b></center></html>";

        switch (posicion) {
            case 0:
                vista.getCardMaquina1().setText(cartaOculta);
                break;
            case 1:
                vista.getCardMaquina2().setText(cartaOculta);
                break;
            case 2:
                vista.getCardMaquina3().setText(cartaOculta);
                break;
        }
    }

    public void ocultarCartaJugador(int posicion){
            switch (posicion){
                case 0:
                    vista.getCardPlayer1().setVisible(false);
                    break;
                case 1:
                    vista.getCardPlayer2().setVisible(false);
                    break;
                case 2:
                    vista.getCardPlayer3().setVisible(false);
                    break;
            }
    }

    public void ocultarCartaMaquina(int posicion){
        switch (posicion){
            case 0:
                vista.getCardMaquina1().setVisible(false);
                break;
            case 1:
                vista.getCardMaquina2().setVisible(false);
                break;
            case 2:
                vista.getCardMaquina3().setVisible(false);
                break;
        }
    }

    private Monster search(int offset){
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://db.ygoprodeck.com/api/v7/cardinfo.php?num=1&offset=" + offset + "&sort=random" )).build();
                HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    JSONObject json  = new JSONObject(response.body());
                    JSONArray data = json.getJSONArray("data");

                    if(data.length() > 0){
                        JSONObject obj = data.getJSONObject(0);
                        int id = obj.getInt("id");
                        String name = obj.getString("name");
                        String type = obj.getString("type");

                        if(!type.contains("Monster")){
                            return search(offset +1);
                        }
                        int atk = obj.has("atk") ? obj.getInt("atk") : 0;
                        int def = obj.has("def") ? obj.getInt("def") : 0;

                        JSONArray images = obj.getJSONArray("card_images");
                        String urlImagen = images.getJSONObject(0).getString("image_url");

                        Monster monster = new Monster(id, name, atk, def, urlImagen);

                        return monster;

                    }
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al conectar con la API" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al procesar Datos",  "Error", JOptionPane.ERROR_MESSAGE);
            }
            return null;
    }

    public int makeMoster(){
            Random rand = new Random();
            int number = rand.nextInt(100) +1;
            return number;
    }


}


