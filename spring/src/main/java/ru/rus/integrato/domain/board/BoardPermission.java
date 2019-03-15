package ru.rus.integrato.domain.board;

import ru.rus.integrato.domain.user.User;

import javax.persistence.*;

@Table(name = "board_permission", schema = "public")
@Entity
public class BoardPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Board board;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "permission_read")
    private boolean readPermission;

    @Column(name = "permission_edit")
    private boolean editPermission;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isReadPermission() {
        return readPermission;
    }

    public void setReadPermission(boolean readPermission) {
        this.readPermission = readPermission;
    }

    public boolean isEditPermission() {
        return editPermission;
    }

    public void setEditPermission(boolean editPermission) {
        this.editPermission = editPermission;
    }
}
