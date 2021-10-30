package org.minecraft.gui.button;

import org.minecraft.annotaions.ButtonEventHandler;
import org.minecraft.gui.button.event.ButtonClickEvent;

public final class ButtonTest extends Button {

    @ButtonEventHandler
    public void onClick(ButtonClickEvent event) {
        System.out.println("Success");
    }

}
