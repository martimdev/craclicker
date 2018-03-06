package me.martimdev.craclicker.util;

import me.martimdev.craclicker.obj.AutoClicker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static me.martimdev.craclicker.util.Values.CONFIG_PATH;

public class Config {

    public final static void saveDefaultConfig() throws AWTException, IOException {
        File config = new File(CONFIG_PATH);
        try {
            config.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AutoClicker defaul = new AutoClicker("keyboard", 0, 0, 0, 0, 0, 0, 16, 10, 10, AutoClicker.Modes.PRESSMODE);
        saveAutoClicker(defaul);

    }

    public static void saveAutoClicker(AutoClicker autoClicker) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_PATH));
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<autoclicker" +
                " inputType=\"" + autoClicker.getInputType() + "\"" +
                " buttonPress=\"" + autoClicker.getButtonPress() + "\"" +
                " buttonStart=\"" + autoClicker.getButtonStart() + "\"" +
                " buttonStop=\"" + autoClicker.getButtonStop() + "\"" +
                " keyPress=\"" + autoClicker.getKeyPress() + "\"" +
                " keyStart=\"" + autoClicker.getKeyStart() + "\"" +
                " keyStop=\"" + autoClicker.getKeyStop() + "\"" +
                " button=\"" + autoClicker.getButton() + "\"" +
                " minCps=\"" + autoClicker.getMinCps() + "\"" +
                " maxCps=\"" + autoClicker.getMaxCps() + "\"" +
                " mode=\"" + autoClicker.getMode() + "\"" +
                "></autoclicker>";
        writer.write(xml);
        writer.close();
    }

    public static AutoClicker loadAutoClicker() throws AWTException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(CONFIG_PATH);
        NodeList lista = doc.getElementsByTagName("autoclicker");
        Node nodeAutoclicker = lista.item(0);
        Element autoclicker = (Element) nodeAutoclicker;
        String inputType = autoclicker.getAttribute("inputType");
        int buttonPress = Integer.parseInt(autoclicker.getAttribute("buttonPress"));
        int buttonStart = Integer.parseInt(autoclicker.getAttribute("buttonStart"));
        int buttonStop = Integer.parseInt(autoclicker.getAttribute("buttonStop"));
        int keyPress = Integer.parseInt(autoclicker.getAttribute("keyPress"));
        int keyStart = Integer.parseInt(autoclicker.getAttribute("keyStart"));
        int keyStop = Integer.parseInt(autoclicker.getAttribute("keyStop"));
        int button = Integer.parseInt(autoclicker.getAttribute("button"));
        int minCps = Integer.parseInt(autoclicker.getAttribute("minCps"));
        int maxCps = Integer.parseInt(autoclicker.getAttribute("maxCps"));
        String modeString = autoclicker.getAttribute("mode");
        AutoClicker.Modes mode = null;
        if (modeString.equals("PRESSMODE")) {
            mode = AutoClicker.Modes.PRESSMODE;
        } else if (modeString.equals("CLICKMODE")) {
            mode = AutoClicker.Modes.CLICKMODE;
        }
        return new AutoClicker(inputType, buttonPress, buttonStart, buttonStop, keyPress, keyStart, keyStop, button, minCps, maxCps, mode);
    }

}
