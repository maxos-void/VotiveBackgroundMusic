package me.maxos.votive.votivebackgroundmusic.client

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
import net.minecraft.client.gui.screen.DownloadingTerrainScreen
import net.minecraft.client.gui.screen.ProgressScreen
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen

object MenuBlocker {

	fun onConnectServer() {
		ScreenEvents.AFTER_INIT.register { client, screen, x, z ->
			val title = screen.title
			println(title)
			if (title.toString() == "multiplayer.title") println("+++")
			println("CHILDREN: ${screen.children()}")
			println("RENDER: ${screen.removed()}")
			println(x + z)
			println(screen.javaClass.name)
			println()
		}
	}

}