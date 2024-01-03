package responsibilitychain;

public interface Filter {
	public void doFilter(Request req,Response res,ChainFilter cf);
}
