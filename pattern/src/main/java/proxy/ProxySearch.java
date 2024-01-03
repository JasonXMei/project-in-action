package proxy;

public class ProxySearch implements Search {

	 private RealSearch searcher = new RealSearch(); //维持一个对真实主题的引用  
     private AccessValidator validator;  
     private Logger logger;  
	
	@Override
	public void doSearch(String userName, String keyWord) {
		boolean result = validate(userName);
		if(result){
			searcher.doSearch(userName, keyWord);
			Log(userName);
		}
	}

	//创建访问验证对象并调用其Validate()方法实现身份验证  
    public boolean validate(String userName)  
    {  
        validator = new AccessValidator();  
        return validator.validate(userName);  
    }  

    //创建日志记录对象并调用其Log()方法实现日志记录  
    public void Log(String userName)  
    {  
        logger = new Logger();  
        logger.writeLog(userName); 
    }  
}
