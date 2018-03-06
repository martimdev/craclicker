package me.martimdev.craclicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import me.martimdev.craclicker.obj.AutoClicker;
import me.martimdev.craclicker.util.Config;
import me.martimdev.craclicker.util.Values;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import static me.martimdev.craclicker.util.Values.CONFIG_PATH;

public class Controller implements Initializable {

    String[] keys = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    String[] buttons = {"1", "2", "3", "4", "5"};
    AutoClicker autoclicker;

    @FXML
    private ComboBox<String> keyPressBox;

    @FXML
    private ComboBox<String> keyStartBox;

    @FXML
    private ComboBox<String> keyStopBox;

    @FXML
    private ComboBox<String> buttonPressBox;

    @FXML
    private ComboBox<String> buttonStartBox;

    @FXML
    private ComboBox<String> buttonStopBox;

    @FXML
    private MenuItem pressModeItem;

    @FXML
    private MenuItem clickModeItem;

    @FXML
    private MenuItem keyboardItem;

    @FXML
    private MenuItem mouseItem;

    @FXML
    private Label minCpsLabel;

    @FXML
    private Label maxCpsLabel;

    @FXML
    private Button downMaxCps;

    @FXML
    private Button upMaxCps;

    @FXML
    private Button upMinCps;

    @FXML
    private Button downMinCps;

    @FXML
    private Button power;

    @FXML
    private Button buttonKeyPress;

    @FXML
    private Button buttonButtonPress;

    @FXML
    private Button buttonKeyStart;

    @FXML
    private Button buttonButtonStart;

    @FXML
    private Button buttonKeyStop;

    @FXML
    private Button buttonButtonStop;

