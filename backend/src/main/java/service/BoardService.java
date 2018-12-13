package service;

import bean.BoardBean;
import bean.UserBean;
import domain.board.Board;
import domain.card.BoardCard;
import domain.card.BoardCardList;
import domain.card.TextCard;
import domain.front.BoardCardParams;
import domain.front.BoardId;
import domain.front.BoardParam;
import domain.user.User;
import filter.NeedAuth;
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

    @Inject
    private BoardBean boardBean;

    @POST
    @Path("/createBoard")
    @NeedAuth
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
    @NeedAuth
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
    @NeedAuth
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
    @NeedAuth
    public Response getBoardCards(@Context HttpRequest request) {
        User user = userBean.findUser(request);

        TypedQuery<Board> boardTypedQuery = entityManager.createNamedQuery(Board.GET_USER_BOARD, Board.class);
        boardTypedQuery.setParameter(Board.USER_PARAM, user.getId());
        List<Board> userBoards = boardTypedQuery.getResultList();

        return Response.ok(userBoards).build();
    }

    @POST
    @Path("/createBoardCard")
    @NeedAuth
    public Response createBoardCard(BoardCardParams boardCardParams) {
        //TODO: добавить валидацию
        BoardCard boardCard = new BoardCard();
        boardCard.setPrivate(true);
        boardCard.setCardName(boardCardParams.getCardName());
        boardCard.setCardType(BoardCard.CardType.valueOf(boardCardParams.getCardType()));
        boardCard.setBoardId(boardCardParams.getBoardId());
        boardBean.generateContentForType(boardCard);

        entityManager.persist(boardCard);

        return Response.ok().build();
    }

    @POST
    @Path("/getBoardCardContent")
    @NeedAuth
    public Response getBoardCardContent(BoardCardParams boardCardParams) {
        Object cardContent = boardBean.getCardContent(boardCardParams);

        return Response.ok(cardContent).build();
    }

    @POST
    @Path("/updateTextCard")
    @NeedAuth
    public Response updateTextCard(TextCard textCard) {
        TextCard textCardEntity = entityManager.find(TextCard.class, textCard.getId());

        textCardEntity.setCardText(textCard.getCardText());
        textCardEntity.setTitle(textCard.getTitle());

        return Response.ok().build();
    }

}
