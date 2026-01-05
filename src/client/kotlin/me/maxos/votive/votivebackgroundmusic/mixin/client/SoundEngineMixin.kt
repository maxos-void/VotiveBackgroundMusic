package me.maxos.votive.votivebackgroundmusic.mixin.client

import me.maxos.votive.votivebackgroundmusic.client.MenuMusicPlayer
import net.minecraft.client.sound.SoundEngine
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(SoundEngine::class)
class SoundEngineMixin {
	@Inject(method = ["init"], at = [At("TAIL")])
	private fun onSoundEngineInit(deviceSpecifier: String?, directionalAudio: Boolean, ci: CallbackInfo?) {
		// типа инициализировали саунд движок
		MenuMusicPlayer.startedSoundEngine()
	}
}