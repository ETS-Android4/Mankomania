package com.mankomania.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mankomania.game.screens.LoadingScreen;
import com.mankomania.game.screens.StartScreen;

public class MankomaniaGame extends Game {
    private static MankomaniaGame game;

    public static MankomaniaGame getInstance() {
        if (game == null) {
            game = new MankomaniaGame();
        }
        return game;
    }

    @Override
    public void create() {
        //Get current Screen to dispose later
        Gdx.input.setInputProcessor(new InputMultiplexer());
        Screen currentScreen = this.getScreen();

        //Set new Screen
        ScreenAdapter loadingScreen = new LoadingScreen(new StartScreen());
        this.setScreen(loadingScreen);

        //dispose old Screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }

    public void disposeCurrentScreen() {
        Screen currentScreen = this.getScreen();
        currentScreen.dispose();
    }

    public static void renderMenu(Stage stage, SpriteBatch batch, float delta, Texture background) {
        ScreenUtils.clear(1, 1, 1, 1);
        stage.act(delta);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        this.dispose();
    }
}
