package View.GUI;

import Model.Collections.*;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Statements.*;
import Model.Statements.FileStatements.CloseFileStatement;
import Model.Statements.FileStatements.OpenFileStatement;
import Model.Statements.FileStatements.ReadFileStatement;
import Model.Statements.HeapStatements.HeapWriteStatement;
import Model.Statements.HeapStatements.NewStatement;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.io.BufferedReader;

public class SelectWindowController {
    public ListView<String> programsListView;

    private ObservableList<IStatement> programs;
    private ObservableList<String> programDescriptions;

    @FXML
    public void initialize() throws Exception {
        createProgramStateList();
        programsListView.setItems(programDescriptions);
    }

    public void createNewAnalyzeWindow() {
        if (programsListView.getSelectionModel().getSelectedItem() != null) {
            IStatement program = programs.get(programsListView.getSelectionModel().getSelectedIndex());
            try {
                program.typeCheck(new CustomDictionary<>());
                AnalyzeWindow window = new AnalyzeWindow(InitializeProgramState(program));
            }
            catch (TypeMismatchException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText(ex.getMessage());
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select one of the programs");
            alert.setTitle("Error!");
            alert.show();
        }
    }

    private static ProgramState InitializeProgramState(IStatement statement)
    {
        ICustomStack<IStatement> stack = new CustomStack<>();
        ICustomDictionary<String, IValue> symbolTable = new CustomDictionary<>();
        ICustomList<IValue> output = new CustomList<>();
        ICustomDictionary<StringValue, BufferedReader> fileTable = new CustomDictionary<>();
        IHeap<Integer, IValue> heap = new Heap();

        return new ProgramState(stack, symbolTable, output, fileTable, heap, ProgramState.generateNewID(), statement);
    }

    public void createProgramStateList() {
        programs = FXCollections.observableArrayList();
        programDescriptions = FXCollections.observableArrayList();

        IStatement example1 = new CompoundStatement(new DeclareStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new
                        VariableEvaluationExpression("v"))));

