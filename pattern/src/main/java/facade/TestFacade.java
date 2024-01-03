package facade;

public class TestFacade {
	public static void main(String[] args) {
		 EncryptFacade ef = new EncryptFacade();  
         ef.FileEncrypt("src/facade/src.txt", "src/facade/des.txt");  
	}
}
