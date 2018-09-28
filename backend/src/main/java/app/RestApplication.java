package app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import filter.JWTTokenNeededFilter;
import service.BudgetRestService;
import service.UserService;


@ApplicationPath("/api")
public class RestApplication extends Application
{
	private Set<Class<?>> classes = new HashSet<>();
	private Set<Object> singletons = new HashSet<>();

	public RestApplication() {
		classes.add(UserService.class);
		classes.add(BudgetRestService.class);
		classes.add(JWTTokenNeededFilter.class);
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