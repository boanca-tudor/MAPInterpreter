package View;

import Controller.Controller;
import Model.Collections.HeapException;
import Model.Expressions.ExpressionException;
import Model.Statements.StatementException;
import Repository.RepositoryException;

import java.util.concurrent.ExecutionException;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.executeAllSteps();
        }
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
