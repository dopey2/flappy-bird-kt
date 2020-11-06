package com.flappybird.game.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.flappybird.game.res.CONSTANTS
import com.flappybird.game.utils.GameObject
import com.flappybird.game.utils.GraphicsHelper

class Background(override val batch: Batch) : GameObject, GraphicsHelper {

    private val image = Texture(Gdx.files.internal("images/background.png"))

    private val rect = Rectangle(
            0f,
            0f,
            CONSTANTS.width,
            CONSTANTS.height
    )

    override fun draw(delta: Float) {
        batch.draw(
                image,
                rect.x,
                rect.y,
                ptm(rect.width),
                ptm(rect.height)
        )
        batch.draw(
                image,
                rect.x + ptm(CONSTANTS.width),
                rect.y,
                ptm(rect.width),
                ptm(rect.height)
        )
    }

    fun compute(delta: Float) {
        rect.x -= 1 * delta

        if (rect.x <= ptm(-CONSTANTS.width)) {
            rect.x = 0f
        }
    }
}