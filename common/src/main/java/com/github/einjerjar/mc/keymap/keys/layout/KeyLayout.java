package com.github.einjerjar.mc.keymap.keys.layout;

import com.github.einjerjar.mc.keymap.Keymap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.FileToIdConverter;

/**
 * Layout instance, and Registry of layouts for the mod
 * TODO: Separate the registry
 */
@ToString
@AllArgsConstructor
@Accessors(fluent = true)
public class KeyLayout {
    /**
     * Where the default layouts are located
     */
    private static final String LAYOUT_ROOT = "assets/keymap/layouts";
    /**
     * The default layout (en_us (iirc))
     * TODO: Check if this is even used at all
     */
    @Getter
    protected static KeyLayout layoutDefault;
    /**
     * The current layout
     * TODO: Check if this is even used at all
     */
    @Getter
    protected static KeyLayout layoutCurrent;
    /**
     * List of all registered layouts and their name codes
     */
    @Getter
    protected static HashMap<String, KeyLayout> layouts = new HashMap<>();

    /**
     * The metadata for this layout instance
     */
    @Getter
    @Setter
    protected KeyMeta meta;
    /**
     * The actual key groups
     */
    @Getter
    @Setter
    protected Keys keys;

    /**
     * Registers a layout
     *
     * @param layout The layout to register
     */
    public static void registerLayout(KeyLayout layout) {
        layouts.put(layout.meta.code, layout);
        updateMouseKeys(layout.keys.mouse());
        updateMouseKeys(layout.keys.basic());
        updateMouseKeys(layout.keys.numpad());
        updateMouseKeys(layout.keys.extra());
    }

    /**
     * Assures all the items under the mouse group, that they are, indeed part of the mouse group
     *
     * @param rows The rows under the mouse group, or any other group for that matter
     */
    protected static void updateMouseKeys(List<KeyRow> rows) {
        for (KeyRow row : rows) {
            for (KeyData key : row.row) {
                if (key.code() < 10) {
                    key.mouse(true);
                }
            }
        }
    }

    /**
     * Loads all the predefined layouts from the mod
     */
    public static void loadKeys() throws IOException {
        layouts.clear();

        var resourceManager = Minecraft.getInstance().getResourceManager();
        var keymapLayouts = FileToIdConverter.json("keymap_layouts").listMatchingResources(resourceManager);

        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();

        for (final var entry : keymapLayouts.entrySet()) {
            try {
                Keymap.logger().info("Load layout {}", entry.getKey());
                registerLayout(gson.fromJson(entry.getValue().openAsReader(), KeyLayout.class));
            } catch (JsonSyntaxException | JsonIOException | IOException e) {
                Keymap.logger().error("Failed to load keymap layout {}", entry.getKey(), e);
            }
        }
    }

    /**
     * Should return the current layout, iirc is not used at all
     * TODO: Use or remove, prolly use, idk
     *
     * @return The current layout
     */
    public static KeyLayout getCurrentLayout() {
        return layoutCurrent;
    }

    /**
     * Gets a layout based on its name code
     *
     * @param code The name code of the layout
     *
     * @return The chosen layout, or english if not found
     */
    public static KeyLayout getLayoutWithCode(String code) {
        if (layouts.containsKey(code)) {
            return layouts.get(code);
        } else {
            Keymap.logger().warn("Cannot find layout for [{}], defaulting to en_us", code);
            return layouts.get("en_us");
        }
    }
}
