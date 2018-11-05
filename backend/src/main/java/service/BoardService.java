package service;

import bean.UserBean;
import domain.*;
import filter.JWTTokenNeeded;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
    @Path("/deleteBoard")
    @JWTTokenNeeded
    public Response deleteBoard(@Context HttpRequest request, BoardId boardId) {
        User user = userBean.findUser(request);
        Board board = entityManager.find(Board.class, boardId.getId());

        if (board.getOwnerId() == user.getId()) {
            entityManager.remove(board);

            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    @POST
    @Path("/getBoardCards")
    @JWTTokenNeeded
    public Response createBoard(@Context HttpRequest request, BoardId boardId) {
        Board board = entityManager.find(Board.class, boardId.getId());
        BoardCardList boardCardList = new BoardCardList();
        boardCardList.setBoard(board);

        TypedQuery<BoardCard> boardQuery = entityManager.createNamedQuery(BoardCard.GET_BOARD_CARDS, BoardCard.class);
        boardQuery.setParameter("bord", board.getId());
        List<BoardCard> boardCards = boardQuery.getResultList();

        boardCardList.setBoardCards(boardCards);
        return Response.ok(boardCardList).build();
    }

    @POST
    @Path("/getUserBoard")
    @JWTTokenNeeded
    public Response getBoardCards(@Context HttpRequest request) {
        User user = userBean.findUser(request);
        TypedQuery<Board> boardTypedQuery = entityManager.createNamedQuery(Board.GET_USER_BOARD, Board.class);
        boardTypedQuery.setParameter(Board.USER_PARAM, user.getId());
        List<Board> userBoards = boardTypedQuery.getResultList();

        return Response.ok(userBoards).build();
    }
}
