package org.manger.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MainWindowController extends Application {
    public TableView<Manga> mangaList;

    public static void main() {
        Application.launch();
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mainWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200, 1200);
        stage.setTitle("Manger");
        stage.setScene(scene);
        stage.show();
    }
}
