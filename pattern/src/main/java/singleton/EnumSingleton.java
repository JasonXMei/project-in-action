package singleton;

public enum EnumSingleton {
	Singleton;
	private Resource resource;
	private EnumSingleton(){
		resource = new Resource();
	}
	public Resource getInstance(){
		return resource;
	}
}
class Resource{
}
