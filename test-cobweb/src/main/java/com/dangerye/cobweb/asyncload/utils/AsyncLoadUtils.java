package com.dangerye.cobweb.asyncload.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public final class AsyncLoadUtils<T> {

    private ExecutorService executorService;
    private FutureTask<T> futureTask;

    private AsyncLoadUtils() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public static <T> AsyncLoadUtils<T> load(Callable<T> callable) {
        AsyncLoadUtils<T> result = new AsyncLoadUtils<>();
        result.setFutureTask(new FutureTask<>(callable));
        result.getExecutorService().execute(result.getFutureTask());
        return result;
    }

    public T get(long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        T result;
        try {
            result = futureTask.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            futureTask.cancel(true);
            throw e;
        } finally {
            executorService.shutdown();
        }
        return result;
    }

    private ExecutorService getExecutorService() {
        return executorService;
    }

    private FutureTask<T> getFutureTask() {
        return futureTask;
    }

    private void setFutureTask(FutureTask<T> futureTask) {
        this.futureTask = futureTask;
    }
}
