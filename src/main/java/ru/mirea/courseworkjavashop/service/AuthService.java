package ru.mirea.courseworkjavashop.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.courseworkjavashop.domain.dto.UserInfoDTO;
import ru.mirea.courseworkjavashop.domain.model.User;
import ru.mirea.courseworkjavashop.domain.security.SecurityUser;
import ru.mirea.courseworkjavashop.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    /**
     * Получение авторизованного пользователя
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<User> getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        return Optional.of(((SecurityUser) authentication.getPrincipal()).getUser());
    }

    /**
     * Получение информации о пользователе для шаблона
     *
     * @return
     */
    @Transactional(readOnly = true)
    public UserInfoDTO getUserInfo() {
        // Получаем авторизованного пользователя
        var user = getAuthUser().orElse(null);

        // Если пользователь не авторизован, то возвращаем пустой объект
        if (user == null) {
            return new UserInfoDTO(false, null);
        } else {
            // Если пользователь авторизован, то возвращаем информацию о нём
            return new UserInfoDTO(true, userRepository.findById(user.getId()).orElseThrow());
        }
    }
}
