package com.allinx.english.presentation.test.finish.customview.extension

import android.graphics.Canvas
import android.text.StaticLayout
import androidx.core.graphics.withTranslation

/**
 * StaticLayout Extension для удобства отрисовки текста.
 */
fun StaticLayout.draw(canvas: Canvas, x: Float, y: Float) {
    canvas.withTranslation(x, y) {
        draw(this)
    }
}