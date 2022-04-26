package com.mankomania.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mankomania.game.Connection;
import com.mankomania.game.MankomaniaGame;

import io.socket.emitter.Emitter;

public class StartScreen extends ScreenAdapter {
    private final Stage stage;
    private final Texture background;
    private final SpriteBatch batch;
    private InputMultiplexer inputMultiplexer;

    public static String lobbyID;
    public static Connection con;
    public String[] players = new String[4]; //Array with players ID


    public StartScreen() {
        stage = new Stage();
        batch = new SpriteBatch();
        background = new Texture("background.jpg");

        inputMultiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        if (!inputMultiplexer.getProcessors().contains(stage, true)) {
            inputMultiplexer.addProcessor(stage);
        }
        initTableAndAddActor(stage);
    }

    private void initTableAndAddActor(Stage stage) {
        Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        Texture logoTexture = new Texture(Gdx.files.internal("mankomania.png"));

        skin.getFont("font").getData().setScale(5, 5);
        Image logoImage = new Image(logoTexture);

        TextButton startButton = new TextButton("START", skin, "default");
        TextButton settingsButton = new TextButton("SETTINGS", skin, "default");
        startButton.getLabel().setFontScale(Gdx.graphics.getHeight() / 400f);
        settingsButton.getLabel().setFontScale(Gdx.graphics.getHeight() / 400f);
        startButton.addListener(startListener());

        stage.addActor(buildTable(startButton, settingsButton, logoImage));
    }

    private Table buildTable(TextButton startButton, TextButton settingsButton, Image logoImage) {
        Table tab = new Table();
        tab.setFillParent(true);
        tab.setWidth(stage.getWidth());
        tab.align(Align.center | Align.top);
        tab.padTop(Gdx.graphics.getHeight() / 20f);
        tab.add(logoImage).width(Gdx.graphics.getWidth() / 1.5f).height(Gdx.graphics.getHeight() / 4f);
        tab.row();
        tab.add(startButton).padBottom(50).width(Gdx.graphics.getWidth() / 2f).height(Gdx.graphics.getHeight() / 5f);
        tab.row();
        tab.add(settingsButton).padBottom(50).width(Gdx.graphics.getWidth() / 2f).height(Gdx.graphics.getHeight() / 5f);
        tab.row();
        return tab;
    }

    private ClickListener startListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                MankomaniaGame.getInstance().disposeCurrentScreen();
                MankomaniaGame.getInstance().setScreen(new LobbyScreen());

                /**
                 * Create connection to Server
                 */
                con = new Connection();

                /**
                 * Emits Server, that the client wants to join a lobby
                 * Server returns String Array with IDs of players
                 */
                Emitter.Listener el = new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        lobbyID = args[0].toString();
                        System.out.println("LobbyID: " + lobbyID);

                        String temp = args[1].toString().substring(1, args[1].toString().length()-1);

                        String temp1 = temp.replaceAll("[\"]", "");

                        players = temp1.split(",");

                        for(int i = 0; i < players.length; i++){
                            System.out.println("Player " + i + ": " + players[i]);
                        }

                    }
                };
                con.joinRoom(el);
            }
        };
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        MankomaniaGame.renderMenu(stage, batch, delta, background);
    }

    @Override
    public void dispose() {
        inputMultiplexer.removeProcessor(stage);
        stage.dispose();
    }
}
