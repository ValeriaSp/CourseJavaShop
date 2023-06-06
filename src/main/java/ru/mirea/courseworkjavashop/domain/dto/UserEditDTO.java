package ru.mirea.courseworkjavashop.domain.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEditDTO {

    @NotEmpty(message = "Поле имя не может быть пустым")
    private String name;

    @NotEmpty(message = "Поле email не может быть пустым")
    @Email(message = "Некорректный email")
    private String email;
}
