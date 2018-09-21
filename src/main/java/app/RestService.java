package app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import service.BudgetRestService;
import service.UserService;


@ApplicationPath("/rest")
public class RestService extends Application
{
	private Set<Object> singletons = new HashSet<Object>();

	public RestService() {
		singletons.add(new BudgetRestService());
		singletons.add(new UserService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}