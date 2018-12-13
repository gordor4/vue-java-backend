package domain.card;

import javax.persistence.*;

@Table(name = "chat_card_content", schema = "public")
@Entity
public class ChatCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "dashboard_id")
    private int boardCardId;

    @Column(name = "chat_name")
    private String chatName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardCardId() {
        return boardCardId;
    }

    public void setBoardCardId(int boardCardId) {
        this.boardCardId = boardCardId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
