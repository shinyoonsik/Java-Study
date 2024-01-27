package org.example.factory;

import java.util.List;

public abstract class DataFactory<T>{
    public List<T> createAll(){
        return List.of(
                createObject(),
                createObject(),
                createObject()
        );
    }

    public T createOne(){
        return createObject();
    }

    protected abstract T createObject();
}
