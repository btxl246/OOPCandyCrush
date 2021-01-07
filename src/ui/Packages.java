/**
 * Object-Oriented Programming project.
 * @author Bui Thi Xuan Lan - ITDSIU19007
 * @author Nguyen Duc Minh - ITITIU19030
 */

package ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Packs images into JPanels for use in UI.
 */

public class Packages implements ItemListener  {
    private JPanel cards;                                           // JPanel with CardLayout.
    private String tileChoice = "candy 1";                          // Tile theme choice.
    private JComboBox<String> cb;                                   // JComboBox to accompany cards.
    private String characterChoice = "in/images/character/0.png";   // Character icon choice.

    /**
     * Packs nine tile themes into a JPanel with a JComboBox of their names and a CardLayout JPanel displaying each theme.
     * @return Tile JPanel.
     */

    public JPanel tilePackage() {
        JPanel tilePanel = new JPanel();
        tilePanel.setBackground(new Color(253, 230, 240));
        tilePanel.setLayout(new BoxLayout(tilePanel, BoxLayout.PAGE_AXIS));
        tilePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((new Color(238, 77, 144)), 3, true), "Choose a tile package"));
        ((TitledBorder) tilePanel.getBorder()).setTitleFont(new Font("Candice", Font.PLAIN, 20));
        ((TitledBorder) tilePanel.getBorder()).setTitleColor(new Color(109, 18, 51));

        String BOTTLE = "Bottle";
        String CANDY1 = "Candy 1";
        String CANDY2 = "Candy 2";
        String CANDY3 = "Candy 3";
        String CUPCAKE = "Cupcake";
        String FISH1 = "Fish 1";
        String FISH2 = "Fish 2";
        String FRUIT = "Fruit";
        String SQUARE = "Square";
        String[] comboBoxItems = {BOTTLE, CANDY1, CANDY2, CANDY3, CUPCAKE, FISH1, FISH2, FRUIT, SQUARE};
        cb = new JComboBox<>(comboBoxItems);
        cb.setForeground(new Color(109, 18, 51));
        cb.setBackground(Color.WHITE);
        cb.setEditable(false);
        cb.addItemListener(this);
        cb.setFont(new Font("Candice", Font.PLAIN, 25));

        JPanel comboBoxPane = new JPanel();
        comboBoxPane.setBackground(new Color(253, 230, 240));
        comboBoxPane.add(cb);

        cards = new JPanel(new CardLayout());
        cards.add(fillTilePanel("bottle"), BOTTLE);
        cards.add(fillTilePanel("candy 1"), CANDY1);
        cards.add(fillTilePanel("candy 2"), CANDY2);
        cards.add(fillTilePanel("candy 3"), CANDY3);
        cards.add(fillTilePanel("cupcake"), CUPCAKE);
        cards.add(fillTilePanel("fish 1"), FISH1);
        cards.add(fillTilePanel("fish 2"), FISH2);
        cards.add(fillTilePanel("fruit"), FRUIT);
        cards.add(fillTilePanel("square"), SQUARE);

        tilePanel.add(comboBoxPane);
        tilePanel.add(cards);
        tileChoice = cb.getItemAt(cb.getSelectedIndex()).toLowerCase();

        return tilePanel;
    }

    /**
     * Fills each tile theme in a JPanel.
     * @param tile Tile theme.
     * @return JPanel of one theme.
     */
    private JPanel fillTilePanel(String tile) {
        String type;
        if (tile.equalsIgnoreCase("fruit"))
            type = ".jpg";
        else
            type = ".png";

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3));
        for (int i = 0; i < 6; i++) {
            JButton button = new JButton();
            button.setFocusPainted(false);
            button.setBackground(Color.WHITE);
            button.setPreferredSize(new Dimension(60, 60));
            button.setIcon(new ImageIcon("in/images/" + tile + "/" + i + type));
            panel.add(button);
        }

        return panel;
    }

    /**
     * Packs 15 characters into a JPanel for the player to choose as an icon.
     * @return JPanel of all character icons.
     */

    public JPanel characterPackage() {
        final int[] empty = {-1};
        ArrayList<String> al = new ArrayList<>();

        JPanel characterPanel = new JPanel();
        characterPanel.setBackground(new Color(253, 230, 240));
        characterPanel.setLayout( new GridLayout(5, 3));
        characterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((new Color(238, 77, 144)), 3, true), "Choose a character"));
        ((TitledBorder) characterPanel.getBorder()).setTitleFont(new Font("Candice", Font.PLAIN, 20));
        ((TitledBorder) characterPanel.getBorder()).setTitleColor(new Color(109, 18, 51));

        for (int i = 0; i < 15; i++) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(50, 50));
            button.setFocusPainted(false);
            button.setBackground(Color.WHITE);
            button.setIcon(new ImageIcon("in/images/character/" + i + ".png"));
            al.add("in/images/character/" + i + ".png");
            button.addActionListener(e -> {
                characterPanel.getComponent(empty[0]).setBackground(Color.WHITE);
                button.setBackground(Color.RED);
                empty[0] = al.indexOf(button.getIcon().toString());
                characterChoice = button.getIcon().toString();
            });
            characterPanel.add(button);
        }

        characterPanel.getComponent(0).setBackground(Color.RED);
        empty[0] = 0;

        return characterPanel;
    }

    /**
     * Shows JPanel of a tile theme when its name is chosen.
     * @param e ItemEvent.
     */

    @Override
    public void itemStateChanged(ItemEvent e) {
        tileChoice = cb.getItemAt(cb.getSelectedIndex()).toLowerCase();
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, (String) e.getItem());
    }

    /**
     * Returns the tile theme choice.
     * @return Name of the theme.
     */

    public String getTileChoice() {
        return this.tileChoice;
    }

    /**
     * Returns the character icon choice.
     * @return Path of the icon.
     */

    public String getCharacterChoice() {
        return this.characterChoice;
    }
}
