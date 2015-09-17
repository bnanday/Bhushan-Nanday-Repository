package p1;

public class MergeSortDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A = {5,2,7,8,300,1,0,9,4,6};
		
		MergeSort.sort(A);
		
		for(int i=0; i<A.length; i++){
			
			System.out.println(A[i]);
		}
	}

}
