package com.github.einjerjar.mc.widgets2;

import com.github.einjerjar.mc.widgets.utils.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Getter
@Accessors(fluent = true)
public class ELabel2 extends EWidget2 {
    @Setter
    protected Component text;

    @Setter
    protected Point<Boolean> centered = new Point<>(true);

    public ELabel2(@NotNull Component text, int x, int y, int w, int h) {
        super(x, y, w, h);
        this.text(text);
        focusable(false);
    }

    @Override
    protected void onRenderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int y = (centered.y() ? center().y() - font.lineHeight / 2 : rect().top()) + 1;
        int lColor = tColor();

        if (centered.x()) {
            guiGraphics.drawCenteredString(font, text, center().x(), y, lColor);
        } else {
            guiGraphics.drawString(font, text, rect().left(), y, lColor);
        }
    }
}
