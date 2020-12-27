package ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Packages implements ItemListener  {
    private JPanel cards;
    private String tileChoice = "fruit";
    private JComboBox<String> cb;
    private String characterChoice = "in/images/character/0.png";

    public Packages() {
    }

    public JPanel tilePackage() {
        JPanel tilePanel = new JPanel();
        tilePanel.setBackground(new Color(253, 230, 240));
        tilePanel.setLayout(new BoxLayout(tilePanel, BoxLayout.PAGE_AXIS));
        tilePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((new Color(238, 77, 144)), 3, true), "Choose a tile package"));
        ((TitledBorder) tilePanel.getBorder()).setTitleFont(new Font("Candice", Font.PLAIN, 20));
        ((TitledBorder) tilePanel.getBorder()).setTitleColor(new Color(109, 18, 51));

        String FRUIT = "Fruit";
        String STRIPED_FISH = "Striped Fish";
        String SQUARE = "Square";

        JPanel comboBoxPane = new JPanel();
        comboBoxPane.setBackground(new Color(253, 230, 240));

        String[] comboBoxItems = {FRUIT, STRIPED_FISH, SQUARE};
        cb = new JComboBox<>(comboBoxItems);
        cb.setForeground(new Color(109, 18, 51));
        cb.setBackground(Color.WHITE);
        cb.setEditable(false);
        cb.addItemListener(this);
        cb.setFont(new Font("Candice", Font.PLAIN, 25));
        comboBoxPane.add(cb);

        cards = new JPanel(new CardLayout());
        cards.add(fillTilePanel("fruit"), FRUIT);
        cards.add(fillTilePanel("striped fish"), STRIPED_FISH);
        cards.add(fillTilePanel("square"), SQUARE);

        tilePanel.add(comboBoxPane);
        tilePanel.add(cards);
        tileChoice = cb.getItemAt(cb.getSelectedIndex()).toLowerCase();

        return tilePanel;
    }

    public JPanel fillTilePanel(String tile) {
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        tileChoice = cb.getItemAt(cb.getSelectedIndex()).toLowerCase();
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, (String) e.getItem());
    }

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
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    characterPanel.getComponent(empty[0]).setBackground(Color.WHITE);

                    button.setBackground(Color.RED);

                    empty[0] = al.indexOf(button.getIcon().toString());
                    characterChoice = button.getIcon().toString();
                }
            });
            characterPanel.add(button);
        }

        characterPanel.getComponent(0).setBackground(Color.RED);
        empty[0] = 0;

        return characterPanel;
    }

    public String getTileChoice() {
        return tileChoice;
    }

    public String getCharacterChoice() {
        return characterChoice;
    }
}
