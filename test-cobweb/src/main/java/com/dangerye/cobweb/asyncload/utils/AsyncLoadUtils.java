package com.dangerye.cobweb.asyncload.utils;

import java.util.concurrent.*;

public final class AsyncLoadUtils<T> {

    private ExecutorService executorService;
    private FutureTask<T> futureTask;

    private AsyncLoadUtils() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public static <T> AsyncLoadUtils<T> load(AsyncFunction<T> function) {
        AsyncLoadUtils<T> result = new AsyncLoadUtils<>();
        result.setFutureTask(new FutureTask<>(function::action));
        result.getExecutorService().execute(result.getFutureTask());
        return result;
    }

    public T get(long timeout) {
        T result = null;
        try {
            result = futureTask.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            futureTask.cancel(true);
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
