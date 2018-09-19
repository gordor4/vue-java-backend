package app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import service.BudgetRestService;


@ApplicationPath("/rest")
public class RestService extends Application
{
	private Set<Object> singletons = new HashSet<Object>();

	public RestService() {
		singletons.add(new BudgetRestService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}