package responsibilitychain;

import java.util.ArrayList;
import java.util.List;

public class ChainFilter implements Filter {
	
	List<Filter> filters = new ArrayList<Filter>();
	int index = 0;
	
	public ChainFilter addFilter(Filter filter){
		this.filters.add(filter);
		return this;
	}
	
	public void removeFilter(Filter filter){
		filters.remove(filter);
	}

	@Override
	public void doFilter(Request req, Response res, ChainFilter cf) {
		if(index == filters.size()) return;
		Filter f = filters.get(index);
		index++;
		f.doFilter(req, res, cf);
	}
	
}
