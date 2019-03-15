package ru.rus.integrato.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rus.integrato.domain.board.Board;
import ru.rus.integrato.domain.card.BoardCard;
import ru.rus.integrato.domain.card.BoardCardList;
import ru.rus.integrato.domain.card.TextCard;
import ru.rus.integrato.domain.front.BoardCardParams;
import ru.rus.integrato.domain.front.BoardId;
import ru.rus.integrato.domain.front.BoardParam;
import ru.rus.integrato.domain.user.User;
import ru.rus.integrato.filter.AuthFilter;
import ru.rus.integrato.repository.BoardCardRepository;
import ru.rus.integrato.repository.BoardRepository;
import ru.rus.integrato.repository.TextCardRepository;
import ru.rus.integrato.repository.UserRepository;
import ru.rus.integrato.service.CardService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(path = "/board")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class BoardController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCardRepository boardCardRepository;

    @Autowired
    private TextCardRepository textCardRepository;

    @Autowired
    private CardService cardService;

    @PostMapping(path = "/createBoard", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserBoards(@RequestBody BoardParam boardParam) {
        User user = userRepository.findUserByUsername((String) request.getAttribute(AuthFilter.USER));

        Board board = new Board();
        board.setBoardName(boardParam.getBoardName());
        board.setOwner(user);
        board.setCreationDate(new Date());
        board.setBoardDescription(boardParam.getBoardDescription());
        board.setPublic(Boolean.valueOf(boardParam.getIsPublic()));
        board.setPublicEdit(Boolean.valueOf(boardParam.getIsPublicEdit()));
        boardRepository.save(board);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/deleteBoard", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteBoard(@RequestBody BoardId boardId) {
        User user = userRepository.findUserByUsername((String) request.getAttribute(AuthFilter.USER));
        Board board = boardRepository.getOne(boardId.getId());

        if (board.getOwner().getId() == user.getId()) {
            boardRepository.delete(board);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(path = "/getBoardCards", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserBoardCards(@RequestBody BoardId boardId) {
        Board board = boardRepository.getOne(boardId.getId());
        BoardCardList boardCardList = new BoardCardList();
        boardCardList.setBoard(board);

        List<BoardCard> boardCards = boardCardRepository.getBoardCardByBoardId(board.getId());

        boardCardList.setBoardCards(boardCards);
        return ResponseEntity.ok(boardCardList);
    }

    @PostMapping(path = "/getUserBoard")
    public ResponseEntity getUserBoards() {
        User user = userRepository.findUserByUsername((String) request.getAttribute(AuthFilter.USER));
        List<Board> userBoards = boardRepository.getBoardsByOwnerId(user.getId());
        return ResponseEntity.ok(userBoards);
    }


    @PostMapping(path = "/createBoardCard", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBoardCard(@RequestBody BoardCardParams boardCardParams) {
        //TODO: добавить валидацию
        BoardCard boardCard = new BoardCard();
        boardCard.setPrivate(true);
        boardCard.setCardName(boardCardParams.getCardName());
        boardCard.setCardType(BoardCard.CardType.valueOf(boardCardParams.getCardType()));
        boardCard.setBoardId(boardCardParams.getBoardId());
        cardService.generateContentForType(boardCard);

        boardCardRepository.save(boardCard);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/getBoardCardContent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBoardCardContent(@RequestBody BoardCardParams boardCardParams) {
        Object cardContent = cardService.getCardContent(boardCardParams);

        return ResponseEntity.ok(cardContent);
    }

    @PostMapping(path = "/updateTextCard", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateTextCard(@RequestBody TextCard textCard) {
        TextCard textCardEntity = textCardRepository.getOne(textCard.getId());

        textCardEntity.setCardText(textCard.getCardText());
        textCardEntity.setTitle(textCard.getTitle());

        return ResponseEntity.ok().build();
    }
}
