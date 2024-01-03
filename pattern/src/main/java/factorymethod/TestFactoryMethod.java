package factorymethod;

public class TestFactoryMethod {
	public static void main(String[] args) {
		Logger fileLogger = new FileFactory().getLogger();
		fileLogger.writeLog();
		Logger dbLogger = new DatabaseFactory().getLogger();
		dbLogger.writeLog();
	}
}
