package me.maxos.votive.votivebackgroundmusic.mixin.client

import net.minecraft.client.gui.screen.DownloadingTerrainScreen
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Redirect

@Mixin(DownloadingTerrainScreen::class)
abstract class DownloadingTerrainScreenMixin : Screen(Text.empty()) {

	@Redirect(
		method = ["render"],
		at = At(
			value = "FIELD",
			target = "Lnet/minecraft/client/gui/screen/DownloadingTerrainScreen;TEXT:Lnet/minecraft/text/Text;"
		)
	)
	private fun redirectText(): Text {
		return Text.empty() // Возвращаем пустой текст вместо константы
	}
}