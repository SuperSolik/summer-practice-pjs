import graph.Edge;
import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;

// Панелька, рисующая граф
public class DrawPanel extends JPanel implements MouseMotionListener {
    private static final int VERTEX_SIZE = 20;
    public static final int  FRAME_WIDTH = 600 ,FRAME_HEIGHT = 600;
    private Paint[] p;
    private Graph graph;
    private Vertex selectedVertex;

    DrawPanel(Graph graph) {
        p = new GradientPaint[4];
        p[0] = new GradientPaint(0, 300, Color.RED, 150, 300, Color.YELLOW);
        p[1] = new GradientPaint(150, 300, Color.YELLOW, 300, 300, Color.GREEN);
        p[2] = new GradientPaint(300, 300, Color.GREEN, 450, 300, Color.BLUE);
        p[3] = new GradientPaint(450, 300, Color.BLUE, 600, 300, Color.MAGENTA);
        setPreferredSize(new Dimension(600, 600));
        this.graph = graph;
        this.addMouseMotionListener(this);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    for (Vertex v : graph.getVertices()) {
                        if (Math.abs(v.getX() - e.getX()) <= VERTEX_SIZE && Math.abs(v.getY() - e.getY()) <= VERTEX_SIZE) {
                            selectedVertex = v;
                            break;
                        }
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                selectedVertex = null;
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        //метод отрисовки
        if (graph.getVertices().size() == 0) {
            for (int i = 0; i < p.length; i++) {
                ((Graphics2D) g).setPaint(p[i]);
                g.fillRect(i * 150, 0, 150, 600);
            }
            g.setColor(Color.BLACK);
            g.drawString("Здесь будет граф", 200, 300);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.BLACK);
            for (Edge e : graph.getEdges()) {
                g.drawLine((int) e.getSource().getX(), (int) e.getSource().getY(), (int) e.getDest().getX(), (int) e.getDest().getY());
                //TODO приделать стрелочки
            }
            for (Vertex v : graph.getVertices()) {
                g.setColor(Color.lightGray);
                g.fillOval((int) v.getX() - VERTEX_SIZE / 2, (int) v.getY() - VERTEX_SIZE / 2, VERTEX_SIZE, VERTEX_SIZE);
                g.setColor(Color.BLACK);
                g.drawOval((int) v.getX() - VERTEX_SIZE / 2, (int) v.getY() - VERTEX_SIZE / 2, VERTEX_SIZE, VERTEX_SIZE);
                g.drawString(v.getName(), (int) v.getX() - 10 * v.getName().length() / 2, (int) v.getY() + 5);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        System.out.println("drag "+e.getX() + "\t" + e.getY());
        if (selectedVertex != null) {
            selectedVertex.setX(e.getX());
            selectedVertex.setY(e.getY());

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("move "+e.getX() + "\t" + e.getY());

    }
    class PopUpPanel{
        private String[] elements;
        private int focusedElement = -1;
        private int x,y;
        private boolean isVisible = false;
        static final int WIDTH = 100,ELEMENT_HEIGHT = 30;
        PopUpPanel(String[] elements){
            this.elements = elements;
        }
        void show(int x, int y){

        }
        void hide(){
            isVisible = false;
        }
        boolean isActive(){
            return isVisible;
        }
        void updateMousePosition(int x, int y){

        }
        int getMouseSelection(){
            return focusedElement;
        }
        void draw(Graphics g){

        }
    }
}
