package adapter;

public class TestClassAdapter {
	public static void main(String[] args) {
		ClassAdapter ca = new ClassAdapter();
		int scores[] = { 84, 76, 50, 69, 90, 91, 88, 96 }; // 定义成绩数组

		System.out.println("-----------------成绩排序结果-----------------");
		ca.sort(scores);
		// 遍历输出成绩
		for (int i : scores) {
			System.out.print(i + " ");
		}
	}
}
