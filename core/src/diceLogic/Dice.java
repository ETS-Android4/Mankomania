package diceLogic;

import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.List;

public class Dice {
    private SecureRandom secRand = new SecureRandom();
    private List<Integer> dice;


    public List<Integer> throwDice (int numberOfFaces, int numberOfDice){
        dice = new ArrayList<>();
        for(int i = 0; i < numberOfDice; i++){
         dice.add((secRand.nextInt(numberOfFaces - 1) + 1));
        }
        return dice;
    }
    public int getSumOfDice (List<Integer> diceToSum){
        int sum = 0;
        for(Integer die: diceToSum){
            sum += die;
        }
        return sum;
    }
    //-----------GETTER-----------------
    public List<Integer> getDice(){ return dice; }
    //---------------------------------
}
