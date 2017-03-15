package ACSL;

import java.util.Random;

public class GenerateTestDataAgram {
	public static void main(String[] args) {
		Random ran = new Random();
		for (int i = 0; i<6; i++){
			int value = ran.nextInt(14);
			char val = 0;
			if((value >9)||(value<1)){
				if (value ==10){
					val = 'T';
				}
				if (value ==11){
					val = 'J';
				}
				if (value ==12){
					val = 'Q';
				}
				if (value ==13){
					val = 'K';
				}
				if (value ==1){
					val = 'A';
				}
			}
			else{
				val = (char) value;
			}
			int suit = ran.nextInt(4);
			char su = 0;
			if (suit == 0){
				su = 'D';
			}
			if (suit == 1){
				su = 'C';
			}
			if (suit == 2){
				su = 'S';
			}
			if (suit == 3){
				su = 'H';
			}
			String ending = new String();
			if (i == 5){
				ending = "";
			}
			else{
				ending = ", ";
			}
			System.out.print(val + "" + su + ending);
		}
	}
}
