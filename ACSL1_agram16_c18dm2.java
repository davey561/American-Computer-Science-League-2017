package ACSL;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * ACSL Assignment 1
 * @author student Davey Morse
 *
 */
public class ACSL1_agram16_c18dm2 {

	public enum Suit{
		C, D, H, S
	}
	public static void main(String[] args) {
		//declarations
		char [] char_suits = new char [6]; //for letter inputs (which indicate cards' suits): 1 - aces; 2; spades; 3 - hearts; 4 - cloves
		int [] nums = new int [6]; // a parallel array to 'suits'; indicates the number on each card inputted
		char []characters = new char[6];// stores the chars that represent tens and face cards (T,J,Q,K)
		Scanner scan = new Scanner(System.in);
		String [] card_data = new String [6];
		Suit [] suits;

		//for dealers' decisions
		ArrayList <Integer> samesuitindices = new ArrayList <Integer> ();
		

		//store input
		while (true){
			//reset all variables
			samesuitindices = new ArrayList <Integer> ();
			int dealerchoice = 0;
			char_suits = new char [6];
			nums = new int [6];
			characters = new char[6];
			card_data = new String [6];
			
			scan = new Scanner(System.in);
			//split data by card

			card_data = scan.nextLine().split("\\s*,\\s*"); //splits input data using delimiter ","
			//for the card the opponent puts down:
			nums[0] = Character.getNumericValue(card_data[0].charAt(0));
			char_suits[0] = card_data[0].charAt(1);
			if (nums[0]>9){
				characters[0] = card_data[0].charAt(0);
				nums[0] = -99;
			}
			//for the dealer's hand:
			for (int i = 1; i < card_data.length; i++)
			{
				nums[i] = Character.getNumericValue(card_data[i].charAt(0));
				if (nums[i]>9){
					characters[i] = card_data[i].charAt(0);
					nums[i] = -99;
				}
				char_suits[i] = card_data[i].charAt(1);
			}


			//converts character values to ints
			nums = convertCharacters(nums, characters);

			//Convert Suits to ENUM
			suits = convertToSuit(char_suits);

			//Dealer's logic, as explicated by instructions

			//If he does have a card of the same suit as the opponent's single card,
			//then return lowest card of higher rank than opponents card.
			
			//if he has card of same suit
			if (hasSameSuit(suits) == true){
				//find all the cards of same suit
				samesuitindices = findSameSuitCards(suits);
				//if dealer has a card of this suit of higher rank
				if(higherRank(nums, samesuitindices) == true){
					dealerchoice = findIndexClosestCard(nums, samesuitindices);
				}
				//else if dealer does not have card of higher rank
				else{
					dealerchoice = findIndexLowestCardSS (samesuitindices, nums);
				}
			}
			//else if no card of same suit
			else{
				//find lowest card
				dealerchoice = findIndexLowestCardDS(nums, suits);
			}
			//convert back to letter if a character number
			if (isChar(dealerchoice, nums)){
				System.out.println(convertFinalInt(dealerchoice, nums) + "" + suits[dealerchoice]);
			}
			else{
				System.out.println(nums[dealerchoice] + "" + suits[dealerchoice]);
			}
		}
	}
	public static int tieBreak(ArrayList <Integer>tying_indices, Suit[]suits){
		for (Suit n: Suit.values()){
			for (int i = 0; i< tying_indices.size(); i++){
				if(suits[tying_indices.get(i)] == n){
					return tying_indices.get(i);
				}
			}
		}
		return -9;
	}
	public static int findIndexLowestCardDS(int [] nums, Suit[] suits){
		int templowest = 99;
		ArrayList <Integer>ties = new ArrayList <Integer>();
		//for each number
		for (int i = 1; i<nums.length; i++){
			
			if(nums[i]<templowest){
				templowest = nums[i];
				ties.clear();
				ties.add(i);
			}
			else if (nums[i] == templowest){
				ties.add(i);
			}
		}
		//if there is only one tie
		if(ties.size()<2){
			return templowest;
		}
		else{
			return tieBreak(ties, suits);
		}
	}
	public static int findIndexLowestCardSS(ArrayList <Integer>samesuitindices, int[] nums){
		int templowest = 99;
		for (int i = 0; i<samesuitindices.size(); i++){
			if(nums[samesuitindices.get(i)]<templowest){
				templowest = samesuitindices.get(i);
			}
		}
		return templowest;
	}
	public static int findIndexClosestCard (int [] nums, ArrayList <Integer >samesuitindices){
		int storeddif = 99;
		int tempdif = 99;
		int index = 99;
		for(int i = 0; i<samesuitindices.size(); i++){
			if(nums[samesuitindices.get(i)]>nums[0]){
				tempdif = nums[samesuitindices.get(i)]-nums[0];
				if(tempdif<storeddif){
					storeddif = tempdif;
					index = samesuitindices.get(i);
				}
			}
		}
		return index;
	}
	public static boolean higherRank(int [] nums, ArrayList <Integer> samesuitindices){
		for(int i = 0; i<samesuitindices.size(); i++){
			if(nums[samesuitindices.get(i)]>nums[0]){
				return true;
			}
		}
		return false;
	}
	public static ArrayList findSameSuitCards(Suit[] suits){
		ArrayList <Integer> samesuitindices = new ArrayList <Integer>();
		//which cards are they?
		for(int i = 1; i<suits.length; i++){
			if(suits[i] == suits[0]){
				samesuitindices.add(i);
			}
		}
		return samesuitindices;
	}


	public static Suit[] convertToSuit(char[] char_suits){
		Suit [] suits = new Suit [char_suits.length];
		for (int i = 0; i<char_suits.length; i++){
			if (char_suits[i] == 'D'){
				suits[i] = Suit.D;
			}
			else if(char_suits[i] == 'C'){
				suits[i] = Suit.C;
			}
			else if(char_suits[i] == 'H'){
				suits[i] = Suit.H;
			}
			else if(char_suits[i] == 'S'){
				suits[i] = Suit.S;
			}
		}
		return suits;
	}
	public static boolean hasSameSuit(Suit [] suits){
		for (int i = 1; i<suits.length; i++){
			if (suits[0] == suits[i]){
				return true;
			}
		}
		return false;
	}
	public static int[] convertCharacters(int[] ints, char[] chars){
		char [] list = new char[]{'A','T','J','Q','K'};
		//inserting missing ints into ints array
		for(int i = 0; i<ints.length; i++){
			for (int j = 0; j<list.length; j++){
				if (chars[i] == list[j]){
					if (j ==0){
						ints[i] = 1;
					}
					else{
						ints[i] = 9 + j;
					}
				}
			}
		}
		return ints;
	}
	public static char convertFinalInt(int dealerchoice, int[] nums){
		char done = 0;
		char [] list = new char[]{'A','T','J','Q','K'};
		if(nums[dealerchoice] == 1){
			done = 'A';
		}
		else if(nums[dealerchoice] >9){
			done = list[nums[dealerchoice]- 9];
		}
		return done;
	}
	public static boolean isChar(int dealerchoice, int[] nums){
		if((nums[dealerchoice]>9)||(nums[dealerchoice] ==1)){
			return true;
		}
		else{
			return false;
		}
	}
}
