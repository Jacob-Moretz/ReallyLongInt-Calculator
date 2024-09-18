// CS 0445 Spring 2024
// In Class Exercise 3
// Testing the leftShift() method in the LinkedListPlus<T> class

// This program should run without changes with your LinkedListPlus class.  This
// program requires the following files:
//		A2LList.java
//		LinkedListPlus.java

public class InClass3
{
	static String [] S = {"Worf", "Picard", "Data", "Crusher", "Troi", "Riker"};
	public static void main(String [] args)
	{
		LinkedListPlus<String> L = new LinkedListPlus<String>();
		
		for (int i = 0; i < S.length; i++)
		{
			L.add(S[i]);
		}
		
		System.out.println("L is: " + L.toString());
		
		L.leftShift(3);
		
		System.out.println("L is: " + L.toString());
		
		// Make sure list is still correct by trying some other ops
		
		L.add(1, new String("Q"));
		L.remove(2);
		L.add(new String("LaForge"));
		
		System.out.println("L is: " + L.toString());
		
		L.leftShift(-4);  // should not change the list
		
		System.out.println("L is: " + L.toString());
		
		L.leftShift(3);
		
		System.out.println("L is: " + L.toString());
		
		L.leftShift(2);  // list should be empty after this
		
		System.out.println("L is: " + L.toString());
	}
}
		