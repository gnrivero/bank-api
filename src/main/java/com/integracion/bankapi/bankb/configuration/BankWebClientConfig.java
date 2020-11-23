package com.integracion.bankapi.bankb.configuration;

import com.integracion.bankapi.bankb.login.http.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
@AllArgsConstructor
public class BankWebClientConfig {

    private BankProperties props;

    @Bean
    public LoginRequest loginRequest(){
        return LoginRequest.builder()
                                .nombreUsuario(props.getAuth().getUsername())
                                .clave(props.getAuth().getPassword())
                                .build();
    }

    @Bean
    public WebClient bankWebClientSetUp(){
        return WebClient.builder()
                .baseUrl(props.getHost())
                .defaultHeaders(request -> {
                    request.setAccept(List.of(MediaType.APPLICATION_JSON));
                    request.setContentType(MediaType.APPLICATION_JSON);
                })
                .build();
    }

}