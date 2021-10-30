package org.minecraft.gui.button.event;

import org.minecraft.gui.button.Button;

public final class ButtonClickEvent extends ButtonEvent {

    public ButtonClickEvent(Button button) {
        super(button);
    }

    public Button getClickedButton() {
        return super.getButton();
    }

}
