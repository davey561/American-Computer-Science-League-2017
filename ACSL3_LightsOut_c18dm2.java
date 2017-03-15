package ACSL;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import OrbitalDM.Coordinates;

/**
 * ACSL third program 2017, Lights Out Assignment
 * @author student Davey Morse
 * @date March.11.2017
 * Worked on when assignment was received on Thursday as well
 *
 *Remember to attach coordinates class
 */
public class ACSL3_LightsOut_c18dm2 {
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		Coordinates pushed_square = new Coordinates(); 						//the coordinates of the pushed object
		ArrayList <Integer> unique_squares = new ArrayList <Integer> (); 	//keeps track of the number of unique x squares (relative to board 1) for the row just examined
		int part;															//the section of input currently being looked at (ranges from 1 to 4)
		boolean [][] board1 = new boolean[8][8]; 							//first board, represents on and off in terms of boolean true and false
		boolean [][] board2 = new boolean [8][8]; 							//second board, represents on and off in terms of boolean true and false
		Scanner scan = new Scanner(System.in); 								//Scanner
		String [] four_parts = new String[4]; 								//the input, broken up into its four constituent hexadecimal parts. If spaces aren't deleted and string is compiled into one, too many characters to store (seems like strings max out at 38)
		StringBuffer [] binary = new StringBuffer [4]; 						//Array of stringbuffers to store the binary versions of each of the four hexadecimal parts of the input
		boolean firsttime = true;											//keeps track of whether or not program is currently taking in the first line of input (special condition below if it is)

		//while loop to iteratively repeat scanning and algorithm
		big: while(true){
			pushed_square = new Coordinates(); 				//setting coordinates of "pushed square" (the square selected) to null
			unique_squares = new ArrayList <Integer> (); 	//clearing uniquesquares arraylist for next iteration of scanning
			//if not on the first line being taken in
			if (firsttime==false){
				board1 = setEqual(board1, board2); //set the first board equal to the contents that was just processed into the second board
			}
			four_parts = scan.nextLine().split("\\s");
			for(int i = 0; i<4; i++){
				//interprets as a BigInt of radix 16, hexadecimal
				//converts bin to binary string
				binary[i] = new StringBuffer ((new BigInteger(four_parts[i],16)).toString(2));
				//for each zero needed to fill the binary stringbuffer to length 16
				for (int to_sixteen = binary[i].length(); to_sixteen<16; to_sixteen++){
					//inserts a zero in the beginning
					binary[i].insert(0, "0");
				}
			}

			//converting this binary input to the table, reading in as board2
			//for each row in board 2
			for (int r = 0; r<8; r++){
				part = (int) r/2; // 'part' is the index of the binary array that is relevant to the given section of the board
				//for each column in board 2
				for (int c = 0; c<8; c++){
					//if the value at this index in board is 1
					if (binary[part].charAt(8*(r%2) + c)== '1'){
						board2[r][c] = true; //sets to true
					} else {
						//sets to false
						board2[r][c] = false;
					}
				}
			}
			//If this is the first time looping through:
			if (firsttime == true){
				//set board 1 equal to contents of board two at this moment
				board1 = setEqual(board1, board2);
				//set first time equal to false, now that above command is completed
				firsttime = false;
				//continue the big while loop to read in the input again, now to actually fill the second board
				continue big;
			}
			//start from bottom to top, left to right
			//for each row of boards
			gothrough: for(int r = 0; r< 8;r++){
				//if there are no differences yet between the values of squares in the boards
				if (unique_squares.size() == 0){
					//for each column in the boards
					for (int c = 0; c<8; c++){
						//if the boards have different values at this index
						if(squareCheck(r, c, board1, board2) == false){
							//records this coordinate pair index
							unique_squares.add(c);
						}
					}
				}
				//if this past row has had differences, number of differences determines where the x spot was (and y location determines where y spot was)
				else{
					pushed_square.setX((unique_squares.get(0)+ unique_squares.get(unique_squares.size()-1))/2);
					//if one different square:
					if (unique_squares.size() == 1){
						//pushed square must be two up
						pushed_square.setY(r+1);
					} else if (unique_squares.size() == 3){
						//pushed square must be one up
						pushed_square.setY(r+0);
					} else if (unique_squares.size() == 5){
						//pushed sqare must be in this row
						pushed_square.setY(r-1);
					}
					//break the large for loop after a row with differences has been processed
					break gothrough;
				}
			}
			//Print the square that was pushed, as calculated above
			pushed_square.print();
		}
		//after every round, board 1 takes on values of board 2, board two becomes new board
	}
	/**
	 * Checks that two given boards at a given index row, col have equal boolean values
	 * @param row the row number of the index
	 * @param col the column number of the index
	 * @param board1 the first board
	 * @param board2 the second board
	 * @return true if the values are the same, false if they are different
	 */
	public static boolean squareCheck (int row, int col, boolean [][] board1, boolean [][] board2){
		if (board1[row][col] != board2[row][col]){
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * To fix a reference pointer error, sets values of two boolean two d arrays equal
	 * both arrays have the same dimensions
	 * @param one the array to be modified
	 * @param two the array to be copied
	 * @return  the copied board values`
	 */
	public static boolean[][] setEqual(boolean [][] modify, boolean [][] copy){
		for (int r = 0; r<modify.length; r++){
			for (int c = 0; c<modify[0].length; c++){
				modify[r][c] = copy[r][c];
			}
		}
		return modify;
	}
}
