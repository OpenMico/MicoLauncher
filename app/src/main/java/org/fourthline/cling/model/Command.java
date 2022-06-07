package org.fourthline.cling.model;

/* loaded from: classes5.dex */
public interface Command<T> {
    void execute(ServiceManager<T> serviceManager) throws Exception;
}
