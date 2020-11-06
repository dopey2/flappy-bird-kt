package com.flappybird.game.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.*
import com.flappybird.game.res.CONSTANTS
import com.flappybird.game.utils.GameObject
import com.flappybird.game.utils.GraphicsHelper

class Pipe : GameObject, GraphicsHelper {

    override val batch: Batch

    private val world: World

    private val pipeUpImage = Texture(Gdx.files.internal("images/pipeup.png"))
    private val pipeDownImage = Texture(Gdx.files.internal("images/pipedown.png"))

    private val pipeWidth = 45f
    private val pipeHeight = CONSTANTS.height

    private val pipeUpBody: Body
    private val pipeDownBody: Body

    constructor(batch: Batch, world: World, randomY: Float) {
        this.batch = batch
        this.world = world

        val pipeUpBodyDef = BodyDef().apply {
            type = BodyDef.BodyType.KinematicBody
            position.set(
                    ptm(CONSTANTS.width + pipeWidth / 2),
                    ptm(0f - 70 + randomY)
            )
        }

        val pipeDownBodyDef = BodyDef().apply {
            type = BodyDef.BodyType.KinematicBody
            position.set(
                    ptm(CONSTANTS.width + pipeWidth / 2),
                    ptm(pipeHeight + 70 + randomY)
            )
        }

        pipeUpBody = world.createBody(pipeUpBodyDef)
        pipeDownBody = world.createBody(pipeDownBodyDef)


        val fixtureDef = FixtureDef()
        fixtureDef.shape = PolygonShape().apply {
            setAsBox(
                    ptm(pipeWidth / 2),
                    ptm(pipeHeight / 2)
            )
        }

        pipeUpBody.createFixture(fixtureDef)
        pipeDownBody.createFixture(fixtureDef)

        pipeDownBody.setLinearVelocity(-2f, 0f)
        pipeUpBody.setLinearVelocity(-2f, 0f)
    }

    override fun draw(delta: Float) {
        batch.draw(
                pipeUpImage,
                pipeUpBody.position.x - ptm(pipeWidth / 2),
                pipeUpBody.position.y - ptm(pipeHeight / 2),
                ptm(pipeWidth),
                ptm(pipeHeight)
        )
        batch.draw(
                pipeDownImage,
                pipeDownBody.position.x - ptm(pipeWidth / 2),
                pipeDownBody.position.y - ptm(pipeHeight / 2),
                ptm(pipeWidth),
                ptm(pipeHeight)
        )
    }

    override fun dispose() {
        world.destroyBody(pipeDownBody)
        world.destroyBody(pipeUpBody)
    }

    fun isOutOfScreen(): Boolean {
        return pipeDownBody.position.x < -ptm(pipeWidth)
    }
}