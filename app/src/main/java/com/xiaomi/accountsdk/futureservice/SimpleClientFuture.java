package com.xiaomi.accountsdk.futureservice;

import java.util.concurrent.ExecutionException;

/* loaded from: classes2.dex */
public class SimpleClientFuture<DataType> extends ClientFuture<DataType, DataType> {
    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture
    protected DataType convertServerDataToClientData(DataType datatype) throws Throwable {
        return datatype;
    }

    public SimpleClientFuture() {
        super(null);
    }

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture
    public void interpretExecutionException(ExecutionException executionException) throws Exception {
        throw executionException;
    }
}
