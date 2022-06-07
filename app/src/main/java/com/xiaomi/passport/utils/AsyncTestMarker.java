package com.xiaomi.passport.utils;

/* loaded from: classes4.dex */
public final class AsyncTestMarker {
    private static final Marker DEFAULT_MARKER = new MarkerDefaultImpl();
    private static volatile Marker marker = DEFAULT_MARKER;

    /* loaded from: classes4.dex */
    public interface Marker {
        void decrement();

        void increment();
    }

    public static void setMarker(Marker marker2) {
        marker = marker2;
    }

    public static void resetMarker() {
        marker = DEFAULT_MARKER;
    }

    public static void increment() {
        marker.increment();
    }

    public static void decrement() {
        marker.decrement();
    }

    /* loaded from: classes4.dex */
    private static class MarkerDefaultImpl implements Marker {
        @Override // com.xiaomi.passport.utils.AsyncTestMarker.Marker
        public void decrement() {
        }

        @Override // com.xiaomi.passport.utils.AsyncTestMarker.Marker
        public void increment() {
        }

        private MarkerDefaultImpl() {
        }
    }
}
