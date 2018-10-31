package service;

import bean.UserBean;
import domain.Board;
import domain.BoardParam;
import domain.User;
import filter.JWTTokenNeeded;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Stateless
@Path("/board")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class BoardService {

    @PersistenceContext(unitName = "postgres")
    private EntityManager entityManager;

    @Inject
    private UserBean userBean;

    @POST
    @Path("/createBoard")
    @JWTTokenNeeded
    public Response createBoard(@Context HttpRequest request, BoardParam boardParam) {
        User user = userBean.findUser(request);

        Board board = new Board();
        board.setBoardName(boardParam.getBoardName());
        board.setOwnerId(user.getId());
        board.setCreationDate(new Date());
        board.setBoardDescription(boardParam.getBoardDescription());
        board.setPublic(Boolean.valueOf(boardParam.getIsPublic()));
        board.setPublicEdit(Boolean.valueOf(boardParam.getIsPublicEdit()));
        entityManager.persist(board);

        return Response.ok().build();
    }

    @POST
    @Path("/getUserBoard")
    @JWTTokenNeeded
    public Response createBoard(@Context HttpRequest request) {
        User user = userBean.findUser(request);
        TypedQuery<Board> boardTypedQuery = entityManager.createNamedQuery(Board.GET_USER_BOARD, Board.class);
        boardTypedQuery.setParameter(Board.USER_PARAM, user.getId());
        List<Board> userBoards = boardTypedQuery.getResultList();

        return Response.ok(userBoards).build();
    }
}
