package org.minecraft.gui.button;

import org.minecraft.gui.button.event.ButtonEvent;
import org.minecraft.object.GameObject;
import org.minecraft.util.vector.Vector2f;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Button extends GameObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final List<Button> buttons = new ArrayList<>();

    public static void addButton(Button button) {
        buttons.add(button);
    }

    public static void callEvent(ButtonEvent event) {
        buttons.forEach(it -> Button.callEvent(it, event));
    }

    private static void callEvent(Button button, ButtonEvent event) {
        Class<? extends Button> clazz =  button.getClass();
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (method.getParameterCount() > 0 && method.getParameters()[0].getType().equals(event.getClass()))
                callEvent(button,method, event);
        }
    }

    private static void callEvent(Button button,Method method, ButtonEvent event) throws IllegalArgumentException {
        if (method.getParameterCount() != 1)
            throw new IllegalArgumentException("There is no parameter in method " + method + "!");

        try {
            method.invoke(button, event);
        } catch (IllegalArgumentException e) {
            System.err.println("Wrong argument");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("Something went wrong while trying to invoke method " + method + "!");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Vector2f pos;
    private Vector2f scale;
    private int texID;

    public Button(Vector2f pos,Vector2f scale,int texID) {
        this.pos = pos;
        this.scale = scale;
        this.texID = texID;
    }

    public void hide() {

    }

    /**
     * Show this button on the screen
     */
    public void show() {

    }

    /**
     * Removes this button
     */
    public void remove() {

    }

}
