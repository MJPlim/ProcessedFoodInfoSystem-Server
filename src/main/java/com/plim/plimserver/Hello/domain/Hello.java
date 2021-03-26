package com.plim.plimserver.Hello.domain;

import lombok.Builder;

import java.time.LocalDate;

public class Hello {
    private final String name;
    private final int age;
    private final String say;
    private final LocalDate date;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSay() {
        return say;
    }

    public LocalDate getDate() {
        return date;
    }

    @Builder
    public Hello(String name, int age, String say, LocalDate date) {
        this.name = name;
        this.age = age;
        this.say = say;
        this.date = date;
    }
}
