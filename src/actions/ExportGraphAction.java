package actions;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportGraphAction extends AbstractAction {
    private Graph graph;

    public ExportGraphAction(Graph graph) {
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