        IStatement example2 = new CompoundStatement(new DeclareStatement("a", new IntType()),
                new CompoundStatement(new DeclareStatement("b", new IntType()),
                        new CompoundStatement(new AssignStatement("a",
                                new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(0)), '/'), '+')),
                                new CompoundStatement(new AssignStatement("b",
                                        new ArithmeticExpression(new VariableEvaluationExpression("a"),
                                                new ValueExpression(new IntValue(1)), '+')),
                                        new PrintStatement(new VariableEvaluationExpression("b"))))));

        IStatement example3 = new CompoundStatement(new DeclareStatement("a", new BoolType()),
                new CompoundStatement(new DeclareStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableEvaluationExpression("a"),
                                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableEvaluationExpression("v"))))));

        IStatement example4 = new CompoundStatement(new DeclareStatement("varf", new StringType()),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenFileStatement(new VariableEvaluationExpression("varf")),
                                new CompoundStatement(new DeclareStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFileStatement(
                                                new VariableEvaluationExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(
                                                        new VariableEvaluationExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(
                                                                new VariableEvaluationExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(
                                                                        new VariableEvaluationExpression("varc")),
                                                                        new CloseFileStatement(
                                                                                new VariableEvaluationExpression("varf"))
                                                                ))))))));

        IStatement example5 = new CompoundStatement(new DeclareStatement("a", new IntType()),
                new CompoundStatement(new DeclareStatement("b", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntValue(3))),
                                new CompoundStatement(new AssignStatement("b", new ValueExpression(new IntValue(4))),
                                        new IfStatement(new RelationalExpression(
                                                new VariableEvaluationExpression("a"),
                                                new VariableEvaluationExpression("b"),
                                                "<="
                                        ),
                                                new PrintStatement(new VariableEvaluationExpression("a")),
                                                new PrintStatement(new VariableEvaluationExpression("b")))))));

        IStatement example6 = new CompoundStatement(new DeclareStatement("varf", new StringType()),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenFileStatement(new VariableEvaluationExpression("varf")),
                                new CompoundStatement(new DeclareStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFileStatement(
                                                new VariableEvaluationExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(
                                                        new VariableEvaluationExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(
                                                                new VariableEvaluationExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(
                                                                        new VariableEvaluationExpression("varc")),
                                                                        new CompoundStatement(new CloseFileStatement(
                                                                                new VariableEvaluationExpression("varf")),
                                                                                new CloseFileStatement(
                                                                                        new VariableEvaluationExpression("varf")
                                                                                ))
                                                                ))))))));

        IStatement example7 = new CompoundStatement(new DeclareStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new DeclareStatement("a",
                                new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewStatement("a",
                                        new VariableEvaluationExpression("v")),
                                        new CompoundStatement(new PrintStatement
                                                (new VariableEvaluationExpression("v")),
                                                new PrintStatement(new VariableEvaluationExpression("a")))))));

        IStatement example8 = new CompoundStatement(
                new DeclareStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new DeclareStatement("a",
                                        new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a",
                                                new VariableEvaluationExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new HeapReadExpression(
                                                        new VariableEvaluationExpression("v"))),
                                                new PrintStatement(
                                                        new ArithmeticExpression(
                                                                new HeapReadExpression(
                                                                        new HeapReadExpression(
                                                                                new VariableEvaluationExpression(
                                                                                        "a"
                                                                                )
                                                                        )),
                                                                new ValueExpression(new IntValue(5)), '+')
                                                )
                                        )
                                )
                        )
                )
        );

        IStatement example9 = new CompoundStatement(
                new DeclareStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReadExpression(new VariableEvaluationExpression("v"))),
                                new CompoundStatement(
                                        new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new HeapReadExpression(new VariableEvaluationExpression("v")),
                                                        new ValueExpression(new IntValue(5)), '+'
                                                )
                                        )
                                )
                        )
                )
        );

        IStatement example10 = new CompoundStatement(
                new DeclareStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new DeclareStatement("a",
                                        new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a",
                                                new VariableEvaluationExpression("v")),
                                        new CompoundStatement(
                                                new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(
                                                        new HeapReadExpression(new HeapReadExpression(
                                                                new VariableEvaluationExpression("a")
                                                        )))
                                        )
                                )
                        )));

        IStatement example11 = new CompoundStatement(
                new DeclareStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableEvaluationExpression("v"),
                                                new ValueExpression(new IntValue(0)), ">"),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableEvaluationExpression("v")),
                                                new AssignStatement("v", new ArithmeticExpression(
                                                        new VariableEvaluationExpression("v"),
                                                        new ValueExpression(new IntValue(1)),
                                                        '-'))
                                        )
                                )
                                , new PrintStatement(new VariableEvaluationExpression("v")))));

        IStatement example12 = new CompoundStatement(
                new DeclareStatement("v", new IntType()),
                new CompoundStatement(
                        new DeclareStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(new HeapWriteStatement("a",
                                                                new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignStatement("v",
                                                                                new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(
                                                                                        new VariableEvaluationExpression(
                                                                                                "v"
                                                                                        )),
                                                                                new PrintStatement(
                                                                                        new HeapReadExpression(
                                                                                                new VariableEvaluationExpression("a")
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableEvaluationExpression("v")),
                                                        new PrintStatement(
                                                                new HeapReadExpression(new VariableEvaluationExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        IStatement example13 = new CompoundStatement(
                new DeclareStatement("v", new IntType()),
                new CompoundStatement(
                        new DeclareStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(new HeapWriteStatement("a",
                                                                new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignStatement("v",
                                                                                new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(
                                                                                        new VariableEvaluationExpression(
                                                                                                "v"
                                                                                        )),
                                                                                new PrintStatement(
                                                                                        new HeapReadExpression(
                                                                                                new VariableEvaluationExpression("a")
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )),
                                                new CompoundStatement(
                                                        new ForkStatement(
                                                                new CompoundStatement(
                                                                        new AssignStatement("v",
                                                                                new ValueExpression(new IntValue(16))),
                                                                        new PrintStatement(
                                                                                new VariableEvaluationExpression(
                                                                                        "v"
                                                                                ))
                                                                )
                                                        ),
                                                        new CompoundStatement(new PrintStatement(new VariableEvaluationExpression("v")),
                                                                new PrintStatement(
                                                                        new HeapReadExpression(new VariableEvaluationExpression("a")))

                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        IStatement example14 = new CompoundStatement(
                new DeclareStatement("v", new IntType()),
                new CompoundStatement(
                        new DeclareStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(new HeapWriteStatement("a",
                                                                new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignStatement("v",
                                                                                new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(
                                                                                        new VariableEvaluationExpression(
                                                                                                "v"
                                                                                        )),
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(
                                                                                                        new HeapWriteStatement(
                                                                                                                "a", new ValueExpression(new IntValue(100))
                                                                                                        ),
                                                                                                        new CompoundStatement(
                                                                                                                new AssignStatement("v",
                                                                                                                        new ValueExpression(new IntValue(50))),
                                                                                                                new PrintStatement(
                                                                                                                        new VariableEvaluationExpression("v")
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new PrintStatement(
                                                                                                new HeapReadExpression(
                                                                                                        new VariableEvaluationExpression("a")
                                                                                                )
                                                                                        )
                                                                                )

                                                                        )
                                                                )
                                                        )),
                                                new CompoundStatement(new PrintStatement(new VariableEvaluationExpression("v")),
                                                        new PrintStatement(
                                                                new HeapReadExpression(new VariableEvaluationExpression("a")))

                                                )
                                        )
                                )
                        )
                )
        );

        IStatement example15 = new CompoundStatement(new DeclareStatement("v", new BoolType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new
                        VariableEvaluationExpression("v"))));

        addEntry(example1);
        addEntry(example2);
        addEntry(example3);
        addEntry(example4);
        addEntry(example5);
        addEntry(example6);
        addEntry(example7);
        addEntry(example8);
        addEntry(example9);
        addEntry(example10);
        addEntry(example11);
        addEntry(example12);
        addEntry(example13);
        addEntry(example14);
        addEntry(example15);
    }

    private void addEntry(IStatement example) {
        programs.add(example);
        programDescriptions.add(example.toString());
    }
}
