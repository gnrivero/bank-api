package com.integracion.bankapi.model;

import java.io.File;

public class ProviderFileDTO {


    private String providerCode;
    private File file;

    public ProviderFileDTO() {
    }




    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }
}