    public Controller() throws AWTException, IOException, SAXException, ParserConfigurationException {
        new File("res").mkdir();
        new File("res/configs").mkdir();
        if (!new File(CONFIG_PATH).isFile()) {
            try {
                Config.saveDefaultConfig();
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        autoclicker = Config.loadAutoClicker();
    }

    @FXML
    void onChangeCps(ActionEvent e) {
        if (e.getSource().equals(upMinCps)) {
            if (autoclicker.getMinCps() < 25 && autoclicker.getMinCps() < autoclicker.getMaxCps()) {
                autoclicker.setMinCps(autoclicker.getMinCps() + 1);
                minCpsLabel.setText(String.valueOf(autoclicker.getMinCps()));
            }
        } else if (e.getSource().equals(downMinCps)) {
            if (autoclicker.getMinCps() > 1) {
                autoclicker.setMinCps(autoclicker.getMinCps() - 1);
                minCpsLabel.setText(String.valueOf(autoclicker.getMinCps()));
            }
        } else if (e.getSource().equals(upMaxCps)) {
            if (autoclicker.getMaxCps() < 25) {
                autoclicker.setMaxCps(autoclicker.getMaxCps() + 1);
                maxCpsLabel.setText(String.valueOf(autoclicker.getMaxCps()));
            }
        } else if (e.getSource().equals(downMaxCps)) {
            if (autoclicker.getMaxCps() > 1 && autoclicker.getMaxCps() > autoclicker.getMinCps()) {
                autoclicker.setMaxCps(autoclicker.getMaxCps() - 1);
                maxCpsLabel.setText(String.valueOf(autoclicker.getMaxCps()));
            }
        }
    }

    @FXML
    void onChangeInputType(ActionEvent e) {
        if (e.getSource().equals(keyboardItem)) {
            autoclicker.setInputType("keyboard");
            showKeys();
        } else if (e.getSource().equals(mouseItem)) {
            autoclicker.setInputType("mouse");
            showButtons();
        }
    }

    @FXML
    void onChangeMode(ActionEvent e) {
        if (e.getSource().equals(pressModeItem)) {
            autoclicker.setMode(AutoClicker.Modes.PRESSMODE);
            pressModeItem.setText("PressMode ✓");
            clickModeItem.setText("ClickMode");
        } else if (e.getSource().equals(clickModeItem)) {
            autoclicker.setMode(AutoClicker.Modes.CLICKMODE);
            clickModeItem.setText("ClickMode ✓");
            pressModeItem.setText("PressMode");
        }
    }

    @FXML
    void onChangeKeys(ActionEvent e) throws NoSuchFieldException, IllegalAccessException {
        if (e.getSource().equals(keyPressBox)) {
            String keyString = keyPressBox.getSelectionModel().getSelectedItem();
            int keyCode = (int) NativeKeyEvent.class.getField("VC_" + keyString).get(new Object());
            autoclicker.setKeyPress(keyCode);
        } else if (e.getSource().equals(keyStartBox)) {
            String keyString = keyStartBox.getSelectionModel().getSelectedItem();
            int keyCode = (int) NativeKeyEvent.class.getField("VC_" + keyString).get(new Object());
            autoclicker.setKeyStart(keyCode);
        } else if (e.getSource().equals(keyStopBox)) {
            String keyString = keyStopBox.getSelectionModel().getSelectedItem();
            int keyCode = (int) NativeKeyEvent.class.getField("VC_" + keyString).get(new Object());
            autoclicker.setKeyStop(keyCode);
        }
    }

    @FXML
    void onChangeButtons(ActionEvent e) {
        if (e.getSource().equals(buttonPressBox)) {
            String buttonName = buttonPressBox.getSelectionModel().getSelectedItem();
            autoclicker.setButtonPress(Integer.parseInt(buttonName));
        } else if (e.getSource().equals(buttonStartBox)) {
            String buttonName = buttonStartBox.getSelectionModel().getSelectedItem();
            autoclicker.setButtonStart(Integer.parseInt(buttonName));
        } else if (e.getSource().equals(buttonStopBox)) {
            String buttonName = buttonStopBox.getSelectionModel().getSelectedItem();
            autoclicker.setButtonStop(Integer.parseInt(buttonName));
        }
    }

    @FXML
    void powerEvent() {
        try {
            Config.saveAutoClicker(autoclicker);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (autoclicker.isLigado()) {
            autoclicker.setLigado(false);
            power.setText("Desligado");
            power.setStyle("-fx-background-color: #0F4A77;");
        } else {
            autoclicker.setLigado(true);
            power.setText("Ligado");
            power.setStyle("-fx-background-color: #1568AA");
        }
    }

    @FXML
    void openGitHub() throws IOException {
        Desktop.getDesktop().browse(URI.create(Values.GIT_HUB_URL));
    }

    void loadComponentsValue() {
        minCpsLabel.setText(String.valueOf(autoclicker.getMinCps()));
        maxCpsLabel.setText(String.valueOf(autoclicker.getMaxCps()));
        if (autoclicker.getMode().equals(AutoClicker.Modes.PRESSMODE)) {
            pressModeItem.setText("PressMode ✓");
        } else if (autoclicker.getMode().equals(AutoClicker.Modes.CLICKMODE)) {
            clickModeItem.setText("ClickMode ✓");
        }
        if (autoclicker.getInputType().equals("keyboard")) {
            showKeys();
        } else if (autoclicker.getInputType().equals("mouse")) {
            autoclicker.setInputType("mouse");
            showButtons();
        }
        keyPressBox.setPromptText(NativeKeyEvent.getKeyText(autoclicker.getKeyPress()));
        keyStartBox.setPromptText(NativeKeyEvent.getKeyText(autoclicker.getKeyStart()));
        keyStopBox.setPromptText(NativeKeyEvent.getKeyText(autoclicker.getKeyStop()));
        buttonPressBox.setPromptText(String.valueOf(autoclicker.getButtonPress()));
        buttonStartBox.setPromptText(String.valueOf(autoclicker.getButtonStart()));
        buttonStopBox.setPromptText(String.valueOf(autoclicker.getButtonStop()));
    }

    void showButtons() {
        buttonPressBox.setVisible(true);
        buttonStartBox.setVisible(true);
        buttonStopBox.setVisible(true);
        buttonButtonPress.setVisible(true);
        buttonButtonStart.setVisible(true);
        buttonButtonStop.setVisible(true);
        keyPressBox.setVisible(false);
        keyStartBox.setVisible(false);
        keyStopBox.setVisible(false);
        buttonKeyPress.setVisible(false);
        buttonKeyStart.setVisible(false);
        buttonKeyStop.setVisible(false);
    }

    void showKeys() {
        keyPressBox.setVisible(true);
        keyStartBox.setVisible(true);
        keyStopBox.setVisible(true);
        buttonKeyPress.setVisible(true);
        buttonKeyStart.setVisible(true);
        buttonKeyStop.setVisible(true);
        buttonPressBox.setVisible(false);
        buttonStartBox.setVisible(false);
        buttonStopBox.setVisible(false);
        buttonButtonPress.setVisible(false);
        buttonButtonStart.setVisible(false);
        buttonButtonStop.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        keyPressBox.getItems().addAll(keys);
        keyStartBox.getItems().addAll(keys);
        keyStopBox.getItems().addAll(keys);
        buttonPressBox.getItems().addAll(buttons);
        buttonStartBox.getItems().addAll(buttons);
        buttonStopBox.getItems().addAll(buttons);
        loadComponentsValue();
        GlobalScreen.addNativeMouseListener(autoclicker);
        GlobalScreen.addNativeKeyListener(autoclicker);
        autoclicker.setLigado(false);
        Thread t1 = new Thread(() -> {
            try {
                autoclicker.click();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
    }

}
