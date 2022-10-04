package com.koisv.koilock

import org.bukkit.plugin.java.JavaPlugin

class KoiLock : JavaPlugin() {
    override fun onLoad() {
        logger.info("Now Loading...")
    }

    override fun onEnable() {
        logger.info("Now Enabling...")
        server.pluginManager.registerEvents(LockEvents(), this)
    }

    override fun onDisable() {
        logger.info("Shutting Down...")
    }
}