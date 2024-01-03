package proxy;

public class AccessValidator {
	public boolean validate(String userName){
		if(userName.equalsIgnoreCase("jason")){
			System.out.println(userName + "，登陆成功！");
			return true;
		}
		System.out.println(userName + "，登陆失败！");
		return false;
	}
}
