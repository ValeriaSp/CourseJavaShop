package ru.mirea.courseworkjavashop.domain.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ru.mirea.courseworkjavashop.domain.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoDTO {
    private boolean isAuth;
    private User user;
}
