package adapter;

public class ClassAdapter extends QuickSort implements ScoreOperation{

	@Override
	public int[] sort(int[] array) {
		return super.quickSort(array);
	}

	@Override
	public int search(int[] array, int key) {
		return 0;
	}

}
