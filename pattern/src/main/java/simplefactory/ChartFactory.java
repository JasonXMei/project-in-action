package simplefactory;

public class ChartFactory {

	public static Chart getChart(String chartType){
		/*
		//不灵活
		Chart chart = null;
		if(chartType.equalsIgnoreCase("histogram")){
			chart = new HistogramChart();
			System.out.println("创建柱状图！");
		}else if(chartType.equalsIgnoreCase("pie")){
			chart = new PieChart();
			System.out.println("创建饼状图！");
		}else if(chartType.equalsIgnoreCase("line")){
			chart = new LineChart();
			System.out.println("创建折线图！");
		}else{
			System.out.println("输入图像类型有误，请检查！");
		}
		return chart;
		*/
		//反射，需要传入类的全名
		try {
			Class<?> chartClass = Class.forName(chartType);
			return (Chart)chartClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
