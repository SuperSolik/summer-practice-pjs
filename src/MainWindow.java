import javax.swing.*;
// Основное окно интерфейса
class MainWindow extends JFrame {
    private DrawPanel panel;
    private JSlider speedSlider;
    MainWindow(){
        setSize(640,640); //поменять потом
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kosaraju algorithm");
        panel = new DrawPanel();
        panel.setSize(600,600);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        rightPanel.add(new JLabel("Скорость анимации"));
        speedSlider = new JSlider(JSlider.VERTICAL,0,100,0);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        rightPanel.add(speedSlider);
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
