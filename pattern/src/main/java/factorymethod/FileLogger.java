package factorymethod;

public class FileLogger implements Logger{

	@Override
	public void writeLog() {
		System.out.println("文件记录！");
	}

}
