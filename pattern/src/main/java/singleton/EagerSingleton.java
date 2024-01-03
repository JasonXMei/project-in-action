package singleton;

/**
 * 饿汉式单例模式
 * @author abo
 */
public class EagerSingleton {
	
	private static EagerSingleton instance = new EagerSingleton();
	
	private EagerSingleton(){
	}
	
	public static EagerSingleton getInstance(){
		return instance;
	}
}
