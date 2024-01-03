package adapter;

public class QuickSort {
	public int[] quickSort(int array[]) {
		sort(array, 0, array.length - 1);
		return array;
	}

	public void sort(int array[], int startIndex, int endIndex) {
		int temp = 0;
		if (startIndex < endIndex) {
			temp = partition(array, startIndex, endIndex);
			sort(array, startIndex, temp - 1);
			sort(array, temp + 1, endIndex);
		}
	}

	public int partition(int[] array, int startIndex, int endIndex) {
		int x = array[endIndex];
		int j = startIndex - 1;
		for (int i = startIndex; i <= endIndex - 1; i++) {
			if (array[i] <= x) {
				j++;
				swap(array, j, i);
			}
		}
		swap(array, j + 1, endIndex);
		return j + 1;
	}

	public void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
}
