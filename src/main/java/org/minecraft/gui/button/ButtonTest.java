package org.minecraft.gui.button;

import org.minecraft.annotaions.ButtonEventHandler;
import org.minecraft.gui.button.event.ButtonClickEvent;
import org.minecraft.util.vector.Vector2f;

public final class ButtonTest extends Button {

    public ButtonTest(Vector2f pos, Vector2f scale, int texID) {
        super(pos, scale, texID);
    }

    @ButtonEventHandler
    public void onClick(ButtonClickEvent event) {
        Button button = event.getClickedButton();
        if (button != this)
            return;

        System.out.println("Success");
    }

}
