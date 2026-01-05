package me.maxos.votive.votivebackgroundmusic.client

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.client.sound.SoundInstance
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.math.random.Random
import java.io.File

object MenuMusicPlayer {
	private val MENU_MUSIC = Identifier("votivebackgroundmusic", "menu_music")
	private var isPlaying = false
	private var isWorld = false
	private var currentMusic: SoundInstance? = null
	var isInitSoundEngine = false

//	private val soundsFolderUrl = javaClass.classLoader.getResource("assets/votivebackgroundmusic/sounds")


	private var client: MinecraftClient? = null

	fun startedSoundEngine() {
		isInitSoundEngine = true
		if (!isWorld) {
			stopMusic(client ?: return, false)
		}
	}

	fun init() {
		ClientTickEvents.START_CLIENT_TICK.register { client ->
			this.client = client
			client.soundManager
			if (!isPlaying && !isWorld && isInitSoundEngine) playMenuMusic(client)

		}
		ClientPlayConnectionEvents.JOIN.register { t, b, v ->
			println("ВЫКЛЮЧАЕМ МУЗЫКУ МУЗЫКУ!!!")
			stopMusic(v, true)
		}
		ClientPlayConnectionEvents.DISCONNECT.register { t, v ->
			isWorld = false
		}

	}

	fun playMenuMusic(client: MinecraftClient) {
		isPlaying = true
		val soundEvent = SoundEvent.of(MENU_MUSIC)

		currentMusic = PositionedSoundInstance(
			soundEvent.id,
			SoundCategory.MASTER,
			0.7f,
			1.0f,
			Random.create(),
			true,
			0,
			SoundInstance.AttenuationType.NONE,
			0.0, 0.0, 0.0,
			true
		)

	//	currentMusic = PositionedSoundInstance.master(soundEvent, 1F)
		client.soundManager.play(currentMusic!!)
		println("МУЗЫКА ВКЛЮЧЕНА")
	}

	fun stopMusic(client: MinecraftClient, isWorld: Boolean) {
		isPlaying = false
		this.isWorld = isWorld
		currentMusic?.let {
			client.soundManager.stop(it)
			currentMusic = null
		}
		println("МУЗЫКА ВЫКЛЮЧЕНА!!!")
	}

}