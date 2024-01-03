package proxy;

public class TestProxy {
	public static void main(String[] args) {
		Search search = new ProxySearch();
		search.doSearch("jason", "love java");
	}
}
