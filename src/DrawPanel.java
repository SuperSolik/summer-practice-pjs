import javax.swing.*;
import java.awt.*;

// Панелька, рисующая граф
public class DrawPanel extends JPanel {
    private Paint[] p;
    DrawPanel(){
        p = new GradientPaint[4];
        p[0] = new GradientPaint(0,300,Color.RED,150,300,Color.YELLOW);
        p[1] = new GradientPaint(150,300,Color.YELLOW,300,300,Color.GREEN);
        p[2] = new GradientPaint(300,300,Color.GREEN,450,300,Color.BLUE);
        p[3] = new GradientPaint(450,300,Color.BLUE,600,300,Color.MAGENTA);
        setMinimumSize(new Dimension(600,600));
    }
    @Override
    public void paint(Graphics g) {
        //метод отрисовки
        for(int i = 0 ; i < p.length ; i++){
            ((Graphics2D)g).setPaint(p[i]);
            g.fillRect(i*150,0,150,600);
        }
        g.setColor(Color.BLACK);
        g.drawString("Панелька графа",200,300);
    }
}
