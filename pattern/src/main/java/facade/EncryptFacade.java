package facade;

public class EncryptFacade {
	FileReaderSystem reader;
	CipherMachine cipher;
	FileWriterSystem writer;
	
	public EncryptFacade(){
		reader = new FileReaderSystem();
		cipher = new CipherMachine();
		writer = new FileWriterSystem();
	}
	
	public void FileEncrypt(String srcFilePath,String desFilePath){
		String plainText = reader.Read(srcFilePath);
		String encryptStr = cipher.Encrypt(plainText);
		writer.write(encryptStr, desFilePath);
	}
}
