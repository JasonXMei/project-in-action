package abstractfactory;

public class TestAbstractFactory {
	public static void main(String[] args) {
		 //使用抽象层定义  
        SkinFactory factory = (SkinFactory)XMLUtil.getBean(); 
        Button bt = factory.createButton();   
        TextField tf = factory.createTextField(); 
        bt.display();  
        tf.display();  
	}
}
