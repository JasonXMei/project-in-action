package singleton;

/**
 * 懒汉式单例模式
 * 延迟加载(Lazy Load)技术
 * @author abo
 *
 */
public class LazySingleton {
	
	/*
	 * 如果使用双重检查锁定来实现懒汉式单例类，需要在静态成员变量之前增加修饰符volatile，
	 * 被volatile修饰的成员变量可以确保多个线程都能够正确处理
	 * 在Java规范中，volatile修饰的成员变量在每次被线程访问时，都强迫从共享内存中重读该成员变量的值。
	 * 而且，当成员变量发生变化时，强迫线程将变化值回写到共享内存。这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个值。
	 * 此外，由于使用volatile关键字屏蔽掉了虚拟机中一些必要的代码优化，所以在效率上比较低，因此需要慎重使用。
	 */
	private static volatile LazySingleton instance = null;
	
	private LazySingleton(){
	}
	
	//双重检查锁定(Double-Check Locking),防止多线程情况下出现多个实例对象
	public static LazySingleton getInstance(){
		if(instance == null){
			synchronized (LazySingleton.class) {
				if(instance == null){
					instance = new LazySingleton();
				}
			}
		}
		return instance;
	}
}
