package com.ibm.urlreader.Enum;

public enum ProtocolEnum {

    HTTP("HTTP"),
    HTTPS("HTTPS");

    private String protocol;

    ProtocolEnum(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol(){
        return protocol;
    }
}
