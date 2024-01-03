package factorymethod;

public class FileFactory implements Factory {

	@Override
	public Logger getLogger() {
		Logger logger = new FileLogger();
		return logger;
	}

}
