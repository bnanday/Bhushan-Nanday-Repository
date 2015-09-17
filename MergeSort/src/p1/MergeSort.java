package p1;

public class MergeSort {
	
	public static void sort(int[] a ){
		
		int n = a.length;
		
		if(n < 2){
			
			return;
		}
				
				
				
		int mid = n/2;
		
		int[] left = new int[mid];
		int[] right = new int[n-mid];
		
		for(int i=0; i<mid; i++){
			
			left[i] = a[i];
		}
		
		int count = 0;
		for(int i=mid; i<n; i++){
					
			right[count] = a[i];
			count++;
		}
		
		sort(right);
		sort(left);
		merge(left,right,a);
	}
	
	
	public static void merge(int[] left, int[] right, int[] a){
		
		int i=0, j=0, k=0;
		int nl = left.length;
		int nr = right.length;
		int nk = a.length;
		
		
		while(i<nl && j<nr){
			
			if(left[i] <= right[j]){
				
				a[k] = left[i];
				i++;
			}else{
				
				a[k] = right[j];
				j++;
			}
			
			k++;
			
		}
		
		while(i < nl){
			
			a[k] = left[i];
			i++;
			k++;
		}
		
		while(j < nr){
			
			a[k] = right[j];
			j++;
			k++;
		}
		
	}

}
