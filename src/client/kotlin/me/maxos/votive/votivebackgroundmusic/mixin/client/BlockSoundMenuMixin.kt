package me.maxos.votive.votivebackgroundmusic.mixin.client

import net.minecraft.client.sound.SoundInstance
import net.minecraft.client.sound.SoundManager
import net.minecraft.sound.SoundEvents.MUSIC_MENU
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo


@Mixin(SoundManager::class)
class BlockSoundMenuMixin {
	// офаем музыку в меню
	@Inject(at = [At("HEAD")], method = ["play(Lnet/minecraft/client/sound/SoundInstance;)V"], cancellable = true)
	private fun injectPlayDirect(sound: SoundInstance, ci: CallbackInfo) {
		if (sound.id == MUSIC_MENU.registryKey().value) ci.cancel()
	}

	@Inject(at = [At("HEAD")], method = ["play(Lnet/minecraft/client/sound/SoundInstance;I)V"], cancellable = true)
	private fun injectPlayDelayed(sound: SoundInstance, delay: Int, ci: CallbackInfo) {
		if (sound.id == MUSIC_MENU.registryKey().value) ci.cancel()
	}
}