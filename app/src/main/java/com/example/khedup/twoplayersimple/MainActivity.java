package com.example.khedup.twoplayersimple;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.khedup.twoplayersimple.Models.Player;

import static android.util.Log.d;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private ArrayList<TextView> diceResults = new ArrayList<>();
    private ArrayList<Button> diceButtons = new ArrayList<>();
    private ArrayList<Boolean> diceHold = new ArrayList<>();
    private int numRolls = 0;
    private int numKeeps = 0;
    private boolean playerOneTurn = true;

    Button rollButton;
    Button keep1;
    Button keep2;
    Button keep3;
    Button keep4;
    Button keep5;
    Button keep6;

    TextView p1Health;
    TextView p1Energy;
    TextView p1Vic;
    TextView p2Health;
    TextView p2Energy;
    TextView p2Vic;

    Player p1 = new Player();
    Player p2 = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start Here DATABASE SETUP.

        p1Health = (TextView) findViewById(R.id.H1Cell);
        p1Energy = (TextView) findViewById(R.id.E1Cell);
        p1Vic = (TextView) findViewById(R.id.VP1Cell);
        p2Health = (TextView) findViewById(R.id.H2Cell);
        p2Energy = (TextView) findViewById(R.id.E2Cell);
        p2Vic = (TextView) findViewById(R.id.VP2Cell);


        p1Health.setText(Integer.toString(p1.getHealth()));
        p2Health.setText(Integer.toString(p2.getHealth()));


        diceResults.add((TextView) findViewById(R.id.resultOne));
        diceResults.add((TextView) findViewById(R.id.resultTwo));
        diceResults.add((TextView) findViewById(R.id.resultThree));
        diceResults.add((TextView) findViewById(R.id.resultFour));
        diceResults.add((TextView) findViewById(R.id.resultFive));
        diceResults.add((TextView) findViewById(R.id.resultSix));
        diceButtons.add((Button) findViewById(R.id.button1));
        diceButtons.add((Button) findViewById(R.id.button2));
        diceButtons.add((Button) findViewById(R.id.button3));
        diceButtons.add((Button) findViewById(R.id.button4));
        diceButtons.add((Button) findViewById(R.id.button5));
        diceButtons.add((Button) findViewById(R.id.button6));
        for (int i = 0; i < 6; i++) {
            diceHold.add(false);
        }
        //Rolling Dice
        rollButton = (Button) findViewById(R.id.buttonRoll);
        rollButton.setText(R.string.startRound);
        rollButton.setOnClickListener(this);


        //Keep Buttons
        keep1 = (Button) findViewById(R.id.button1);
        keep1.setOnClickListener(this);
        keep2 = (Button) findViewById(R.id.button2);
        keep2.setOnClickListener(this);
        keep3 = (Button) findViewById(R.id.button3);
        keep3.setOnClickListener(this);
        keep4 = (Button) findViewById(R.id.button4);
        keep4.setOnClickListener(this);
        keep5 = (Button) findViewById(R.id.button5);
        keep5.setOnClickListener(this);
        keep6 = (Button) findViewById(R.id.button6);
        keep6.setOnClickListener(this);


    }

    public void resetHolds() {
        for (int i = 0; i < 6; i++) {
            diceHold.set(i, false);
            //        diceButtons.get(i).setBackgroundColor(Color.DKGRAY);
        }
    }

    public void resetResults() {
        for (int i = 0; i < diceResults.size(); i++) {
            diceResults.get(i).setText(R.string.buttonRollString);
            diceResults.get(i).setBackgroundColor(Color.WHITE);
        }
    }

    public void updateResults() {
        for (int i = 0; i < diceResults.size(); i++) {
            if (!diceHold.get(i)) {
                double random = Math.random();
                if (random < .166) {
                    diceResults.get(i).setText(R.string.outcomeOne);
                    diceResults.get(i).setBackgroundColor(Color.WHITE);
                } else if (random < .333) {
                    diceResults.get(i).setText(R.string.outcomeTwo);
                    diceResults.get(i).setBackgroundColor(Color.WHITE);
                } else if (random < .5) {
                    diceResults.get(i).setText(R.string.outcomeThree);
                    diceResults.get(i).setBackgroundColor(Color.WHITE);
                } else if (random < .666) {
                    diceResults.get(i).setText(R.string.outcomeAttack);
                    diceResults.get(i).setBackgroundColor(Color.RED);
                } else if (random < .833) {
                    diceResults.get(i).setText(R.string.outcomeEnergy);
                    diceResults.get(i).setBackgroundColor(Color.YELLOW);
                } else {
                    diceResults.get(i).setText(R.string.outcomeHeal);
                    diceResults.get(i).setBackgroundColor(Color.GREEN);
                }
            }
        }
    }

    public void turn(Player me, Player other) {
        int numOnes = 0;
        int numTwos = 0;
        int numThrees = 0;
        int energy = 0;
        int heal = 0;
        int attack = 0;
        for (int i = 0; i < diceResults.size(); i++) {
            if (diceResults.get(i).equals(R.string.outcomeOne)) {
                numOnes++;
            } else if (diceResults.get(i).getText().toString().equals(getString(R.string.outcomeTwo))) {
                numTwos++;
            } else if (diceResults.get(i).getText().toString().equals(getString(R.string.outcomeThree))) {
                numThrees++;
            } else if (diceResults.get(i).getText().toString().equals(getString(R.string.outcomeAttack))) {
                attack--;
            } else if (diceResults.get(i).getText().toString().equals(getString(R.string.outcomeEnergy))) {
                energy++;
            } else if (diceResults.get(i).getText().toString().equals(getString(R.string.outcomeHeal))) {
                heal++;
            }

        }

        //Done "parsing" update player's variables.
        me.updateHealth(heal);
        me.updateEnergy(energy);
        other.updateHealth(attack);
        if (numOnes > 2) {
            if (numOnes == 3) {
                me.updateVictoryPoint(1);
            } else if (numOnes == 4) {
                me.updateVictoryPoint(2);
            } else if (numOnes == 5) {
                me.updateVictoryPoint(3);
            } else {
                me.updateVictoryPoint(4);
            }
        }
        if (numTwos > 2) {
            if (numTwos == 3) {
                me.updateVictoryPoint(2);
            } else if (numTwos == 4) {
                me.updateVictoryPoint(3);
            } else if (numTwos == 5) {
                me.updateVictoryPoint(4);
            } else {
                me.updateVictoryPoint(5);
            }
        }
        if (numThrees > 2) {
            if (numThrees == 3) {
                me.updateVictoryPoint(3);
            } else if (numThrees == 4) {
                me.updateVictoryPoint(4);
            } else if (numThrees == 5) {
                me.updateVictoryPoint(5);
            } else {
                me.updateVictoryPoint(6);
            }
        }


        p1Health = (TextView) findViewById(R.id.H1Cell);
        p1Energy = (TextView) findViewById(R.id.E1Cell);
        p1Vic = (TextView) findViewById(R.id.VP1Cell);
        p2Health = (TextView) findViewById(R.id.H2Cell);
        p2Energy = (TextView) findViewById(R.id.E2Cell);
        p2Vic = (TextView) findViewById(R.id.VP2Cell);


        if (playerOneTurn) {
            p1Health.setText(Integer.toString(me.getHealth()));
            p1Vic.setText(Integer.toString(me.getVictoryPoint()));
            p1Energy.setText(Integer.toString(me.getEnergy()));
            p2Health.setText(Integer.toString(other.getHealth()));
            p2Vic.setText(Integer.toString(other.getVictoryPoint()));
            p2Energy.setText(Integer.toString(other.getEnergy()));
        } else if (!playerOneTurn) {
            p1Health.setText(Integer.toString(other.getHealth()));
            p1Vic.setText(Integer.toString(other.getVictoryPoint()));
            p1Energy.setText(Integer.toString(other.getEnergy()));
            p2Health.setText(Integer.toString(me.getHealth()));
            p2Vic.setText(Integer.toString(me.getVictoryPoint()));
            p2Energy.setText(Integer.toString(me.getEnergy()));
        }
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonRoll) {

            d("roll", Integer.toString(numRolls) + " " + Boolean.toString(playerOneTurn));
            if (numRolls == 0 && playerOneTurn) {
                resetResults();
                rollButton.setText(R.string.player1turn1);
                numRolls++;
            } else if (numRolls == 1 && playerOneTurn) {
                resetHolds();
                updateResults();
                rollButton.setText(R.string.player1turn2);
                numRolls++;
            } else if (numRolls == 2 && playerOneTurn) {
                updateResults();
                rollButton.setText(R.string.player1turn3);
                numRolls++;
            } else if (numRolls == 3 && playerOneTurn) {
                updateResults();
                rollButton.setText(R.string.finishTurn);
                numRolls = 0;
                turn(p1, p2);
                playerOneTurn = false;
            } else if (numRolls == 0 && !playerOneTurn) {
                resetResults();
                rollButton.setText(R.string.player1turn1);
                numRolls++;
            } else if (numRolls == 1 && !playerOneTurn) {
                resetHolds();
                updateResults();
                rollButton.setText(R.string.player1turn2);
                numRolls++;
            } else if (numRolls == 2 && !playerOneTurn) {
                updateResults();
                rollButton.setText(R.string.player1turn3);
                numRolls++;
            } else if (numRolls == 3 && !playerOneTurn) {
                updateResults();
                rollButton.setText(R.string.finishTurn);
                numRolls = 0;
                turn(p2, p1);
                playerOneTurn = true;
            }

        } else if (v.getId() == R.id.button1 && numRolls != 0 && numRolls != 4) {
            diceHold.set(0, true);
            // diceButtons.get(0).setBackgroundColor(Color.BLACK);
        } else if (v.getId() == R.id.button2 && numRolls != 0 && numRolls != 4) {
            diceHold.set(1, true);
            // diceButtons.get(1).setBackgroundColor(Color.BLACK);
        } else if (v.getId() == R.id.button3 && numRolls != 0 && numRolls != 4) {
            diceHold.set(2, true);
            //  diceButtons.get(2).setBackgroundColor(Color.BLACK);
        } else if (v.getId() == R.id.button4 && numRolls != 0 && numRolls != 4) {
            diceHold.set(3, true);
            //  diceButtons.get(3).setBackgroundColor(Color.BLACK);
        } else if (v.getId() == R.id.button5 && numRolls != 0 && numRolls != 4) {
            diceHold.set(4, true);
            //  diceButtons.get(4).setBackgroundColor(Color.BLACK);
        } else if (v.getId() == R.id.button6 && numRolls != 0 && numRolls != 4) {
            diceHold.set(5, true);
            //  diceButtons.get(5).setBackgroundColor(Color.BLACK);
        }


    }
}
