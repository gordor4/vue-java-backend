package ru.rus.integrato.domain.board;


import ru.rus.integrato.domain.user.User;

import javax.persistence.*;
import java.util.Date;

@Table(name = "board", schema = "public")
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "board_description")
    private String boardDescription;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "is_public_edit")
    private Boolean isPublicEdit;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isPublicEdit() {
        return isPublicEdit;
    }

    public void setPublicEdit(Boolean publicEdit) {
        isPublicEdit = publicEdit;
    }

    public String getBoardDescription() {
        return boardDescription;
    }

    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
