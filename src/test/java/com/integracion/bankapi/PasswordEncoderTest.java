package com.integracion.bankapi;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.BDDAssertions.assertThat;

public class PasswordEncoderTest {

    @Test
    public void testEncoding(){
        String encoded = new BCryptPasswordEncoder().encode("1234");

        assertThat(new BCryptPasswordEncoder().matches("1234", encoded)).isTrue();
    }

}
