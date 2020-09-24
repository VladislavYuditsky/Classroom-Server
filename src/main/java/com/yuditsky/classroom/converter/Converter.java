package com.yuditsky.classroom.converter;

public interface Converter<F, T> {
    T convert(F object);
}
