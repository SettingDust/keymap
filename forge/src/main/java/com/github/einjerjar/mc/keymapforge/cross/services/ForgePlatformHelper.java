package com.github.einjerjar.mc.keymapforge.cross.services;

import com.github.einjerjar.mc.keymap.cross.services.IKeybindHelper;
import com.github.einjerjar.mc.keymap.cross.services.IPlatformHelper;
import com.github.einjerjar.mc.keymap.cross.services.ITickHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override public String loader() {
        return "forge";
    }

    @Override public boolean modLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Override public boolean dev() {
        return !FMLLoader.isProduction();
    }

    @Override public File config(String file) {
        return new File(FMLPaths.GAMEDIR.get().resolve("config/" + file).toUri());
    }

    @Override public IKeybindHelper keybindHelper() {
        return new ForgeKeybindHelper();
    }

    @Override public ITickHelper tickHelper() {
        return new ForgeTickHelper();
    }
}
