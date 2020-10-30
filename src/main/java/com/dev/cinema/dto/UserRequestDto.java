package com.dev.cinema.dto;

import com.dev.cinema.validation.EmailSpecification;
import com.dev.cinema.validation.FieldsValueMatch;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatedPassword",
        message = "Passwords don't match"
)
public class UserRequestDto {
    @EmailSpecification
    private String email;
    @NotNull(message = "Password should not be null")
    @Size(min = 4)
    private String password;
    private String repeatedPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "email='" + email + '\'' + '}';
    }
}
