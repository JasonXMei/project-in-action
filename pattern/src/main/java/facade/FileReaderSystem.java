package facade;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileReaderSystem {
	public String Read(String fileNameSrc) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileNameSrc));
			String content = null;
			while((content = reader.readLine()) != null){
				sb.append(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader != null){
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
