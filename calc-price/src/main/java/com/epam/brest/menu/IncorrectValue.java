package com.epam.brest.menu;

public class IncorrectValue implements EnteredValue {

    @Override
    public Types getType() {
        return Types.INCORRECT;
    }
}
