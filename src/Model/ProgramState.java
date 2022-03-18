package Model;

import Model.Collections.*;
import Model.Expressions.ExpressionException;
import Model.Statements.IStatement;
import Model.Statements.StatementException;
import Model.Values.IValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.util.EmptyStackException;

public class ProgramState {
    private ICustomStack<IStatement> executionStack;
    private ICustomDictionary<String, IValue> symbolTable;
    private ICustomList<IValue> output;
    private ICustomDictionary<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, IValue> heap;
    private int id;
    private static int nextID = 0;

    public static synchronized int generateNewID() {
        return ++nextID;
    }

    public ProgramState(ICustomStack<IStatement> stack, ICustomDictionary<String, IValue> symbolTable,
                 ICustomList<IValue> output, ICustomDictionary<StringValue, BufferedReader> fileTable,
                        IHeap<Integer, IValue> heap,
                        int id,
                        IStatement program) {
        executionStack = stack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = id;
        //initialProgram = deepCopy(program);
        executionStack.push(program);
    }

    @Override
    public String toString() {
        return "Program ID : " + id + '\n' +
                "Execution Stack : " + executionStack.toLogString() + '\n' +
                "Symbol Table : " + symbolTable.toLogString() + '\n' +
                "Output : " + output.toLogString() + '\n' +
                "File Table : " + fileTable.toLogString() + '\n' +
                "Heap : " + heap.toLogString() + '\n';
    }

    public ICustomStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(ICustomStack<IStatement> stack) {
        executionStack = stack;
    }

    public ICustomDictionary<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(ICustomDictionary<String, IValue> table) {
        symbolTable = table;
    }

    public ICustomList<IValue> getOutput() {
        return output;
    }

    public void setOutput(ICustomList<IValue> output) {
        this.output = output;
    }

    public ICustomDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(ICustomDictionary<StringValue, BufferedReader> table) {
        fileTable = table;
    }

    public IHeap<Integer, IValue> getHeap() { return heap; }

    public void setHeap(IHeap<Integer, IValue> heap) { this.heap = heap; }

    public int getId() {
        return id;
    }

    public void setID(int newId) {
        id = newId;
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public ProgramState executeOneStep() throws EmptyStackException,
            StatementException, ExpressionException, HeapException {
        if (executionStack.isEmpty()) throw new EmptyStackException();
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

}
