import actions.ExportGraphAction;
import actions.FunctionalAction;
import actions.ImportGraphAction;
import graph.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// Основное окно интерфейса
class MainWindow extends JFrame implements Listener {
    /*
    TODO
    Добавить кнопки для работы с алгоритмом
    В начало, шаг назад, шаг вперед, автостеп, сразу в конец
     */
    private DrawPanel panel;
    private JSlider speedSlider;
    private VerticesList verticesList;
    private JButton autoStepButton;
    private Graph graph;
    private GraphAlgo algo = new GraphAlgo();
    private ArrayList<ArrayList<Color>> stages = new ArrayList<>();
    private int currentGraphState = 0, stateTimer;
    private boolean autoStep = false;

    MainWindow() {
        graph = new Graph();
        graph.onModify(this);

        setSize(950, 655); //поменять потом
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kosaraju algorithm");
        setResizable(false);
        panel = new DrawPanel(graph);
        panel.setMinimumSize(new Dimension(600, 600));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(new JLabel("Скорость анимации"));
        speedSlider = new JSlider(JSlider.VERTICAL, 0, 100, 10);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(speedSlider);
            rightPanel.add(panel);
            rightPanel.add(new JPanel());
        }
//        rightPanel.add(speedSlider);

        // graph.VerticesList
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Name");

        verticesList = new VerticesList(model);

        {
            var jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jsp.setPreferredSize(new Dimension(300, 300));
            rightPanel.add(jsp);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        JButton toStartButton = new JButton(new FunctionalAction(e -> {
            currentGraphState = 0;
            if(autoStep){
                autoStepButton.setText("Start Animation");
                autoStep = false;
            }
            if (stages.isEmpty())
                stages.addAll(algo.Kosaraju(graph, verticesList));
        }));
        autoStepButton = new JButton(new FunctionalAction((ActionEvent e) -> {
            if (stages.isEmpty())
                stages.addAll(algo.Kosaraju(graph, verticesList));
            if (autoStep) {
                autoStep = false;
                ((JButton) e.getSource()).setText("Start Animation");
            } else {
                autoStep = true;
                ((JButton) e.getSource()).setText("Stop Animation");
                if(currentGraphState==stages.size()-1)currentGraphState=0;
            }
        }));
        JButton stepBackButton = new JButton(new FunctionalAction(e -> {
            if (currentGraphState > 0) currentGraphState--;
            autoStep = false;
            autoStepButton.setText("Start Animation");
        }));
        JButton stepForwardButton = new JButton(new FunctionalAction(e -> {
            if (stages.isEmpty())
                stages.addAll(algo.Kosaraju(graph, verticesList));
            autoStep = false;
            autoStepButton.setText("Start Animation");
            if (currentGraphState < stages.size() - 1) currentGraphState++;
        }));
        JButton toEndButton = new JButton(new FunctionalAction(e -> {
            if (stages.isEmpty())
                stages.addAll(algo.Kosaraju(graph, verticesList));
            currentGraphState = stages.size() - 1;
        }));

        toStartButton.setText("<<");
        stepBackButton.setText("<");
        stepForwardButton.setText(">");
        toEndButton.setText(">>");
        autoStepButton.setText("Start Animation");
        buttonPanel.add(toStartButton);
        buttonPanel.add(stepBackButton);
        buttonPanel.add(stepForwardButton);
        buttonPanel.add(toEndButton);
        buttonPanel.add(autoStepButton);
        rightPanel.add(buttonPanel);
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(panel);
        mainPanel.add(rightPanel);
        add(mainPanel);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // file menu
        JMenu fileMenu = new JMenu("File");

        JMenuItem item = new JMenuItem("Import graph");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        item.addActionListener(new ImportGraphAction(this.graph));
        fileMenu.add(item);

        item = new JMenuItem("Export graph");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        item.addActionListener(new ExportGraphAction(this.graph));
        fileMenu.add(item);

        // settings menu
//        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("About");
        item = new JMenuItem(new FunctionalAction(e-> openHtmlViewer(
                new File( "doc"+File.separator +"help.htm"))));
        item.setText("Help");
        helpMenu.add(item);
        item = new JMenuItem(new FunctionalAction(e-> openHtmlViewer(
                new File(  "doc"+File.separator +"author.htm"))));
        item.setText("Authors");
        helpMenu.add(item);
        menuBar.add(fileMenu);
//        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
        setVisible(true);
        new Thread(this::startUpdateCycle).start();
    }

    private void startUpdateCycle() {
        try {
            while (this.isEnabled()) {
                if (stages != null && !stages.isEmpty()) {
                    if (stages.get(0).size() != graph.getVertices().size())
                        panel.updateColors(null);
                    else {
                        panel.updateColors(stages.get(currentGraphState));
                        if (autoStep) {
                            stateTimer++;
                            if (stateTimer >= 101 - speedSlider.getValue()) {
                                stateTimer = 0;
                                if (currentGraphState < stages.size() - 1)
                                    currentGraphState++;
                            }
                            if(currentGraphState == stages.size()-1){
                                autoStep = false;
                                autoStepButton.setText("Start Animation");
                            }
                        }
                    }
                } else panel.updateColors(null);
                panel.repaint();
                for (Vertex v : graph.getVertices()) {
                    if (v.getX() < 0) v.setX(1);
                    if (v.getX() > DrawPanel.FRAME_WIDTH - DrawPanel.VERTEX_SIZE / 2)
                        v.setX(DrawPanel.FRAME_WIDTH - DrawPanel.VERTEX_SIZE / 2);
                    if (v.getY() < 0) v.setY(1);
                    if (v.getY() > DrawPanel.FRAME_HEIGHT - DrawPanel.VERTEX_SIZE / 2)
                        v.setY(DrawPanel.FRAME_HEIGHT - DrawPanel.VERTEX_SIZE / 2);
                }
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performAction() {
        stages.clear();
        currentGraphState = 0;
        autoStep = false;
        autoStepButton.setText("Start Animation");
    }
    private  void openHtmlViewer(File htmlFile) {
        if(Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null,"Unable to read file");
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Not supported on this system");
        }
    }
}