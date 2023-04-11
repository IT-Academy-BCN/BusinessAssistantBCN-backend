package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.dto.IdOnly;
import com.businessassistantbcn.usermanagement.dto.output.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.input.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.output.UserDto;
import com.businessassistantbcn.usermanagement.helper.DtoHelper;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import java.util.Optional;

@Service
public class UserManagementService implements IUserManagementService {

    @Autowired
    UserManagementRepository userRepository;
    @Autowired
    PropertiesConfig propertiesConfig;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12;

    /**
     * Añade un usuario a la base de datos. Funcionamiento:
     * 1. Comprueba si el número máximo de usuarios está excedido
     * 2. Comprueba si el usuario existe
     * 3. Si el usuario existe, actualiza el último acceso y devuelve empty
     * 4. Si el usuario no existe, lo crea y devuelve el usuario creado
     *
     * @param userEmailDto
     * @return
     */

    public Mono<?> addUser(UserEmailDto userEmailDto) {

        Mono<?> response;

        if (!limitUsersDbExceeded()) {
            Optional<User> user = userRepository.findByEmail(userEmailDto.getEmail()).blockOptional();
            if (!user.isEmpty()) {
                setLatestAccess(user.get());
                userRepository.save(user.get()).block();
                response = Mono.empty();
            } else {
                userEmailDto.setPassword(encoder.encode(userEmailDto.getPassword()));
                response = userRepository.save(DtoHelper.convertToUserFromEmailDto(userEmailDto)).map(DtoHelper::convertToDto);
            }
        } else {//número máximo de usuarios excedido
            response = Mono.just(new ErrorDto(propertiesConfig.getError()));
        }
        return response;
    }

    public void setLatestAccess(User user) {
        user.setLatestAccess(System.currentTimeMillis());
        userRepository.save(user).block();
    }

    public boolean limitUsersDbExceeded() {
        return propertiesConfig.getEnabled() && (userRepository.count().block() >= propertiesConfig.getMaxusers());
    }

    @Override
    public Mono<UserDto> getUserById(IdOnly idOnly) {

        Mono<User> user = userRepository.findByUuid(idOnly.getUuid());

        Mono<UserDto> response;

        if (user.blockOptional().isEmpty()) {
            response = Mono.empty();
        } else {
            setLatestAccess(user.block());
            response = user.map(DtoHelper::convertToDto);
        }
        return response;

    }

    public Mono<UserDto> getUserByEmail(UserEmailDto userEmailDto) {

        Mono<User> user = userRepository.findByEmail(userEmailDto.getEmail());

        Mono<UserDto> response;

        if (user.blockOptional().isEmpty()) {
            response = Mono.empty();
        } else {
            setLatestAccess(user.block());
            response = user.map(DtoHelper::convertToDto);
        }
        return response;
    }
}