package com.jaspersoft.jasperserver.jrsh.core.operation.async;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

public interface AsyncOperation {

    ListenableFuture<AsyncOperationResult> executeAsync(Session session, ListeningExecutorService service);

}
