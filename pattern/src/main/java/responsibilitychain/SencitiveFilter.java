package responsibilitychain;

public class SencitiveFilter implements Filter {

	@Override
	public void doFilter(Request req, Response res, ChainFilter cf) {
		req.reqStr = req.reqStr.replaceAll("敏感", "")
				 	 + "sencitiveFilter of request!";
		cf.doFilter(req, res, cf);
		res.resStr += "sencitiveFilter of response!";
	}

}
