import graph.Graph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Основное окно интерфейса
class MainWindow extends JFrame {
    private DrawPanel panel;
    private JSlider speedSlider;
    private JTable verticesList;
    private Graph graph;
    MainWindow(){
        setSize(800,640); //поменять потом
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kosaraju algorithm");
        panel = new DrawPanel();
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
        JMenu fileMenu = new JMenu("File");
        JMenuItem item = new JMenuItem("Import graph");
//        item.setAction();
        fileMenu.add(item);
        item = new JMenuItem("Export graph");
//        item.setAction();
        fileMenu.add(item);
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
    }
}
