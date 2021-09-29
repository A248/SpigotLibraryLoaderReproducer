package com.reproducer.pluginindependent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

    @Override
    public void onLoad() {
        try {
            Class.forName("com.github.benmanes.caffeine.cache.Caffeine");
            throw new AssertionError("The bug is more extensive than previously thought");
        } catch (ClassNotFoundException ignored) {
            getLogger().info(
                    """
                    onLoad - Tried and failed to load Caffeine class.
                    This is expected behavior.""");
        }
    }

    @Override
    public void onEnable() {
        java.net.URLClassLoader ucl;
        getLogger().info("onEnable - No-op");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        try {
            Class.forName("com.github.benmanes.caffeine.cache.Caffeine");
            getLogger().info(
                    """
                            onServerLoad(ServerLoadEvent) - Loaded Caffeine class, but not from this plugin!\s
                            This is unexpected behavior and means the library loader is leaking libraries.
                            To solve this, the library loader should properly isolate dependencies.
                             """);
        } catch (ClassNotFoundException ignored) {
            getLogger().info("Hooray, the bug is solved");
        }
    }

}
