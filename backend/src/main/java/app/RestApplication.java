package app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import service.BudgetRestService;
import service.UserService;


@ApplicationPath("/rest")
public class RestApplication extends Application
{
	private Set<Class<?>> classes = new HashSet<>();
	private Set<Object> singletons = new HashSet<Object>();

	public RestApplication() {
		singletons.add(new BudgetRestService());
		classes.add( UserService.class);
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}
}