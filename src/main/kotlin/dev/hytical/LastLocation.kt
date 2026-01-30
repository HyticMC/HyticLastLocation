package dev.hytical

import com.onarandombox.MultiverseCore.MultiverseCore
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class LastLocation : JavaPlugin() {
    lateinit var adventure: BukkitAudiences
        private set
    var multiverseCore: MultiverseCore? = null

    override fun onEnable() {
        adventure = BukkitAudiences.builder(this).build()
        val plugin = server.pluginManager.getPlugin("Multiverse")
        when(plugin != null) {
            true -> {
                this.multiverseCore = plugin as? MultiverseCore
            }
            else -> logger.severe { "MultiversePlugin not loaded, using fallback teleport logic." }
        }
    }

    override fun onDisable() {
        if(::adventure.isInitialized) {
            adventure.close()
        }
    }

    fun teleport(player: Player, sender: CommandSender, location: Location) {
        if(multiverseCore != null) {
            multiverseCore?.teleportPlayer(sender, player, location)
        } else {
            player.teleport(location)
        }
    }
}
