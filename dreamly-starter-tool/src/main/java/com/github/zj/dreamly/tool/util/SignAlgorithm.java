package com.github.zj.dreamly.tool.util;

public enum SignAlgorithm {

    MD2withRSA("MD2withRSA"),
    MD5withRSA("MD5withRSA"),
    SHA1withRSA("SHA1withRSA"),
    SHA256withRSA("SHA256withRSA"),
    SHA384withRSA("SHA384withRSA"),
    SHA512withRSA("SHA512withRSA");

    private String algorithm;

    SignAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
