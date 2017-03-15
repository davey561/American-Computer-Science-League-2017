package ACSL;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author student Davey Morse
 * ACSL ascending 2017, #2
 *
 */
public class ACSL2_ascending17_c18dm2 {
	public static void main(String[] args) {
		//declarations
		Scanner scan = new Scanner(System.in); //Scanner
		int previous = 0; //previous number, temporary storage
		int temp = 0; //another temporary number for use
		String subst = new String(); //Substring program is currently examining
		boolean left = true; //
		boolean stop = false;
		int le;
		int ri;

		while(true){
			String input = scan.nextLine();
			ri = input.length()+1;
			le = -1;
			stop = false;
			left = true;
			subst = new String();
			temp = 0;
			previous = 0;

			big: while (stop == false){
				// left-to-right
				if(left == true){
					stop = true;
					if (le+1 != ri){
						le++;
					}
					else{
						break big;
					}
					for(int i = le+1; i<ri;i++){
						temp = 0;
						subst = input.substring(le, i);
						for(int j = 0; j<subst.length(); j++){
							temp += Character.getNumericValue(subst.charAt(j))*Math.pow(10, subst.length()-j-1);
						}
						if (temp>previous){
							le+= subst.length()-1;
							previous = temp;
							System.out.print(previous + " ");
							stop = false;
							left = false;
							continue big;
						}
					}
				}
				// right-to-left
				else if (left == false){
					stop = true;
					if (le+1 != ri){
						ri--;
					}
					else{
						break big;
					}
					for(int i = ri-1; i>le;i--){
						temp = 0;
						subst = input.substring(i, ri);
						for(int j = 0; j<subst.length(); j++){
							temp += Character.getNumericValue(subst.charAt(subst.length()-j-1))*Math.pow(10, subst.length()-j-1);
						}
						if (temp>previous){
							ri-= subst.length()-1;
							previous = temp;
							System.out.print(previous + " ");
							stop = false;
							left = true;
							continue big;
						
						}
					}
				}
			}
			System.out.println();
		}
	}
}
