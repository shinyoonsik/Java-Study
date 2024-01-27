package org.example.factory;

public class CarDataFactory extends DataFactory<Car> {
    private static CarDataFactory carDataFactory;

    private CarDataFactory() {
    }

    public synchronized static CarDataFactory getInstance() {
        if(carDataFactory == null){
            carDataFactory = new CarDataFactory();
        }
        return carDataFactory;
    }

    @Override
    protected Car createObject() {
        return Car.builder()
                .name("myCar")
                .type("SUV")
                .build();
    }
}
