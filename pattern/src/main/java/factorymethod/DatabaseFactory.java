package factorymethod;

public class DatabaseFactory implements Factory {

	@Override
	public Logger getLogger() {
		Logger logger = new DatabaseLogger();
		return logger;
	}

}
