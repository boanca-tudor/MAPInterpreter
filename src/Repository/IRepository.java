package Repository;

import Model.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramsList();
    void setProgramsList(List<ProgramState> newList);
    void addProgramState(ProgramState newState);
    void clear();
    void logProgramStateExec(ProgramState state) throws RepositoryException;
    void clearFile() throws RepositoryException;
}
