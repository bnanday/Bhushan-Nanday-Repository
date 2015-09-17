package p1;

public class LinkedListDemo {

	public static void main(String[] args) {
		

		LinkedList list = new LinkedList();
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(40);
		
		System.out.println("Printing List in forward direction");
		list.print();
		System.out.println("Reversing the list");
		list.reverseList();
		list.print();
		System.out.println("Removing 30");
		list.remove(30);
		list.print();

	}

}
