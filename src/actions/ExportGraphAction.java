package actions;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
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
        fileChooser.setFileFilter(new FileNameExtensionFilter("Graph json", "json"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try (FileWriter fileWriter = new FileWriter(selectedFile)) {
                JsonGenerator jsonGenerator = Json.createGenerator(fileWriter);

                jsonGenerator.writeStartArray();
                for (Vertex source : this.graph.getVertices()) {
                    jsonGenerator.writeStartObject()
                        .write("name", source.getName())
                        .write("x", source.getX())
                        .write("y", source.getY());

                        jsonGenerator.writeStartArray("dests");
                        for (Edge edge : source .getEdges()) {
                            jsonGenerator.write(edge.getDest().getName());
                        }
                        jsonGenerator.writeEnd();
                    jsonGenerator.writeEnd();
                }
                jsonGenerator.writeEnd();

                jsonGenerator.close();
            } catch (IOException err) {/* never reaches, because file selected by user */}
        }
    }
}
