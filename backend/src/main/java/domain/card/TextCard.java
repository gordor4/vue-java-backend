package domain.card;

import javax.persistence.*;

@Table(name = "text_card_content", schema = "public")
@Entity
@NamedQueries({
        @NamedQuery(name = TextCard.FIND_TEXT_CARD_CONTENT, query = "Select c from TextCard WHERE boardCard.id = :id")
})
public class TextCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn(name = "dashboard_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private BoardCard boardCard;

    @Column(name = "dashboard_text")
    private String dashboardText;

    @Column(name = "title")
    private String text;

    public static final String FIND_TEXT_CARD_CONTENT = "find card content";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BoardCard getBoardCard() {
        return boardCard;
    }

    public void setBoardCard(BoardCard boardCard) {
        this.boardCard = boardCard;
    }

    public String getDashboardText() {
        return dashboardText;
    }

    public void setDashboardText(String dashboardText) {
        this.dashboardText = dashboardText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
