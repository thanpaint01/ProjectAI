package Model;

import java.util.Random;

import project.caro.config.ConfigGame;

public class Board {
	/*
	 * x : 1 o : 2 null : -1
	 */
	public int numWin = ConfigGame.NUMBER_WIN;
	public int[][] matrix;

	public Board(int rows, int cols) {
		this.matrix = new int[rows][cols];
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[i].length; j++) {
				this.matrix[i][j] = -1;
			}
		}
	}

	public boolean isValid(int rows, int cols) {
		return false;
	}

	public Board move(int rows, int cols, int target) {
		Board boardClone = this.clone();
		if (boardClone.matrix[rows][cols] == -1) {
			boardClone.matrix[rows][cols] = target;
			return boardClone;
		}
		return null;
	}



	public boolean isOver() {
		int numX = check(1);
		int numO = check(2);
		if (numX == this.numWin || numO == this.numWin)
			return true;
		return false;
	}

	protected Board clone() {
		Board boardClone = new Board(matrix.length, matrix.length);
		for (int rowIndex = 0; rowIndex < matrix.length; rowIndex++) {
			for (int colIndex = 0; colIndex < matrix[rowIndex].length; colIndex++) {
				boardClone.matrix[rowIndex][colIndex] = this.matrix[rowIndex][colIndex];
			}
		}
		return boardClone;
	}

	public int count(int[][] matrix, int[] location) {
		int rs = 1;
		int count = 1;
		final int loacation_row = location[0];
		final int loacation_col = location[1];
		int numWin = 3;
		// Đếm theo hàng ngang
		for (int i = loacation_col + 1; i < matrix[loacation_row].length; i++) {
			if (matrix[loacation_row][loacation_col] == matrix[loacation_row][i]) {
				count++;
				if (count == numWin) {
					if (!checkCorrect(loacation_row, loacation_col + 1 - 2) || !checkCorrect(loacation_row, i + 1)
							|| matrix[loacation_row][loacation_col - 1] == -1 || matrix[loacation_row][i + 1] == -1) {

					} else {
						if (matrix[loacation_row][loacation_col] != matrix[loacation_row][loacation_col - 1]
								&& matrix[loacation_row][loacation_col] != matrix[loacation_row][i + 1]) {
							break;
						}

					}
				}
				if (count >= rs) {
					rs = count;
				}
			} else {

				break;
			}

		}
		count = 1;
		// Đếm theo hàng dọc
		for (int i = loacation_row + 1; i < matrix.length; i++) {
			if (matrix[loacation_row][loacation_col] == matrix[i][loacation_col]) {
				count++;
				if (count == numWin) {
					if (!checkCorrect(loacation_row + 1 + (-2), loacation_col) || !checkCorrect(i + 1, loacation_col)
							|| matrix[loacation_row + 1 + (-2)][loacation_col] == -1
							|| matrix[i + 1][loacation_col] == -1) {

					} else {
						if (matrix[loacation_row][loacation_col] != matrix[loacation_row + 1 + (-2)][loacation_col]
								&& matrix[loacation_row][loacation_col] != matrix[i + 1][loacation_col]) {
							break;
						}

					}
				}
				if (count >= rs) {
					rs = count;
				}
			} else {
				break;
			}

		}
		count = 1;
		// Đếm theo đường chéo ngược
		for (int i = 1; i < matrix.length - loacation_col && i < matrix.length - loacation_row; i++) {
			if (matrix[loacation_row][loacation_col] == matrix[loacation_row + i][loacation_col + i]) {

				count++;
				if (count == numWin) {
					if (!checkCorrect(loacation_row + (-1), loacation_col + (-1))
							|| !checkCorrect(loacation_row + (i + 1), loacation_col + (i + 1))
							|| matrix[loacation_row + (-1)][loacation_col + (-1)] == -1
							|| matrix[loacation_row + (i + 1)][loacation_col + (i + 1)] == -1) {

					} else {
						if (matrix[loacation_row][loacation_col] != matrix[loacation_row + (-1)][loacation_col + (-1)]
								&& matrix[loacation_row][loacation_col] != matrix[loacation_row + (i + 1)][loacation_col
										+ (i + 1)]) {
							break;
						}

					}
				}
				if (count >= rs) {
					rs = count;
				}
			} else {
				break;
			}
		}
		count = 1;
		// Đếm theo chéo thuận
		for (int i = 1; i < matrix.length - loacation_col; i++) {
			if (!checkCorrect(loacation_row - i, loacation_col + i)) {

				break;
			}
			if (matrix[loacation_row][loacation_col] == matrix[loacation_row - i][loacation_col + i]) {
				count++;
				if (count == numWin) {
					if (!checkCorrect(loacation_row + 1, loacation_col - 1)
							|| !checkCorrect(loacation_row - (i + 1), loacation_col + (i + 1))
							|| matrix[loacation_row + 1][loacation_col - 1] == -1
							|| matrix[loacation_row - (i + 1)][loacation_col + (i + 1)] == -1) {

					} else {
						if (matrix[loacation_row][loacation_col] != matrix[loacation_row + (-1)][loacation_col - (-1)]
								&& matrix[loacation_row][loacation_col] != matrix[loacation_row + (i + 1)][loacation_col
										- (i + 1)]) {
							break;
						}

					}
				}
				if (count >= rs) {
					rs = count;
				}
			} else {
				break;
			}
		}

		return rs;
	}

	public boolean checkCorrect(int rowIndex, int colIndex) {
		return rowIndex >= 0 && colIndex >= 0 && rowIndex < matrix.length && colIndex <= matrix[rowIndex].length;
	}

	/*
	 * Check target win or no. If win return target, no return -1
	 */
	public int check(int target) {
		int[] location = new int[2];
		int count = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {

				if (matrix[i][j] == target) {

					location[0] = i;
					location[1] = j;
					count = count(matrix, location);

				}
				if (count >= numWin) {
					return target;
				} else
					count = 0;

			}
		}
		return -1;
	}

}
