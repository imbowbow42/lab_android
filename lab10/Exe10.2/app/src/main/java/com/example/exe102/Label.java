package com.example.exe102;

public enum Label {
    CONFIRMED("Confirmed"),
    RECOVERED("Recovered"),
    DEATHS("Deaths"),
    CRITICAL("Critical");

    final String value;

    Label(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
