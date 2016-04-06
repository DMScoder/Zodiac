package com.zodiac.Game;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Player {

    private String playerName = "Default";
    private Color color;
    private ArrayList<Player> enemies = new ArrayList<Player>();
    private ArrayList<Player> allies = new ArrayList<Player>();

    public static Player THE_PLAYER = new Player(Color.RED);

    public Player(Color color)
    {
        this.color = color;
    }

    public void addEnemy(Player enemy)
    {
        enemies.add(enemy);
    }

    public void addAlly(Player ally)
    {
        allies.add(ally);
    }

    public void removeEnemy(Player enemy)
    {
        enemies.remove(enemy);
    }

    public void removeAlly(Player ally)
    {
        allies.remove(ally);
    }

    public boolean isAlly(Player player)
    {
        return allies.contains(player);
    }

    public boolean isEnemy(Player player)
    {
        return enemies.contains(player);
    }

    public Player(String playerName, Color color)
    {
        this.playerName = playerName;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
