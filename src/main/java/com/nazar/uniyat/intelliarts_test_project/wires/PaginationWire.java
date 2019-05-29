package com.nazar.uniyat.intelliarts_test_project.wires;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationWire {

    private String next;
    private List<?> result;

}
