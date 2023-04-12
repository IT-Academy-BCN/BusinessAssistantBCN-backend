package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.dto.*;
import com.businessassistantbcn.usermanagement.dto.output.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.helper.DtoHelper;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import java.util.Optional;

@Service
public class UserManagementService implements IUserManagementService {

    @Autowired
    UserManagementRepository userRepository;
    @Autowired
    PropertiesConfig propertiesConfig;

    @Override
    public Mono<GenericResultDto<?>> addUser(SingUpRequest singup) {
        Mono<GenericResultDto<?>> response;
        if (!limitUsersDbExceeded()) {
            Optional<User> userFound = userRepository.findByEmail(singup.getUserEmail()).blockOptional();
            if (userFound.isEmpty()) {
                singup.encodePassword();
                response = userRepository.save(DtoHelper.convertToUserFromSingup(singup))
                        .map(DtoHelper::userToGenericResponse);
            } else {
                saveLatestAccess(userFound.get());
                String errorMsg = "already exist other user whit email: "+singup.getUserEmail();
                response = Mono.just(new GenericResultDto<>(new ErrorDto(errorMsg)));
            }
        } else {//número máximo de usuarios excedido
            response = Mono.just(new GenericResultDto<>(new ErrorDto(propertiesConfig.getError())));
        }
        return response;
    }

    private boolean limitUsersDbExceeded() {
        return propertiesConfig.getEnabled() &&
                (userRepository.count().block() >= propertiesConfig.getMaxusers());
    }

    private void saveLatestAccess(User user) {
        user.setLatestAccess(System.currentTimeMillis());
        userRepository.save(user).block();
    }

    @Override
    public Mono<GenericResultDto<UserResponse>> getUserById(IdOnly idOnly) {
        return doIsFoundLogic(userRepository.findByUuid(idOnly.getUserId()));
    }

    @Override
    public Mono<GenericResultDto<UserResponse>> getUserByEmail(EmailOnly emailOnly) {
        return doIsFoundLogic(userRepository.findByEmail(emailOnly.getUserEmail()));
    }

    private Mono<GenericResultDto<UserResponse>> doIsFoundLogic(Mono<User> user){
        if(user.blockOptional().isPresent()){
            saveLatestAccess(user.block());
            return user.map(DtoHelper::userToGenericResponse);
        }else {
            return Mono.empty();
        }
    }
}