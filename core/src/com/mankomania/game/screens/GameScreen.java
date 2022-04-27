package com.mankomania.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mankomania.game.DiceAnimation;

public class GameScreen extends ScreenAdapter {
    private final Texture gameBoard;
    private final Stage stage;
    private final SpriteBatch batch;
    private final Skin skin;
    private final float boxWidth;
    private final float boxHeight;
    private final Dialog turnDialog;
    private InputMultiplexer inputMultiplexer;

    private boolean turnDialogIsShown;
    private boolean turnDialogNeeded;
    private String turnDialogPlayerName;
    private String turnDialogPlayerMoney;
    private boolean turnDialogIsCurrentPlayer;

    private static final float BOARD_SIZING_FACTOR = 1f;
    private static final float BOARD_PLAYER_BOX_WIDTH_FACTOR = 4.5f;
    private static final float BOARD_PLAYER_BOX_HEIGHT_FACTOR = 4f;
    private static final float BOARD_PLAYER_MONEY_FACTOR = 4.5f;
    private static final String BOARD_TEXT_STYLE = "title";

    private DiceAnimation diceAnimation;
    private static final float duration = 3;
    private float elapsed;

    public GameScreen() {
        gameBoard = new Texture("board.jpg");
        stage = new Stage();
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        boxWidth = calcWidthFactor(BOARD_PLAYER_BOX_WIDTH_FACTOR);
        boxHeight = calcHeightFactor(BOARD_PLAYER_BOX_HEIGHT_FACTOR);
        turnDialogIsShown = false;
        turnDialog = new Dialog("INFO", skin, "alt") {};
        turnDialogNeeded = false;
        diceAnimation = new DiceAnimation();
        elapsed = 0;

        inputMultiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        if (!inputMultiplexer.getProcessors().contains(stage, true)) {
            inputMultiplexer.addProcessor(stage);
        }
    }

    @Override
    public void render(float delta) {
        if (elapsed >= duration){
            diceAnimation.removeDice();
        diceAnimation.setDiceShown(false);
        }
        super.render(delta);
        if(diceAnimation.getDiceShown()){
            elapsed+=delta;
        }
        stage.act(delta);
        ScreenUtils.clear(0.9f, 0.9f, 0.9f, 1);
        drawGameBoard();
        drawPlayerInformation();
        if (!turnDialogIsShown && turnDialogNeeded) {
            drawTurnDialog();
            turnDialogIsShown = true;
        }
        stage.draw();
    }

    public void showTurnDialog(String playerName, String playerMoney, boolean isCurrentPlayer) {
        this.turnDialogNeeded = true;
        this.turnDialogPlayerName = playerName;
        this.turnDialogPlayerMoney = playerMoney;
        this.turnDialogIsCurrentPlayer = isCurrentPlayer;
    }

    public void hideTurnDialog() {
        this.turnDialogNeeded = false;
        this.turnDialog.hide();
    }

    private void drawGameBoard() {
        batch.begin();
        batch.draw(
                gameBoard,
                calcBoardPosition(Gdx.graphics.getWidth()),
                calcBoardPosition(Gdx.graphics.getHeight()),
                calcBoardSize(),
                calcBoardSize()
        );
        batch.end();
    }

    private void drawPlayerInformation() {
        batch.begin();
        drawPlayerBox(0, 0, "P1");
        drawPlayerBox(0, Gdx.graphics.getHeight() - boxHeight, "P2");
        drawPlayerBox(Gdx.graphics.getWidth() - boxWidth, Gdx.graphics.getHeight() - boxHeight, "P3");
        drawPlayerBox(Gdx.graphics.getWidth() - boxWidth, 0, "P4");
        drawPlayerMetadata(100000);
        batch.end();
    }

    private float calcWidthFactor(float factor) {
        return Gdx.graphics.getWidth() / factor;
    }

    private float calcHeightFactor(float factor) {
        return Gdx.graphics.getHeight() / factor;
    }

    private void drawPlayerBox(float x, float y, String playerName) {
        Texture boxTexture = new Texture(playerName + ".png");
        batch.draw(boxTexture, x, y, boxWidth, boxHeight);
    }

    private void drawPlayerMetadata(int money) {
        float labelHeight = calcHeightFactor(BOARD_PLAYER_MONEY_FACTOR);
        Label label = new Label("$ " + money, skin, BOARD_TEXT_STYLE);
        label.setFontScale(labelHeight / 200f);
        label.setSize(Gdx.graphics.getWidth(), labelHeight);
        label.setColor(Color.GREEN);
        label.setPosition(0, 0);
        label.setAlignment(Align.center);
        stage.addActor(label);
    }

    private void drawTurnDialog() {
        float scale = Gdx.graphics.getWidth() / 1000f;

        Table tab = new Table();
        tab.align(Align.center);
        Label nextTurnLabel = new Label("Next Turn:", skin, BOARD_TEXT_STYLE);
        Label playerNameLabel = new Label(turnDialogPlayerName, skin, BOARD_TEXT_STYLE);
        Label moneyLabel = new Label("Current money: $" + turnDialogPlayerMoney, skin, BOARD_TEXT_STYLE);

        tab.add(nextTurnLabel).row();
        tab.add(playerNameLabel).row();
        tab.add(moneyLabel).row();
        tab.pad(20);

        if (turnDialogIsCurrentPlayer) {
            TextButton startTurnButton = new TextButton("START TURN", skin, "default");
            startTurnButton.setTransform(true);
            turnDialog.button(startTurnButton, true).padBottom(20);
            startTurnButton.addListener(startTurnListener());
        } else {
            Label l4 = new Label("Waiting for player...", skin, "default");
            tab.add(l4).row();
        }

        turnDialog.getContentTable().add(tab);
        turnDialog.setScale(scale);
        turnDialog.show(stage);

        float dialogXPosition = (Gdx.graphics.getWidth() / 2f) - ((turnDialog.getWidth() * scale) / 2f);
        float dialogYPosition = (Gdx.graphics.getHeight() / 2f) - ((turnDialog.getHeight() * scale) / 2f);

        turnDialog.setPosition(dialogXPosition, dialogYPosition);
    }

    private float calcBoardPosition(int base) {
        return base / 2f - (Gdx.graphics.getHeight() / BOARD_SIZING_FACTOR / 2);
    }

    private float calcBoardSize() {
        return Gdx.graphics.getHeight() / BOARD_SIZING_FACTOR;
    }


    public ClickListener startTurnListener(){
        return new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                hideTurnDialog();
                diceAnimation.showDice(stage);
            }
        };
    }

    @Override
    public void dispose() {
        inputMultiplexer.removeProcessor(stage);
        this.dispose();
    }
}
