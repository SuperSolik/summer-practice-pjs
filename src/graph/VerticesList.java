package graph;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class VerticesList {
    private DefaultTableModel model;
    private List<Vertex> vertices = new ArrayList<>();

    public VerticesList(DefaultTableModel model) {
        this.model = model;
    }

    void append(Vertex vertex) {
        this.model.addRow(new Object[]{vertex.getName()});
        this.vertices.add(vertex);
    }

    String getNext() {
        int lastElIndex = this.vertices.size() - 1;

        Vertex next = this.vertices.get(lastElIndex);
        this.vertices.remove(lastElIndex);

        return next.getName();
    }

    void remove(Vertex vertex) {
        this.vertices.remove(vertex);
    }
}
