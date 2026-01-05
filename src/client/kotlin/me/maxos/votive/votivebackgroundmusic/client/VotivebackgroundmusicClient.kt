package me.maxos.votive.votivebackgroundmusic.client

import net.fabricmc.api.ClientModInitializer

class VotivebackgroundmusicClient : ClientModInitializer {

	override fun onInitializeClient() {
		MenuMusicPlayer.init()
	}
}
