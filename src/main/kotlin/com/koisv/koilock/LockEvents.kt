package com.koisv.koilock

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.block.Chest
import org.bukkit.block.data.type.Sign
import org.bukkit.block.data.type.WallSign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*


class LockEvents : Listener {
    @EventHandler
    private fun onOpen(e: PlayerInteractEvent) {
        val block = e.clickedBlock
        if (block != null && block.state is Chest) {
            val data = block.state as Chest
            if (data.isLocked) {
                data.setLock(UUID.randomUUID().toString())
                data.update()
                e.player.playSound(block.location, Sound.BLOCK_WOODEN_DOOR_OPEN, 1F, 1F)
                e.isCancelled = true
            }
            val hand = e.item
            val handData = hand?.type?.createBlockData()
            if (handData is Sign) {
                if (e.action != Action.RIGHT_CLICK_BLOCK) return
                if (e.blockFace != BlockFace.DOWN && e.blockFace != BlockFace.UP) {
                    e.useItemInHand()
                    e.isCancelled = true
                    e.player.playSound(block.location,Sound.BLOCK_WOOD_PLACE, 1F, 0.8F)
                    hand.subtract()
                    val front = block.getRelative(e.blockFace)
                    val itemWallData = Material.getMaterial(
                        hand.type.name.replace("_SIGN","_WALL_SIGN")
                    )?.createBlockData()
                    if (itemWallData != null) {
                        front.blockData = itemWallData.apply {
                            val wallData = this as WallSign
                            wallData.facing = e.blockFace
                        }
                        val signFront = front.state as org.bukkit.block.Sign
                        signFront.line(0, Component.text("[잠금]"))
                        signFront.line(1, Component.text(e.player.name))
                        signFront.update()
                    }
                }
            }

        }
    }
}