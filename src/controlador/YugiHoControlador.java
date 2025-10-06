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
        private ArrayList<Monster> cartasJugador = new ArrayList<>();
        private ArrayList<Monster> cartasMaquina = new ArrayList<>();
        private int puntosJugador = 0;
        private int puntosMaquina = 0;
        private boolean iniciaJuego = false;
        private int ronda = 1;
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
            JOptionPane.showMessageDialog(vista, "Inicia el juego, puedes seleccionar tus cartas como jugador");
        }
        // Generamos las cartas del jugador con las funciones auxiliares que tenemos para hacer la busqueda y la generación del moustro
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

        }
        // De momento este boton no esta en funcionamiento, ya que cuando selecionamos la carta esa función invoca  una carta random para la maquina
        // lo que ejecuta la batalla al realizar esta acción.
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

            // para que la maquina tome una carta aleatorea
            ArrayList<Integer> indicesDisponibles = new ArrayList<>();
            for(int i = 0; i < cartasMaquina.size(); i++ ){
                if(cartasMaquina.get(i) != null){
                    indicesDisponibles.add(i);
                }
            }
            // Control cuando no haya espacios disponibles
            if(indicesDisponibles.isEmpty()){
                JOptionPane.showMessageDialog(vista, "No hay cartas disponibles");
                return;
            }
            // Toma una carta de forma aleatoria en las que ya agrego de la maquina.
            Random random = new Random();
            int indiceMaquina = indicesDisponibles.get(random.nextInt(indicesDisponibles.size()));
            Monster cardMaquina = cartasMaquina.get(indiceMaquina);
            //Vamos a mostrar las cartas quese estan enfrentando...
            mostrarCartaBatalla(cardJugador, true);
            mostrarCartaBatalla(cardMaquina, false);
            // cartas que van a pelear
        //Realicemos un timer, este timer va a dar una espera antes de anunciar quien tiene mas ataque...
            Timer timer = new Timer(1000, e ->{
                JOptionPane.showMessageDialog(vista, String.format("Tu carta: %s (ATK:%d DEF:%d)\nVS\nCarta enemiga: %s (ATK:%d DEF:%d)",
                        cardJugador.getName(), cardJugador.getAtk(), cardJugador.getDef(),
                        cardMaquina.getName(), cardMaquina.getAtk(), cardMaquina.getDef()));

                batalla(cardJugador, cardMaquina,indice , indiceMaquina);
            });
            timer.setRepeats(false);
            timer.start();
    }
    // implementaciòn del mostrarCartaBatalla, me dara la vista de ambas cartas en batalla.
    private void mostrarCartaBatalla(Monster monster, boolean esJugador){
            JLabel label = esJugador ? vista.getImagen1() : vista.getImagen2();

        if (label != null) {
            label.setIcon(null);

            // Cargar imagen en segundo plano
            new Thread(() -> {
                try {
                    URL url = new URL(monster.getUrlImagen());
                    Image image = ImageIO.read(url);

                    // Imagen más grande para los paneles centrales
                    Image redimension = image.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(redimension);

                    SwingUtilities.invokeLater(() -> {
                        label.setIcon(imageIcon);
                        label.setHorizontalTextPosition(JLabel.CENTER);
                        label.setVerticalTextPosition(JLabel.BOTTOM);
                    });

                } catch (Exception e) {
                    System.out.println("Error al cargar imagen: " + e.getMessage());
                }
            }).start();
        }
    }
    //Funcion para realizar la batalla.
    public void batalla(Monster cardJugador, Monster cardMaquina, int iJugador, int imachine){
            int atkJugador = cardJugador.getAtk();
            int atkMaquina = cardMaquina.getAtk();

            if(atkJugador > atkMaquina){
                puntosJugador ++;
                JOptionPane.showMessageDialog(vista, "El ataque de" + atkJugador + "es mayor al de:" + atkMaquina + "\n" + "Jugador Gana la Ronda:" + ronda);
                cartasMaquina.set(imachine, null);
                ocultarCartaMaquina(imachine);
                ronda++;
            } else if (atkJugador < atkMaquina) {
                puntosMaquina ++;
                JOptionPane.showMessageDialog(vista, "El ataque de" + atkMaquina + "es mayor al de:" + atkJugador + "\n" + "Maquina Gana la Ronda:" + ronda);
                cartasJugador.set(iJugador, null);
                ocultarCartaJugador(iJugador);
                ronda++;
            } else {
                JOptionPane.showMessageDialog(vista, "Empate" + "\n" + "No hay puntos por ganar, ambas cartas destruidas");
                cartasMaquina.set(imachine, null);
                cartasJugador.set(iJugador, null);
                ocultarCartaMaquina(imachine);
                ocultarCartaJugador(iJugador);
                ronda++;
            }
            //Anunciamos el ganador de la batalla
            anunciarGanador();
    }
    // Función auxiliar para anunciar el ganador
    public void anunciarGanador(){
            //Realizamos la comparación de los puntos
            if(puntosJugador >= maximo_rondas_ganar){
                JOptionPane.showMessageDialog(vista, "Ronda: " + ronda+ "\n" +"Jugador Gana El Duelo" + "\n" + "Resultado Final"  + "\n" + puntosJugador + " - " + puntosMaquina);
                //Generamos un restart del juego gane jugador o maquina.
                reStart();
            } else if ( puntosMaquina >= maximo_rondas_ganar) {
                JOptionPane.showMessageDialog(vista, "Ronda: " + ronda+ "\n" + "Maquina Gana El Duelo" + "\n" + "Resultado Final"  + "\n" + puntosJugador + " - " + puntosMaquina);
                reStart();
            }
    }
        //Función auxiliar para restablecer el juego.
    public void reStart(){
            cartasJugador.clear();
            cartasMaquina.clear();
            puntosJugador = 0;
            puntosMaquina = 0;
            iniciaJuego = false;

            vista.getCardPlayer1().setVisible(true);
            vista.getCardPlayer2().setVisible(true);
            vista.getCardPlayer3().setVisible(true);
            vista.getCardMaquina1().setVisible(true);
            vista.getCardMaquina2().setVisible(true);
            vista.getCardMaquina3().setVisible(true);

            vista.getCardPlayer1().setText("");
            vista.getCardPlayer1().setIcon(null);
            vista.getCardPlayer2().setText("");
            vista.getCardPlayer2().setIcon(null);
            vista.getCardPlayer3().setText("");
            vista.getCardPlayer3().setIcon(null);
            vista.getCardMaquina1().setText("");
            vista.getCardMaquina2().setText("");
            vista.getCardMaquina3().setText("");
            vista.getImagen1().setIcon(null);
            vista.getImagen2().setIcon(null);

            ronda = 1;
    }

    public void mostrarCartaJugador(Monster monster,int posicion) {
        // Creamos un label que nos va a renderizar la carta.
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
            label.setIcon(null);

            JLabel labelFinal = label;
            new Thread(() -> {
                try {
                    URL url = new URL(monster.getUrlImagen());
                    Image image = ImageIO.read(url);
                    Image redimension = image.getScaledInstance(150, 220, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(redimension);

                    SwingUtilities.invokeLater(() -> {
                        labelFinal.setIcon(imageIcon);
                        labelFinal.setHorizontalTextPosition(JLabel.CENTER);
                        labelFinal.setVerticalTextPosition(JLabel.BOTTOM);
                    });

                } catch (Exception e) {
                    System.out.println("Error al cargar imagen: " + e.getMessage());
                }
            }).start();
        }
    }

    //Mostrar o indicar que la maquina ya posee cartas
    public void mostrarCartasMaquina(Monster monster, int posicion) {
        // Al final solo de hace la simulación de que la carta esta oculta, se puede complementar con el código de mostrar las del jugador
        // Pero decidimos manejarlo así, a futuro se puede cargar la parte posteriro de la carta, para indicar que el jugador no ve las de la maquina.
        switch (posicion) {
            case 0:
                vista.getCardMaquina1().setText("Carta Oculta");
                break;
            case 1:
                vista.getCardMaquina2().setText("Carta Oculta");
                break;
            case 2:
                vista.getCardMaquina3().setText("Carta Oculta");
                break;
        }
    }
  // Esta función nos ayuda a controlar las cartas que van siendo eliminadas, si el jugador pierde se elimina su carta y no se muestra
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
    // Esta función nos ayuda a controlar las cartas que van siendo eliminadas, si la maquina pierde se elimina su carta y no se muestra
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
// buscamos las cartas, contexto distinto al de pokeApi, se debe manejar el offset, como argumento de busqueda.
    private Monster search(int offset){
            try {
                //Realizamos la respectiva conexión a la Api
                HttpClient client = HttpClient.newHttpClient();
                // El offset debe ir incluido en la url para realizar la busqueda adecuadamente
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://db.ygoprodeck.com/api/v7/cardinfo.php?num=1&offset=" + offset + "&sort=random" )).build();
                HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    JSONObject json  = new JSONObject(response.body());
                    JSONArray data = json.getJSONArray("data");
                    //Axtraemos los datos

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
                        // Al final, este trae toda la carta no solo la imagen del moustro, por tanto los datos de ataque y defensa se utilizan
                        // para hacer complementos del códgo e informar quien gana o quien pierde.
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

    //Sacamos generamos un numero al azar para que las cartas que la batalla sea mas justa.
    public int makeMoster(){
            Random rand = new Random();
            int number = rand.nextInt(500);
            return number;
    }


}


