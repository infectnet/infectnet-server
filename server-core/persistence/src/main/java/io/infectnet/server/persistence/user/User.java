package io.infectnet.server.persistence.user;

import java.time.LocalDateTime;

public class User {

    private String userName;

    private String email;

    private String password;

    private LocalDateTime registrationDate;

    /**
     * Constructs a new {@link User} object.
     *
     * @param userName the username of the new user
     * @param email the email address of the new user
     * @param password the password of the new user
     * @param registrationDate the date of the new user's registration
     */
    public User(String userName, String email, String password, LocalDateTime registrationDate) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName the username of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email address  of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password connected to the username of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password connected to the username of the user.
     *
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the date of the user's registration.
     *
     * @return the date of the user's registration
     */
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the date of the user's registration.
     *
     * @param registrationDate the date of the user's registration
     */
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!getUserName().equals(user.getUserName())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        return getRegistrationDate().equals(user.getRegistrationDate());

    }

    @Override
    public int hashCode() {
        int result = getUserName() != null ? getUserName().hashCode() : 0;
        result = 31 * result + (getRegistrationDate() != null ? getRegistrationDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
