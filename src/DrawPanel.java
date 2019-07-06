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
    public static final int VERTEX_SIZE = 20, ARROW_SIZE = 10;
    public static final int FRAME_WIDTH = 600, FRAME_HEIGHT = 600;
    private Paint[] p;
    private Graph graph;
    private Vertex selectedVertex, editVertex;
    private PopUpPanel vertexMenu, canvasMenu;
    private int editVertexMarkerSize = 2*VERTEX_SIZE;
    private int[] arrowXs,arrowYs;
    DrawPanel(Graph graph) {
        p = new GradientPaint[4];
        p[0] = new GradientPaint(0, 300, Color.RED, 150, 300, Color.YELLOW);
        p[1] = new GradientPaint(150, 300, Color.YELLOW, 300, 300, Color.GREEN);
        p[2] = new GradientPaint(300, 300, Color.GREEN, 450, 300, Color.BLUE);
        p[3] = new GradientPaint(450, 300, Color.BLUE, 600, 300, Color.MAGENTA);
        arrowXs = new int[3];
        arrowYs = new int[3];
        setPreferredSize(new Dimension(600, 600));

        vertexMenu = new PopUpPanel(new String[]{"Add/remove edge", "Remove node", "Rename node"});
        canvasMenu = new PopUpPanel(new String[]{"Create node", "Clear graph"});
        this.graph = graph;
        this.addMouseMotionListener(this);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                for (Vertex v : graph.getVertices()) {
                    if (Math.abs(v.getX() - e.getX()) <= VERTEX_SIZE && Math.abs(v.getY() - e.getY()) <= VERTEX_SIZE) {
                        selectedVertex = v;
                        break;
                    }
                }
                if(e.getButton() == MouseEvent.BUTTON3){
                    if(selectedVertex != null){
                        vertexMenu.show(e.getX(), e.getY());
                        editVertex = selectedVertex;
                        canvasMenu.hide();
                    }
                    else {
                        canvasMenu.show(e.getX(),e.getY());
                        vertexMenu.hide();
                    }
                }
                if(e.getButton() == MouseEvent.BUTTON1){
                    if (editVertex != null && selectedVertex != null && editVertex!=selectedVertex) {
                        if(editVertex.isConnected(selectedVertex))
                            graph.removeEdge(editVertex,selectedVertex);
                        else
                            graph.createEdge(editVertex,selectedVertex);
                        editVertex = null;
                    }
                    if(vertexMenu.isActive()){
                        switch (vertexMenu.getMouseSelection()){
                            case 0:
                                break;
                            case 1:
                                graph.getVertices().remove(editVertex);
                                editVertex = null;
                                break;
                            case 2:
                                String result = JOptionPane.showInputDialog(null,"Write vertex name","",JOptionPane.QUESTION_MESSAGE);
                                if(result!=null && result.length()>0)
                                    editVertex.setName(result);
                                editVertex = null;
                                break;
                            default:
                                break;
                        }
                        editVertexMarkerSize = 2*VERTEX_SIZE;
                        vertexMenu.hide();
                    }
                    if(canvasMenu.isActive()){
                        switch (canvasMenu.getMouseSelection()){
                            case 0:
                                String result = JOptionPane.showInputDialog(null,"Write vertex name","",JOptionPane.QUESTION_MESSAGE);
                                if(result!=null && result.length()>0)
                                    graph.getVertices().add(new Vertex(result,e.getX(),e.getY()));
                                break;
                            case 1:
                                graph.clear();
                                break;
                            default:
                                break;
                        }
                        canvasMenu.hide();
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
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.BLACK);
            for (Edge e : graph.getEdges()) {
                g.drawLine((int) e.getSource().getX(), (int) e.getSource().getY(), (int) e.getDest().getX(), (int) e.getDest().getY());
                if(graph.isDirected()){
                    double dx = e.getSource().getX()-e.getDest().getX();
                    double dy = e.getSource().getY()-e.getDest().getY();
                    double length = Math.hypot(dx,dy);
                    double angle = Math.acos(dx/length);
                    if(dy>0)
                        angle = 2*Math.PI - angle;
                    arrowXs[2] = (int) (e.getDest().getX() + (dx*VERTEX_SIZE/2)/length);
                    arrowYs[2] = (int) (e.getDest().getY() + (dy*VERTEX_SIZE/2)/length);
                    arrowXs[0] = (int) ( arrowXs[2] - Math.sin(angle- Math.PI/3)*ARROW_SIZE);
                    arrowYs[0] = (int) (arrowYs[2] - Math.cos(angle- Math.PI/3)*ARROW_SIZE);
                    arrowXs[1] = (int) ( arrowXs[2] - Math.sin(angle - Math.PI + Math.PI/3)*ARROW_SIZE);
                    arrowYs[1] = (int) (arrowYs[2] - Math.cos(angle - Math.PI + Math.PI/3)*ARROW_SIZE);

                    g.fillPolygon(arrowXs,arrowYs,3);
                }
                //TODO приделать стрелочки
            }
            for (Vertex v : graph.getVertices()) {
                g.setColor(Color.lightGray);
                g.fillOval((int) v.getX() - VERTEX_SIZE / 2, (int) v.getY() - VERTEX_SIZE / 2, VERTEX_SIZE, VERTEX_SIZE);
                g.setColor(Color.BLACK);
                g.drawOval((int) v.getX() - VERTEX_SIZE / 2, (int) v.getY() - VERTEX_SIZE / 2, VERTEX_SIZE, VERTEX_SIZE);
                g.drawString(v.getName(), (int) v.getX() - 10 * v.getName().length() / 2, (int) v.getY() + 5);
                if(v == editVertex){
                    g.drawOval((int) v.getX() -editVertexMarkerSize/4
                            , (int) v.getY() - editVertexMarkerSize/4
                            , editVertexMarkerSize/2
                            , editVertexMarkerSize/2);
                    editVertexMarkerSize++;
                    if(editVertexMarkerSize > 4*VERTEX_SIZE) editVertexMarkerSize = 2*VERTEX_SIZE;
                }
            }
        }
        if(vertexMenu.isVisible)vertexMenu.draw(g);
        if(canvasMenu.isVisible)canvasMenu.draw(g);
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
        if(vertexMenu.isVisible)
            vertexMenu.updateMousePosition(e.getX(),e.getY());
        if(canvasMenu.isVisible)
            canvasMenu.updateMousePosition(e.getX(),e.getY());
        //System.out.println("move "+e.getX() + "\t" + e.getY());

    }

    class PopUpPanel {
        static final int WIDTH = 130, ELEMENT_HEIGHT = 30;
        private String[] elements;
        private int focusedElement = -1;
        private int x, y;
        private boolean isVisible = false;

        PopUpPanel(String[] elements) {
            this.elements = elements;
        }

        void show(int x, int y) {
            isVisible = true;
            if (x + WIDTH > FRAME_WIDTH) this.x = FRAME_WIDTH - WIDTH;
            else this.x = x;
            if (y + elements.length*ELEMENT_HEIGHT > FRAME_HEIGHT) this.y = FRAME_HEIGHT - elements.length*ELEMENT_HEIGHT;
            else this.y = y;
        }

        void hide() {
            isVisible = false;
        }

        boolean isActive() {
            return isVisible;
        }

        void updateMousePosition(int x, int y) {
            Rectangle r = new Rectangle();
            r.x = this.x;
            r.width = WIDTH;
            r.height = ELEMENT_HEIGHT;
            for (int i = 0; i < elements.length; i++) {
                r.y = this.y + i*ELEMENT_HEIGHT;
                if(r.contains(x,y)){
                    focusedElement = i;
                    return;
                }
            }
            focusedElement = -1;
        }

        int getMouseSelection() {
            return focusedElement;
        }

        void draw(Graphics g) {
            for(int i = 0; i < elements.length; i++){
                if(i == focusedElement)
                    g.setColor(Color.LIGHT_GRAY);
                else
                    g.setColor(Color.GRAY);
                g.fillRect(x, y+i*ELEMENT_HEIGHT, WIDTH, ELEMENT_HEIGHT);
                g.setColor(Color.BLACK);
                g.drawRect(x, y+i*ELEMENT_HEIGHT, WIDTH, ELEMENT_HEIGHT);
                g.drawString(elements[i],x+5,y+i*ELEMENT_HEIGHT + ELEMENT_HEIGHT/2);
            }
        }
    }
}