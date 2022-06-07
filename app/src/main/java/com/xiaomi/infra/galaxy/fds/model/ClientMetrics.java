package com.xiaomi.infra.galaxy.fds.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class ClientMetrics {
    private List<MetricData> a = new ArrayList();

    /* loaded from: classes3.dex */
    public enum LatencyMetricType {
        ExecutionTime
    }

    public List<MetricData> getMetrics() {
        return this.a;
    }

    public void setMetrics(List<MetricData> list) {
        this.a = list;
    }

    public void add(MetricData metricData) {
        this.a.add(metricData);
    }
}
