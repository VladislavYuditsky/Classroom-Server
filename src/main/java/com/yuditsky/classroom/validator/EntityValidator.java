package com.yuditsky.classroom.validator;

public interface EntityValidator<T> {
    void validate(T object);
}
