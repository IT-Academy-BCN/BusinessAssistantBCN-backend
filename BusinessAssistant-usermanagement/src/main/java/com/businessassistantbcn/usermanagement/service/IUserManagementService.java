package com.businessassistantbcn.usermanagement.service;

import com.businessassistantbcn.usermanagement.dto.*;

import reactor.core.publisher.Mono;

public interface IUserManagementService {

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
    Mono<GenericResultDto<?>> addUser(SingUpRequest singup);

    Mono<GenericResultDto<UserResponse>> getUserById(IdOnly idOnly);

    Mono<GenericResultDto<UserResponse>> getUserByEmail(EmailOnly emailOnly);

}
