package simplefactory;

public class LineChart implements Chart {

	public LineChart(){
		System.out.println("初始化折线图！");
	}
	
	@Override
	public void display() {
		System.out.println("显示折线图！");
	}

}
