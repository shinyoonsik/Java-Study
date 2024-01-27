package org.example.factory;

public class UserDataFactory extends DataFactory<User> {

    private static UserDataFactory userDataFactory;

    private UserDataFactory() {
    }

    public synchronized static UserDataFactory getInstance() {
        if (userDataFactory == null) {
            userDataFactory = new UserDataFactory();
        }
        return userDataFactory;
    }

    @Override
    protected User createObject() {
        return User.builder()
                .name("유저")
                .age(12)
                .gender("man")
                .build();
    }
}
