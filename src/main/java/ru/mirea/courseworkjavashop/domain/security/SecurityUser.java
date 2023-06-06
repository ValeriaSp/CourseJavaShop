package ru.mirea.courseworkjavashop.domain.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mirea.courseworkjavashop.domain.model.User;

import java.util.Collection;
import java.util.Collections;


@AllArgsConstructor
public class SecurityUser implements UserDetails {

    private User user;

    /**
     * Получение роли пользователя
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    /**
     * Получение пароля пользователя
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Получение имени пользователя
     * @return
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Проверка на истечение срока действия аккаунта
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверка на блокировку аккаунта
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверка на истечение срока действия пароля
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверка на активность аккаунта
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Получение пользователя
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Обновление пользователя
     * @param user
     */
    public void updateUser(User user) {
        this.user = user;
    }
}
