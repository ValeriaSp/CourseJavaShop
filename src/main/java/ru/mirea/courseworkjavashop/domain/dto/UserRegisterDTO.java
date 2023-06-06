package ru.mirea.courseworkjavashop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterDTO {
    @NotEmpty(message = "Поле имя не может быть пустым")
    private String name;

    @NotEmpty(message = "Поле email не может быть пустым")
    @Email(message = "Некорректный email")
    private String email;

    @NotEmpty(message = "Поле пароль не может быть пустым")
    private String password;
}
