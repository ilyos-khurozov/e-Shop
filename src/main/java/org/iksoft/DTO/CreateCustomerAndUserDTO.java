package org.iksoft.DTO;

/**
 * @author IK
 *
 * DTO class for getting data about user
 * and customer at one time
 */

public class CreateCustomerAndUserDTO {
    private String username;
    private String email;
    private String password;
    private String confirm;
    private String name;
    private String country;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CreateCustomerDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirm='" + confirm + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
