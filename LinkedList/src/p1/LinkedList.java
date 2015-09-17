package p1;

public class LinkedList {
	
	private Node head;
	
	public LinkedList(){
		
		
	}
	
	public void add(int n){
		
		Node nodeToAdd = new Node(n);
		
		if(head == null){
			
			head = nodeToAdd;
		}else{
			
			Node temp = head;
			
			while(temp.getNext() != null){
				
				temp = temp.getNext();
			}
			
			temp.setNext(nodeToAdd);
		}
	}
	
	public void print(){
		
		Node temp = this.head;
		
		while(temp != null){
			
			System.out.println(temp.getData());
			temp = temp.getNext();
			
		}
	}
	
	public void reversePrint(){
		
		printReverse(this.head);
	}
	
	public void printReverse(Node n){
		
		if(n.getNext() == null){
			
			System.out.println(n.getData());
			return;
		}else{
			
			printReverse(n.getNext());
			System.out.println(n.getData());
		}
	}
	
	public void reverseList(){
		
		Node temp = this.head;
		reverse(temp);
		
	}
	
	public void reverse(Node n){
		
		if(n.getNext() == null){
			
			this.head = n;
			return;
		}else{
			
			reverse(n.getNext());
			Node q = n.getNext();
			q.setNext(n);
			n.setNext(null);
		}
		
	}
	
	public void remove(int n){
		
		if(head.getData() == n){
			
			head = head.getNext();
			return;
		}
		
		Node prev = null;
		Node curr = this.head;
		
		while(curr.getData() != n){
			
			prev = curr;
			curr = curr.getNext();
			
		}
		
		prev.setNext(curr.getNext());
	}

}
