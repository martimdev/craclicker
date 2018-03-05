package me.martimdev.craclicker.obj;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;

public class AutoClicker extends Object implements NativeKeyListener, NativeMouseInputListener {

    public enum Modes {
        PRESSMODE("PRESSMODE"),
        CLICKMODE("PRESSMODE"),;

        Modes(String value) {

        }
    }

    private final Robot clicker = new Robot();
    private static boolean ativo;
    private boolean ligado;
    private String inputType;
    private int buttonPress;
    private int buttonStart;
    private int buttonStop;
    private int keyPress;
    private int keyStart;
    private int keyStop;
    private int button;
    private int minCps;
    private int maxCps;
    private Modes mode;

    public AutoClicker(String inputType, int buttonPress, int buttonStart, int buttonStop, int keyPress, int keyStart, int keyStop, int button, int minCps, int maxCps, Modes mode) throws AWTException {
        this.ligado = false;
        this.inputType = inputType;
        this.buttonPress = buttonPress;
        this.buttonStart = buttonStart;
        this.buttonStop = buttonStop;
        this.keyPress = keyPress;
        this.keyStart = keyStart;
        this.keyStop = keyStop;
        this.button = button;
        this.minCps = minCps;
        this.maxCps = maxCps;
        this.mode = mode;
    }

    public void click() throws InterruptedException {
        while (true) {
            int cps = (int) ((this.getMinCps()) + Math.random() * (this.getMaxCps() - 4));
            Thread.sleep(1000 / cps);
            if (isAtivo() && this.isLigado()) {
                System.out.println(cps);
                this.clicker.mousePress(button);
                this.clicker.mouseRelease(button);
            }
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (this.getInputType().equals("keyboard")) {
            if (this.getMode().equals(Modes.CLICKMODE)) {
                if (e.getKeyCode() == this.getKeyStart()) {
                    this.enable();
                } else if (e.getKeyCode() == this.getKeyStop()) {
                    this.disable();
                }
            } else if (this.getMode().equals(Modes.PRESSMODE)) {
                if (e.getKeyCode() == this.getKeyPress()) {
                    this.enable();
                }
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (this.getInputType().equals("keyboard")) {
            if (this.getMode().equals(Modes.PRESSMODE)) {
                if (e.getKeyCode() == this.getKeyPress()) {
                    this.disable();
                }
            }
        }
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        if (this.getInputType().equals("mouse")) {
            if (this.getMode().equals(Modes.CLICKMODE)) {
                if (e.getButton() == this.getButtonStart()) {
                    enable();
                } else if (e.getButton() == this.getButtonStop()) {
                    disable();
                }
            } else if (this.getMode().equals(Modes.PRESSMODE)) {
                if (e.getButton() == this.getButtonPress()) {
                    this.enable();
                }
            }
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        if (this.getInputType().equals("mouse"))
            if (this.getMode().equals(Modes.PRESSMODE)) {
                if (e.getButton() == this.getButtonPress()) {
                    this.disable();
                }
            }
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
    }

    protected void enable() {
        setAtivo(true);
    }

    protected void disable() {
        setAtivo(false);
    }

    public static boolean isAtivo() {
        return ativo;
    }

    public static void setAtivo(boolean ativo) {
        AutoClicker.ativo = ativo;
    }

    public boolean isLigado() {
        return ligado;
    }

    public void setLigado(boolean ligado) {
        this.ligado = ligado;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public Modes getMode() {
        return mode;
    }

    public void setMode(Modes mode) {
        this.mode = mode;
    }

    public int getMinCps() {
        return minCps;
    }

    public void setMinCps(int minCps) {
        this.minCps = minCps;
    }

    public int getMaxCps() {
        return maxCps;
    }

    public void setMaxCps(int maxCps) {
        this.maxCps = maxCps;
    }

    public int getButtonPress() {
        return buttonPress;
    }

    public void setButtonPress(int buttonPress) {
        this.buttonPress = buttonPress;
    }

    public int getButtonStart() {
        return buttonStart;
    }

    public void setButtonStart(int buttonStart) {
        this.buttonStart = buttonStart;
    }

    public int getButtonStop() {
        return buttonStop;
    }

    public void setButtonStop(int buttonStop) {
        this.buttonStop = buttonStop;
    }

    public int getKeyPress() {
        return keyPress;
    }

    public void setKeyPress(int keyPress) {
        this.keyPress = keyPress;
    }

    public int getKeyStart() {
        return keyStart;
    }

    public void setKeyStart(int keyStart) {
        this.keyStart = keyStart;
    }

    public int getKeyStop() {
        return keyStop;
    }

    public void setKeyStop(int keyStop) {
        this.keyStop = keyStop;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

}
