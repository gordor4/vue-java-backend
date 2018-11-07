package service;

import bean.UserBean;
import domain.board.Board;
import domain.card.BoardCard;
import domain.card.BoardCardList;
import domain.front.BoardId;
import domain.front.BoardParam;
import domain.user.User;
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
        board.setOwner(user);
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

        if (board.getOwner().getId() == user.getId()) {
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
        boardQuery.setParameter("board", board.getId());
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

    @POST
    @Path("/createBoardCard")
    @JWTTokenNeeded
    public Response createBoardCard() {
        return Response.ok().build();
    }
}
