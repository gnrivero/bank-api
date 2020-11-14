package com.integracion.bankapi.model.cbu;

public final class Cbu {

    private static Integer accountId;

    private final String bankId = "456";
    private final String branchId = "0001";

    public Cbu builder() {
        return this;
    }

    public Cbu setAccountId(Integer accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getGeneratedValue() {
        String cbu = bankId.concat(branchId);
        cbu = cbu.concat("9");
        String account = String.valueOf(accountId);
        int precedingZeroes = 12 - account.length();
        for (int i = 0; i <= precedingZeroes; i++){
            cbu = cbu.concat("0");
        }
        cbu = cbu.concat(account)
                .concat("9");

        return cbu;
    }

}