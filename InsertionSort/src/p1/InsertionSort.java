package p1;

public class InsertionSort {
	
	public static int[] sort(int[] a){
		
		int n = a.length;
		int value,hole;
		
		for(int i=1; i<a.length; i++){
			
			value = a[i];
			hole = i;
			
			while(hole > 0 && a[hole-1] > value){
				a[hole] = a[hole-1];
				hole --;
				
			}
			
			a[hole] = value;
		
		}
		
		return a;
	}

}
