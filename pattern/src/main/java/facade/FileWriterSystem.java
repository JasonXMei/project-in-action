package facade;

import java.io.PrintWriter;

public class FileWriterSystem {
	public void write(String encryptStr,String desFilePath){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(desFilePath);
			pw.write(encryptStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
