package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.dto.input.EmailOnly;
import com.businessassistantbcn.usermanagement.dto.input.IdOnly;
import com.businessassistantbcn.usermanagement.dto.input.SingUpRequest;
import com.businessassistantbcn.usermanagement.dto.output.ErrorDto;
import com.businessassistantbcn.usermanagement.dto.output.GenericResultDto;
import com.businessassistantbcn.usermanagement.dto.output.UserResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.helper.DtoHelper;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;

import java.util.Optional;

@Service
@Log4j2
public class UserManagementService implements IUserManagementService {

    @Autowired
    UserManagementRepository userRepository;
    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Mono<GenericResultDto<?>> addUser(SingUpRequest singup) {
        Mono<GenericResultDto<?>> response;
        if (isSaveNewUserAllowed()) {
            Optional<User> userFound = userRepository.findByEmail(singup.getUserEmail()).blockOptional();
            if (userFound.isEmpty()) {
                User user = DtoHelper.convertSingupToUser(singup).encodePassword(encoder);
                response = userRepository.save(user)
                        .map(DtoHelper::convertUserToGenericUserResponse);
            } else {
                saveLatestAccess(userFound.get());
                String errorMsg = "already exist other user whit email: "+singup.getUserEmail();
                response = Mono.just(new GenericResultDto<>(new ErrorDto(errorMsg)));
            }
        } else {
            response = Mono.just(new GenericResultDto<>(new ErrorDto(propertiesConfig.getErrorLimitDb())));
        }
        return response;
    }

    private boolean isSaveNewUserAllowed() {
        if(propertiesConfig.getEnabled()){
            return userRepository.count()
                    .blockOptional()
                    .filter(count -> propertiesConfig.getMaxusers() > count)
                    .isPresent();
        }else {
            return true;
        }
    }

    private User saveLatestAccess(User user) {
        user.setLatestAccess(System.currentTimeMillis());
        return userRepository.save(user).block();
    }

    @Override
    public Mono<GenericResultDto<UserResponse>> getUserById(IdOnly idOnly) {
        return doIsFoundLogic(userRepository.findByUuid(idOnly.getUserId()));
    }

    @Override
    public Mono<GenericResultDto<UserResponse>> getUserByEmail(EmailOnly emailOnly) {
        return doIsFoundLogic(userRepository.findByEmail(emailOnly.getUserEmail()));
    }

    private Mono<GenericResultDto<UserResponse>> doIsFoundLogic(Mono<User> userMono){
        Optional<User> userOptional = userMono.blockOptional();
        if(userOptional.isPresent()){
            User user = saveLatestAccess(userOptional.get());
            return Mono.just(DtoHelper.convertUserToGenericUserResponse(user));
        }else {
            return Mono.empty();
        }
    }
}