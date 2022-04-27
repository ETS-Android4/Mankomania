package com.mankomania.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mankomania.game.Connection;
import com.mankomania.game.MankomaniaGame;

import io.socket.emitter.Emitter;

public class LobbyScreen extends ScreenAdapter {
    private final Stage stage;
    private final Texture background;
    private final SpriteBatch batch;
    private final Table tab;
    private final Table innerTab;
    private InputMultiplexer inputMultiplexer;

    public LobbyScreen() {

        stage = new Stage();
        batch = new SpriteBatch();
        background = new Texture("background.jpg");

        tab = new Table();
        tab.setFillParent(true);
        tab.setWidth(stage.getWidth());
        tab.align(Align.center | Align.top);

        innerTab = new Table();
        innerTab.setWidth(stage.getWidth());
        innerTab.align(Align.center | Align.top);

        inputMultiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        if (!inputMultiplexer.getProcessors().contains(stage, true)) {
            inputMultiplexer.addProcessor(stage);
        }
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        skin.getFont("font").getData().setScale(5, 5);

        TextButton backButton = new TextButton("BACK", skin, "default");
        backButton.getLabel().setFontScale(Gdx.graphics.getHeight() / 400f);
        backButton.addListener(backListener());

        TextButton startButton = new TextButton("START", skin, "default");
        startButton.getLabel().setFontScale(Gdx.graphics.getHeight() / 400f);
        startButton.addListener(startListener());

        Texture lobbyText = new Texture(Gdx.files.internal("text-field-lobby.png"));
        Image lobbyImage = new Image(lobbyText);

        Texture lobbyBackgroundTexture = new Texture(Gdx.files.internal("lobby-background.png"));
        TextureRegionDrawable lobbyBackground = new TextureRegionDrawable(lobbyBackgroundTexture);

        innerTab.setBackground(lobbyBackground);
        innerTab.add().expandY();
        innerTab.row();
        innerTab.add(new Label("Player1", skin)).pad(20f).align(Align.center);
        innerTab.row();
        innerTab.add(new Label("Player2", skin)).pad(20f).align(Align.center);
        innerTab.row();
        innerTab.add(new Label("Player3", skin)).pad(20f).align(Align.center);
        innerTab.row();
        innerTab.add().expandY();

        tab.add(lobbyImage).size(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 5f).padTop(50).colspan(2).center();
        tab.row();
        tab.add(innerTab).colspan(2).expandY();
        tab.row();

        tab.add(backButton).expand().bottom().left().padLeft(20).padBottom(20).width(Gdx.graphics.getWidth() / 5f).height(Gdx.graphics.getHeight() / 6f);
        tab.add(startButton).expand().bottom().right().padRight(20).padBottom(20).width(Gdx.graphics.getWidth() / 5f).height(Gdx.graphics.getHeight() / 6f);
        backButton.addListener(backListener());

        stage.addActor(tab);
    }


    public ClickListener backListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                MankomaniaGame.getInstance().disposeCurrentScreen();
                MankomaniaGame.getInstance().setScreen(new StartScreen());
                /**
                 * Close connection
                 */
                //StartScreen.con.closeConnection();
            }
        };
    }

    public ClickListener startListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {

                /**
                 * Emits to server, that the client is ready to play
                 */
                Emitter.Listener el = new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        System.out.println("Response: " + args[0]);

                        Connection.start = true;

                    }
                };

                /**
                 * Server responses with "startGame", when 4 player are available
                 */

                Connection.con.readyForGame(el, Connection.lobbyID);

                if(Connection.start == true){
                    MankomaniaGame.getInstance().disposeCurrentScreen();
                    MankomaniaGame.getInstance().setScreen(new GameScreen());
                }
            }
        };
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        MankomaniaGame.renderMenu(stage, batch, delta, background);
    }

    public void doDispose(){
        MankomaniaGame.getInstance().disposeCurrentScreen();
        MankomaniaGame.getInstance().setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        inputMultiplexer.removeProcessor(stage);
        stage.dispose();
    }
}
