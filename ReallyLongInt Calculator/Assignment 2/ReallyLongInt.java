// CS 0445 Spring 2024
// This is a partial implementation of the ReallyLongInt class.  You need to
// complete the implementations of the remaining methods.  Also, for this class
// to work, you must complete the implementation of the LinkedListPlus class.
// See additional comments below.
//Jacob Moretz
public class ReallyLongInt 	extends LinkedListPlus<Integer> 
							implements Comparable<ReallyLongInt>
{
	private ReallyLongInt()
	{
		super();
	}

	// Data is stored with the LEAST significant digit first in the list.  This is
	// done by adding all digits at the front of the list, which reverses the order
	// of the original string.  Note that because the list is doubly-linked and 
	// circular, we could have just as easily put the most significant digit first.
	// You will find that for some operations you will want to access the number
	// from least significant to most significant, while in others you will want it
	// the other way around.  A doubly-linked list makes this access fairly
	// straightforward in either direction.
	public ReallyLongInt(String s)
	{
		super();
		char c;
		int digit = -1;
		// Iterate through the String, getting each character and converting it into
		// an int.  Then make an Integer and add at the front of the list.  Note that
		// the add() method (from A2LList) does not need to traverse the list since
		// it is adding in position 1.  Note also the the author's linked list
		// uses index 1 for the front of the list.
		for (int i = 0; i < s.length(); i++)
		{
			c = s.charAt(i);
			if (('0' <= c) && (c <= '9'))
			{
				digit = c - '0';
				// Do not add leading 0s
				if (!(digit == 0 && this.getLength() == 0)) 
					this.add(1, Integer.valueOf(digit));
			}
			else throw new NumberFormatException("Illegal digit " + c);
		}
		// If number is all 0s, add a single 0 to represent it
		if (digit == 0 && this.getLength() == 0)
			this.add(1, Integer.valueOf(digit));
	}

	// Copy constructor can just call super()
	public ReallyLongInt(ReallyLongInt rightOp)
	{
		super(rightOp);
	}
	
	// Constructor with a long argument.  You MUST create the ReallyLongInt
	// digits by parsing the long argument directly -- you cannot convert to a String
	// and call the constructor above.  As a hint consider the / and % operators to
	// extract digits from the long value.
	public ReallyLongInt(long X)
	{
		super();
		if (X == 0)
		{
			this.add(1, 0);
			return;
		}
		while (X > 0)
		{
			long value = X % 10;
			this.add((int) value);
			X = X / 10;
		}
		
	}
	
	// Method to put digits of number into a String.  Note that toString()
	// has already been written for LinkedListPlus, but you need to
	// override it to show the numbers in the way they should appear.
	//go backward through list 
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		Node curr = firstNode.getPrevNode();
		int i = 0;
		while (i < this.getLength())
		{
			b.append(curr.data.toString());
			curr = curr.getPrevNode();
			i++;
		}
		return b.toString();
	}

	// See notes in the Assignment sheet for the methods below.  Be sure to
	// handle the (many) special cases.  Some of these are demonstrated in the
	// RLITest.java program.

	// Return new ReallyLongInt which is sum of current and argument
	//use add() to travse the list systematically using references.
	//start a beginning of each RLI and traverse one at a time while adding
	public ReallyLongInt add(ReallyLongInt rightOp)
	{
		// Determine the maximum size required for the result
		int maxSize = Math.max(this.getLength(), rightOp.getLength()) + 1;
		ReallyLongInt result = new ReallyLongInt();
	
		int carry = 0;
		for (int i = 0; i < maxSize; i++) {
			int digitSum = carry;
			if (i < this.getLength())
				digitSum += this.get(i);
			if (i < rightOp.getLength())
				digitSum += rightOp.get(i);
	
			result.add(digitSum % 10);
			carry = digitSum / 10;
		}
		return result;
	}
	
	// Return new ReallyLongInt which is difference of current and argument
	public ReallyLongInt subtract(ReallyLongInt rightOp) throws ArithmeticException
	{	
		if (this.compareTo(rightOp) < 0) 
		{
			throw new ArithmeticException("Subtraction results in a negative number");
		}
		int n1 = 0;
		int n2 = 0;
		int carry = 0;
		int num = 0;
		ReallyLongInt result = new ReallyLongInt();
		Node next1 = this.firstNode;
		Node next2 = rightOp.firstNode;
		for (int i = 0; i < this.getLength(); i++)
		{
			n1 = next1.getData();
			if (rightOp.getLength() > i)
			{
				n2 = next2.getData();
			}
			else
			{
				n2 = 0;
			}
			num = n1 - n2 - carry;
			if (num < 0)
			{
				carry = 1;
				num = num + 10;
			}
			else
			{
				carry = 0;
			}
			result.add(num);
			next1 = next1.getNextNode();
			if (rightOp.getLength() > i)
				next2 = next2.getNextNode();
		}

		while (result.getLength() > 1 && result.firstNode.getPrevNode().getData() == 0)
		{
			result.rightShift(1);
		}
		return result;
    }
	// Return new ReallyLongInt which is product of current and argument
	public ReallyLongInt multiply(ReallyLongInt rightOp)
	{
		ReallyLongInt result = new ReallyLongInt(0);

		for (int i = 0; i < this.getLength(); i++)
		{
			for (int j = 0; j < rightOp.getLength(); j++)
			{
				long product = this.get(i) * rightOp.get(j);
				for (int k = 0; k < i + j; k++)
				{
					product *= 10;
				}
				result = result.add(new ReallyLongInt(product));
			}
		}
		while (result.getLength() > 1 && result.firstNode.getPrevNode().getData() == 0)
		{
			result.rightShift(1);
		}
		return result;
	}
	
	// Return -1 if current ReallyLongInt is less than rOp
	// Return 0 if current ReallyLongInt is equal to rOp
	// Return 1 if current ReallyLongInt is greater than rOp
	public int compareTo(ReallyLongInt rOp)
	{
		int thisLength = this.getLength();
		int rOpLength = rOp.getLength();

		if (thisLength < rOpLength)
		{
			return -1;
		}
		else if (thisLength > rOpLength)
		{
			return 1;
		}
		Node thisNode = this.firstNode.getNextNode();
		Node rOpNode = rOp.firstNode.getNextNode();
		while (thisNode != this.firstNode && rOpNode != rOp.firstNode)
		{
			int thisDig = thisNode.getData();
			int rOpDig = rOpNode.getData();

			if (thisDig < rOpDig)
			{
				return -1;
			}
			else if (thisDig > rOpDig)
			{
				return 1;
			}
			thisNode = thisNode.getNextNode();
			rOpNode = rOpNode.getNextNode();
		}
		int thislastDig = thisNode.getData();
		int rOplastDig = rOpNode.getData();
		if (thislastDig < rOplastDig)
		{
			return -1;
		}
		else if (thislastDig > rOplastDig)
		{
			return 1;
		}
		return 0;

	}
	// Is current ReallyLongInt equal to rightOp?  Note that the argument
	// in this case is Object rather than ReallyLongInt.  It is written
	// this way to correctly override the equals() method defined in the
	// Object class.
	public boolean equals(Object rightOp)
	{
		ReallyLongInt ans = (ReallyLongInt) rightOp;
		int compared = this.compareTo(ans);
		if (compared == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public Integer get(int index) {
		if (index < 0 || index >= getLength()) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		}
		if (index == 0) return firstNode.getData();
		Node current = firstNode;
		for (int i = 0; i < index; i++) {
			current = current.getNextNode();
		}
		return current.getData();
	}
}
