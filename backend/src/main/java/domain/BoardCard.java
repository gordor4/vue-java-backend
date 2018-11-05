package domain;

import javax.persistence.*;

@Table(name = "dashboard_card", schema = "public")
@Entity
@NamedQueries({
        @NamedQuery(name = BoardCard.GET_BOARD_CARDS, query = "Select c from BoardCard c WHERE c.boardId = :board")
})
public class BoardCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "baord_id")
    private int boardId;

    @Column(name = "card_name")
    private String cardName;

    public static final String GET_BOARD_CARDS = "get all cards";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
