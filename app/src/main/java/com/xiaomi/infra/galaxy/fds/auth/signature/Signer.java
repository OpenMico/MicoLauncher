package com.xiaomi.infra.galaxy.fds.auth.signature;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedListMultimap;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.infra.galaxy.fds.SubResource;
import com.xiaomi.infra.galaxy.fds.exception.GalaxyFDSException;
import com.xiaomi.infra.galaxy.fds.model.HttpMethod;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.mipush.sdk.Constants;
import io.netty.util.internal.StringUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* loaded from: classes3.dex */
public class Signer {
    private static final Log a = LogFactory.getLog(Signer.class);
    private static final Set<String> b = new HashSet();
    private static final String c = XiaomiHeader.DATE.getName();

    static String a(String str) {
        return str == null ? "" : str;
    }

    static {
        for (SubResource subResource : SubResource.values()) {
            b.add(subResource.getName());
        }
    }

    public static byte[] sign(HttpMethod httpMethod, URI uri, LinkedListMultimap<String, String> linkedListMultimap, String str, SignAlgorithm signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        Preconditions.checkNotNull(httpMethod);
        Preconditions.checkNotNull(uri);
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(signAlgorithm);
        String a2 = a(httpMethod, uri, linkedListMultimap);
        if (a.isDebugEnabled()) {
            Log log = a;
            log.debug("Sign for request: " + httpMethod + StringUtils.SPACE + uri + ", stringToSign=" + a2);
        }
        Mac instance = Mac.getInstance(signAlgorithm.name());
        instance.init(new SecretKeySpec(str.getBytes(), signAlgorithm.name()));
        return instance.doFinal(a2.getBytes());
    }

    public static String signToBase64(HttpMethod httpMethod, URI uri, LinkedListMultimap linkedListMultimap, String str, SignAlgorithm signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        return a.a(sign(httpMethod, uri, linkedListMultimap, str, signAlgorithm));
    }

    public static URI generatePresignedUri(String str, String str2, String str3, List<String> list, Date date, HttpMethod httpMethod, String str4, String str5, SignAlgorithm signAlgorithm) throws GalaxyFDSException {
        URI uri;
        try {
            URI uri2 = new URI(str);
            if (list != null && !list.isEmpty()) {
                uri = new URI(uri2.getScheme(), null, uri2.getHost(), uri2.getPort(), "/" + str2 + "/" + str3, org.apache.commons.lang.StringUtils.join(list, MusicGroupListActivity.SPECIAL_SYMBOL) + MusicGroupListActivity.SPECIAL_SYMBOL + "GalaxyAccessKeyId=" + str4 + MusicGroupListActivity.SPECIAL_SYMBOL + "Expires=" + date.getTime(), null);
                return new URI(uri.toString() + MusicGroupListActivity.SPECIAL_SYMBOL + "Signature=" + signToBase64(httpMethod, uri, null, str5, signAlgorithm));
            }
            uri = new URI(uri2.getScheme(), null, uri2.getHost(), uri2.getPort(), "/" + str2 + "/" + str3, "GalaxyAccessKeyId=" + str4 + MusicGroupListActivity.SPECIAL_SYMBOL + "Expires=" + date.getTime(), null);
            return new URI(uri.toString() + MusicGroupListActivity.SPECIAL_SYMBOL + "Signature=" + signToBase64(httpMethod, uri, null, str5, signAlgorithm));
        } catch (URISyntaxException e) {
            a.error("Invalid URI syntax", e);
            throw new GalaxyFDSException("Invalid URI syntax", e);
        } catch (InvalidKeyException e2) {
            a.error("Invalid secret key spec", e2);
            throw new GalaxyFDSException("Invalid secret key spec", e2);
        } catch (NoSuchAlgorithmException e3) {
            a.error("Unsupported signature algorithm:" + signAlgorithm, e3);
            throw new GalaxyFDSException("Unsupported signature algorithm:" + signAlgorithm, e3);
        }
    }

    public static String getAuthorizationHeader(HttpMethod httpMethod, URI uri, LinkedListMultimap linkedListMultimap, String str, String str2, SignAlgorithm signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        String signToBase64 = signToBase64(httpMethod, uri, linkedListMultimap, str2, signAlgorithm);
        return "Galaxy-V2 " + str + Constants.COLON_SEPARATOR + signToBase64;
    }

    static String a(HttpMethod httpMethod, URI uri, LinkedListMultimap<String, String> linkedListMultimap) {
        StringBuilder sb = new StringBuilder();
        sb.append(httpMethod.name());
        sb.append("\n");
        sb.append(a(linkedListMultimap, Common.CONTENT_MD5).get(0));
        sb.append("\n");
        sb.append(a(linkedListMultimap, Common.CONTENT_TYPE).get(0));
        sb.append("\n");
        long b2 = b(uri);
        if (b2 > 0) {
            sb.append(b2);
            sb.append("\n");
        } else {
            String str = "";
            if ("".equals(a(linkedListMultimap, c).get(0))) {
                str = a(linkedListMultimap, Common.DATE).get(0);
            }
            sb.append(a(str));
            sb.append("\n");
        }
        sb.append(a(linkedListMultimap));
        sb.append(a(uri));
        return sb.toString();
    }

    static String a(LinkedListMultimap<String, String> linkedListMultimap) {
        if (linkedListMultimap == null) {
            return "";
        }
        TreeMap treeMap = new TreeMap();
        for (String str : linkedListMultimap.keySet()) {
            String lowerCase = str.toLowerCase();
            if (lowerCase.startsWith("x-xiaomi-")) {
                treeMap.put(lowerCase, Joiner.on((char) StringUtil.COMMA).join(linkedListMultimap.get((LinkedListMultimap<String, String>) str)));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : treeMap.entrySet()) {
            sb.append((String) entry.getKey());
            sb.append(Constants.COLON_SEPARATOR);
            sb.append((String) entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    static String a(URI uri) {
        StringBuilder sb = new StringBuilder();
        sb.append(uri.getPath());
        TreeMap treeMap = new TreeMap();
        for (Map.Entry<String, String> entry : Utils.parseUriParameters(uri).entries()) {
            String key = entry.getKey();
            if (b.contains(key)) {
                treeMap.put(key, entry.getValue());
            }
        }
        if (!treeMap.isEmpty()) {
            sb.append("?");
            boolean z = true;
            for (Map.Entry entry2 : treeMap.entrySet()) {
                if (z) {
                    z = false;
                    sb.append((String) entry2.getKey());
                } else {
                    sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
                    sb.append((String) entry2.getKey());
                }
                if (!((String) entry2.getValue()).isEmpty()) {
                    sb.append("=");
                    sb.append((String) entry2.getValue());
                }
            }
        }
        return sb.toString();
    }

    static List<String> a(LinkedListMultimap<String, String> linkedListMultimap, String str) {
        LinkedList linkedList = new LinkedList();
        if (linkedListMultimap == null) {
            linkedList.add("");
            return linkedList;
        }
        List<String> list = linkedListMultimap.get((LinkedListMultimap<String, String>) str);
        if (list != null && !list.isEmpty()) {
            return list;
        }
        linkedList.add("");
        return linkedList;
    }

    static long b(URI uri) {
        List<String> list = Utils.parseUriParameters(uri).get((LinkedListMultimap<String, String>) "Expires");
        if (list == null || list.isEmpty()) {
            return 0L;
        }
        return Long.parseLong(list.get(0));
    }
}
