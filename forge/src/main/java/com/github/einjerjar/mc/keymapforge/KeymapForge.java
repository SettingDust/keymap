package com.github.einjerjar.mc.keymapforge;

import com.github.einjerjar.mc.keymap.Keymap;
import com.github.einjerjar.mc.keymap.client.gui.screen.ConfigScreen;
import java.io.File;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(Keymap.MOD_ID)
public class KeymapForge {
    public KeymapForge() {
        switch (FMLEnvironment.dist) {
            case CLIENT -> IDK.clientInit();
            case DEDICATED_SERVER -> IDK.serverInit();
        }
    }

    public static File configDirProvider(String name) {
        return new File(FMLPaths.GAMEDIR.get().resolve("config/" + name).toUri());
    }

    // Prevents referent issue from fml
    public static class IDK {
        private IDK() {}

        private static void serverInit() {
            Keymap.logger().warn(Keymap.SERVER_WARN);
        }

        private static void clientInit() {
            Keymap.init();

            ModLoadingContext.get()
                    .registerExtensionPoint(
                            IConfigScreenFactory.class, () -> (minecraft, parent) -> new ConfigScreen(parent));
        }
    }
}
