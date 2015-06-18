package com.jaspersoft.jasperserver.jrsh.core.operation.async.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.async.AsyncOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.async.AsyncOperationResult;
import lombok.extern.log4j.Log4j;

import java.util.concurrent.Callable;

/**
 * Dummy import operation.
 */
@Log4j
@Master(name = "async-import")
public class AsyncImportOperation implements AsyncOperation {

    @Override
    public ListenableFuture<AsyncOperationResult> executeAsync(final Session session, final ListeningExecutorService service) {
        ListenableFuture<AsyncOperationResult> futureResult = service
                .submit(new Callable<AsyncOperationResult>() {
                    @Override
                    public AsyncOperationResult call() throws Exception {
                        SessionStorage storage = session.getStorage();
                        return new AsyncOperationResult(null, null, null, null, 0, 0, 0);
                    }
                });

        futureResult.addListener(new Runnable() {
            @Override
            public void run() {
                // Log
                log.info("Logged");
            }
        }, service);
        return futureResult;
    }
}
