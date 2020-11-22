package com.integracion.bankapi.bankb.webclient;

import com.integracion.bankapi.bankb.configuration.BankProperties;
import com.integracion.bankapi.bankb.login.http.LoginRequest;
import com.integracion.bankapi.bankb.login.http.LoginResponse;
import com.integracion.bankapi.bankb.transfer.TransferRequest;
import com.integracion.bankapi.bankb.transfer.TransferResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@AllArgsConstructor
public class BankWebClient {

    private WebClient webClient;
    private BankProperties props;
    private LoginRequest loginRequest;

    private static List<String> validTokens = new ArrayList<>();

    public LoginResponse authenticateUser() {
        return webClient.post()
                        .uri(props.getAuth().getUri())
                        .body(BodyInserters.fromValue(loginRequest))
                        .retrieve()
                        .bodyToMono(LoginResponse.class)
                        .block();
    }

    private String getToken() {
        if (validTokens.isEmpty()) {
            Optional.ofNullable(authenticateUser().getUser().getToken())
                    .ifPresent(validTokens::add);
        }

        return validTokens.stream().findFirst().get();
    }

    public TransferResponse makeTransfer(TransferRequest request) {
        return webClient.post()
                .uri(props.getTransfer().getUri())
                .header("Authorization", "Bearer " + getToken())
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(TransferResponse.class)
                .block();
    }

}