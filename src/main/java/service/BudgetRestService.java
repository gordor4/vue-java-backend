package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import domain.Consumption;

@Path("/movies")
public class BudgetRestService
{

	//переделать на JPA?


	@GET
	@Path("/get")
	@Produces({ javax.ws.rs.core.MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Consumption movieByImdbId(@QueryParam("imdbId") String imdbId) {
		return new Consumption(100.0, "Трата");
	}

//	@POST
//	@Path("/addmovie")
//	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//	public Response addMovie(Consumption budget) {
//		if (null != inventory.get(budget.getImdbId())) {
//			return Response
//					.status(Response.Status.NOT_MODIFIED)
//					.entity("Consumption is Already in the database.").build();
//		}
//
//		inventory.put(budget.getImdbId(), budget);
//		return Response.status(Response.Status.CREATED).build();
//	}
}
