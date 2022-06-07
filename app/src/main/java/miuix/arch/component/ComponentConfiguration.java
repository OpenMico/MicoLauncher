package miuix.arch.component;

import androidx.annotation.Keep;
import java.util.List;
import java.util.Map;

@Keep
/* loaded from: classes5.dex */
public interface ComponentConfiguration {
    Map<String, AppComponentDelegate<?>> createAppComponentDelegates();

    Map<Integer, Map<Integer, List<Task>>> createBackgroundComponentInitMap();

    Map<Integer, List<Task>> createMainComponentInitMap();

    Map<String, Task> createOnEventTaskMap();

    String getComponentManagerDomain();
}
