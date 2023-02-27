package com.wings.notificationservice.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.wings.notificationservice.constants.AppConstants.THREAD_POOL_COUNT;

@Service
public class WingsExecutorService {
    private ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_COUNT);

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
