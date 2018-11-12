package domain.front;

public class BoardCardParams {

    private String cardName;
    private String cardType;
    private Integer boardId;
    private Integer boardCardId;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public Integer getBoardCardId() {
        return boardCardId;
    }

    public void setBoardCardId(Integer boardCardId) {
        this.boardCardId = boardCardId;
    }
}
