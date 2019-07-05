import graph.Edge;
import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Основное окно интерфейса
class MainWindow extends JFrame {
    private DrawPanel panel;
    private JSlider speedSlider;
    private JTable verticesList;
    private Graph graph;
    MainWindow(){
        this.graph = new Graph();

        setSize(800,640); //поменять потом
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kosaraju algorithm");
        panel = new DrawPanel(graph);
        panel.setMinimumSize(new Dimension(600,600));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        rightPanel.add(new JLabel("Скорость анимации"));
        speedSlider = new JSlider(JSlider.VERTICAL,0,100,0);
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
        DefaultTableModel model = new DefaultTableModel();
        verticesList = new JTable(model);
//        var a = new TableColumn();
//        a.setHeaderValue("Vertex name");
//        verticesList.addColumn(a);
        model.addColumn("Name");
        for(int i = 0 ; i < 100 ; i++)model.addRow(new Object[]{""+i});
        {
            var jsp = new JScrollPane(verticesList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jsp.setPreferredSize(new Dimension(300,300));
            rightPanel.add(jsp);
        }
        JButton algoButton = new JButton("Algo");
//        algoButton.setAction(); // TODO set action on button
        rightPanel.add(algoButton);
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
        item.addActionListener(new ImportGraphAction(this.graph));
        fileMenu.add(item);

        item = new JMenuItem("Export graph");
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
                panel.repaint();
                for(Vertex v : graph.getVertices()){
                    if(v.getX()<0)v.setX(1);
                    if(v.getX()>DrawPanel.FRAME_WIDTH)v.setX(DrawPanel.FRAME_WIDTH-1);
                    if(v.getX()<0)v.setY(1);
                    if(v.getY()>DrawPanel.FRAME_HEIGHT)v.setY(DrawPanel.FRAME_HEIGHT-1);
                }
                //calculate forces
                Thread.sleep(20);
            }
        }catch (InterruptedException e){e.printStackTrace();}
    }

    class ImportGraphAction extends AbstractAction {
        Graph graph;

        ImportGraphAction(Graph graph) {
            this.graph = graph;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Graph file", "ogf"));
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try (Scanner fileScanner = new Scanner(selectedFile)) {
                    this.graph.clear();
                    while (fileScanner.hasNextLine()) {
                        String rowLine = fileScanner.nextLine();
                        if (rowLine.isEmpty()) {
                            return;
                        }

                        String[] splitLine = rowLine.split(" ");

                        String originVertexName = splitLine[0];
                        int originVertexX = Integer.valueOf(splitLine[1]);
                        int originVertexY = Integer.valueOf(splitLine[2]);

                        boolean vertexCreated = this.graph.createVertex(
                            originVertexName,
                            originVertexX,
                            originVertexY
                        );

                        if (!vertexCreated) { // vertex was created in previous iterations
                            Vertex originVertex = this.graph.getVertex(originVertexName);
                            originVertex.setX(originVertexX);
                            originVertex.setY(originVertexY);
                        }

                        for (int i = 3; i != splitLine.length; ++i) {
                            String destVertexName = splitLine[i];
                            this.graph.createVertex(destVertexName);
                            this.graph.createEdge(originVertexName, destVertexName);
                        }
                    }
                } catch (FileNotFoundException err) {/* never reaches, because file selected by user */}
            }
        }
    }

    class ExportGraphAction extends AbstractAction {
        Graph graph;

        ExportGraphAction(Graph graph) {
            this.graph = graph;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Graph file", "ogf"));
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try (FileWriter fileWriter = new FileWriter(selectedFile);) {
                    for (Vertex origin : this.graph.getVertices()) {
                        StringBuilder lineBuilder = new StringBuilder();
                        lineBuilder.append(origin.getName())
                                .append(" " + (int)origin.getX())
                                .append(" " + (int)origin.getY());

                        for (Edge edge : origin.getEdges()) {
                            lineBuilder.append(' ' + edge.getDest().getName());
                        }

                        fileWriter.write(lineBuilder.toString() + '\n');
                    }
                } catch (IOException err) {/* never reaches, because file selected by user */}
            }
        }
    }
}
