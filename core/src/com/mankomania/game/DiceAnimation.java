package com.mankomania.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.security.SecureRandom;
import java.util.ArrayList;


public class DiceAnimation extends ScreenAdapter {

    private final SecureRandom random;
    private final ArrayList<Image> firstDiceList;
    private final ArrayList<Image> secondDiceList;
    private final float spacing = Gdx.graphics.getWidth() / 14f;
    private boolean diceShown;

    public DiceAnimation() {
        random = new SecureRandom();
        firstDiceList = new ArrayList<>();
        secondDiceList = new ArrayList<>();
        initDice();
    }

    public void showDice(Stage stage) {
        int num = random.nextInt(6) + 1;
        int num2 = random.nextInt(6) + 1;

        for (Image image : firstDiceList) {
            if ((firstDiceList.indexOf(image) + 1) == num) {
                image.setPosition(Gdx.graphics.getWidth() / 2f + spacing, Gdx.graphics.getHeight() / 2f);
                image.setSize(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
                stage.addActor(image);
                image.setVisible(true);
            }
        }

        for (Image image : secondDiceList) {
            if ((secondDiceList.indexOf(image) + 1) == num2) {
                image.setPosition(Gdx.graphics.getWidth() / 2f - 2 * spacing, Gdx.graphics.getHeight() / 2f);
                image.setSize(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
                stage.addActor(image);
                image.setVisible(true);
            }
        }
        setDiceShown(true);
    }

    public void removeDice() {
        for (Image image : firstDiceList) {
            if (image.isVisible()) {
                image.remove();
            }
        }

        for (Image image : secondDiceList) {
            if (image.isVisible()) {
                image.remove();
            }
        }
    }

    public void setDiceShown(boolean diceShown) {
        this.diceShown = diceShown;
    }

    public boolean getDiceShown(){
        return diceShown;
    }

    private void initDice() {
        Texture one = new Texture(Gdx.files.internal("dice/dice-six-faces-one.png"));
        Texture two = new Texture(Gdx.files.internal("dice/dice-six-faces-two.png"));
        Texture three = new Texture(Gdx.files.internal("dice/dice-six-faces-three.png"));
        Texture four = new Texture(Gdx.files.internal("dice/dice-six-faces-four.png"));
        Texture five = new Texture(Gdx.files.internal("dice/dice-six-faces-five.png"));
        Texture six = new Texture(Gdx.files.internal("dice/dice-six-faces-six.png"));

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
}