package ru.mirea.courseworkjavashop.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.mirea.courseworkjavashop.domain.dto.AdminEditUserDTO;
import ru.mirea.courseworkjavashop.domain.dto.UserEditDTO;
import ru.mirea.courseworkjavashop.domain.dto.UserRegisterDTO;
import ru.mirea.courseworkjavashop.domain.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-05T12:23:02+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public User registerDTOToUser(UserRegisterDTO userRegisterDTO) {
        if ( userRegisterDTO == null ) {
            return null;
        }

        User user = new User();

        user.setName( userRegisterDTO.getName() );
        user.setEmail( userRegisterDTO.getEmail() );

        user.setPassword( passwordEncoder.encode(userRegisterDTO.getPassword()) );
        user.setRole( "ROLE_USER" );
        user.setBalance( 0 );

        return user;
    }

    @Override
    public UserEditDTO userToUserEditDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserEditDTO userEditDTO = new UserEditDTO();

        userEditDTO.setName( user.getName() );
        userEditDTO.setEmail( user.getEmail() );

        return userEditDTO;
    }

    @Override
    public AdminEditUserDTO userToAdminEditUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        AdminEditUserDTO adminEditUserDTO = new AdminEditUserDTO();

        adminEditUserDTO.setId( user.getId() );
        adminEditUserDTO.setName( user.getName() );
        adminEditUserDTO.setEmail( user.getEmail() );
        adminEditUserDTO.setBalance( user.getBalance() );
        adminEditUserDTO.setRole( user.getRole() );

        adminEditUserDTO.setPassword( "" );

        return adminEditUserDTO;
    }
}
