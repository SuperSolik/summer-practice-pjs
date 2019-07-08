package actions;

import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
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
        fileChooser.setFileFilter(new FileNameExtensionFilter("Graph file", "ogf"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try (Scanner fileScanner = new Scanner(selectedFile)) {
                this.graph.clear();
                while (fileScanner.hasNextLine()) {
                    String rowLine = fileScanner.nextLine();
                    if (rowLine.isEmpty()) {
                        return;
                    }

                    String[] splitLine = rowLine.split(" ");

                    String originVertexName = splitLine[0];
                    int originVertexX = Integer.valueOf(splitLine[1]);
                    int originVertexY = Integer.valueOf(splitLine[2]);

                    boolean vertexCreated = this.graph.createVertex(
                            originVertexName,
                            originVertexX,
                            originVertexY
                    );

                    if (!vertexCreated) { // vertex was created in previous iterations
                        Vertex originVertex = this.graph.getVertex(originVertexName);
                        originVertex.setX(originVertexX);
                        originVertex.setY(originVertexY);
                    }

                    for (int i = 3; i != splitLine.length; ++i) {
                        String destVertexName = splitLine[i];
                        this.graph.createVertex(destVertexName);
                        this.graph.createEdge(originVertexName, destVertexName);
                    }
                }
            } catch (FileNotFoundException err) {/* never reaches, because file selected by user */}
        }
    }
}
