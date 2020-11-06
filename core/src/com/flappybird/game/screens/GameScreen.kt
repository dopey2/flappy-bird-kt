package com.flappybird.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.TimeUtils
import com.flappybird.game.Game
import com.flappybird.game.objects.Background
import com.flappybird.game.objects.Bird
import com.flappybird.game.objects.Pipe
import com.flappybird.game.res.CONSTANTS
import com.flappybird.game.utils.GraphicsHelper
import ktx.app.KtxScreen
import ktx.graphics.use


class GameScreen(val game: Game) : KtxScreen, GraphicsHelper, ContactListener {

    private val camera = OrthographicCamera().apply {
        setToOrtho(false, ptm(Gdx.graphics.width), ptm(Gdx.graphics.height))
    }

    private val world = World(Vector2(0f, -16f), false).apply {
        setContactListener(this@GameScreen)
    }

    private val pipes = mutableListOf<Pipe>()
    private val background =  Background(game.batch)
    private val bird = Bird(game.batch, world)

    private val SPAWN_INTERVAL = 1_500_000_000f
    private val JUMP_INTERVAL = 100_000_000f
    private val FPS = 60

    private var lastSpawnTimestamp = TimeUtils.nanoTime()
    private var lastRandomY: Float = 0f
    private var lastJumpTimeStamp = 0L
    private var start = false

    override fun render(delta: Float) {
        draw(delta)
        compute(delta)
    }

    private fun draw(delta: Float) {
        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.use { batch ->
            background.draw(delta)
            pipes.forEach { it.draw(delta) }
            bird.draw(delta)
        }
    }

    private fun compute(delta: Float) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if(!start){
                start = true
            }
            if(lastJumpTimeStamp + JUMP_INTERVAL < TimeUtils.nanoTime()) {
                bird.jump()
                lastJumpTimeStamp = TimeUtils.nanoTime()
            }
        }

        if(!start){ return }

        removeIfOutOfScreen()
        spawnPipe()

        world.step(1f / FPS , 6, 2)
        background.compute(delta)
    }

    private fun spawnPipe() {
        if (lastSpawnTimestamp + SPAWN_INTERVAL < TimeUtils.nanoTime()) {

            var botLimit = lastRandomY - 150
            var topLimit = lastRandomY + 150

            if(botLimit < -CONSTANTS.height / 2 + 100) {
                botLimit = -CONSTANTS.height / 2 + 100
            }

            if(topLimit > CONSTANTS.height / 2 - 100) {
                topLimit = CONSTANTS.height / 2 - 100
            }

            lastRandomY = MathUtils.random(botLimit, topLimit)

            pipes.add(Pipe(game.batch, world, lastRandomY))
            lastSpawnTimestamp = TimeUtils.nanoTime()
        }
    }

    override fun show() {
        spawnPipe()
    }

    private fun removeIfOutOfScreen() {
        if(pipes.size > 0 && pipes.get(0).isOutOfScreen()) {
            pipes.get(0).dispose()
            pipes.removeAt(0)
        }
    }

     override fun dispose() {
         bird.dispose()
         pipes.forEach { it.dispose()}
    }

    override fun beginContact(contact: Contact?) {

    }

    override fun endContact(contact: Contact?) {}
    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}
}