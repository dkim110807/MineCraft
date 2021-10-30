package org.minecraft.gui.button.event;

import org.minecraft.gui.button.Button;

public class ButtonEvent {

    private final Button button;

    public ButtonEvent(Button button) {
        this.button = button;
    }

    public final Button getButton() {
        return button;
    }

}
