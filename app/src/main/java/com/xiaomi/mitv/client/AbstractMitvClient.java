package com.xiaomi.mitv.client;

import com.xiaomi.mitv.annotation.Required;
import com.xiaomi.mitv.entity.BaseOrderParam;
import com.xiaomi.mitv.exception.MitvCommonException;
import com.xiaomi.mitv.utils.BeanUtil;
import com.xiaomi.mitv.utils.HttpUtil;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public abstract class AbstractMitvClient {
    private static final String URL_EQUAL_SIGN = "=";
    private static final String URL_PATH_CHARACTER = "/";
    protected static final String URL_QS_MARK = "?";
    private static final String URL_SYMBOL_AND = "&";

    protected static String commonCreateOrder(BaseOrderParam baseOrderParam, String str, boolean z) throws MitvCommonException {
        try {
            validateParam(baseOrderParam);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(baseOrderParam.getDeviceID());
            sb.append("/");
            sb.append(baseOrderParam.getPlatform());
            sb.append("/");
            sb.append(baseOrderParam.getSdk_version());
            sb.append("/");
            sb.append(baseOrderParam.getLanguage());
            sb.append("/");
            sb.append(baseOrderParam.getCountry());
            String buildUrl = buildUrl(sb, BeanUtil.convert2map(baseOrderParam, false));
            if (z) {
                return HttpUtil.doGet(buildUrl);
            }
            return HttpUtil.doHttpsGetAllowAllSSL(buildUrl);
        } catch (MitvCommonException e) {
            throw e;
        } catch (Exception e2) {
            throw new MitvCommonException(e2);
        }
    }

    protected static String buildUrl(StringBuilder sb, Map<String, Object> map) {
        sb.append(URL_QS_MARK);
        Set<String> keySet = map.keySet();
        int i = 1;
        for (String str : keySet) {
            Object obj = map.get(str);
            sb.append(str);
            sb.append(URL_EQUAL_SIGN);
            if (obj instanceof String) {
                obj = URLEncoder.encode((String) obj);
            }
            sb.append(obj);
            if (i != keySet.size()) {
                sb.append("&");
            }
            i++;
        }
        return sb.toString();
    }

    protected static <T> void validateParam(T t) throws MitvCommonException {
        for (Field field : getAllFields(t)) {
            if (field.isAnnotationPresent(Required.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(t) == null) {
                        throw new MitvCommonException(field.getName() + "不能为空");
                    }
                } catch (IllegalAccessException e) {
                    throw new MitvCommonException(e);
                }
            }
        }
    }

    protected static List<Field> getAllFields(Object obj) {
        ArrayList arrayList = new ArrayList();
        for (Class<?> cls = obj.getClass(); cls != null; cls = cls.getSuperclass()) {
            arrayList.addAll(new ArrayList(Arrays.asList(cls.getDeclaredFields())));
        }
        return arrayList;
    }
}
