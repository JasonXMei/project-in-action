package bridge;

public class TestBridge {
	public static void main(String[] args) {
		Image image = (Image)XMLUtil.getBean("image");
        ImageImp imp = (ImageImp)XMLUtil.getBean("os");  
        image.setImageImp(imp);  
        image.parseFile("小龙女"); 
	}
}	
