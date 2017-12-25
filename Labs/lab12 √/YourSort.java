/* YourSort.java */

public class YourSort {

  public static void sort(int[] A) {
    int size = A.length();
	if (size < 30) {
		new Sort().insertionSort(A);
	} else {
		new Sort().quicksort(A);
	}
  }
}
