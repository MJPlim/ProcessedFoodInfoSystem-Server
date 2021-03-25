package com.plim.plimserver.Hello.domain;

import lombok.Builder;

public class Hello {
    private final String name;
    private final int age;
    private final String say;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSay() {
        return say;
    }

    @Builder
    public Hello(String name, int age, String say) {
        this.name = name;
        this.age = age;
        this.say = say;
    }
}
