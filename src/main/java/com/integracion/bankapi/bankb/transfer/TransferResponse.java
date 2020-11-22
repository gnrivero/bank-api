package com.integracion.bankapi.bankb.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransferResponse {
    @JsonProperty("mensaje")
    private String mensaje;
}