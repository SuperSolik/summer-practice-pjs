package actions;

import graph.Graph;
import graph.Vertex;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParsingException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ImportGraphAction extends AbstractAction {
    /*
    TODO
    Сделать проверку на корректный файл
    Возможно добавить поддержку json
     */
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
                        this.graph.clear();

                        System.out.println("неправильное имя у полей в json");
                        // TODO неправильное имя у полей в json
                    } catch (ClassCastException err) {
                        throw err;
                    }
                }
            } catch (JsonParsingException | ClassCastException err) {
                // delete already created nodes
                this.graph.clear();

                System.out.println("неправильный формат (к примеру ожидается массив [], а пришел объект {})");
                // TODO неправильный формат (к примеру ожидается массив [], а пришел объект {})
            } catch (IOException err) {
                /* never reaches, because file selected by user */
            }
        }
    }
}
