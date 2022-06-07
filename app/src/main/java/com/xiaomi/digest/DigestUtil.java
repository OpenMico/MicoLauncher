package com.xiaomi.digest;

import com.xiaomi.micolauncher.module.skill.manager.SkillStatHelper;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes3.dex */
public class DigestUtil {
    public static final String[] MQS_JE_EXP_LIST = {"java.lang.OutOfMemoryError", "No space left on device"};

    private static String a(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("NoSuchAlgorithmException", e2);
        }
    }

    public static String calcuateJavaDigest(String str) {
        String[] split = str.replaceAll("\\t", "").split("\\n");
        StringBuilder sb = new StringBuilder();
        int min = Math.min(split.length, 20);
        for (int i = 0; i < min; i++) {
            split[i] = split[i].replaceAll("((java:)|(length=)|(index=)|(Index:)|(Size:))\\d+", "$1XX").replaceAll("\\$[0-9a-fA-F]{1,10}@[0-9a-fA-F]{1,10}|@[0-9a-fA-F]{1,10}|0x[0-9a-fA-F]{1,10}", "XX").replaceAll("\\d+[B,KB,MB]*", "");
        }
        for (int i2 = 0; i2 < min && (!split[i2].contains("...") || !split[i2].contains(SkillStatHelper.SKILL_STAT_MORE)); i2++) {
            sb.append(split[i2]);
            sb.append('\n');
        }
        return a(sb.toString());
    }
}
