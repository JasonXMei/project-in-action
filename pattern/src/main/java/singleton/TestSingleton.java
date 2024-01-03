package singleton;

public class TestSingleton {
	public static void main(String[] args) {
		EagerSingleton e1 = EagerSingleton.getInstance();
		EagerSingleton e2 = EagerSingleton.getInstance();
		System.out.println(e1 == e2);
		
		LazySingleton lazy1 = LazySingleton.getInstance();
		LazySingleton lazy2 = LazySingleton.getInstance();
		System.out.println(lazy1 == lazy2);
		
		IoDHSingleton i1 = IoDHSingleton.getInstance();
		IoDHSingleton i2 = IoDHSingleton.getInstance();
		System.out.println(i1 == i2);
		
		Resource r1 = EnumSingleton.Singleton.getInstance();
		Resource r2 = EnumSingleton.Singleton.getInstance();
		System.out.println(r1 == r2);
	}

}
