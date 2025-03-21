package com.mladentsev.taskmanagementsystem.dto;


import com.mladentsev.taskmanagementsystem.enums.ERoles;
import com.mladentsev.taskmanagementsystem.repositories.IUserRepositories;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
public class RequestRegisterDto {

    private PasswordEncoder encoder;

    private IUserRepositories iUserRepositories;

    @NotBlank(message = "Параметр 'login' является обязательным для запроса.")
    @Size(min = 3, max = 20, message = "Значение параметра 'login' должно содержать не менее 6 и не более 20 символов.")
    private String login;

    @NotBlank(message = "Параметр 'password' является обязательным для запроса.")
    @Size(min = 8, message = "Значение параметра 'password' должно содержать не менее 8 символов.")
    private String password;

    @NotBlank(message = "Имя является обязательным для запроса.")
    @Size(min = 3, max = 20, message = "Имя должно содержать не менее 3 и не более 20 символов.")
    private String firstName;

    @NotBlank(message = "Фамилия является обязательным для запроса.")
    @Size(min = 3, max = 20, message = "Фамилия должно содержать не менее 3 и не более 20 символов.")
    private String lastName;

    @NotBlank(message = "Электронная почта является обязательным для запроса.")
    @Email(message = "Необходимо ввести корректный адрес электронной почты.")
    private String email;

    @NotNull(message = "Список ролей являются обязательным полем")
    private Set<ERoles> roles;


    
    public @NotBlank(message = "Параметр 'login' является обязательным для запроса.")
            @Size(min = 3, max = 20, message = "Значение параметра 'login' должно содержать не менее 6 и не более 20 символов.")
            String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank(message = "Параметр 'login' является обязательным для запроса.")
                         @Size(min = 3, max = 20, message = "Значение параметра 'login' должно содержать не менее 6 и не более 20 символов.")
                         String login) {
        this.login = login;
    }

    public @NotBlank(message = "Параметр 'password' является обязательным для запроса.")
            @Size(min = 8, message = "Значение параметра 'password' должно содержать не менее 8 символов.")
            String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Параметр 'password' является обязательным для запроса.")
                            @Size(min = 8, message = "Значение параметра 'password' должно содержать не менее 8 символов.")
                            String password) {
        this.password = password;
    }

    public @NotBlank(message = "Имя является обязательным для запроса.")
            @Size(min = 3, max = 20, message = "Имя должно содержать не менее 3 и не более 20 символов.")
            String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "Имя является обязательным для запроса.")
                             @Size(min = 3, max = 20, message = "Имя должно содержать не менее 3 и не более 20 символов.")
                             String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Фамилия является обязательным для запроса.")
            @Size(min = 3, max = 20, message = "Фамилия должно содержать не менее 3 и не более 20 символов.")
            String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Фамилия является обязательным для запроса.")
                            @Size(min = 3, max = 20, message = "Фамилия должно содержать не менее 3 и не более 20 символов.")
                            String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Электронная почта является обязательным для запроса.")
            @Email(message = "Необходимо ввести корректный адрес электронной почты.")
            String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Электронная почта является обязательным для запроса.")
                         @Email(message = "Необходимо ввести корректный адрес электронной почты.")
                         String email) {
        this.email = email;
    }

    public @NotNull(message = "Список ролей являются обязательным полем") Set<ERoles> getRoles() {
        return roles;
    }

    public void setRoles(@NotNull(message = "Список ролей являются обязательным полем") Set<ERoles> roles) {
        this.roles = roles;
    }



    @Override
    public String toString() {
        return "RequestRegisterDto{" +
                " login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }

}
