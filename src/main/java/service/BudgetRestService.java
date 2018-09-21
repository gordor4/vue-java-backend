package service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import anotation.JWTTokenNeeded;
import bean.BudgetBean;
import domain.Payment;

@Path("/budget")
public class BudgetRestService
{

	@Inject
	private BudgetBean budget;

	@GET
	@Path("/get")
	@JWTTokenNeeded
	@Produces({ javax.ws.rs.core.MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Payment> getUserPayment() {
		return null;
	}

//	@POST
//	@Path("/addmovie")
//	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//	public Response addMovie(Payment budget) {
//		if (null != inventory.get(budget.getImdbId())) {
//			return Response
//					.status(Response.Status.NOT_MODIFIED)
//					.entity("Payment is Already in the database.").build();
//		}
//
//		inventory.put(budget.getImdbId(), budget);
//		return Response.status(Response.Status.CREATED).build();
//	}
}
