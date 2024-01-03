package simplefactory;

public class TestSimpleFactory {

	public static void main(String[] args) {
		/**
		 * 解耦图形类路径：
		 * 1、写一个接口，里面定义常量
		 * 2、properties（使用java自带的Properties类解析）
		 * 3、xml（使用dom4j解析）
		 */
		Chart lineChart = ChartFactory.getChart("simplefactory.LineChart");
		lineChart.display();
		Chart pieChart = ChartFactory.getChart("simplefactory.PieChart");
		pieChart.display();
	}

}
