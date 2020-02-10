package com.epam.brest.menu;

public interface EnteredValue {
    enum Types {EXIT, INCORRECT, VALUE}
    Types getType();
}

