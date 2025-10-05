package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

    // Colores Temáticos
    private static final Color DARK_BLUE = new Color(10, 25, 50);
    private static final Color GOLD_ACCENT = new Color(127, 93, 191);
    private static final Color DARK_GREY = new Color(30, 30, 30);

    public YugiHoGui() {
        // Inicialización y Configuración del Panel Principal
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(DARK_BLUE);
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));


        JPanel machineZone = createMachineZone();
        panelPrincipal.add(machineZone, BorderLayout.NORTH);

        JPanel duelZone = createDuelZone();
        panelPrincipal.add(duelZone, BorderLayout.CENTER);

        JPanel playerZone = createPlayerZone();
        panelPrincipal.add(playerZone, BorderLayout.SOUTH);

        setTitle("Yu-Gi-Oh! Duelo Digital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
    }

    //metodo para ver mostrar frame
    public static void verFrame (){
        SwingUtilities.invokeLater(() -> {
            YugiHoGui frame = new YugiHoGui();
            frame.setVisible(true);
        });
    }

    //panel enemigo
    private JPanel createMachineZone() {
        JPanel cardSlotsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        cardSlotsPanel.setBackground(DARK_BLUE);

        CardMaquina1 = createCardSlot("OPPONENT CARD 1");
        CardMaquina2 = createCardSlot("OPPONENT CARD 2");
        CardMaquina3 = createCardSlot("OPPONENT CARD 3");

        cardSlotsPanel.add(CardMaquina1);
        cardSlotsPanel.add(CardMaquina2);
        cardSlotsPanel.add(CardMaquina3);

        // botonesde cartas de la máquina y el de selección
        btnCartasMaquina = createStyledButton("Ataque enemigo");
        selecionarCartaMaquinaButton = createStyledButton("Generar cartas de la maquina");

        // zona de la maquina
        JPanel machineZone = new JPanel(new BorderLayout(10, 0));
        machineZone.setBackground(DARK_BLUE);
        machineZone.add(cardSlotsPanel, BorderLayout.CENTER);

        // botones oponente
        JPanel machineButtonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        machineButtonsPanel.setBackground(DARK_BLUE);
        machineButtonsPanel.add(btnCartasMaquina);
        machineButtonsPanel.add(selecionarCartaMaquinaButton);
        machineZone.add(machineButtonsPanel, BorderLayout.EAST);

        machineZone.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GOLD_ACCENT, 2),
                "ZONA DEL OPONENTE", 0, 0,
                new Font("Arial", Font.BOLD, 14), GOLD_ACCENT));

        return machineZone;
    }

    private JPanel createDuelZone() {
        // Panel de Monstruos
        JPanel monsterPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        monsterPanel.setBackground(DARK_BLUE.darker());

        // JLabels (monstruos en campo)
        Imagen1 = createMonsterSlot("PLAYER MONSTER");
        Imagen2 = createMonsterSlot("OPPONENT MONSTER");

        monsterPanel.add(Imagen1);
        monsterPanel.add(Imagen2);

        // Botón de Duelo
        btnDuelo = new JButton("¡DUELO!");
        btnDuelo.setFont(new Font("Impact", Font.BOLD, 28));
        btnDuelo.setBackground(new Color(127, 93, 191));
        btnDuelo.setForeground(Color.WHITE);
        btnDuelo.setFocusPainted(false);
        btnDuelo.setPreferredSize(new Dimension(200, 70));
        btnDuelo.setBorder(BorderFactory.createRaisedBevelBorder());

        // panel cntral (Monstruos arriba, Botón de Duelo abajo)
        JPanel duelZone = new JPanel(new BorderLayout(10, 10));
        duelZone.setBackground(DARK_BLUE);
        duelZone.add(monsterPanel, BorderLayout.CENTER);


        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setBackground(DARK_BLUE);
        buttonWrapper.add(btnDuelo);
        duelZone.add(buttonWrapper, BorderLayout.SOUTH);

        return duelZone;
    }


    private JPanel createPlayerZone() {
        // Panel cartas del Jugador
        JPanel playerCardSlots = new JPanel(new GridLayout(1, 3, 15, 10));
        playerCardSlots.setBackground(DARK_BLUE);

        CardPlayer1 = createCardSlot("PLAYER CARD 1");
        CardPlayer2 = createCardSlot("PLAYER CARD 2");
        CardPlayer3 = createCardSlot("PLAYER CARD 3");

        playerCardSlots.add(CardPlayer1);
        playerCardSlots.add(CardPlayer2);
        playerCardSlots.add(CardPlayer3);

        // Panel de Botones de Selección
        JPanel playerSelectButtons = new JPanel(new GridLayout(1, 3, 15, 0));
        playerSelectButtons.setBackground(DARK_BLUE);

        //botones de selección
        Selectbutton1 = createStyledButton("SELECT 1");
        SelectButton2 = createStyledButton("SELECT 2");
        SelectButton3 = createStyledButton("SELECT 3");

        playerSelectButtons.add(Selectbutton1);
        playerSelectButtons.add(SelectButton2);
        playerSelectButtons.add(SelectButton3);

        // Botón mis Cartas
        btnMisCartas = createStyledButton("Generar mis cartas");
        btnMisCartas.setPreferredSize(new Dimension(150, 40));

        JPanel playerZone = new JPanel(new BorderLayout(10, 10));
        playerZone.setBackground(DARK_BLUE);

        // Contenedor principal botones de selección
        JPanel cardsAndSelect = new JPanel(new BorderLayout(0, 10));
        cardsAndSelect.setBackground(DARK_BLUE);
        cardsAndSelect.add(playerCardSlots, BorderLayout.CENTER);
        cardsAndSelect.add(playerSelectButtons, BorderLayout.SOUTH);

        playerZone.add(cardsAndSelect, BorderLayout.CENTER);

        // panel para el botón "Mis Cartas"
        JPanel deckButtonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deckButtonWrapper.setBackground(DARK_BLUE);
        deckButtonWrapper.add(btnMisCartas);
        playerZone.add(deckButtonWrapper, BorderLayout.WEST);

        playerZone.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GOLD_ACCENT, 2),
                "ZONA DEL JUGADOR", 0, 0,
                new Font("Arial", Font.BOLD, 14), GOLD_ACCENT));

        return playerZone;
    }

    private JLabel createCardSlot(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(150, 180));
        label.setBackground(DARK_GREY);
        label.setForeground(GOLD_ACCENT.brighter());
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(GOLD_ACCENT, 2));
        label.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return label;
    }

    private JLabel createMonsterSlot(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 250));
        label.setBackground(DARK_GREY.darker());
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createDashedBorder(GOLD_ACCENT, 4, 5, 5, true));
        label.setFont(new Font("Impact", Font.ITALIC, 16));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(GOLD_ACCENT);
        button.setForeground(DARK_BLUE.darker());
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(DARK_BLUE.darker(), 2));
        return button;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnDuelo() {
        return btnDuelo;
    }

    public JButton getBtnCartasMaquina() {
        return btnCartasMaquina;
    }

    public JButton getBtnMisCartas() {
        return btnMisCartas;
    }

    public JLabel getCardPlayer1() {
        return CardPlayer1;
    }

    public JLabel getCardPlayer2() {
        return CardPlayer2;
    }

    public JLabel getCardPlayer3() {
        return CardPlayer3;
    }

    public JLabel getCardMaquina3() {
        return CardMaquina3;
    }

    public JLabel getCardMaquina2() {
        return CardMaquina2;
    }

    public JLabel getCardMaquina1() {
        return CardMaquina1;
    }

    public JLabel getImagen1() {
        return Imagen1;
    }

    public JLabel getImagen2() {
        return Imagen2;
    }
    public JButton getSelectbutton1() {
        return Selectbutton1;
    }

    public JButton getSelectButton3() {
        return SelectButton3;
    }

    public JButton getSelectButton2() {
        return SelectButton2;
    }

}
