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
        model.addRow(new Object[]{vertex.getName()});

        vertices.add(vertex);
    }

    Vertex getNext() {
        int lastElIndex = vertices.size() - 1;

        Vertex next = vertices.get(lastElIndex);
        vertices.remove(lastElIndex);

        return next;
    }

    boolean hasNext() {
        return !vertices.isEmpty();
    }

    void remove(Vertex vertex) {
        vertices.remove(vertex);
    }
}
