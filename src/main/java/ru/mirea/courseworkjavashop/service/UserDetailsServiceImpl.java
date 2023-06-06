package ru.mirea.courseworkjavashop.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.courseworkjavashop.domain.security.SecurityUser;
import ru.mirea.courseworkjavashop.repository.UserRepository;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    /**
     * Поиск пользователя по email
     *
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        return user.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
