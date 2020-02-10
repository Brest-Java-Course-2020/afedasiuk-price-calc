package com.epam.brest.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class CSVFileReader implements FileReader {

    @Override
    public Map<Integer, BigDecimal> readData(String filePath) throws IOException {
        Map<Integer, BigDecimal> resultMap = new TreeMap<>();
        InputStream inputStream = getClass().getResourceAsStream("/" + filePath);
        try (InputStreamReader isr = new InputStreamReader(inputStream)) {
            BufferedReader br = new BufferedReader(isr);
            String line;
            String[] values;
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                resultMap.put(Integer.parseInt(values[0]), new BigDecimal(values[1]));
            }
        }
        return resultMap;
    }
}
