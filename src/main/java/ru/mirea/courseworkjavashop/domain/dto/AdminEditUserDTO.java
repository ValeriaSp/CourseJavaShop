package ru.mirea.courseworkjavashop.domain.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminEditUserDTO {

    private int id;

    @NotEmpty(message = "Поле имя не может быть пустым")
    private String name;

    @NotEmpty(message = "Поле email не может быть пустым")
    @Email(message = "Некорректный email")
    private String email;

    @Min(value = 0, message = "Баланс не может быть отрицательным")
    private double balance;

    @NotEmpty(message = "Поле роль не может быть пустым")
    private String role;

    private boolean changePassword;

    private String password;
}
