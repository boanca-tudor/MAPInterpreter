package Repository;

import Model.ProgramState;
import Model.Statements.IStatement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> repository;
    private String logFilePath;

    public Repository(ProgramState program, String logFilePath) {
        repository = new ArrayList<>();
        addProgramState(program);
        this.logFilePath = logFilePath;
        try {
            clearFile();
        }
        catch (RepositoryException ignored) {}
    }

    @Override
    public void addProgramState(ProgramState newState) {
        repository.add(newState);
    }

    @Override
    public void clear() {
        repository.clear();
    }

    public void setLogFilePath(String path)
    {
        logFilePath = path;
    }

    @Override
    public void logProgramStateExec(ProgramState state) throws RepositoryException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(state.toString());
            logFile.close();
        }
        catch (IOException exception) {
            throw new RepositoryException("Error opening file!");
        }
    }

    @Override
    public void clearFile() throws RepositoryException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.print("");
            logFile.close();
        }
        catch (IOException exception) {
            throw new RepositoryException("Error opening file!");
        }
    }

    @Override
    public List<ProgramState> getProgramsList() {
        return repository;
    }

    @Override
    public void setProgramsList(List<ProgramState> newList) {
        repository = newList;
    }
}
