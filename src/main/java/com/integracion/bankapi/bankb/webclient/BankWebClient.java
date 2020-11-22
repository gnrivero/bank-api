package com.integracion.bankapi.bankb.webclient;

import com.integracion.bankapi.bankb.configuration.BankProperties;
import com.integracion.bankapi.bankb.login.http.LoginRequest;
import com.integracion.bankapi.bankb.login.http.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;


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

    public String getToken() {
        if (validTokens.isEmpty()) {
            return authenticateUser().getUser().getToken();
        } else {
            return validTokens.stream().findFirst().get();
        }
    }

    //TODO: Response del banco
    public String makeTransfer() {

        return getToken();

    }

}