package io.infectnet.server.service;

import java.time.LocalDateTime;

public class UserDTO {

    private String userName;

    private String email;

    private String password;

    private LocalDateTime registrationDate;

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

        UserDTO userDTO = (UserDTO) o;

        if (getUserName() != null ? !getUserName().equals(userDTO.getUserName()) : userDTO.getUserName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(userDTO.getEmail()) : userDTO.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(userDTO.getPassword()) : userDTO.getPassword() != null)
            return false;
        return getRegistrationDate() != null ? getRegistrationDate().equals(userDTO.getRegistrationDate()) : userDTO.getRegistrationDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getUserName() != null ? getUserName().hashCode() : 0;
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getRegistrationDate() != null ? getRegistrationDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
