package com.flappybird.game.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.flappybird.game.res.CONSTANTS

class Background(override val batch: Batch) : GameObject {

    private val image = Texture(Gdx.files.internal("images/background.png"))

    private val rect = Rectangle(
            0f,
            0f,
            CONSTANTS.width,
            CONSTANTS.height
    )

    override fun draw(delta: Float) {
        batch.draw(image, rect.x, rect.y, rect.width, rect.height)
        batch.draw(image, rect.x + CONSTANTS.width, rect.y, rect.width, rect.height)
    }

    override fun compute(delta: Float) {
        rect.x -= 100 * delta

        if (rect.x <= -CONSTANTS.width) {
            rect.x = 0f
        }
    }
}