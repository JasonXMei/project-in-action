package simplefactory;

public class HistogramChart implements Chart {

	public HistogramChart(){
		System.out.println("初始化柱状图！");
	}
	
	@Override
	public void display() {
		System.out.println("显示柱状图！");
	}

}
