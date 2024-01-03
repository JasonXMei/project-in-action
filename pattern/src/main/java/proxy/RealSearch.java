package proxy;

public class RealSearch implements Search {

	@Override
	public void doSearch(String userName, String keyWord) {
		System.out.println(userName+"，使用关键词:"+keyWord+"查询信息！");
	}

}
