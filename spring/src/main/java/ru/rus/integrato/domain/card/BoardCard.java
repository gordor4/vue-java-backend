package ru.rus.integrato.domain.card;

import javax.persistence.*;

@Table(name = "dashboard_card", schema = "public")
@Entity
public class BoardCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "board_id")
    private int boardId;

    @Column(name = "card_name")
    private String cardName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Column(name = "private")
    private boolean isPrivate;

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

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public enum CardType {
        TEXT_CARD("text"),
        EMAIL_CARD("email");

        private final String text;

        CardType(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
