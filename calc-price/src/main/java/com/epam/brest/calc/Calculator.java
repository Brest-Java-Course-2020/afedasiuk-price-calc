package com.epam.brest.calc;

import java.math.BigDecimal;

public interface Calculator {

    BigDecimal calc(BigDecimal weight, BigDecimal distance, BigDecimal pricePerKg, BigDecimal pricePerKm);

}
