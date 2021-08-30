package org.manger.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CategoryManager {

    @FXML private Pane chooseCategoryPane;
    @FXML private ListView<String> selectCategoryList;
    @FXML private Pane addCategoryPane;
    @FXML private TextField newCategoryBar;
    @FXML private Button addCategoryButton;
    private DataStorage storage;
    private MainWindowController mainWindowController;

    public void setStorage(DataStorage storage) {
        this.storage = storage;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setCategoryList(ListView<String> selectCategoryList) {
        this.selectCategoryList = selectCategoryList;
    }

    /**
     * This method is always called when this window
     * is closed.
     */
    private void emptyCategoryList() {
        selectCategoryList.getItems().clear();
    }

    @FXML
    private void categorySelected(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            emptyCategoryList();
            storage.setSelectedCategory(selectCategoryList.getSelectionModel().getSelectedItem());
            closeThisWindow();
        }
    }

    public void switchToCategoryAdding() {
        chooseCategoryPane.setVisible(false);
        addCategoryPane.setVisible(true);
    }

    public void switchToCategoryList() {
        chooseCategoryPane.setVisible(true);
        addCategoryPane.setVisible(false);
    }

    public void addCategory(ActionEvent actionEvent) {
        String newCategory = newCategoryBar.getText();
        if(!newCategory.isEmpty()) {
            mainWindowController.addCategoryToList(newCategory);
            closeThisWindow();
        }
    }

    private void closeThisWindow() {
        Stage stage = (Stage) addCategoryButton.getScene().getWindow();
        stage.close();
    }
}
