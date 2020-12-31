package ui;

import model.Grid;
import model.Model;
import ui.imagePackages.Packages;
import ui.leaderBoard.LeaderBoard;
import ui.leaderBoard.Profile;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UI implements Runnable {
    private Packages packages;
    private Model model;
    private Profile profile;
    private final LeaderBoard lB = LeaderBoard.getInstance();

    private JFrame homeFrame;
    private JFrame playFrame;
    private JFrame overFrame;

    private JFrame playModeFrame;
    private JFrame customizeFrame;

    private JPanel leaderBoardPanel;
    private JPanel infoPanel;
    private JLabel yourScoreLabel;

    private JTextField rowText;
    private JTextField colText;

    private String playMode = "";
    private String playerName = "";

    private ArrayList<ArrayList<JButton>> playGrid;

    @Override
    public void run() {
        setHomeFrame();
        this.homeFrame.setVisible(true);
    }

    public void setLeaderBoardPanel() {
        this.leaderBoardPanel = new JPanel();
        leaderBoardPanel.setLayout(new GridBagLayout());
        leaderBoardPanel.setPreferredSize(new Dimension(700, 500));

        JLabel leaderBoardLabel = new JLabel("Leader Board", SwingConstants.CENTER);
        leaderBoardLabel.setFont(new Font("Candice",Font.PLAIN,50));
        leaderBoardLabel.setForeground(new Color(247, 217, 76));
        leaderBoardLabel.setOpaque(true);
        leaderBoardLabel.setBackground(Color.decode("#a82052"));

        ArrayList<Profile> leaderBoard;
        if (this.playerName.isBlank()) {
            leaderBoard = lB.readData();
        }
        else {
            this.profile = new Profile();
            profile.setName(playerName);
            profile.setScore(this.model.getGrid().getTotalScore());
            lB.considerScore(profile.getScore(), profile);
            lB.writeData();
            leaderBoard = lB.readData();
        }

        JTable table = new JTable() {
            @Override
            public void setTableHeader(JTableHeader tableHeader) {
                tableHeader.setFont(new Font("Candice", Font.PLAIN, 25));
                tableHeader.setForeground(new Color(109, 18, 51));
                tableHeader.setBackground(Color.WHITE);
                super.setTableHeader(tableHeader);
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (row == 0) {
                    component.setBackground(Color.RED);
                    component.setForeground(Color.WHITE);
                }
                else if (row == 1) {
                    component.setBackground(Color.ORANGE);
                    component.setForeground(Color.WHITE);
                }
                else if (row == 2) {
                    component.setBackground(Color.YELLOW);
                    component.setForeground(Color.WHITE);
                }
                else {
                    component.setBackground(new Color(253, 230, 240));
                    component.setForeground(new Color(109, 18, 51));
                }

                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

                return component;
            }
        };
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFont(new Font("Candice",Font.PLAIN,25));
        table.setForeground(new Color(109, 18, 51));
        table.setBackground(new Color(253, 230, 240));
        table.setFillsViewportHeight(true);
        table.setRowHeight(50);
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        table.setModel(new DefaultTableModel(new Object [][] {}, new String [] {"Position", "Name", "Score"}));
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int row = 0; row < leaderBoard.size(); row++) {
            Object[] newRow = new Object[]{row + 1, leaderBoard.get(row).getName(), leaderBoard.get(row).getScore()};
            model.addRow(newRow);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        JScrollPane tableScrollPane = new JScrollPane(table);

        leaderBoardPanel.add(leaderBoardLabel, gBC(GridBagConstraints.BOTH, 0.2, 0.1, 0, 0, 1, 1));
        leaderBoardPanel.add(tableScrollPane, gBC(GridBagConstraints.BOTH, 1, 1, 0, 1, 1, 1));
    }

    public void setHomeFrame() {
        this.homeFrame = new JFrame("HOME SCREEN");
        homeFrame.setLayout(new FlowLayout());

        BufferedImage homeScreen = null;
        try {
            homeScreen = ImageIO.read(new File("in/images/homeFrame.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeFrame.setContentPane(new ImagePanel(homeScreen));
        homeFrame.getContentPane().setPreferredSize(new Dimension(homeScreen.getWidth(), homeScreen.getHeight()));

        JButton startButton = new JButton("Play!");
        startButton.setFont(new Font("Candice", Font.PLAIN, 70));
        startButton.setForeground(Color.WHITE);
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setBounds(478, 460, 315, 94);
        startButton.addActionListener(new HomeFrameButtonListener());

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Candice", Font.PLAIN, 65));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBounds(465, 570, 338, 76);
        exitButton.addActionListener(new HomeFrameButtonListener());

        JButton lBButton = new JButton("Leader Board");
        lBButton.setFont(new Font("Candice", Font.PLAIN, 50));
        lBButton.setForeground(Color.WHITE);
        lBButton.setBorder(BorderFactory.createEmptyBorder());
        lBButton.setFocusPainted(false);
        lBButton.setContentAreaFilled(false);
        lBButton.setBounds(homeScreen.getWidth()/2 - 319/2, 655, 319, 60);
        lBButton.addActionListener(new HomeFrameButtonListener());

        homeFrame.add(startButton);
        homeFrame.add(exitButton);
        homeFrame.add(lBButton);

        homeFrame.pack();
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class HomeFrameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Play!")) {
                UI.this.homeFrame.dispose();
                setPlayModeFrame();
                UI.this.playModeFrame.setVisible(true);
            }
            else if (e.getActionCommand().equals("Exit"))
                System.exit(0);
            else if (e.getActionCommand().equals("Leader Board")) {
                setLeaderBoardPanel();
                JFrame frame = new JFrame("LEADER BOARD");
                frame.add(UI.this.leaderBoardPanel);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        }
    }

    public void setPlayModeFrame() {
        this.playModeFrame = new JFrame("PLAY MODE");

        BufferedImage modeScreen = null;
        try {
            modeScreen = ImageIO.read(new File("in/images/playMode.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playModeFrame.setContentPane(new ImagePanel(modeScreen));
        playModeFrame.getContentPane().setPreferredSize(new Dimension(modeScreen.getWidth(), modeScreen.getHeight()));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Candice", Font.PLAIN, 30));
        backButton.setForeground(new Color(9, 70, 239));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setBounds(54, 16, 78, 50);
        backButton.addActionListener(new PlayModeFrameButtonListener());

        JLabel label = new JLabel("Choose a play mode:");
        label.setFont(new Font("Candice", Font.PLAIN, 20));
        label.setForeground(new Color(109, 18, 51));
        label.setBounds(modeScreen.getWidth()/2 - 174/2, 125, 174, 50);

        JButton defaultButton = new JButton("Default");
        defaultButton.setFont(new Font("Candice", Font.PLAIN, 35));
        defaultButton.setForeground(Color.WHITE);
        defaultButton.setBorder(BorderFactory.createEmptyBorder());
        defaultButton.setFocusPainted(false);
        defaultButton.setContentAreaFilled(false);
        defaultButton.setBounds(modeScreen.getWidth()/2 - 135/2, 200, 135, 50);
        defaultButton.addActionListener(new PlayModeFrameButtonListener());

        JButton customizeButton = new JButton("Customize");
        customizeButton.setFont(new Font("Candice", Font.PLAIN, 35));
        customizeButton.setForeground(Color.WHITE);
        customizeButton.setBorder(BorderFactory.createEmptyBorder());
        customizeButton.setFocusPainted(false);
        customizeButton.setContentAreaFilled(false);
        customizeButton.setBounds(modeScreen.getWidth()/2 - 183/2, 300, 183, 50);
        customizeButton.addActionListener(new PlayModeFrameButtonListener());

        playModeFrame.add(backButton);
        playModeFrame.add(label);
        playModeFrame.add(defaultButton);
        playModeFrame.add(customizeButton);

        playModeFrame.pack();
        playModeFrame.setLocationRelativeTo(null);
        playModeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class PlayModeFrameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Back")) {
                UI.this.playModeFrame.dispose();
                UI.this.homeFrame.setVisible(true);
            }
            else {
                UI.this.playModeFrame.dispose();
                UI.this.playMode = e.getActionCommand();
                setNameFrame();
            }
        }
    }

    public void setNameFrame() {
        JFrame nameFrame = new JFrame("PLAYER'S NAME");
        nameFrame.getContentPane().setBackground(new Color(253, 230, 240));
        nameFrame.setPreferredSize(new Dimension(320, 160));
        nameFrame.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Enter your name: ");
        label.setFont(new Font("Candice", Font.PLAIN, 25));
        label.setForeground(new Color(109, 18, 51));
        JTextField text = new JTextField(5);
        text.setFont(new Font("Candice", Font.PLAIN, 25));
        text.setForeground(new Color(109, 18, 51));
        text.setBorder(BorderFactory.createEmptyBorder());
        JPanel top = new JPanel();
        top.setBackground(new Color(253, 230, 240));
        top.setLayout(new FlowLayout());
        top.add(label);
        top.add(text);

        JButton back = new JButton(" Back ");
        back.setFont(new Font("Candice", Font.PLAIN, 25));
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(0, 169, 239));
        back.setBorder(BorderFactory.createLineBorder(new Color(14, 123, 247), 3, true));
        back.setFocusPainted(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameFrame.dispose();
                UI.this.playModeFrame.setVisible(true);
            }
        });

        JButton go = new JButton(" Go ");
        go.setFont(new Font("Candice", Font.PLAIN, 25));
        go.setForeground(Color.WHITE);
        go.setBackground(new Color(254, 68, 149));
        go.setBorder(BorderFactory.createLineBorder(new Color(229, 64, 116), 3, true));
        go.setFocusPainted(false);
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.getText().isBlank()) {
                    UIManager.put("OptionPane.background", new Color(253, 230, 240));
                    UIManager.put("Panel.background", new Color(253, 230, 240));
                    UIManager.put("OptionPane.messageForeground", new Color(109, 18, 51));
                    UIManager.put("OptionPane.messageFont", new Font("Candice", Font.PLAIN, 25));
                    UIManager.put("OptionPane.buttonFont", new Font("Candice", Font.PLAIN, 25));
                    UIManager.put("Button.border", BorderFactory.createLineBorder(new Color(14, 123, 247), 3, true));
                    UIManager.put("Button.background", new Color(0, 169, 239));
                    UIManager.put("Button.foreground", Color.WHITE);
                    UIManager.put("OptionPane.okButtonText", " Okay ");
                    JOptionPane.showMessageDialog(null, "Sorry, we didn't catch that.\nPlease try again.", "WARNING", JOptionPane.WARNING_MESSAGE, null);

                    UIManager.put("OptionPane.background", null);
                    UIManager.put("Panel.background", null);
                    UIManager.put("OptionPane.messageForeground", null);
                    UIManager.put("OptionPane.messageFont", null);
                    UIManager.put("OptionPane.buttonFont", null);
                    UIManager.put("Button.border", null);
                    UIManager.put("Button.background", null);
                    UIManager.put("Button.foreground", null);
                    UIManager.put("OptionPane.okButtonText", null);
                }
                else {
                    UI.this.playerName = text.getText();
                    nameFrame.dispose();
                    if (UI.this.playMode.equals("Default")) {
                        UI.this.rowText = new JTextField("5");
                        UI.this.colText = new JTextField("5");

                        UI.this.packages = new Packages();

                        setPlayFrame();
                        UI.this.playFrame.setVisible(true);
                    }
                    else if (playMode.equals("Customize")) {
                        setCustomizeFrame();
                        UI.this.customizeFrame.setVisible(true);
                    }
                }
            }
        });

        nameFrame.add(top, gBC(GridBagConstraints.CENTER, 0.5, 0.5, 0, 0, 2, 1));
        nameFrame.add(back, gBC(GridBagConstraints.CENTER, 0.5, 0.5, 0, 1, 1, 1));
        nameFrame.add(go, gBC(GridBagConstraints.CENTER, 0.5, 0.5, 1, 1, 1, 1));

        nameFrame.pack();
        nameFrame.setVisible(true);
        nameFrame.setLocationRelativeTo(null);
        nameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setCustomizeFrame() {
        this.customizeFrame = new JFrame("CUSTOMIZE SCREEN");
        customizeFrame.getContentPane().setBackground(new Color(253, 230, 240));
        customizeFrame.setPreferredSize(new Dimension(550, 600));
        customizeFrame.setLayout(new GridBagLayout());

        JLabel rowLabel = new JLabel("Rows: ");
        rowLabel.setFont(new Font("Candice", Font.PLAIN, 25));
        rowLabel.setForeground(new Color(109, 18, 51));
        JLabel colLabel = new JLabel("Columns: ");
        colLabel.setFont(new Font("Candice", Font.PLAIN, 25));
        colLabel.setForeground(new Color(109, 18, 51));

        this.rowText = new JTextField("5", 2);
        rowText.setFont(new Font("Candice", Font.PLAIN, 25));
        rowText.setForeground(new Color(109, 18, 51));
        rowText.setBackground(new Color(253, 230, 240));
        rowText.setBorder(BorderFactory.createEmptyBorder());
        rowText.setHorizontalAlignment(JTextField.CENTER);
        rowText.setEditable(false);
        this.colText = new JTextField("5", 2);
        colText.setFont(new Font("Candice", Font.PLAIN, 25));
        colText.setForeground(new Color(109, 18, 51));
        colText.setBackground(new Color(253, 230, 240));
        colText.setBorder(BorderFactory.createEmptyBorder());
        colText.setHorizontalAlignment(JTextField.CENTER);
        colText.setEditable(false);

        JSlider rowSlider = new JSlider(JSlider.HORIZONTAL, 5, 10, 5);
        rowSlider.setFont(new Font("Candice", Font.PLAIN, 25));
        rowSlider.setForeground(new Color(109, 18, 51));
        rowSlider.setBackground(new Color(253, 230, 240));
        rowSlider.setMajorTickSpacing(5);
        rowSlider.setPaintLabels(true);
        rowSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rowText.setText(String.valueOf(rowSlider.getValue()));
            }
        });
        JSlider colSlider = new JSlider(JSlider.HORIZONTAL, 5, 10, 5);
        colSlider.setFont(new Font("Candice", Font.PLAIN, 25));
        colSlider.setForeground(new Color(109, 18, 51));
        colSlider.setBackground(new Color(253, 230, 240));
        colSlider.setMajorTickSpacing(5);
        colSlider.setPaintLabels(true);
        colSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                colText.setText(String.valueOf(colSlider.getValue()));
            }
        });

        JPanel rowPanel = new JPanel();
        rowPanel.setBackground(new Color(253, 230, 240));
        rowPanel.setLayout(new FlowLayout());
        rowPanel.add(rowLabel);
        rowPanel.add(rowText);
        JPanel colPanel = new JPanel();
        colPanel.setBackground(new Color(253, 230, 240));
        colPanel.setLayout(new FlowLayout());
        colPanel.add(colLabel);
        colPanel.add(colText);
        JPanel dimPanel = new JPanel();
        dimPanel.setBackground(new Color(253, 230, 240));
        dimPanel.setLayout(new GridLayout(2, 2));
        dimPanel.add(rowPanel);
        dimPanel.add(rowSlider);
        dimPanel.add(colPanel);
        dimPanel.add(colSlider);
        dimPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((new Color(238, 77, 144)), 3, true), "Set dimension"));
        ((TitledBorder) dimPanel.getBorder()).setTitleFont(new Font("Candice", Font.PLAIN, 20));
        ((TitledBorder) dimPanel.getBorder()).setTitleColor(new Color(109, 18, 51));

        JButton backButton = new JButton(" Back ");
        backButton.setFont(new Font("Candice", Font.PLAIN, 25));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(0, 169, 239));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(14, 123, 247), 3, true));
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UI.this.customizeFrame.dispose();
                UI.this.homeFrame.setVisible(true);
                UI.this.playModeFrame.setVisible(true );
            }
        });
        JButton startButton = new JButton(" Start ");
        startButton.setFont(new Font("Candice", Font.PLAIN, 25));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(254, 68, 149));
        startButton.setBorder(BorderFactory.createLineBorder(new Color(229, 64, 116), 3, true));
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UI.this.customizeFrame.dispose();
                setPlayFrame();
                UI.this.playFrame.setVisible(true);
            }
        });

        this.packages = new Packages();

        customizeFrame.add(packages.tilePackage(), gBC(GridBagConstraints.BOTH, 0, 1, 0, 0, 1, 1));
        customizeFrame.add(packages.characterPackage(), gBC(GridBagConstraints.BOTH, 1, 1, 1, 0, 1, 1));

        customizeFrame.add(dimPanel, gBC(GridBagConstraints.BOTH, 0.5, 0.3, 0, 2, 2, 1));

        customizeFrame.add(backButton, gBC(GridBagConstraints.CENTER, 0.5, 0.2, 0, 3, 1, 1));
        customizeFrame.add(startButton, gBC(GridBagConstraints.CENTER, 0.5, 0.2, 1, 3, 1, 1));

        customizeFrame.pack();
        customizeFrame.setLocationRelativeTo(null);
        customizeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setPlayFrame() {
        this.playFrame = new JFrame("PLAY SCREEN");
        playFrame.getContentPane().setBackground(new Color(253, 230, 240));
        playFrame.setLayout(new GridBagLayout());

        this.model = new Model(new Grid(Integer.parseInt(this.rowText.getText()), Integer.parseInt(this.colText.getText()), packages.getTileChoice()));

        JButton character = new JButton();
        character.setIcon(new ImageIcon(packages.getCharacterChoice()));
        character.setBorder(BorderFactory.createDashedBorder(new Color(238, 77, 144), 3, 5, 1, true));
        character.setFocusPainted(false);
        character.setContentAreaFilled(false);
        JLabel nameLabel = new JLabel(this.playerName);
        nameLabel.setFont(new Font("Candice", Font.PLAIN, 25));
        nameLabel.setForeground(new Color(109, 18, 51));
        this.infoPanel = new JPanel();
        infoPanel.setBackground(new Color(253, 230, 240));
        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(character);
        infoPanel.add(nameLabel);

        this.yourScoreLabel = new JLabel();
        yourScoreLabel.setFont(new Font("Candice", Font.PLAIN, 25));
        yourScoreLabel.setForeground(new Color(109, 18, 51));

        JPanel playPanel = new JPanel();
        playPanel.setLayout(new GridLayout(model.getGrid().getRows(), model.getGrid().getCols()));
        this.playGrid = new ArrayList<>();
        for (int y = 0; y < model.getGrid().getRows(); y++) {
            ArrayList<JButton> row = new ArrayList<>();
            for (int x = 0; x < model.getGrid().getCols(); x++) {
                JButton tileButton = new JButton();
                tileButton.setPreferredSize(new Dimension(60, 60));
                tileButton.addActionListener(new EventHandler(model, x, y));
                row.add(tileButton);
                playPanel.add(tileButton);
            }
            playGrid.add(row);
        }

        JButton backButton = new JButton(" Back to Home ");
        backButton.setFont(new Font("Candice", Font.PLAIN, 25));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(0, 169, 239));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(14, 123, 247), 3, true));
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (option() == 0) {
                    UI.this.playFrame.dispose();
                    UI.this.homeFrame.setVisible(true);
                }
            }
        });

        JButton replayButton = new JButton(" Replay ");
        replayButton.setFont(new Font("Candice", Font.PLAIN, 25));
        replayButton.setForeground(Color.WHITE);
        replayButton.setBackground(new Color(254, 68, 149));
        replayButton.setBorder(BorderFactory.createLineBorder(new Color(229, 64, 116), 3, true));
        replayButton.setFocusPainted(false);
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (option() == 0) {
                    UI.this.playFrame.dispose();
                    setPlayFrame();
                    UI.this.playFrame.setVisible(true);
                }
            }
        });

        JButton exitButton = new JButton(" Exit Game ");
        exitButton.setFont(new Font("Candice", Font.PLAIN, 25));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(229, 45, 19));
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(196, 18, 24), 3, true));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (option() == 0) {
                    System.exit(0);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(253, 230, 240));
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backButton);
        buttonPanel.add(replayButton);
        buttonPanel.add(exitButton);

        model.addModelObserver(this);

        playFrame.add(infoPanel, gBC(GridBagConstraints.CENTER, 0.5, 0.5, 0, 0, 1, 1));
        playFrame.add(yourScoreLabel, gBC(GridBagConstraints.CENTER, 0.5, 0.5, 1, 0, 1, 1));

        playFrame.add(playPanel, gBC(GridBagConstraints.CENTER, 1, 1, 0, 1, 2, 1));

        playFrame.add(buttonPanel, gBC(GridBagConstraints.CENTER, 0.5, 0.5, 0, 2, 2, 1));

        playFrame.pack();
        playFrame.setLocationRelativeTo(null);
        playFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setOverFrame() {
        this.overFrame = new JFrame("GAME OVER");
        overFrame.getContentPane().setBackground(new Color(253, 230, 240));
        overFrame.setPreferredSize(new Dimension(1000, 500));
        overFrame.setLayout(new GridBagLayout());

        JLabel finalScore = new JLabel("Your score: " + this.model.getGrid().getTotalScore());
        finalScore.setForeground(new Color(109, 18, 51));
        finalScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        finalScore.setFont(new Font("Candice", Font.PLAIN, 25));

        JButton backButton = new JButton(" Back to Home ");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setFont(new Font("Candice", Font.PLAIN, 25));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(0, 169, 239));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(14, 123, 247), 3, true));
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UI.this.overFrame.dispose();
                UI.this.homeFrame.setVisible(true);
            }
        });

        JButton replayButton = new JButton(" Replay ");
        replayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        replayButton.setFont(new Font("Candice", Font.PLAIN, 25));
        replayButton.setForeground(Color.WHITE);
        replayButton.setBackground(new Color(254, 68, 149));
        replayButton.setBorder(BorderFactory.createLineBorder(new Color(229, 64, 116), 3, true));
        replayButton.setFocusPainted(false);
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UI.this.overFrame.dispose();
                setPlayFrame();
                UI.this.playFrame.setVisible(true);
            }
        });

        JButton newButton = new JButton(" New Game ");
        newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newButton.setFont(new Font("Candice", Font.PLAIN, 25));
        newButton.setForeground(Color.WHITE);
        newButton.setBackground(Color.YELLOW);
        newButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3, true));
        newButton.setFocusPainted(false);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UI.this.overFrame.dispose();
                UI.this.playModeFrame.setVisible(true);
            }
        });

        JButton exitButton = new JButton(" Exit Game ");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(new Font("Candice", Font.PLAIN, 25));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(229, 45, 19));
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(196, 18, 24), 3, true));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        overFrame.add(this.infoPanel, gBC(GridBagConstraints.CENTER, 0.2, 0, 0, 0, 1, 1));
        overFrame.add(finalScore, gBC(GridBagConstraints.CENTER, 0.2, 0, 0, 1, 1, 1));

        overFrame.add(backButton, gBC(GridBagConstraints.CENTER, 0.2, 0.05, 0, 2, 1, 1));
        overFrame.add(replayButton, gBC(GridBagConstraints.CENTER, 0.2, 0.05, 0, 3, 1, 1));
        overFrame.add(newButton, gBC(GridBagConstraints.CENTER, 0.2, 0.05, 0, 4, 1, 1));
        overFrame.add(exitButton, gBC(GridBagConstraints.CENTER, 0.2, 0.05, 0, 5, 1, 1));

        setLeaderBoardPanel();
        overFrame.add(this.leaderBoardPanel, gBC(GridBagConstraints.BOTH, 1, 1, 1, 0, 1, 6));

        overFrame.pack();
        overFrame.setVisible(true);
        overFrame.setLocationRelativeTo(null);
        overFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void update() {
        for (int y = 0; y < this.model.getGrid().getRows(); y++) {
            for (int x = 0; x < model.getGrid().getCols(); x++) {
                JButton tileButton = this.playGrid.get(y).get(x);
                tileButton.setIcon(new ImageIcon(model.getGrid().getIcon(new Point(x, y))));
                tileButton.setBackground(Color.WHITE);
            }
        }

        Point first = model.getSelector().getFirst();
        if (first != null) {
            playGrid.get(first.y).get(first.x).setBackground(Color.RED);
        }

        this.yourScoreLabel.setText(String.valueOf(model.getGrid().getTotalScore()));

        if (model.exit()) {
            System.out.println("UI exit(): " + model.exit());
            for (ArrayList<JButton> al : playGrid)
                for (JButton button : al)
                    button.setEnabled(false);
            this.playFrame.dispose();
            setOverFrame();
        }

        playFrame.repaint();
    }

    public static class ImagePanel extends JComponent {
        private final Image image;	        // The image.

        public ImagePanel(Image image) {	// The constructor.
            this.image = image;
        }

        /**
         * Override the paintComponent() method in the JComponent class.
         * @param g The graphics.
         */

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

    public static class EventHandler implements ActionListener {
        private final Model model;
        private final int x;
        private final int y;

        public EventHandler(Model model, int x, int y) {
            this.model = model;
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.model.select(this.x, this.y);
        }
    }

    public GridBagConstraints gBC(int fill, double weightx, double weighty, int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints gBC = new GridBagConstraints();
        gBC.fill = fill;
        gBC.weightx = weightx;
        gBC.weighty = weighty;
        gBC.gridx = gridx;
        gBC.gridy = gridy;
        gBC.gridwidth = gridwidth;
        gBC.gridheight = gridheight;

        return gBC;
    }

    public int option() {
        UIManager.put("OptionPane.background", new Color(253, 230, 240));
        UIManager.put("Panel.background", new Color(253, 230, 240));
        UIManager.put("OptionPane.messageForeground", new Color(109, 18, 51));
        UIManager.put("OptionPane.messageFont", new Font("Candice", Font.PLAIN, 25));
        UIManager.put("OptionPane.buttonFont", new Font("Candice", Font.PLAIN, 25));
        UIManager.put("Button.border", BorderFactory.createLineBorder(new Color(14, 123, 247), 3, true));
        UIManager.put("Button.background", new Color(0, 169, 239));
        UIManager.put("Button.foreground", Color.WHITE);

        Object[] options = {"Yes", "No"};
        int n = JOptionPane.showOptionDialog(null, "Are you sure?\nYou will lose your current progress.", "WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("OptionPane.messageFont", null);
        UIManager.put("OptionPane.buttonFont", null);
        UIManager.put("Button.border", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);

        return n;
    }
}
