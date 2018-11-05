package domain;

import java.util.List;

public class BoardCardList {

    private Board board;
    private List<BoardCard> boardCards;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<BoardCard> getBoardCards() {
        return boardCards;
    }

    public void setBoardCards(List<BoardCard> boardCards) {
        this.boardCards = boardCards;
    }
}
