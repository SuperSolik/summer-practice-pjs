import actions.AlgoButtonAction;
import actions.ExportGraphAction;
import actions.ImportGraphAction;
import graph.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;

// Основное окно интерфейса
class MainWindow extends JFrame {
    private DrawPanel panel;
    private JSlider speedSlider;
    private VerticesList verticesList;
    private Graph graph;
    private GraphAlgo algo;
    private ArrayList<ArrayList<Color>> stages;
    private int currentGraphState = 0, stateTimer;

    MainWindow(){
        this.graph = new Graph();
        this.algo = new GraphAlgo();

        setSize(800,640); //поменять потом
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kosaraju algorithm");
        panel = new DrawPanel(graph);
        panel.setMinimumSize(new Dimension(600,600));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        rightPanel.add(new JLabel("Скорость анимации"));
        speedSlider = new JSlider(JSlider.VERTICAL,0,100,10);
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
            jsp.setPreferredSize(new Dimension(300,300));
            rightPanel.add(jsp);
        }

        // Algo Button
        JButton algoButton = new JButton("Algo");
        stages = new ArrayList<>();
        algoButton.setAction(new AlgoButtonAction(graph, algo, stages, verticesList));
        algoButton.setText("Algo");
        rightPanel.add(algoButton);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
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
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        item.addActionListener(new ExportGraphAction(this.graph));
        fileMenu.add(item);

        // settings menu
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("About");
        item = new JMenuItem("Help");
//        item.setAction();
        helpMenu.add(item);
        item = new JMenuItem("Authors");
//        item.setAction();
        helpMenu.add(item);
        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
        setVisible(true);
        new Thread(this::startUpdateCycle).start();
    }
    private void startUpdateCycle(){
        try {
            while (this.isEnabled()) {
                if(stages!= null && !stages.isEmpty()) {
                    if (stages.get(0).size() != graph.getVertices().size())
                        panel.updateColors(null);
                    else {
                        panel.updateColors(stages.get(currentGraphState));
                        stateTimer++;
                        if (stateTimer >= speedSlider.getValue() + 1) {
                            stateTimer = 0;
                            if (currentGraphState < stages.size() - 1)
                                currentGraphState++;
                        }
                    }
                }
                else panel.updateColors(null);
                panel.repaint();
                for(Vertex v : graph.getVertices()){
                    if(v.getX()<0)v.setX(1);
                    if(v.getX()>DrawPanel.FRAME_WIDTH-DrawPanel.VERTEX_SIZE/2)v.setX(DrawPanel.FRAME_WIDTH-DrawPanel.VERTEX_SIZE/2);
                    if(v.getY()<0)v.setY(1);
                    if(v.getY()>DrawPanel.FRAME_HEIGHT-DrawPanel.VERTEX_SIZE/2)v.setY(DrawPanel.FRAME_HEIGHT-DrawPanel.VERTEX_SIZE/2);
                }
                //calculate forces
//                Thread.sleep(20);
                Thread.sleep(20);
            }
        }catch (InterruptedException e){e.printStackTrace();}
    }
}
