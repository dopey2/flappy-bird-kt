package com.flappybird.game.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.*
import com.flappybird.game.res.CONSTANTS

class Pipe : GameObject {

    override val batch: Batch

    private val pipeUpImage = Texture(Gdx.files.internal("images/pipeup.png"))
    private val pipeDownImage = Texture(Gdx.files.internal("images/pipedown.png"))

    private val pipeWidth = 45f
    private val pipeHeight = CONSTANTS.height

    private val pipeUpBody: Body
    private val pipeDownBody: Body


    constructor(batch: Batch, world: World) {
        this.batch = batch

        val randomY = MathUtils.random(-CONSTANTS.height / 2 + 100, CONSTANTS.height / 2 - 100)

        val pipeUpBodyDef = BodyDef().apply {
            type = BodyDef.BodyType.KinematicBody
            position.set(CONSTANTS.width + pipeWidth / 2 , 0f - 50 + randomY )
        }

        val pipeDownBodyDef = BodyDef().apply {
            type = BodyDef.BodyType.KinematicBody
            position.set(CONSTANTS.width + pipeWidth / 2, pipeHeight + 50 + randomY )
        }

        pipeUpBody = world.createBody(pipeUpBodyDef)
        pipeDownBody = world.createBody(pipeDownBodyDef)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = PolygonShape().apply { setAsBox(45f / 2, pipeHeight / 2  ) }

        pipeUpBody.createFixture(fixtureDef)
        pipeDownBody.createFixture(fixtureDef)

        pipeDownBody.setLinearVelocity(-200f, 0f)
        pipeUpBody.setLinearVelocity(-200f, 0f)
    }

    override fun draw(delta: Float) {
        batch.draw(
                pipeUpImage,
             pipeUpBody.position.x - pipeWidth / 2 ,
             pipeUpBody.position.y - pipeHeight / 2,
                pipeWidth,
                pipeHeight
        )
        batch.draw(
                pipeDownImage,
                pipeDownBody.position.x - pipeWidth / 2,
                pipeDownBody.position.y - pipeHeight / 2 - 5,
                pipeWidth,
                pipeHeight
        )
    }

    override fun compute(delta: Float) {

    }

    fun overlaps(target: Rectangle): Boolean {
        //return Rectangle(pipeDownBody.position.x, pipeDownBody.position.y, 45f, CONSTANTS.height).
        //return rectPipeDown.overlaps(target) || rectPipeUp.overlaps(target)
        return false
    }
}