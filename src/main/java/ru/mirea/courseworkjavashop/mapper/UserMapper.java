package ru.mirea.courseworkjavashop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mirea.courseworkjavashop.domain.dto.AdminEditUserDTO;
import ru.mirea.courseworkjavashop.domain.dto.UserEditDTO;
import ru.mirea.courseworkjavashop.domain.dto.UserRegisterDTO;
import ru.mirea.courseworkjavashop.domain.model.User;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * Маппинг UserRegisterDTO в User
     *
     * @param userRegisterDTO
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegisterDTO.getPassword()))")
    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "balance", constant = "0")
    @Mapping(target = "products", ignore = true)
    public abstract User registerDTOToUser(UserRegisterDTO userRegisterDTO);

    /**
     * Маппинг User в UserInfoDTO
     *
     * @param user
     * @return
     */
    public abstract UserEditDTO userToUserEditDTO(User user);


    /**
     * Маппинг User в AdminEditUserDTO
     *
     * @param user
     * @return
     */
    @Mapping(target = "password", constant = "")
    public abstract AdminEditUserDTO userToAdminEditUserDTO(User user);

//    /**
//     * Маппинг AdminEditUserDTO в User
//     * @param adminEditUserDTO
//     * @return
//     */
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "password", expression = "java(passwordEncoder.encode(adminEditUserDTO.getPassword()))")
//
}
