// Write a program that prints the numbers from 1 to 50. But for multiples of 3, 
// print "Fizz", for multiples of 5, print "Buzz", and for multiples of both,
// print "FizzBuzz".

class FizzBuzz{
    public static void main(String arg[]){
        for (int i = 1; i <= 50; i++){
            if (i % 3 == 0 && i % 5 == 0)
                System.out.println("FizzBuzz");
            else if (i % 3 == 0)
                System.out.println("Fizz");
            else if (i % 5 == 0)
                System.out.println("Buzz");
            else
                System.out.println(i);
        }
    }
}