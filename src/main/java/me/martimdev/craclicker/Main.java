package me.martimdev.craclicker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static me.martimdev.craclicker.util.Values.APP_NAME;
import static me.martimdev.craclicker.util.Values.ICON_PATH;

public class Main extends Application {

    public static void main(String[] args) throws NativeHookException {
        Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
        GlobalScreen.registerNativeHook();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent scene = FXMLLoader.load(getClass().getResource("layouts/main.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));
        stage.setTitle(APP_NAME);
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

}
