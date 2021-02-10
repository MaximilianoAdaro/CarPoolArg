package austral.ing.lab1.jsonModel;

import austral.ing.lab1.model.User;

public class UserDto {

    public UserDto(Long userId, String firstName, String lastName, String email, String password, String avatarPath, Boolean isAdministrator) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.avatarPath = avatarPath;
        this.isAdministrator = isAdministrator;
    }

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String avatarPath;
    private Boolean isAdministrator;

    public static UserDto from(User u) {
        return new UserDto(u.getUserId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword(), u.getAvatarPath(), u.getAdministrator());
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", isAdministrator=" + isAdministrator +
                '}';
    }
}
