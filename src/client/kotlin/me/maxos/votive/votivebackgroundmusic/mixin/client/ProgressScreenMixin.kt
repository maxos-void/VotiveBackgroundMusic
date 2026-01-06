package me.maxos.votive.votivebackgroundmusic.mixin.client

import net.minecraft.client.gui.screen.ConnectScreen
import net.minecraft.client.gui.screen.ProgressScreen
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.jetbrains.annotations.Nullable
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(ProgressScreen::class)
abstract class ProgressScreenMixin : Screen(Text.empty()) {

	@Shadow
	@Nullable
	private var title: Text? = null

	@Shadow
	@Nullable
	private var task: Text? = null

	@Inject(method = ["<init>"], at = [At("TAIL")])
	private fun onConstruct(closeAfterFinished: Boolean, callbackInfo: CallbackInfo) {
		this.title = Text.empty()
		this.task = Text.empty()
	}

	@Inject(method = ["setTitle"], at = [At("HEAD")], cancellable = true)
	private fun onSetTitle(title: Text, callbackInfo: CallbackInfo) {
		this.title = Text.empty()
		callbackInfo.cancel()
	}

	@Inject(method = ["setTitleAndTask"], at = [At("HEAD")], cancellable = true)
	private fun onSetTitleAndTask(title: Text, callbackInfo: CallbackInfo) {
		this.title = Text.empty()
		this.task = Text.empty()
		callbackInfo.cancel()
	}

	@Inject(method = ["setTask"], at = [At("HEAD")], cancellable = true)
	private fun onSetTask(task: Text, callbackInfo: CallbackInfo) {
		this.task = Text.empty()
		callbackInfo.cancel()
	}
}