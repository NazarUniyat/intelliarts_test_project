package com.nazar.uniyat.intelliarts_test_project.wires;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExceptionWire {

    public ExceptionWire(String message, String name) {
        this.message = message;
        this.name = name;
    }

    private String message;
    private String name;

}
