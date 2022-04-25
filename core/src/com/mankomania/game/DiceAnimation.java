package com.mankomania.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
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
import java.util.ArrayList;
import java.util.Random;


public class DiceAnimation extends ScreenAdapter {
    private final Skin skin;
    private final Stage stage;
    private final Table tab;
    private Random random;
    private final ArrayList<Image> firstDiceList;
    private final ArrayList<Image> secondDiceList;
    private final float spacing = Gdx.graphics.getWidth() / 14f;
    private InputMultiplexer inputMultiplexer;
    private int count = 1;

    public DiceAnimation() {
        firstDiceList = new ArrayList<>();
        secondDiceList = new ArrayList<>();
        initDice();

        random = new Random();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        skin.getFont("font").getData().setScale(5, 5);

        Texture back = new Texture("background.jpg");
        TextureRegionDrawable background = new TextureRegionDrawable(back);

        tab = new Table();
        tab.setFillParent(true);
        tab.setWidth(stage.getWidth());
        tab.align(Align.center | Align.bottom);
        tab.setBackground(background);

        TextButton rollButton = new TextButton("ROLL", skin, "default");
        rollButton.getLabel().setFontScale(Gdx.graphics.getHeight() / 400f);
        rollButton.addListener(rollListener());
        tab.add(rollButton).width(Gdx.graphics.getWidth() / 5f).height(Gdx.graphics.getHeight() / 6f).align(Align.center).padBottom(20f);

        inputMultiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        if (!inputMultiplexer.getProcessors().contains(stage, true)) {
            inputMultiplexer.addProcessor(stage);
        }

        stage.addActor(tab);
    }

    public void counter(){
        if (count == 1) {
            showDice();
            count++;
        }
    }

    private void showDice (){
        int num = random.nextInt(6)+1;
        int num2 = random.nextInt(6)+1;

        for (Image image : firstDiceList) {
            if ((firstDiceList.indexOf(image) + 1) == num) {
                image.setPosition(Gdx.graphics.getWidth() / 2f+spacing, Gdx.graphics.getHeight() / 2f);
                image.setSize(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
                stage.addActor(image);
            }
        }

        for (Image image : secondDiceList) {
            if ((secondDiceList.indexOf(image) + 1) == num2) {
                image.setPosition(Gdx.graphics.getWidth() / 2f-2*spacing, Gdx.graphics.getHeight() / 2f);
                image.setSize(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
                stage.addActor(image);
            }
        }

        String rolled = Integer.toString(num+num2);
        Label rolledLabel = new Label("Gewuerfelt: " + rolled, skin);
        rolledLabel.setPosition(Gdx.graphics.getWidth() / 2f - rolledLabel.getWidth()/2, Gdx.graphics.getHeight() / 3f);
        stage.addActor(rolledLabel);
    }

    private void initDice(){
        Texture one =new Texture(Gdx.files.internal("dice/dice-six-faces-one.png"));
        Texture two =new Texture(Gdx.files.internal("dice/dice-six-faces-two.png"));
        Texture three =new Texture(Gdx.files.internal("dice/dice-six-faces-three.png"));
        Texture four =new Texture(Gdx.files.internal("dice/dice-six-faces-four.png"));
        Texture five =new Texture(Gdx.files.internal("dice/dice-six-faces-five.png"));
        Texture six =new Texture(Gdx.files.internal("dice/dice-six-faces-six.png"));

        Image diceOne = new Image(one);
        Image diceTwo = new Image(two);
        Image diceThree = new Image(three);
        Image diceFour = new Image(four);
        Image diceFive = new Image(five);
        Image diceSix = new Image(six);
        Image diceOneSecond = new Image(one);
        Image diceTwoSecond = new Image(two);
        Image diceThreeSecond = new Image(three);
        Image diceFourSecond = new Image(four);
        Image diceFiveSecond = new Image(five);
        Image diceSixSecond = new Image(six);

        firstDiceList.add(diceOne);
        firstDiceList.add(diceTwo);
        firstDiceList.add(diceThree);
        firstDiceList.add(diceFour);
        firstDiceList.add(diceFive);
        firstDiceList.add(diceSix);

        secondDiceList.add(diceOneSecond);
        secondDiceList.add(diceTwoSecond);
        secondDiceList.add(diceThreeSecond);
        secondDiceList.add(diceFourSecond);
        secondDiceList.add(diceFiveSecond);
        secondDiceList.add(diceSixSecond);
    }

    @Override
    public void render(float delta){
       super.render(delta);
       stage.act(delta);
       stage.draw();
    }

    @Override
    public void dispose(){
        stage.dispose();
    }

    public ClickListener rollListener(){
        return new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
               counter();
            }
        };
    }
}

