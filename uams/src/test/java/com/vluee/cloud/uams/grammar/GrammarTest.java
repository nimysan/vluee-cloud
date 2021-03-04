package com.vluee.cloud.uams.grammar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrammarTest {


    /**
     * 可以抓到
     */
    @Test
    public void testCatchRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            try {
                GrammarTest.this.throwRuntimeException();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void throwRuntimeException() {
        throw new RuntimeException("A runtime exception happened");
    }
}
