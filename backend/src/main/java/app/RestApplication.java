package app;

import filter.JWTTokenNeededFilter;
import service.BoardService;
import service.UserService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/api")
public class RestApplication extends Application
{
	private Set<Class<?>> classes = new HashSet<>();
	private Set<Object> singletons = new HashSet<>();

	public RestApplication() {
		classes.add(UserService.class);
		classes.add(BoardService.class);
		classes.add(JWTTokenNeededFilter.class);
		classes.add(AppConfiguration.class);
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