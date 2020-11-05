package com.flappybird.game.objects
import com.badlogic.gdx.graphics.g2d.Batch

interface GameObject {
    val batch: Batch
    fun draw(delta: Float)
    fun compute(delta: Float) = Unit
}