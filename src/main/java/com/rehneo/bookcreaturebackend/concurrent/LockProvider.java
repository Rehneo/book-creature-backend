package com.rehneo.bookcreaturebackend.concurrent;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

@Component
public class LockProvider {
    private final ReentrantLock lock = new ReentrantLock();


    @Bean
    public ReentrantLock getLock() {
        return lock;
    }
}
