package ru.mirea.courseworkjavashop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BalanceTopupDTO {
    @Min(value = 100, message = "Минимальная сумма пополнения 100 рублей")
    @Max(value = 10000, message = "Максимальная сумма пополнения 10 000 рублей")
    private int amount;

    @NotEmpty(message = "Номер телефона")
    private String phone;

    @NotEmpty(message = "Введите имя")
    private String name;
}
