package responsibilitychain;

public class HtmlFilter implements Filter {

	@Override
	public void doFilter(Request req, Response res, ChainFilter cf) {
		req.reqStr = req.reqStr.replaceAll("<", "[").replaceAll(">", "]")
					 + "htmlFilter of request!";
		cf.doFilter(req, res, cf);
		res.resStr += "htmlFilter of response!";
	}

}
