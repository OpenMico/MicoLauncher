package com.xiaomi.accountsdk.account.data;

import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.ObjectUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class XiaomiUserCoreInfo {
    public final String avatarAddress;
    public final Calendar birthday;
    public final Education education;
    public final String emailAddress;
    public final Gender gender;
    public final Income income;
    public final boolean isSetSafeQuestions;
    public final String locale;
    public final String locationZipCode;
    public final String nickName;
    public final ArrayList<String> phoneList;
    public final String region;
    public final String safePhone;
    public final ArrayList<SnsInfo> snsInfoList;
    public final String userId;
    public final String userName;

    private XiaomiUserCoreInfo(String str, String str2, String str3, String str4, String str5, ArrayList<String> arrayList, ArrayList<SnsInfo> arrayList2, String str6, Gender gender, Calendar calendar, boolean z, String str7, String str8, String str9, Education education, Income income) {
        this.userId = str;
        this.userName = str2;
        this.nickName = str3;
        this.avatarAddress = str4;
        this.safePhone = str5;
        this.phoneList = arrayList;
        this.snsInfoList = arrayList2;
        this.emailAddress = str6;
        this.gender = gender;
        this.birthday = calendar;
        this.isSetSafeQuestions = z;
        this.locale = str7;
        this.region = str8;
        this.locationZipCode = str9;
        this.education = education;
        this.income = income;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private String mAvatarAddress;
        private Calendar mBirthday;
        private Education mEducation;
        private String mEmailAddress;
        private Gender mGender;
        private Income mIncome;
        private boolean mIsSetSafeQuestions;
        private String mLocale;
        private String mNickName;
        private ArrayList<String> mPhoneList;
        private String mRegion;
        private String mSafePhone;
        private ArrayList<SnsInfo> mSnsInfoList;
        private String mUserId;
        private String mUserName;
        private String mlocationZipCode;

        public Builder(String str) {
            this.mUserId = str;
        }

        public Builder setUserName(String str) {
            this.mUserName = str;
            return this;
        }

        @Deprecated
        public Builder setNickName(String str) {
            this.mNickName = str;
            return this;
        }

        public Builder setAvatarAddress(String str) {
            this.mAvatarAddress = str;
            return this;
        }

        public Builder setSafePhone(String str) {
            this.mSafePhone = str;
            return this;
        }

        public Builder setPhoneList(ArrayList<String> arrayList) {
            this.mPhoneList = arrayList;
            return this;
        }

        public Builder setEmailAddress(String str) {
            this.mEmailAddress = str;
            return this;
        }

        public Builder setGender(Gender gender) {
            this.mGender = gender;
            return this;
        }

        public Builder setBirthday(Calendar calendar) {
            this.mBirthday = calendar;
            return this;
        }

        public Builder setIsSetSafeQuestions(boolean z) {
            this.mIsSetSafeQuestions = z;
            return this;
        }

        public Builder setLocale(String str) {
            this.mLocale = str;
            return this;
        }

        public Builder setRegion(String str) {
            this.mRegion = str;
            return this;
        }

        public Builder setLocationZipCode(String str) {
            this.mlocationZipCode = str;
            return this;
        }

        public Builder setEducation(Education education) {
            this.mEducation = education;
            return this;
        }

        public Builder setIncome(Income income) {
            this.mIncome = income;
            return this;
        }

        public XiaomiUserCoreInfo build() {
            return new XiaomiUserCoreInfo(this.mUserId, this.mUserName, this.mNickName, this.mAvatarAddress, this.mSafePhone, this.mPhoneList, this.mSnsInfoList, this.mEmailAddress, this.mGender, this.mBirthday, this.mIsSetSafeQuestions, this.mLocale, this.mRegion, this.mlocationZipCode, this.mEducation, this.mIncome);
        }

        public void setSnsInfoList(ArrayList<SnsInfo> arrayList) {
            this.mSnsInfoList = arrayList;
        }
    }

    /* loaded from: classes2.dex */
    public static class SnsInfo {
        private static final String J_SNS_ALLOW_UNBIND = "allowUnbind";
        private static final String J_SNS_ICON = "snsIcon";
        private static final String J_SNS_NICKNAME = "snsNickName";
        private static final String J_SNS_TYPE = "snsType";
        private static final String J_SNS_TYPE_NAME = "snsTypeName";
        private static final String TAG = "SnsInfo";
        private boolean allowUnbind;
        private final String icon;
        private final String nickName;
        private final int snsType;
        private final String snsTypeName;

        public SnsInfo(int i, String str, String str2, String str3, boolean z) {
            this.snsType = i;
            this.snsTypeName = str;
            this.nickName = str2;
            this.icon = str3;
            this.allowUnbind = z;
        }

        public String getNickName() {
            return this.nickName;
        }

        public int getSnsType() {
            return this.snsType;
        }

        public String getSnsTypeName() {
            return this.snsTypeName;
        }

        public boolean getSnsAllowUnbind() {
            return this.allowUnbind;
        }

        public String getIcon() {
            return this.icon;
        }

        public static ArrayList<SnsInfo> parseSnsList(List list) {
            ArrayList<SnsInfo> arrayList = new ArrayList<>();
            if (list != null) {
                for (Object obj : list) {
                    if (obj instanceof Map) {
                        arrayList.add(parseSnsInfoMap((Map) obj));
                    }
                }
            }
            return arrayList;
        }

        public static SnsInfo parseSnsInfoMap(Map map) {
            return new SnsInfo(XiaomiUserCoreInfo.getInt(map, J_SNS_TYPE, 0), XiaomiUserCoreInfo.getString(map, J_SNS_TYPE_NAME), XiaomiUserCoreInfo.getString(map, J_SNS_NICKNAME), XiaomiUserCoreInfo.getString(map, J_SNS_ICON), XiaomiUserCoreInfo.getBoolean(map, J_SNS_ALLOW_UNBIND, true));
        }

        public static JSONArray toJSONArray(ArrayList<SnsInfo> arrayList) {
            JSONArray jSONArray = new JSONArray();
            if (arrayList != null) {
                Iterator<SnsInfo> it = arrayList.iterator();
                while (it.hasNext()) {
                    SnsInfo next = it.next();
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put(J_SNS_TYPE, next.snsType);
                        jSONObject.put(J_SNS_TYPE_NAME, next.snsTypeName);
                        jSONObject.put(J_SNS_ICON, next.icon);
                        jSONObject.put(J_SNS_NICKNAME, next.nickName);
                        jSONObject.put(J_SNS_ALLOW_UNBIND, next.allowUnbind);
                        jSONArray.put(jSONObject);
                    } catch (JSONException e) {
                        AccountLog.w(TAG, e);
                    }
                }
            }
            return jSONArray;
        }

        public static List<SnsInfo> parseSnsListStr(String str) {
            List<Object> jsonArrayStringToList = ObjectUtils.jsonArrayStringToList(str);
            if (jsonArrayStringToList == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : jsonArrayStringToList) {
                if (obj instanceof Map) {
                    arrayList.add(parseSnsInfoMap((Map) obj));
                }
            }
            return arrayList;
        }

        public static SnsInfo findSnsInfoByServerType(List<SnsInfo> list, int i) {
            if (list == null) {
                return null;
            }
            for (SnsInfo snsInfo : list) {
                if (snsInfo != null && snsInfo.snsType == i) {
                    return snsInfo;
                }
            }
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public enum Income {
        LESS_2K("less2000"),
        BETWEEN_2K_4K("less4000"),
        BETWEEN_4K_6K("less6000"),
        BETWEEN_6K_8K("less8000"),
        BETWEEN_8K_12K("less12000"),
        OVER_12K("over12000");
        
        public final String level;

        Income(String str) {
            this.level = str;
        }

        public static Income getIncomeTypeByName(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Income[] values = values();
            for (Income income : values) {
                if (income.level.equals(str)) {
                    return income;
                }
            }
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public enum Education {
        MIDDLE_SCHOOL("junior"),
        PREP_SCHOOL("technical"),
        HIGH_SCHOOL("senior"),
        VOCATIONAL_SCHOOL("college"),
        COLLEGE("bachelor"),
        MASTER_DEGREE("master"),
        DOCTOR_AND_ABOVE("doctor");
        
        public final String level;

        Education(String str) {
            this.level = str;
        }

        public static Education getEducationTypeByName(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Education[] values = values();
            for (Education education : values) {
                if (education.level.equals(str)) {
                    return education;
                }
            }
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public enum Flag {
        BASE_INFO(1),
        BIND_ADDRESS(2),
        EXTRA_INFO(4),
        SETTING_INFO(8),
        SECURITY_STATUS(16);
        
        public final int value;

        Flag(int i) {
            this.value = i;
        }
    }

    public static String getString(Map map, String str) {
        if (map == null || TextUtils.isEmpty(str)) {
            return null;
        }
        Object obj = map.get(str);
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    public static int getInt(Map map, String str, int i) {
        if (map != null && !TextUtils.isEmpty(str)) {
            Object obj = map.get(str);
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
        }
        return i;
    }

    public static boolean getBoolean(Map map, String str, boolean z) {
        if (map != null && !TextUtils.isEmpty(str)) {
            Object obj = map.get(str);
            if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue();
            }
        }
        return z;
    }
}
