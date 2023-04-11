package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.dto.EmailOnly;
import com.businessassistantbcn.usermanagement.dto.IdOnly;
import com.businessassistantbcn.usermanagement.dto.SingUpRequest;
import com.businessassistantbcn.usermanagement.dto.output.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserDto;
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
     * @param singup
     * @return
     */

    @Override
    public Mono<?> addUser(SingUpRequest singup) {

        Mono<?> response;

        if (!limitUsersDbExceeded()) {
            Optional<User> userFound = userRepository.findByEmail(singup.getEmail()).blockOptional();
            if (userFound.isEmpty()) {
                singup.setPassword(encoder.encode(singup.getPassword()));
                response = userRepository.save(DtoHelper.convertToUserFromSingup(singup))
                        .map(DtoHelper::convertToDto);
            } else {
                saveLatestAccess(userFound.get());
                //userRepository.save(userFound.get()).block(); //TODO: remove due redunant, done in previous sentence
                response = Mono.empty();
            }
        } else {//número máximo de usuarios excedido
            response = Mono.just(new ErrorDto(propertiesConfig.getError()));
        }
        return response;
    }

    public void saveLatestAccess(User user) {
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
            saveLatestAccess(user.block());
            response = user.map(DtoHelper::convertToDto);
        }
        return response;

    }

    @Override
    public Mono<UserDto> getUserByEmail(EmailOnly emailOnly) {

        Mono<User> user = userRepository.findByEmail(emailOnly.getEmail());

        Mono<UserDto> response;

        if (user.blockOptional().isEmpty()) {
            response = Mono.empty();
        } else {
            saveLatestAccess(user.block());
            response = user.map(DtoHelper::convertToDto);
        }
        return response;
    }
}