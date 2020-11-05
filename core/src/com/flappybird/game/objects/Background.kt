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
        batch.draw(
                image,
                rect.x,
                rect.y,
                rect.width / CONSTANTS.PTM,
                rect.height / CONSTANTS.PTM
        )
        batch.draw(
                image,
                rect.x + CONSTANTS.width / CONSTANTS.PTM,
                rect.y,
                rect.width / CONSTANTS.PTM,
                rect.height / CONSTANTS.PTM
        )
    }

    override fun compute(delta: Float) {
        rect.x -= 1 * delta

        if (rect.x <= -CONSTANTS.width / 48) {
            rect.x = 0f
        }
    }
}