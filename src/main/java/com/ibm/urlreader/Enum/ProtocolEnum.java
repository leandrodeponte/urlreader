package com.ibm.urlreader.Enum;

import lombok.Getter;

public enum ProtocolEnum {

    HTTP("http://"),
    HTTPS("https://");

    @Getter
    private  String protocol;

    ProtocolEnum(String s) {
        this.protocol = s;
    }


}
