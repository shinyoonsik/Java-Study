package org.example.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesTest {

    @Test
    @DisplayName("resources 싱글톤 테스트")
    void test() throws IOException {
        Resources resources1 = Resources.getResources();
        Resources resources2 = Resources.getResources();

        assertEquals(resources1, resources2);
    }

    @Test
    @DisplayName("resources 동작 테스트")
    void test2() throws IOException {
        Resources resources = Resources.getResources();
        String result = resources.get("hello.en");

        assertEquals("hello", result);
    }

}