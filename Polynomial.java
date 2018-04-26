package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
			throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}

	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION

		Node sum=null; //LL that stores the sum
		Node front=null; //front of sum LL
		Node ptr1= poly1, ptr2=poly2; //to traverse through LLs
		int deg1=0, deg2=0; //refers to degree of each poly LL
		float co1=0, co2=0,finalco; // holds coefficients
		Node temp=null;

		if(poly1 == null || poly2 == null) //if one of the polynomials is null while the other isn't
		{
			if(poly1 == null)
			{
				return poly2;

			} else {

				return poly1;
			}
		}

		while(ptr1 != null || ptr2 != null) 
		{	
			try 
			{
				//refers to degree of each poly LL
				deg1=ptr1.term.degree; 
				deg2=ptr2.term.degree; 

				// holds coefficients
				co1=ptr1.term.coeff;
				co2=ptr2.term.coeff; 
			}
			catch (Exception NullPointer)
			{ 
				deg1=0;
				deg2=0;
				co1=0;
				co2=0;
			}

			if(ptr1 != null && ptr2 == null || (ptr2 != null && ptr1 == null)) //for diff sized polynomials
			{
				break;
			}  
			else if(co1 == 0 && co2 == 0) //both coefficients are 0
			{
				continue; //goes to next iteration bc you can't have a 0 coefficient

			}
			else if(deg1 < deg2|| (co1 != 0 && co2 == 0)) //degree 1 is greater or co2 is 0
			{
				temp=new Node(co1, deg1, null); //creates a new node with ptr1 data
				ptr1=ptr1.next;

			} 
			else if(deg2 < deg1 || (co1 == 0 && co2 != 0)) //degree 2 is greater or co1 is 0
			{ 
				temp=new Node(co2, deg2, null); //creates a new node with ptr2 data
				ptr2=ptr2.next;

			} 
			else if (deg1 == deg2) //both degrees are the same
			{
				finalco= co1+co2; //adding coefficients
				temp= new Node(finalco, deg1, null); //creates new node with sum as coefficient and degree as deg1

				//iterate both lists
				ptr1=ptr1.next;
				ptr2=ptr2.next;	
			} 

			//setting up front node as ref to front of sum LL
			if(front==null)
			{
				front=temp;
				sum=front;

			} else {

				sum.next=temp;
				sum=sum.next;	
			}	
		}

		if((ptr1 == null && ptr2 != null)) //if poly2 is longer
		{
			while(ptr2 != null)
			{
				temp= new Node(ptr2.term.coeff, ptr2.term.degree, null);

				if(front==null) //if poly1 is 0
				{
					front=temp;
					sum=front;

				} else {

					sum.next=temp;
					sum=sum.next;	
				}

				ptr2=ptr2.next;
			}
		}

		if((ptr2 == null && ptr1 != null)) //if poly1 is longer
		{
			while(ptr1 != null)
			{
				temp= new Node(ptr1.term.coeff, ptr1.term.degree, null);

				if(front==null) //if poly2 is 0
				{
					front=temp;
					sum=front;

				} else {

					sum.next=temp;
					sum=sum.next;	
				}

				ptr1=ptr1.next;
			}
		}
		
		if(zero(front) == true) //checking for 0 polynomial
		{
			front = null;
			
		} else {
			
			front=delZero(front);//to delete zero coefficients

		}
		
		return front;
	}

	private static Node delZero(Node front) //returns LL with no zeros 
	{
		Node ptr= front, front1= null, newLL= null;;

		while(ptr != null)
		{
			if(ptr.term.coeff != 0)
			{
				Node temp= new Node(ptr.term.coeff, ptr.term.degree, null);
				
				if(front1 == null)
				{
					front1=temp;
					newLL=front1;
					
				} else {
					
					newLL.next=temp;
					newLL=newLL.next;
				}
			}
			
			ptr=ptr.next;
		}
		return front1;
	}
	
	private static boolean zero(Node front) //checks if polynomial is made up of zeros
	{
		boolean zero=false;
		
		for(Node ptr=front; ptr != null; ptr=ptr.next)
		{
			if(ptr.term.coeff == 0)
			{
				zero = true;
				
			} else {
				
				zero=false;
				break;
			}
		}
		
		return zero;
	}

	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION

		Node product=null; //LL that stores the sum
		Node front=null; //front of sum LL
		Node ptr1= poly1, ptr2=poly2; //to traverse through LLs
		int deg1=0, deg2=0,finaldeg; //refers to degree of each poly LL
		float co1=0, co2=0,finalco; // holds coefficients
		Node temp=null;

		while(ptr1 != null)
		{
			//to avoid null pointer exception
			try
			{
				//refers to degree of each poly LL
				deg1=ptr1.term.degree;
				// holds coefficients
				co1=ptr1.term.coeff;
			}
			catch (Exception NullPointer)
			{
				deg1=0;
				co1=0;
			}

			ptr2=poly2;//to restart ptr2 at beginning of second polynomial

			while(ptr2 != null)
			{
				//to avoid null pointer exception
				try
				{
					deg2=ptr2.term.degree; 	
					co2=ptr2.term.coeff; 
				}
				catch (Exception NullPointer)
				{
					deg2=0;
					co2=0;
				}

				finalco= co1*co2; //getting the product of the coefficients
				finaldeg= deg1 + deg2; //getting the sum of degrees
				temp= new Node(finalco, finaldeg, null); //creating new node


				if(inList(temp, front) == true)//to combine terms of the same degree
				{
					ptr2=ptr2.next;
					continue;
				}

				//to avoid null pointer exception
				if(front == null)
				{
					front=temp;
					product=front;

				} else {

					if(inOrder(temp, front) == false) //to reorder terms in ascending order of degree
					{
						product.next=temp;
						product=product.next;
					}	
				}

				ptr2=ptr2.next;
			}

			ptr1=ptr1.next;
		}


		return front;
	}

	private static boolean inList(Node temp, Node product) //checks to see if there is a term with temp's degree in it and combines them together
	{
		for(Node ptr= product; ptr != null; ptr=ptr.next)
		{
			if(ptr.term.degree == temp.term.degree) //to add the terms up and put it in product LL
			{
				ptr.term.coeff+= temp.term.coeff;
				//System.out.println("for degree: " + ptr.term.degree + " this is the coeff: " + ptr.term.coeff);

				return true;	
			}
		}
		
		return false;
	}

	private static boolean inOrder(Node n, Node front) //organizes the degrees in ascending order
	{
		Node least=null;
		Node prev=null;
		Node temp=null;
		Node ptr=front;

		while(ptr != null) //traverses through front
		{
			if(ptr.term.degree > n.term.degree && n.term.degree > prev.term.degree) //if n is less than ptr's degree but greater than prev's degree 
			{
				if(inlist(n, front) == true) //to delete n from LL to avoid duplicates
				{
					delete(n,front);
				}

				least= new Node(n.term.coeff, n.term.degree, null);

				temp= prev.next;
				prev.next=least;
				least.next=temp;

				return true;

			}

			prev=ptr;
			ptr=ptr.next;

		}

		return false;
	}

	private static void delete(Node n, Node front) //deletes node n from LL
	{
		Node prev=null;
		Node ptr=front;

		while(ptr != null)
		{
			if(ptr.term == n.term)
			{
				prev.next=ptr.next;

				//System.out.println("Node deleted is: " + n.term + " and prev.next is: " + prev.next.term);
			}

			prev=ptr;
			ptr=ptr.next;
		}
	}

	private static boolean inlist(Node n, Node front) //checks if term is in LL
	{
		for(Node ptr= front; ptr != null; ptr = ptr.next)
		{
			if(ptr.term == n.term)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION

		float val=0;

		for(Node i=poly; i != null; i=i.next)
		{
			int termdeg= (int)Math.pow(x, i.term.degree);
			float termprod= i.term.coeff * termdeg;
			val+= termprod;

		}
		return val;
	}

	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 

		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
				current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
