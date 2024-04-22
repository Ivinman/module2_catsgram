package ru.yandex.practicum.catsgram.exception;

public class IncorrectParameterException extends RuntimeException {
    private final String parameter;

    public String getParameter() {
        return parameter;
    }

    public IncorrectParameterException(final String parameter) {
        this.parameter = parameter;
    }
}
