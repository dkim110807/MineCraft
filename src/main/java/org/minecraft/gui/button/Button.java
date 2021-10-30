package org.minecraft.gui.button;

import org.minecraft.gui.button.event.ButtonEvent;
import org.minecraft.object.GameObject;

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

    public static boolean callEvent(Button button, ButtonEvent event) {
        if (!buttons.contains(button))
            return false;

        callEvent(button.getClass(), event);
        return true;
    }

    private static void callEvent(Class<? extends Button> clazz, ButtonEvent event) {
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (method.getParameterCount() > 0 && method.getParameters()[0].getType().equals(event.getClass()))
                callEvent(method, event);
        }
    }

    private static void callEvent(Method method, ButtonEvent event) throws IllegalArgumentException {
        if (method.getParameterCount() != 1)
            throw new IllegalArgumentException("There is no parameter in method " + method + "!");

        try {
            method.invoke(new ButtonTest(), event);
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

}
