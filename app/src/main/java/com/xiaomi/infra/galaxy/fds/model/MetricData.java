package com.xiaomi.infra.galaxy.fds.model;

/* loaded from: classes3.dex */
public class MetricData {
    private MetricType a;
    private String b;
    private long c;
    private long d;

    /* loaded from: classes3.dex */
    public enum MetricType {
        Latency,
        Throughput,
        Counter
    }

    public MetricData() {
    }

    public MetricData(MetricType metricType, String str, long j, long j2) {
        this.a = metricType;
        this.b = str;
        this.c = j;
        this.d = j2;
    }

    public MetricType getMetricType() {
        return this.a;
    }

    public void setMetricType(MetricType metricType) {
        this.a = metricType;
    }

    public MetricData withMetricType(MetricType metricType) {
        this.a = metricType;
        return this;
    }

    public String getMetricName() {
        return this.b;
    }

    public void setMetricName(String str) {
        this.b = str;
    }

    public MetricData withMetricName(String str) {
        this.b = str;
        return this;
    }

    public long getValue() {
        return this.c;
    }

    public void setValue(long j) {
        this.c = j;
    }

    public MetricData withValue(long j) {
        this.c = j;
        return this;
    }

    public long getTimestamp() {
        return this.d;
    }

    public void setTimestamp(long j) {
        this.d = j;
    }

    public MetricData withTimeStamp(long j) {
        this.d = j;
        return this;
    }
}
