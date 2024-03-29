package prototype;

import java.io.*;

public class WeeklyLog implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String date;
	private String content;
	private Attachment attachment;

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return (this.name);
	}

	public String getDate() {
		return (this.date);
	}

	public String getContent() {
		return (this.content);
	}
	
	public Attachment getAttachment() {
		return this.attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
	// 克隆方法clone()，此处使用Java语言提供的克隆机制
	public WeeklyLog clone() {
		Object obj = null;
		try {
			obj = super.clone();
			return (WeeklyLog) obj;
		} catch (CloneNotSupportedException e) {
			System.out.println("不支持复制！");
			return null;
		}
	}

	// 使用序列化技术实现深克隆
	public WeeklyLog deepClone() throws IOException, ClassNotFoundException,OptionalDataException {
		// 将对象写入流中
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bao);
		oos.writeObject(this);

		// 将对象从流中取出
		ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return (WeeklyLog) ois.readObject();
	}
}
