package me.maxos.votive.votivebackgroundmusic

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

object OggReader {

	/*
	ВРЕМЕННО НЕ ИСПОЛЬЗУЕТСЯ...
	 */

	@Throws(IOException::class)
	fun calculateDuration(oggFile: File): Int {
		// Проверяем, что файл существует и не пустой
		if (!oggFile.exists() || oggFile.length() == 0L) {
			return 3300 // Дефолтная длительность
		}

		var rate = -1
		var length = -1L // Изменяем на Long!

		val size = oggFile.length().toInt() // Преобразуем Long в Int
		val t = ByteArray(size)

		FileInputStream(oggFile).use { stream ->
			stream.read(t)
		}

		// Поиск длины (granule position)
		var i = size - 1 - 8 - 2 - 4
		while (i >= 0 && length < 0) {
			if (i + 13 < size && // Проверяем границы массива
				t[i] == 'O'.code.toByte() &&
				t[i + 1] == 'g'.code.toByte() &&
				t[i + 2] == 'g'.code.toByte() &&
				t[i + 3] == 'S'.code.toByte()) {

				val byteArray = byteArrayOf(
					t[i + 6], t[i + 7], t[i + 8], t[i + 9],
					t[i + 10], t[i + 11], t[i + 12], t[i + 13]
				)
				val bb = ByteBuffer.wrap(byteArray)
				bb.order(ByteOrder.LITTLE_ENDIAN)
				length = bb.long // Используем .long вместо .getInt(0)
			}
			i--
		}

		// Поиск частоты дискретизации
		i = 0
		while (i < size - 8 - 2 - 4 && rate < 0) {
			if (i + 14 < size && // Проверяем границы массива
				t[i] == 'v'.code.toByte() &&
				t[i + 1] == 'o'.code.toByte() &&
				t[i + 2] == 'r'.code.toByte() &&
				t[i + 3] == 'b'.code.toByte() &&
				t[i + 4] == 'i'.code.toByte() &&
				t[i + 5] == 's'.code.toByte()) {

				val byteArray = byteArrayOf(t[i + 11], t[i + 12], t[i + 13], t[i + 14])
				val bb = ByteBuffer.wrap(byteArray)
				bb.order(ByteOrder.LITTLE_ENDIAN)
				rate = bb.int // Используем .int
			}
			i++
		}

		// Проверяем, что нашли оба значения
		if (rate <= 0 || length <= 0) {
			return 3300 // Дефолтная длительность
		}

		// Рассчитываем длительность в тиках
		// Формула: (длина в сэмплах * 20) / частота дискретизации
		val durationTicks = (length * 20) / rate

		return durationTicks.toInt() // Приводим Long к Int
	}
}