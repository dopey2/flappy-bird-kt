package com.flappybird.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.flappybird.game.Game
import com.flappybird.game.res.CONSTANTS

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = CONSTANTS.width.toInt()
        config.height = CONSTANTS.height.toInt()
        LwjglApplication(Game(), config)
    }
}