package ru.rus.integrato.domain.card;

import javax.persistence.*;

@Table(name = "text_card_content", schema = "public")
@Entity
public class TextCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "dashboard_id")
    private int boardCardId;

    @Column(name = "dashboard_text")
    private String cardText;

    @Column(name = "title")
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardCardId() {
        return boardCardId;
    }

    public void setBoardCardId(int boardId) {
        this.boardCardId = boardId;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String dashboardText) {
        this.cardText = dashboardText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String text) {
        this.title = text;
    }
}
