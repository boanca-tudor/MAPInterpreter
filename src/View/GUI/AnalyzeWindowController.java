package View.GUI;

import Controller.Controller;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Values.IValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.Repository;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;
import java.util.stream.Collectors;

public class AnalyzeWindowController {
    private ProgramState selectedState;
    private IRepository repository;
    private Controller controller;

    @FXML
    private TextField tfProgramsCount;
    @FXML
    private TableView<Map.Entry<Integer, IValue>> tvHeapTable;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, Integer> colHeapAddress;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> colHeapValue;
    @FXML
    private ListView<IValue> lvOut;
    @FXML
    private ListView<StringValue> lvFileTable;
    @FXML
    private ListView<Integer> lvProgramIDs;
    @FXML
    private TableView<Map.Entry<String, IValue>> tvSymbolTable;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> colName;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> colSymbolValue;

    @FXML
    private ListView<IStatement> lvExecutionStack;
    @FXML
    private Button btnRunOneStep;

    @FXML
    public void initialize() throws Exception {
        colName.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey()));
        colSymbolValue.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));

        colHeapAddress.setCellValueFactory(entryIntegerCellDataFeatures -> new SimpleIntegerProperty(entryIntegerCellDataFeatures.getValue().getKey()).asObject());
        colHeapValue.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));
    }

    public void executeOneStep() {
        try {
            controller.executeOneStep();
            updateComponents();
        }
        catch (InterruptedException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public void setState(ProgramState state) {
        selectedState = state;
        repository = new Repository(state, "log1.txt");
        controller = new Controller(repository, true);
        updateComponents();
    }

    private void updateComponents() {
        updateProgramCount();
        updateProgramIDs();

        updateAccessibility();
        updateExecutionStack();
        updateSymbolTable();

        updateOutputList();
        updateHeapTable();
        updateFileList();
    }

    private void updateFileList() {
        ObservableList<StringValue> fileTableValues = FXCollections.observableArrayList(selectedState.getFileTable().getContent().keySet());
        lvFileTable.setItems(fileTableValues);
        lvFileTable.refresh();
    }

    private void updateHeapTable() {
        ObservableList<Map.Entry<Integer, IValue>> heapTableValues = FXCollections.observableArrayList(selectedState.getHeap().getContent().entrySet());
        tvHeapTable.setItems(heapTableValues);
        tvHeapTable.refresh();
    }

    private void updateExecutionStack() {
        ObservableList<IStatement> executionStackValues = FXCollections.observableArrayList(selectedState.getExecutionStack().getContent());
        lvExecutionStack.setItems(executionStackValues);
        lvExecutionStack.refresh();
    }

    private void updateOutputList() {
        ObservableList<IValue> outValues = FXCollections.observableArrayList(selectedState.getOutput().getContent());
        lvOut.setItems(outValues);
        lvOut.refresh();
    }

    private void updateSymbolTable() {
        ObservableList<Map.Entry<String, IValue>> symbolTableValues = FXCollections.observableArrayList(selectedState.getSymbolTable().getContent().entrySet());
        tvSymbolTable.setItems(symbolTableValues);
        tvSymbolTable.refresh();
    }

    private void updateAccessibility() {
        if (repository.getProgramsList().size() == 0) {
            btnRunOneStep.setDisable(true);
            lvProgramIDs.setDisable(true);
        }
    }

    private void updateProgramCount() {
        tfProgramsCount.setText(Integer.toString(repository.getProgramsList().size()));
    }

    private void updateProgramIDs() {
        ObservableList<Integer> programIDValues = FXCollections.observableArrayList();
        for (ProgramState p : repository.getProgramsList()) {
            programIDValues.add(p.getId());
        }

        lvProgramIDs.setItems(programIDValues);
        lvProgramIDs.refresh();
    }

    public void updateTables() {
        Integer id = lvProgramIDs.getSelectionModel().getSelectedItem();

        ProgramState state = repository.getProgramsList().stream()
                .filter(programState -> programState.getId() == id)
                .findAny()
                .orElse(null);

        if (state != null) {
            selectedState = state;
            updateSymbolTable();
            updateExecutionStack();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Program state not found!");
            alert.show();
        }
    }
}
