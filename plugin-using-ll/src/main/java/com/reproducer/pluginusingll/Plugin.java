package com.reproducer.pluginusingll;

import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Override
    public void onLoad() {
        getLogger().info("onLoad - Note that the Caffeine class is not loaded yet.");
    }

    @Override
    public void onEnable() {
        try {
            Class.forName("com.github.benmanes.caffeine.cache.Caffeine");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Library loader malfunction", ex);
        }
        getLogger().info("""
                onEnable - Loaded Caffeine class from this plugin. " +
                This is expected behavior - plugins should see their own libraries.""");
    }

}
