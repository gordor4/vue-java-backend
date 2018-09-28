package service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bean.BudgetBean;
import domain.Payment;
import filter.JWTTokenNeeded;

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
		Payment test = new Payment();
		test.setAmount(321.0);
		return new ArrayList<>();
	}

}
