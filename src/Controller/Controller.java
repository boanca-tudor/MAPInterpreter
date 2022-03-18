package Controller;

import Model.Collections.HeapException;
import Model.Collections.ICustomStack;
import Model.Expressions.ExpressionException;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Statements.StatementException;
import Model.Values.IValue;
import Model.Values.ReferenceValue;
import Repository.IRepository;
import Repository.RepositoryException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.*;

public class Controller {
    private IRepository repository;
    private DisplayFlagState flagState;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.flagState = DisplayFlagState.OFF;
    }

    public Controller(IRepository repository, boolean displayFlagState) {
        this.repository = repository;
        if (displayFlagState) flagState = DisplayFlagState.ON;
        else flagState = DisplayFlagState.OFF;
    }

    public List<ProgramState> removeCompletedList(List<ProgramState> programs) {
        return programs.stream().
                filter(ProgramState::isNotCompleted).
                collect(Collectors.toList());
    }

    public void executeOneStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(3);
        List<ProgramState> programs = removeCompletedList(repository.getProgramsList());

        if (!programs.isEmpty()) {
            List<IValue> addresses = programs.stream().
                    flatMap(program -> program.getSymbolTable().getContent().values().stream())
                    .collect(Collectors.toList());
            programs.get(0).getHeap().setContent(safeGarbageCollector(getAddresses(addresses),
                    getUsedHeapAddresses(programs.get(0).getHeap().getContent()),
                    programs.get(0).getHeap().getContent())
            );
            oneStepForAllPrograms(programs);
            programs = removeCompletedList(repository.getProgramsList());
        }
        else {
            executor.shutdownNow();

            repository.setProgramsList(programs);
        }
    }

    public void executeAllSteps() throws InterruptedException {
        executor = Executors.newFixedThreadPool(3);
        List<ProgramState> programs = removeCompletedList(repository.getProgramsList());

        while (!programs.isEmpty()) {
            List<IValue> addresses = programs.stream().
                            flatMap(program -> program.getSymbolTable().getContent().values().stream())
                            .collect(Collectors.toList());
            programs.get(0).getHeap().setContent(safeGarbageCollector(getAddresses(addresses),
                    getUsedHeapAddresses(programs.get(0).getHeap().getContent()),
                            programs.get(0).getHeap().getContent())
            );
            oneStepForAllPrograms(programs);
            programs = removeCompletedList(repository.getProgramsList());
        }
        executor.shutdownNow();

        repository.setProgramsList(programs);
    }

    public void oneStepForAllPrograms(List<ProgramState> programs) throws InterruptedException {
        programs.forEach(program -> {
            try {
                repository.logProgramStateExec(program);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callList = programs.stream().
                map((ProgramState p) -> (Callable<ProgramState>)(p::executeOneStep)).
                collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream().
                map(future -> {
                    try
                    {
                        return future.get();
                    } catch (InterruptedException | ExecutionException ex) {
                        System.out.println(ex.toString());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        programs.addAll(newProgramList);

        programs.forEach(program -> {
            try {
                repository.logProgramStateExec(program);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });

        repository.setProgramsList(programs);
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> addresses, List<Integer> heapAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().
                filter(e->addresses.contains(e.getKey()) || heapAddresses.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddresses(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {
                    ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    private List<Integer> getUsedHeapAddresses(Map<Integer, IValue> heap) {
        return heap.values().stream().
                filter(value -> value instanceof ReferenceValue).
                map(iValue -> {
                    ReferenceValue val = (ReferenceValue) iValue;
                    return val.getAddress();
                }).collect(Collectors.toList());
    }
}

enum DisplayFlagState {
    ON,
    OFF
}