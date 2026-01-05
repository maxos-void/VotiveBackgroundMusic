package me.maxos.votive.votivebackgroundmusic.mixin.client

import me.maxos.votive.votivebackgroundmusic.client.VotivebackgroundmusicClient
import net.minecraft.client.sound.SoundInstance
import net.minecraft.client.sound.SoundManager
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(SoundManager::class)
class SoundManagerMixin {

	@Unique
	private var isReloading = false

	@Inject(method = ["reload"], at = [At("HEAD")])
	private fun onReloadStart(ci: CallbackInfo) {
		isReloading = true
		println("Началась перезагрузка звуков...")
	}

	@Inject(method = ["reload"], at = [At("TAIL")])
	private fun onReloadEnd(ci: CallbackInfo) {
		isReloading = false
		println("Перезагрузка звуков завершена")
		// Планируем восстановление музыки через тик
		println("Планируем восстановление музыки через тик")
	}

	@Inject(
		method = ["play(Lnet/minecraft/client/sound/SoundInstance;)V"],
		at = [At("HEAD")],
		cancellable = true
	)
	private fun injectPlayDirect(sound: SoundInstance, ci: CallbackInfo) {
		// Во время перезагрузки не блокируем звуки
		if (isReloading) return
		ci.cancel()
		println("Заблокирован звук: ${sound.id}")
	}

}