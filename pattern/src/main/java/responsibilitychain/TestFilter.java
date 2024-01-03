package responsibilitychain;

public class TestFilter {

	public static void main(String[] args) {
		String msg = "<script><html>你好，敏感，我很敏感！";
		ChainFilter cf = new ChainFilter();
		cf.addFilter(new HtmlFilter()).addFilter(new SencitiveFilter());
		Request req = new Request();
		req.setReqStr(msg);
		Response res = new Response();
		res.setResStr("Response:");
		cf.doFilter(req, res, cf);
		System.out.println(req.getReqStr());
		System.out.println(res.getResStr());
	}

}
