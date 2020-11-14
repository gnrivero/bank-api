package com.integracion.bankapi;

import com.integracion.bankapi.model.cbu.Cbu;

import static org.assertj.core.api.BDDAssertions.assertThat;
import org.junit.jupiter.api.Test;

public class CbuTest {

    @Test
    public void testFourDigitsCBU(){
        String cbu = new Cbu().setAccountId(1234).getGeneratedValue();

        assertThat(cbu).isEqualTo("4560001900000000012349");
    }

    @Test
    public void testOneDigitCBU(){
        String cbu = new Cbu().setAccountId(1).getGeneratedValue();

        assertThat(cbu).isEqualTo("4560001900000000000019");
    }
}
