package domain;

public class BoardParam {

    private String boardName;

    private String isPublic;

    private String isPublicEdit;

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getIsPublicEdit() {
        return isPublicEdit;
    }

    public void setIsPublicEdit(String isPublicEdit) {
        this.isPublicEdit = isPublicEdit;
    }
}
