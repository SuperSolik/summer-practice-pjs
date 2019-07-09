package actions;

import graph.Graph;
import graph.Vertex;

import javax.json.*;
import javax.json.stream.JsonParsingException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImportGraphAction extends AbstractAction {

    private Graph graph;

    public ImportGraphAction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Graph json", "json"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try (FileInputStream fileInputStream = new FileInputStream(selectedFile);
                 JsonReader jsonReader = Json.createReader(fileInputStream)) {
                this.graph.clear();

                JsonArray sources = jsonReader.readArray();
                for (JsonObject source : sources.getValuesAs(JsonObject.class)) {
                    try {
                        String sourceName = source.getString("name");
                        int x = source.getInt("x");
                        int y = source.getInt("y");

                        boolean vertexCreated = this.graph.createVertex(sourceName, x, y);
                        if (!vertexCreated) { // vertex was created in previous iterations
                            Vertex originVertex = this.graph.getVertex(sourceName);
                            originVertex.setX(x);
                            originVertex.setY(y);
                        }

                        JsonArray dests = source.getJsonArray("dests");
                        for (JsonString destName : dests.getValuesAs(JsonString.class)) {
                            this.graph.createVertex(destName.getString());
                            this.graph.createEdge(sourceName, destName.getString());
                        }
                    } catch (NullPointerException err) {
                        // delete already created nodes
                        graph.clear();

                        JOptionPane.showMessageDialog(null,
                                "Invalid input file:\nInvalid field name found", "Open file error", JOptionPane.ERROR_MESSAGE);
                        break;
                    } catch (ClassCastException err) {
                        System.out.println("¿Quieres?");
                        throw err;
                    }
                }
            } catch (JsonParsingException | ClassCastException err) {
                // delete created nodes
                this.graph.clear();
                JOptionPane.showMessageDialog(null,
                        "Invalid input file:\n Syntax error while parsing JSON file","Open file error",JOptionPane.ERROR_MESSAGE);
            } catch (IOException err) {
                JOptionPane.showMessageDialog(null,
                        "Unable to open file","Open file error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
