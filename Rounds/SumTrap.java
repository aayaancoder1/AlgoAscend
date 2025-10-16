// Write a program that reads a positive integer n and prints the sum of all numbers from 1 to n 
// that are not divisible by 3 or 5.
// Input: 10  
// Output: 22  
// (Because 1+2+4+7+8 = 22)

// get number
// find all numbers from 1 to n not div by 3 or 5 and add them to sum

import java.util.Scanner;

public class SumTrap
{
    public static void main (String arg[]){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int sum = 0;
        for(int i = 0; i <= n; i++){
            if ((i % 3 != 0) && (i % 5 != 0)){
                sum += i;
            }
        }
        System.out.println("Sum Trap of " + n + " is " + sum);
    }
}