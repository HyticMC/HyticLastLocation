package dev.hytical.utils.managers

import dev.hytical.LastLocation
import dev.hytical.utils.TeleportType
import org.bukkit.Location
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID
import java.util.EnumMap
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class StorageManager(
    private val plugin: LastLocation
) {
    lateinit var locationFile: File
    lateinit var locationFileConfiguration: FileConfiguration
    lateinit var defaultLocationFileConfiguration: FileConfiguration
    private val locations: MutableMap<UUID, EnumMap<TeleportType, Location>> = mutableMapOf()
    private val logger = plugin.logger

    fun initialize(): StorageManager {
        if(!plugin.dataFolder.exists()) plugin.dataFolder.mkdirs()
        this.locationFile = File(plugin.dataFolder, "location.yml")
        if(!locationFile.exists()) locationFile.createNewFile()
        loadLocation()
        return reload()
    }

    fun shutdown() {}

    private fun reload(): StorageManager {
        this.locationFileConfiguration = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "location.yml"))
        this.defaultLocationFileConfiguration = YamlConfiguration.loadConfiguration(
            InputStreamReader(plugin.getResource("location.yml")!!, StandardCharsets.UTF_8)
        )
        locationFileConfiguration.setDefaults(defaultLocationFileConfiguration)
        locationFileConfiguration.options().copyDefaults(true)
        return this@StorageManager
    }

    private fun loadLocation() {
        logger.info("Loading locations from locations.yml...");
        locations.clear()
        val playerSection: ConfigurationSection? = locationFileConfiguration.getConfigurationSection("player")
        if(playerSection != null) {
            logger.info("No player locations found to load.");
            return
        }
    }

}