package domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "board", schema = "public")
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User owner;

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "is_public_edit")
    private Boolean isPublicEdit;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private List<BoardPermission> boardPermissions;

    public int getId() {
        return id;
    }

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

    public boolean isPublicEdit() {
        return isPublicEdit;
    }

    public void setPublicEdit(boolean publicEdit) {
        isPublicEdit = publicEdit;
    }

    public List<BoardPermission> getBoardPermissions() {
        return boardPermissions;
    }

    public void setBoardPermissions(List<BoardPermission> boardPermissions) {
        this.boardPermissions = boardPermissions;
    }
}
