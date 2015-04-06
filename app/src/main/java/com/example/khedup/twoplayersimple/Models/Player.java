package com.example.khedup.twoplayersimple.Models;

/**
 * Created by Khedup on 4/5/2015.
 */

public class Player {
    private int health;
    private int victoryPoint;
    private int energy;

    public Player() {
        health = 10;
        victoryPoint = 0;
        energy = 0;
    }

    public void updateHealth(int i) {
        health += i;
        if(health > 11){
            health = 11;
        }
    }

    public void updateVictoryPoint(int v) {
        victoryPoint += v;
    }

    public void updateEnergy(int e) {
        energy += e;
    }

    public int getHealth() {
        return health;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    public int getEnergy() {
        return energy;
    }
}

