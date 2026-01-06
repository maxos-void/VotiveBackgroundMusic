package me.maxos.votive.votivebackgroundmusic.mixin.client

import net.minecraft.client.gui.screen.ConnectScreen
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(ConnectScreen::class)
abstract class ConnectScreenMixin : Screen(Text.empty()) {

	@Shadow
	private lateinit var status: Text

	@Inject(method = ["<init>"], at = [At("TAIL")])
	private fun onConstruct(parent: Screen, callbackInfo: CallbackInfo) {
		this.status = Text.empty()
	}
	// DownloadingTerrainScreen | ProgressScreen
	@Inject(method = ["setStatus"], at = [At("HEAD")], cancellable = true)
	private fun onSetStatus(status: Text, callbackInfo: CallbackInfo) {
		this.status = Text.empty()
		callbackInfo.cancel()
	}
}