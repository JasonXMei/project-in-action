package singleton;

/**
 * IoDH技术实现单例模式
 * @author abo
 */
public class IoDHSingleton {
	private IoDHSingleton(){
	}
	
	private static class InnerClass{
		//将变量声明为final类型，确保线程安全
		private final static IoDHSingleton instance = new IoDHSingleton();
	}
	
	public static IoDHSingleton getInstance(){
		return InnerClass.instance;
	}
}
