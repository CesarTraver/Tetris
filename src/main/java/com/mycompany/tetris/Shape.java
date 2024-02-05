/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetris;

/**
 *
 * @author alu10720810
 */
public class Shape {
    private Tetrominoes pieceShape;
    private int coordinates[][];
    
    private static int[][][] coordsTable = new int[][][] {
    { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
    { { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } },
    { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
    { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } },
    { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
    { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } },
    { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
    { { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } }
    };
    
    public Shape() {
        int randomShape = (int) Math.random() * 7 + 1;
        pieceShape = Tetrominoes.values()[randomShape];
        coordinates = coordsTable[randomShape];
    }
}


