/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetris;

/**
 *
 * @author alu10720810
 */
public class ConfigData {
      
    private static ConfigData configData = new ConfigData();
    
    private String playerName;
    private int level;
    private int score;
    
    private ConfigData() {
        playerName = "";
        level = 1;
        score = 0;
    }
    
    public static ConfigData getInstance() {
        return configData;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
