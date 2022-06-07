package org.seamless.util.logging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class LoggingUtil {
    public static final String DEFAULT_CONFIG = "default-logging.properties";

    public static void loadDefaultConfiguration() throws Exception {
        loadDefaultConfiguration(null);
    }

    public static void loadDefaultConfiguration(InputStream inputStream) throws Exception {
        if (System.getProperty("java.util.logging.config.file") == null) {
            if (inputStream == null) {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_CONFIG);
            }
            if (inputStream != null) {
                ArrayList arrayList = new ArrayList();
                LogManager.getLogManager().readConfiguration(spliceHandlers(inputStream, arrayList));
                resetRootHandler(instantiateHandlers(arrayList));
            }
        }
    }

    public static Handler[] instantiateHandlers(List<String> list) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            arrayList.add((Handler) Thread.currentThread().getContextClassLoader().loadClass(str).newInstance());
        }
        return (Handler[]) arrayList.toArray(new Handler[arrayList.size()]);
    }

    public static InputStream spliceHandlers(InputStream inputStream, List<String> list) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        StringBuilder sb = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList();
        for (Map.Entry entry : properties.entrySet()) {
            if (entry.getKey().equals("handlers")) {
                arrayList.add(entry.getValue().toString());
            } else {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("\n");
            }
        }
        for (String str : arrayList) {
            for (String str2 : str.trim().split(StringUtils.SPACE)) {
                list.add(str2.trim());
            }
        }
        return new ByteArrayInputStream(sb.toString().getBytes("ISO-8859-1"));
    }

    public static void resetRootHandler(Handler... handlerArr) {
        Logger logger = LogManager.getLogManager().getLogger("");
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        for (Handler handler2 : handlerArr) {
            if (handler2 != null) {
                LogManager.getLogManager().getLogger("").addHandler(handler2);
            }
        }
    }
}
