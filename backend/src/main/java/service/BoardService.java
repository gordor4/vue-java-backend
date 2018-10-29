package service;

import domain.Board;
import domain.User;
import filter.JWTTokenNeeded;
import filter.JWTTokenNeededFilter;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Stateless
@Path("/board")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class BoardService {

    @PersistenceContext(unitName = "postgres")
    private EntityManager entityManager;

    @POST
    @Path("/createBoard")
    @JWTTokenNeeded
    public Response createBoard(@Context HttpRequest request, Board board) {
        Integer id = (Integer) request.getAttribute(JWTTokenNeededFilter.USER);
        User user = entityManager.find(User.class, id);

        board.setOwner(user);
        board.setCreationDate(new Date());
        entityManager.persist(board);


        return Response.ok().build();
    }

}
