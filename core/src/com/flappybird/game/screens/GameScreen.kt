package com.flappybird.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.TimeUtils
import com.flappybird.game.Game
import com.flappybird.game.objects.Background
import com.flappybird.game.objects.Bird
import com.flappybird.game.objects.Pipe
import com.flappybird.game.utils.GraphicsHelper
import ktx.app.KtxScreen
import ktx.graphics.use


class GameScreen(val game: Game) : KtxScreen, GraphicsHelper {

    private val camera = OrthographicCamera().apply {
        setToOrtho(false, ptm(Gdx.graphics.width), ptm(Gdx.graphics.height))
    }

    private var lastSpawnTimestamp = TimeUtils.nanoTime();
    private val SPAWN_INTERVAL = 2_000_000_000f

    private var collision = false
    private val world = World(Vector2(0f, -16f), false)

    private val pipes = mutableListOf<Pipe>()
    private val background =  Background(game.batch)
    private val bird = Bird(game.batch, world)


    val debugRenderer = Box2DDebugRenderer()

    override fun render(delta: Float) {
        draw(delta)
        compute(delta)
    }

    private fun draw(delta: Float) {
        camera.update()
        game.batch.projectionMatrix = camera.combined

        debugRenderer.render(world, camera.combined);
//        return

        game.batch.use { batch ->
//            background.draw(delta)
            pipes.forEach { it.draw(delta) }
            bird.draw(delta)
            game.font.draw(batch, "Collision: $collision", 0f, 400f)
        }
    }

    private fun compute(delta: Float) {

        world.step(1f / 60 , 6, 2)

        background.compute(delta)
        bird.compute(delta)

        var _collision = false

        pipes.forEach {
            it.compute(delta)
            //_collision = _collision || it.overlaps(bird.rect)
        }

        collision = _collision

        spawnPipe()


    }

    fun spawnPipe() {
        if (lastSpawnTimestamp + SPAWN_INTERVAL < TimeUtils.nanoTime()) {
            pipes.add(Pipe(game.batch, world ))
            lastSpawnTimestamp = TimeUtils.nanoTime()
        }
    }

    override fun show() {
        spawnPipe()
    }


    override fun dispose() {
        System.out.println("dispose")
    }


}