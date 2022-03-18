package Model.Statements;

import Model.Collections.ICustomDictionary;
import Model.Expressions.ExpressionException;
import Model.ProgramState;
import Model.Types.*;
import Model.Values.IValue;
import Model.Values.ReferenceValue;

import java.util.Objects;

public class DeclareStatement implements IStatement {
    private String name;
    private IType type;

    public DeclareStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public IStatement deepCopy() {
        return new DeclareStatement(name, type);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        typeEnvironment.put(name, type);
        return typeEnvironment;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        ICustomDictionary<String, IValue> symbolTable = state.getSymbolTable();

        if (symbolTable.containsKey(name)) throw new StatementException("Variable" + name + " is already declared");
        if (Objects.equals(type.toString(), "int"))
            symbolTable.put(name, new IntType().defaultValue());
        else if (Objects.equals(type.toString(), "bool"))
            symbolTable.put(name, new BoolType().defaultValue());
        else if (Objects.equals(type.toString(), "String"))
            symbolTable.put(name, new StringType().defaultValue());
        else if (type instanceof ReferenceType)
            symbolTable.put(name, new ReferenceType(((ReferenceType)type).getInner()).defaultValue());
        return null;
    }
}
