/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.tetris;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author alu10720810
 */
public class Board extends javax.swing.JPanel {
    
    class MyKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (canMove(currentSheap, currentRow, currentCol - 1)) {
                    currentCol--;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (canMove(currentSheap, currentRow, currentCol + 1)) {
                    currentCol++;
                }
                break;
            case KeyEvent.VK_UP:
                Shape newShape = currentSheap.getCopy();
                newShape.rotareRight();
                if (!shapeHitsMatrix(newShape, currentRow, currentCol)) {
                    currentSheap = newShape;
                }
                break;
            case KeyEvent.VK_DOWN:
                /*if (canMove(currentSheap, currentRow, currentCol - 1)) {
                    currentRow++;
                }*/
                dropShape();
                break;
            default:
                break;
        }
        repaint();
    }
}
    
    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;
    public static final int DELTA_TIME = 500; //Milliseconds = ms
    
    private Shape currentSheap;
    private int currentRow;
    private int currentCol;
    private Timer timer;
    private MyKeyAdapter keyAdapter;
    private Tetrominoes[][] matrix;
    private ScoreInterface score;

    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        initMatrix();
        createNewCurrentShape();
        keyAdapter = new MyKeyAdapter();
        addKeyListener(keyAdapter);
        setFocusable(true);
        timer = new Timer(DELTA_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
        });
        timer.start();
    }
    
    public void initMatrix() {
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] = Tetrominoes.NoShape;
            }
            
        }
    }
    
    public boolean canMove(Shape shape, int row, int col) {
        if(col + shape.getMinX() < 0) {
            return false;
        }
        if (col + shape.getMaxX() >= NUM_COLS) {
            return false;
        }
        if (row + shape.getMaxY() >= NUM_ROWS) {
            return false;
        }
        if (shapeHitsMatrix(shape, row, col)) {
            return false;
        }
        return true;
    }
    
        public void setScoreInterface(ScoreInterface scoreInterface) {
        this.score = scoreInterface;
    }
    
    public boolean shapeHitsMatrix(Shape shape, int row, int col) {
        for (int i = 0; i < 4; i++) {
            int rr = row + shape.getY(i);
            int cc = col + shape.getX(i);
            if (cc < 0 || cc >= NUM_COLS) {
                return true;
            }
            if (rr >= 0) {
                if (matrix[rr][cc] != Tetrominoes.NoShape) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void dropShape() {
        int row = currentRow;
        while (canMove(currentSheap, row + 1, currentCol)) {
            row ++;
        }
        currentRow = row;
    }
    
    public void tick() {
        if (shapeHitsMatrix(currentSheap, 0, currentCol)) {
            repaint();
            processGameOver();
            return;
        }
        if (KeyEvent.VK_SPACE != 1) {
            if (canMove(currentSheap, currentRow + 1, currentCol)) {
            currentRow++;
            repaint();
        } else {
            copyCurrentShapeToMatrix();
            checkCompletedRows();
            createNewCurrentShape();
        }
        
        }
    }
    
    public void deleteRow(int row) {
        if (isRowCompleted(row)) {
            for (int i = row; i > 0; i--) {
                for (int j = 0; j < NUM_COLS; j++) {
                    matrix[i][j] = matrix[i - 1][j];
                }
            }
            for (int i = 0; i < NUM_COLS; i++) {
                matrix[0][i] = Tetrominoes.NoShape;
            }
        }
    }
    
    public void checkCompletedRows() {
        for (int row = 0; row < matrix.length; row++) {
                if (isRowCompleted(row)) {
                    deleteRow(row);
                }
            }
    }
    
    public boolean isRowCompleted(int row) {
        for (int col = 0; col < NUM_COLS; col++) {
            if (matrix[row][col] == Tetrominoes.NoShape) {
                return false;
            }
        }
        return true;
    }
    
    public void copyCurrentShapeToMatrix() {
        for (int i = 0; i < 4; i++) {
            int row = currentRow + currentSheap.getY(i);
            int col = currentCol + currentSheap.getX(i);
            matrix[row][col] = currentSheap.getShape();
        }
    }
    
    private void createNewCurrentShape() {
        currentSheap = new Shape();
        currentRow = 0;
        currentCol = NUM_COLS / 2;
    }
    
    private void processGameOver() {
        timer.stop();
        removeKeyListener(keyAdapter);
        currentSheap = null;
        fillmatrixWithGameOver();
    }
    
    private void  fillmatrixWithGameOver() {
        int rowGO = 0;
        int colGO = 0;
        
        timerGO = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                @Override
                matrix[rowGO][colGO] = Tetrominoes.LineShape;
                colGO++;
                if (colGO >= NUM_COLS) {
                    colGO = 0;
                    rowGO++;
                }
                if (rowGo >= NUM_ROWS) {
                    timerGO.stop();
                }
            }
        });
        /*currentSheap = null;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                try {
                    matrix[row][col] = Tetrominoes.LineShape;
                    Thread.sleep(50);
                    repaint();
                } catch (InterruptedException ex) {

                }
            }
            
        }*/
    }
    
    public void initGame() {
        createNewCurrentShape();
        int deltaTime = ConfigData.getInstance().getDeltaTime();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintBorder(g);
            paintMatrix(g);
            paintCurrentShape(g);
        }
        
        public void paintMatrix(Graphics g) {
            for (int row = 0; row < NUM_ROWS; row++) {
                for (int col = 0; col < NUM_COLS; col++) {
                    drawSquare(g, row, col, matrix[row][col]);
                }
                
            }
        }
        
        public void paintBorder(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            BasicStroke bs = new BasicStroke(1);
            g2d.setStroke(bs);
            g2d.drawRect(0, 0, (NUM_COLS * getSquareWidth()) - 2, (NUM_ROWS * getSquareHeight()) - 2);
        }
        
        private void paintCurrentShape(Graphics g) {
            for (int i = 0; i < 4; i++) {
                if (currentSheap.getY(i) + currentRow >= 0) {
                 drawSquare(g, currentRow + currentSheap.getY(i), currentCol + currentSheap.getX(i), 
                         currentSheap.getShape());
                }
            }
        }
        
        
        private void drawSquare(Graphics g, int row, int col,
        Tetrominoes shape) {
            Color colors[] = { new Color(0, 0, 0),
            new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
            };
            int x = col * getSquareWidth();
            int y = row * getSquareHeight();
            Color color = colors[shape.ordinal()];
            g.setColor(color);
            g.fillRect(x + 1, y + 1, getSquareWidth() - 2,
            getSquareHeight() - 2);
            g.setColor(color.brighter());
            g.drawLine(x, y + getSquareHeight() - 1, x, y);
            g.drawLine(x, y, x + getSquareWidth() - 1, y);
            g.setColor(color.darker());
            g.drawLine(x + 1, y + getSquareHeight() - 1,
            x + getSquareWidth() - 1, y + getSquareHeight() - 1);
            g.drawLine(x + getSquareWidth() - 1,
            y + getSquareHeight() - 1,
            x + getSquareWidth() - 1, y + 1);
        }
    
    private int getSquareWidth() {
        return getWidth() / NUM_COLS;
    }
    
    private int getSquareHeight() {
        return getHeight() / NUM_ROWS;
    }




    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}


