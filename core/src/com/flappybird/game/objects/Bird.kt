package com.flappybird.game.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*

class Bird: GameObject {

    override val batch: Batch
    private val world: World

    private val image = Texture(Gdx.files.internal("images/flappy1.png"))

    private val width = 35f
    private val height = 30f

    private val bodyDef: BodyDef
    private val body: Body
    private val fixtureDef: FixtureDef

    constructor(batch: Batch, world: World) {
        this.batch = batch
        this.world = world

        val x = 240f
        val y = 400f

        bodyDef = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            position.set(x + width / 2, y + height / 2)
        }

        body = world.createBody(bodyDef)
        fixtureDef = FixtureDef()
        fixtureDef.shape = PolygonShape().apply { setAsBox(width / 2, height / 2) }
        body.createFixture(fixtureDef)
    }


    override fun draw(delta: Float) {
        batch.draw(image, body.position.x - width / 2, body.position.y - width / 2, width, height)
    }

    override fun compute(delta: Float) {
        if(Gdx.input.isTouched) {
            body.applyLinearImpulse(Vector2(0f, 400f), body.worldCenter, true)
        }
    }
}