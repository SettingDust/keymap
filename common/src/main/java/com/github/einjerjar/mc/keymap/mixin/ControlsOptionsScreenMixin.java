package com.github.einjerjar.mc.keymap.mixin;

import com.github.einjerjar.mc.keymap.client.gui.screen.KeymapScreen;
import com.github.einjerjar.mc.keymap.client.gui.screen.LayoutSelectionScreen;
import com.github.einjerjar.mc.keymap.config.KeymapConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Allows the mod to override the default keybinds screen
 */
@Mixin(ControlsScreen.class)
public class ControlsOptionsScreenMixin {

    @SuppressWarnings("UnreachableCode")
    @Redirect(
            method = "method_19872",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"))
    private void keymap$replaceScreen(final Minecraft instance, final Screen old) {
        Screen scr;
        if (KeymapConfig.instance().firstOpenDone()) scr = new KeymapScreen((ControlsScreen) (Object) this);
        else scr = new LayoutSelectionScreen((ControlsScreen) (Object) this);

        instance.setScreen(scr);
    }
}
