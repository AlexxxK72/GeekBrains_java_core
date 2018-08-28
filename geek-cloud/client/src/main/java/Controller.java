import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;

import javax.xml.soap.Text;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class Controller {
    private CloudClient client;
    private FileHelper fh;
    private String cloudRootPath;
    private String cloudCurrentPath;
    private List<String> cloudListFile;
    private ObservableList<Path> localListFile;
    private List<Path> localListRoot;
    private Path localCurrentPath;
    private boolean isCloudDirectory;

    @FXML
    private Button btnAuth , btnOut, btnCopyLocal, btnDeleteLocal, btnUp,
            btnRenameLocal, btnCopyCloud, btnDeleteCloud, btnRenameCloud;
    @FXML
    private HBox rootPanel, authPanel, outPanel;

    @FXML
    private TextField loginField, passField;

    @FXML
    private Label helloLabel, labelLocalPath, labelCloudPath;

    @FXML
    private ListView localList, cloudList;

    @FXML
    public void initialize() {
        localListFile = FXCollections.observableArrayList();
        localList.setItems(localListFile);
        client = new CloudClient(this);
        fh = FileHelper.getInstance();
        outPanel.managedProperty().bind(outPanel.visibleProperty());
        outPanel.setVisible(false);
        btnAuth.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> client.auth(loginField.getText(), passField.getText()));
        btnOut.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            client.logout();
            flipAuthPanel();
        });
        btnCopyLocal.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            try {
                int index = getSelectedIndex(Storage.Local);
                if (index != -1) {
                    Path currFile = Paths.get(localCurrentPath + localList.getItems().get(index).toString());
                    if (Files.exists(currFile) && !Files.isDirectory(currFile)) {
                        client.uploadFile(cloudCurrentPath + currFile.getFileName(), Files.readAllBytes(currFile));
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        btnDeleteLocal.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            int index = getSelectedIndex(Storage.Local);
            if (index != -1) {
                Path currFile = Paths.get(localList.getItems().get(index).toString());
                fh.deleteFiles(currFile);
                refreshLocalList();
            }
        });
        btnRenameLocal.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
        });
        btnCopyCloud.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

                int index = getSelectedIndex(Storage.Cloud);
                String currFile = cloudList.getItems().get(index).toString();
                if (index != -1) {
                    client.downloadFile(cloudCurrentPath + currFile);
                }
        });

        btnDeleteCloud.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            int index = getSelectedIndex(Storage.Cloud);
            if (index != -1) {
                String name = cloudList.getItems().get(index).toString();
                client.deleteFiles(cloudCurrentPath + name);
            }
        });
        btnRenameCloud.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
        });

        localList.setCellFactory(TextFieldListCell.forListView(new StringConverter<Path>() {

            @Override
            public String toString(Path path) {
                return path.getFileName().toString();
            }

            @Override
            public Path fromString(String string) {
                return Paths.get(localCurrentPath + "\\" + string);
            }
        }));


        localList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null)
                localList.setEditable(false);
            else {
                Path currFile = localListFile.stream()
                        .filter(path -> path.toString().equals(newValue.toString()))
                        .findAny().orElse(null);
                if (currFile != null && !Files.isDirectory(currFile)) {
                    localList.setEditable(true);
                } else localList.setEditable(false);
            }
        });

        cloudList.setCellFactory(TextFieldListCell.forListView());

        cloudList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null && newValue.equals("^"))
                cloudList.setEditable(false);
            else {
                String currFile = cloudListFile.stream()
                        .filter(file -> file == newValue)
                        .findFirst().orElse(null);
                if (currFile != null) {
                    client.isDirectory(cloudCurrentPath + currFile);
                    try {
                        Thread.sleep(200);
                        if (!isCloudDirectory) {
                            cloudList.setEditable(true);
                        } else cloudList.setEditable(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        localList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                int index = getSelectedIndex(Storage.Local);
                if (index != -1) {
                    String fName = localList.getItems().get(index).toString();
                    Path currFile = localListFile.stream()
                            .filter(path -> path.toString().equals(fName))
                            .findAny().orElse(null);
                    if (Files.isDirectory(currFile)) {
                        localCurrentPath = currFile;
                    }
                    refreshLocalList();
                }
            }
        });

        cloudList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                int index = getSelectedIndex(Storage.Cloud);
                if (index != -1) {
                    String currFile = cloudList.getItems().get(index).toString();
                    if(currFile.equals("^")){
                        cloudCurrentPath = Paths.get(cloudCurrentPath + "..\\").normalize().toString();
                        if(!cloudCurrentPath.substring(cloudCurrentPath.length() - 1).equals("\\"))
                            cloudCurrentPath += "\\";

                    }
                    else if (isCloudDirectory) {
                        cloudCurrentPath = cloudCurrentPath + currFile + "\\";
                    }
                    client.getList();
                }
            }
        });

        localList.setOnEditCommit((EventHandler<ListView.EditEvent<String>>) event -> {
            int index = event.getIndex();
            Path oldName = (Path) localList.getItems().get(index);
            Path newName = Paths.get(String.valueOf(event.getNewValue()));
            fh.renameFile(oldName.toString(), newName.toString());
            localListFile.remove(oldName);
            localListFile.add(newName);
        });

        cloudList.setOnEditCommit((EventHandler<ListView.EditEvent<String>>) event -> {
            int index = event.getIndex();
            String oldName = cloudList.getItems().get(index).toString();
            String newName = event.getNewValue();
            client.renameFile(cloudCurrentPath + oldName, cloudCurrentPath + newName);
            cloudListFile.remove(oldName);
            cloudListFile.add(newName);
            cloudList.getItems().set(index, newName);
        });
       initRoots();
    }

    void initRoots(){
        btnUp = new Button("^");
        btnUp.setTextFill(new Color(0.12, 0.55, 0.7, 1));
        btnUp.setMaxWidth(Double.MAX_VALUE);
        btnUp.setVisible(false);
        rootPanel.setHgrow(btnUp, Priority.ALWAYS);
        btnUp.managedProperty().bind(btnUp.visibleProperty());
        btnUp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            localCurrentPath = localCurrentPath.getParent();
            refreshLocalList();
        });
        rootPanel.getChildren().add(btnUp);

        localListRoot = fh.getListRoot();
        for (Path path : localListRoot){
            Button btnRoot = new Button(path.toString().replace(":\\", ""));
            rootPanel.setHgrow(btnRoot, Priority.ALWAYS);
            btnRoot.setMaxWidth(Double.MAX_VALUE);
            rootPanel.getChildren().add(btnRoot);
            btnRoot.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                String root = ((Button)event.getSource()).getText();
                localCurrentPath = Paths.get(root + ":\\");
                refreshLocalList();
            });
        }
        if(localListRoot.contains(Paths.get("C:\\"))){
            localCurrentPath = Paths.get("C:\\");
        }
        else {
           localCurrentPath = localListRoot.get(0);
        }
        refreshLocalList();
    }

    public void setCloudDirectory(boolean cloudDirectory) {
        isCloudDirectory = cloudDirectory;
    }

    public String getCloudRootPath() {
        return cloudRootPath;
    }

    public void setCloudPath(String cloudPath) {
        this.cloudRootPath = cloudPath;
        this.cloudCurrentPath = cloudPath;
    }

    public String getCloudCurrentPath() {
        return cloudCurrentPath;
    }

    public void setCloudCurrentPath(String cloudCurrentPath) {
        this.cloudCurrentPath = cloudCurrentPath;
    }

    public void setCloudListFile(List<String> cloudListFile) {
        this.cloudListFile = cloudListFile;
    }

    public void downloadFile(String name, byte[] file){
        fh.placeFile(localCurrentPath + name, file);
        refreshLocalList();
    }

    public void refreshCloudList(){
        Platform.runLater(() ->{
            labelCloudPath.setText(cloudCurrentPath.replace(cloudRootPath, "cloud:\\"));
            cloudList.getItems().clear();
            if (!cloudRootPath.equals(cloudCurrentPath))
                cloudList.getItems().add("^");
            for (String item : cloudListFile) {
                cloudList.getItems().add(item);
            }
        });
    }

    public void refreshLocalList() {

        Platform.runLater(() -> {
            try {
                labelLocalPath.setText(localCurrentPath.toString());
                localListFile.clear();
                btnUp.managedProperty().bind(btnUp.visibleProperty());
                if (!localListRoot.contains(localCurrentPath)) {
                    btnUp.setVisible(true);
                }
                else {
                    btnUp.setVisible(false);
                }

                localListFile.addAll(Files.list(localCurrentPath).collect(Collectors.toList()));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }

    private int getSelectedIndex(Storage storage){
        ListView view = getListView(storage);
        return view.getFocusModel().getFocusedIndex();
    }

    private ListView getListView(Storage storage){
            if(storage.equals(Storage.Cloud)) return cloudList;
            return localList;
    }

    public void flipAuthPanel(){
        Platform.runLater(() ->{
            authPanel.managedProperty().bind(authPanel.visibleProperty());
            authPanel.setVisible(!authPanel.isVisible());
            outPanel.managedProperty().bind(outPanel.visibleProperty());
            outPanel.setVisible(!outPanel.isVisible());
            if(outPanel.isVisible()){
                helloLabel.setText("Hello, " + client.getLogin());
            }
            else{
                localList.getItems().clear();
                cloudList.getItems().clear();
            }
        });
    }
}
