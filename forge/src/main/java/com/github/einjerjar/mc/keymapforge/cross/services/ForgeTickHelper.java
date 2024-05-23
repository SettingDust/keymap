package com.github.einjerjar.mc.keymapforge.cross.services;

import com.github.einjerjar.mc.keymap.cross.services.ITickHelper;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

public class ForgeTickHelper implements ITickHelper {
    static final IEventBus modEventBus = NeoForge.EVENT_BUS;
    static EndTickListener endTickListener = null;

    @Override
    public void registerEndClientTick(EndTickListener listener) {
        // hacky af, lol
        endTickListener = listener;
        modEventBus.addListener(ForgeTickHelper::clientTick);
    }

    public static void clientTick(ClientTickEvent.Post e) {
        endTickListener.execute(Minecraft.getInstance());
    }
}
