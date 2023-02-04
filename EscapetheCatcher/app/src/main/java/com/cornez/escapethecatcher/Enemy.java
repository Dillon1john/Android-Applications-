package com.cornez.escapethecatcher;

public class Enemy {

    private int mRow;
    private int mCol;

    public void move(int[][] gameBoard, int preyCol, int preyRow) {
        if (mCol < preyCol && gameBoard[mRow][mCol + 1] == BoardCodes.isEMPTY) {
            mCol++;
        } else if (mCol > preyCol && gameBoard[mRow][mCol - 1] == BoardCodes.isEMPTY) {
            mCol--;
        } else if (mRow < preyRow && gameBoard[mRow + 1][mCol] == BoardCodes.isEMPTY) {
            mRow++;
        } else if (mRow > preyRow && gameBoard[mRow - 1][mCol] == BoardCodes.isEMPTY) {
            mRow--;
        }
    }

    public void setRow(int row) {
        mRow = row;
    }

    public int getRow() {
        return mRow;
    }

    public void setCol(int col) {
        mCol = col;
    }

    public int getCol() {
        return mCol;
    }

}
