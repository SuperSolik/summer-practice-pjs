package actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class FunctionalAction extends AbstractAction {
    Consumer<ActionEvent> lambda;

    public FunctionalAction(Consumer<ActionEvent> customaction) {
        this.lambda = customaction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        lambda.accept(e);
    }
}