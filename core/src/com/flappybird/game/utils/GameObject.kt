package com.flappybird.game.utils
import com.badlogic.gdx.graphics.g2d.Batch

interface GameObject {
    val batch: Batch
    fun draw(delta: Float)
    fun dispose() = Unit
}