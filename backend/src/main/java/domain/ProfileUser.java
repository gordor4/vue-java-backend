package domain;

public class ProfileUser {
    private String username;
    private String firstName;
    private String lastName;
    private String secondName;
    private String avatar;

    public ProfileUser(User user, String defaultAvatar) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.secondName = user.getSecondName();

        Avatar avatar = user.getAvatar();

        String avatarString = avatar != null ? avatar.getBinary() : defaultAvatar;
        this.avatar = avatarString;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
