package org.slf4j;

import java.util.Map;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMDCBinder;
import org.slf4j.spi.MDCAdapter;

/* loaded from: classes4.dex */
public class MDC {
    static MDCAdapter a;

    private MDC() {
    }

    static {
        try {
            a = StaticMDCBinder.SINGLETON.getMDCA();
        } catch (Exception e) {
            Util.report("MDC binding unsuccessful.", e);
        } catch (NoClassDefFoundError e2) {
            a = new NOPMDCAdapter();
            String message = e2.getMessage();
            if (message == null || message.indexOf("StaticMDCBinder") == -1) {
                throw e2;
            }
            Util.report("Failed to load class \"org.slf4j.impl.StaticMDCBinder\".");
            Util.report("Defaulting to no-operation MDCAdapter implementation.");
            Util.report("See http://www.slf4j.org/codes.html#no_static_mdc_binder for further details.");
        }
    }

    public static void put(String str, String str2) throws IllegalArgumentException {
        if (str != null) {
            MDCAdapter mDCAdapter = a;
            if (mDCAdapter != null) {
                mDCAdapter.put(str, str2);
                return;
            }
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        throw new IllegalArgumentException("key parameter cannot be null");
    }

    public static String get(String str) throws IllegalArgumentException {
        if (str != null) {
            MDCAdapter mDCAdapter = a;
            if (mDCAdapter != null) {
                return mDCAdapter.get(str);
            }
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        throw new IllegalArgumentException("key parameter cannot be null");
    }

    public static void remove(String str) throws IllegalArgumentException {
        if (str != null) {
            MDCAdapter mDCAdapter = a;
            if (mDCAdapter != null) {
                mDCAdapter.remove(str);
                return;
            }
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        throw new IllegalArgumentException("key parameter cannot be null");
    }

    public static void clear() {
        MDCAdapter mDCAdapter = a;
        if (mDCAdapter != null) {
            mDCAdapter.clear();
            return;
        }
        throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
    }

    public static Map<String, String> getCopyOfContextMap() {
        MDCAdapter mDCAdapter = a;
        if (mDCAdapter != null) {
            return mDCAdapter.getCopyOfContextMap();
        }
        throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
    }

    public static void setContextMap(Map<String, String> map) {
        MDCAdapter mDCAdapter = a;
        if (mDCAdapter != null) {
            mDCAdapter.setContextMap(map);
            return;
        }
        throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
    }

    public static MDCAdapter getMDCAdapter() {
        return a;
    }
}
