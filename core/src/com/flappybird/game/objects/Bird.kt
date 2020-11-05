package com.flappybird.game.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.TimeUtils
import com.flappybird.game.utils.GraphicsHelper

class Bird: GameObject, GraphicsHelper {

    override val batch: Batch
    private val world: World

    private val image = Texture(Gdx.files.internal("images/flappy1.png"))

    private val width = 35f
    private val height = 30f

    private val bodyDef: BodyDef
    private val body: Body
    private val fdef: FixtureDef

    private var lastClickTimeStamp = 0L


    constructor(batch: Batch, world: World) {
        this.batch = batch
        this.world = world

        val x = 240f
        val y = 100f

        bodyDef = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            position.set(
                    ptm(x + width / 2),
                    ptm(y + height / 2)
            )
        }

        body = world.createBody(bodyDef)
        fdef = FixtureDef()
        fdef.shape = PolygonShape().apply { setAsBox(ptm(width / 2), ptm(height / 2)) }
        fdef.density = 1f
        fdef.friction = 0f
        fdef.restitution = 0f
        body.createFixture(fdef)
    }


    override fun draw(delta: Float) {
        batch.draw(
                image,
                body.position.x - ptm(width  / 2),
                body.position.y - ptm(height / 2),
                ptm(width),
                ptm(height)
        )
    }

    override fun compute(delta: Float) {
        if(
                Gdx.input.isKeyJustPressed(Input.Keys.SPACE) &&
                lastClickTimeStamp + 100_000_000 < TimeUtils.nanoTime()
        ) {
            body.applyLinearImpulse(Vector2(0f, 4f ), body.worldCenter, false)
            lastClickTimeStamp = TimeUtils.nanoTime()
        }
    }
}