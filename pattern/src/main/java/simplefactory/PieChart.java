package simplefactory;

public class PieChart implements Chart {

	public PieChart(){
		System.out.println("初始化饼状图！");
	}
	
	@Override
	public void display() {
		System.out.println("显示饼状图！");
	}

}
