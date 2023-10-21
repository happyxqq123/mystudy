package com.asyncHttp.om;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Test1 {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";
        Car car = objectMapper.readValue(carJson,Car.class);
        System.out.println(car.getBrand()+" "+car.getDoors());
    }
}
