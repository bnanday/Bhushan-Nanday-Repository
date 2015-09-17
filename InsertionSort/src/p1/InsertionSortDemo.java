package p1;

public class InsertionSortDemo {

	public static void main(String[] args) {
		
		int[] a = {4,2,6,4,7,9,8,1,2};
		
		a = InsertionSort.sort(a);
		
		for(int i=0; i<a.length; i++){
			
			System.out.println(a[i]);
		}

	}

}
