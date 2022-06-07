package io.realm;

import android.util.JsonReader;
import com.xiaomi.micolauncher.api.cache.ApiRealmObject;
import com.xiaomi.micolauncher.common.stat.MicoTrackEvent;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject;
import com.xiaomi.micolauncher.module.video.db.VideoRealmObject;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject;
import com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject;
import com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject;
import io.realm.BaseRealm;
import io.realm.annotations.RealmModule;
import io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy;
import io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy;
import io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy;
import io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy;
import io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy;
import io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy;
import io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy;
import io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@RealmModule
/* loaded from: classes5.dex */
class DefaultRealmModuleMediator extends RealmProxyMediator {
    private static final Set<Class<? extends RealmModel>> a;

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        return true;
    }

    DefaultRealmModuleMediator() {
    }

    static {
        HashSet hashSet = new HashSet(8);
        hashSet.add(ApiRealmObject.class);
        hashSet.add(MicoTrackEvent.class);
        hashSet.add(AlarmRealmObject.class);
        hashSet.add(HourlyChimeObject.class);
        hashSet.add(CallBlackListRealmObject.class);
        hashSet.add(VideoData.class);
        hashSet.add(AppRealmObject.class);
        hashSet.add(VideoRealmObject.class);
        a = Collections.unmodifiableSet(hashSet);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap hashMap = new HashMap(8);
        hashMap.put(ApiRealmObject.class, com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(MicoTrackEvent.class, com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(AlarmRealmObject.class, com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(HourlyChimeObject.class, com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(CallBlackListRealmObject.class, com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(VideoData.class, com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(AppRealmObject.class, com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(VideoRealmObject.class, com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.getExpectedObjectSchemaInfo());
        return hashMap;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> cls, OsSchemaInfo osSchemaInfo) {
        checkClass(cls);
        if (cls.equals(ApiRealmObject.class)) {
            return com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(MicoTrackEvent.class)) {
            return com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(AlarmRealmObject.class)) {
            return com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(HourlyChimeObject.class)) {
            return com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(CallBlackListRealmObject.class)) {
            return com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(VideoData.class)) {
            return com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(AppRealmObject.class)) {
            return com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(VideoRealmObject.class)) {
            return com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.createColumnInfo(osSchemaInfo);
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public String getSimpleClassNameImpl(Class<? extends RealmModel> cls) {
        checkClass(cls);
        if (cls.equals(ApiRealmObject.class)) {
            return com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(MicoTrackEvent.class)) {
            return com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(AlarmRealmObject.class)) {
            return com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(HourlyChimeObject.class)) {
            return com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(CallBlackListRealmObject.class)) {
            return com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(VideoData.class)) {
            return com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(AppRealmObject.class)) {
            return com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(VideoRealmObject.class)) {
            return com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        try {
            realmObjectContext.set((BaseRealm) obj, row, columnInfo, z, list);
            checkClass(cls);
            if (cls.equals(ApiRealmObject.class)) {
                return cls.cast(new com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy());
            }
            if (cls.equals(MicoTrackEvent.class)) {
                return cls.cast(new com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy());
            }
            if (cls.equals(AlarmRealmObject.class)) {
                return cls.cast(new com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy());
            }
            if (cls.equals(HourlyChimeObject.class)) {
                return cls.cast(new com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy());
            }
            if (cls.equals(CallBlackListRealmObject.class)) {
                return cls.cast(new com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy());
            }
            if (cls.equals(VideoData.class)) {
                return cls.cast(new com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy());
            }
            if (cls.equals(AppRealmObject.class)) {
                return cls.cast(new com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy());
            }
            if (cls.equals(VideoRealmObject.class)) {
                return cls.cast(new com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy());
            }
            throw getMissingProxyClassException(cls);
        } finally {
            realmObjectContext.clear();
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return a;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        Class<?> superclass = e instanceof RealmObjectProxy ? e.getClass().getSuperclass() : e.getClass();
        if (superclass.equals(ApiRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.a) realm.getSchema().c(ApiRealmObject.class), (ApiRealmObject) e, z, map, set)));
        }
        if (superclass.equals(MicoTrackEvent.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.a) realm.getSchema().c(MicoTrackEvent.class), (MicoTrackEvent) e, z, map, set)));
        }
        if (superclass.equals(AlarmRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.a) realm.getSchema().c(AlarmRealmObject.class), (AlarmRealmObject) e, z, map, set)));
        }
        if (superclass.equals(HourlyChimeObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.a) realm.getSchema().c(HourlyChimeObject.class), (HourlyChimeObject) e, z, map, set)));
        }
        if (superclass.equals(CallBlackListRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.a) realm.getSchema().c(CallBlackListRealmObject.class), (CallBlackListRealmObject) e, z, map, set)));
        }
        if (superclass.equals(VideoData.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.a) realm.getSchema().c(VideoData.class), (VideoData) e, z, map, set)));
        }
        if (superclass.equals(AppRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.a) realm.getSchema().c(AppRealmObject.class), (AppRealmObject) e, z, map, set)));
        }
        if (superclass.equals(VideoRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.a) realm.getSchema().c(VideoRealmObject.class), (VideoRealmObject) e, z, map, set)));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(ApiRealmObject.class)) {
            com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insert(realm, (ApiRealmObject) realmModel, map);
        } else if (superclass.equals(MicoTrackEvent.class)) {
            com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.insert(realm, (MicoTrackEvent) realmModel, map);
        } else if (superclass.equals(AlarmRealmObject.class)) {
            com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insert(realm, (AlarmRealmObject) realmModel, map);
        } else if (superclass.equals(HourlyChimeObject.class)) {
            com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.insert(realm, (HourlyChimeObject) realmModel, map);
        } else if (superclass.equals(CallBlackListRealmObject.class)) {
            com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insert(realm, (CallBlackListRealmObject) realmModel, map);
        } else if (superclass.equals(VideoData.class)) {
            com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insert(realm, (VideoData) realmModel, map);
        } else if (superclass.equals(AppRealmObject.class)) {
            com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.insert(realm, (AppRealmObject) realmModel, map);
        } else if (superclass.equals(VideoRealmObject.class)) {
            com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.insert(realm, (VideoRealmObject) realmModel, map);
        } else {
            throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap hashMap = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel realmModel = (RealmModel) it.next();
            Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
            if (superclass.equals(ApiRealmObject.class)) {
                com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insert(realm, (ApiRealmObject) realmModel, hashMap);
            } else if (superclass.equals(MicoTrackEvent.class)) {
                com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.insert(realm, (MicoTrackEvent) realmModel, hashMap);
            } else if (superclass.equals(AlarmRealmObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insert(realm, (AlarmRealmObject) realmModel, hashMap);
            } else if (superclass.equals(HourlyChimeObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.insert(realm, (HourlyChimeObject) realmModel, hashMap);
            } else if (superclass.equals(CallBlackListRealmObject.class)) {
                com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insert(realm, (CallBlackListRealmObject) realmModel, hashMap);
            } else if (superclass.equals(VideoData.class)) {
                com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insert(realm, (VideoData) realmModel, hashMap);
            } else if (superclass.equals(AppRealmObject.class)) {
                com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.insert(realm, (AppRealmObject) realmModel, hashMap);
            } else if (superclass.equals(VideoRealmObject.class)) {
                com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.insert(realm, (VideoRealmObject) realmModel, hashMap);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
            if (!it.hasNext()) {
                return;
            }
            if (superclass.equals(ApiRealmObject.class)) {
                com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insert(realm, it, hashMap);
            } else if (superclass.equals(MicoTrackEvent.class)) {
                com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.insert(realm, it, hashMap);
            } else if (superclass.equals(AlarmRealmObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insert(realm, it, hashMap);
            } else if (superclass.equals(HourlyChimeObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.insert(realm, it, hashMap);
            } else if (superclass.equals(CallBlackListRealmObject.class)) {
                com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insert(realm, it, hashMap);
            } else if (superclass.equals(VideoData.class)) {
                com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insert(realm, it, hashMap);
            } else if (superclass.equals(AppRealmObject.class)) {
                com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.insert(realm, it, hashMap);
            } else if (superclass.equals(VideoRealmObject.class)) {
                com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.insert(realm, it, hashMap);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(ApiRealmObject.class)) {
            com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insertOrUpdate(realm, (ApiRealmObject) realmModel, map);
        } else if (superclass.equals(MicoTrackEvent.class)) {
            com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.insertOrUpdate(realm, (MicoTrackEvent) realmModel, map);
        } else if (superclass.equals(AlarmRealmObject.class)) {
            com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insertOrUpdate(realm, (AlarmRealmObject) realmModel, map);
        } else if (superclass.equals(HourlyChimeObject.class)) {
            com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.insertOrUpdate(realm, (HourlyChimeObject) realmModel, map);
        } else if (superclass.equals(CallBlackListRealmObject.class)) {
            com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insertOrUpdate(realm, (CallBlackListRealmObject) realmModel, map);
        } else if (superclass.equals(VideoData.class)) {
            com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insertOrUpdate(realm, (VideoData) realmModel, map);
        } else if (superclass.equals(AppRealmObject.class)) {
            com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.insertOrUpdate(realm, (AppRealmObject) realmModel, map);
        } else if (superclass.equals(VideoRealmObject.class)) {
            com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.insertOrUpdate(realm, (VideoRealmObject) realmModel, map);
        } else {
            throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap hashMap = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel realmModel = (RealmModel) it.next();
            Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
            if (superclass.equals(ApiRealmObject.class)) {
                com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insertOrUpdate(realm, (ApiRealmObject) realmModel, hashMap);
            } else if (superclass.equals(MicoTrackEvent.class)) {
                com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.insertOrUpdate(realm, (MicoTrackEvent) realmModel, hashMap);
            } else if (superclass.equals(AlarmRealmObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insertOrUpdate(realm, (AlarmRealmObject) realmModel, hashMap);
            } else if (superclass.equals(HourlyChimeObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.insertOrUpdate(realm, (HourlyChimeObject) realmModel, hashMap);
            } else if (superclass.equals(CallBlackListRealmObject.class)) {
                com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insertOrUpdate(realm, (CallBlackListRealmObject) realmModel, hashMap);
            } else if (superclass.equals(VideoData.class)) {
                com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insertOrUpdate(realm, (VideoData) realmModel, hashMap);
            } else if (superclass.equals(AppRealmObject.class)) {
                com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.insertOrUpdate(realm, (AppRealmObject) realmModel, hashMap);
            } else if (superclass.equals(VideoRealmObject.class)) {
                com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.insertOrUpdate(realm, (VideoRealmObject) realmModel, hashMap);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
            if (!it.hasNext()) {
                return;
            }
            if (superclass.equals(ApiRealmObject.class)) {
                com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else if (superclass.equals(MicoTrackEvent.class)) {
                com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else if (superclass.equals(AlarmRealmObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else if (superclass.equals(HourlyChimeObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else if (superclass.equals(CallBlackListRealmObject.class)) {
                com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else if (superclass.equals(VideoData.class)) {
                com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else if (superclass.equals(AppRealmObject.class)) {
                com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else if (superclass.equals(VideoRealmObject.class)) {
                com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        checkClass(cls);
        if (cls.equals(ApiRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(MicoTrackEvent.class)) {
            return cls.cast(com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(AlarmRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(HourlyChimeObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(CallBlackListRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(VideoData.class)) {
            return cls.cast(com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(AppRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(VideoRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        checkClass(cls);
        if (cls.equals(ApiRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(MicoTrackEvent.class)) {
            return cls.cast(com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(AlarmRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(HourlyChimeObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(CallBlackListRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(VideoData.class)) {
            return cls.cast(com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(AppRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(VideoRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Class<? super Object> superclass = e.getClass().getSuperclass();
        if (superclass.equals(ApiRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.createDetachedCopy((ApiRealmObject) e, 0, i, map)));
        }
        if (superclass.equals(MicoTrackEvent.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.createDetachedCopy((MicoTrackEvent) e, 0, i, map)));
        }
        if (superclass.equals(AlarmRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.createDetachedCopy((AlarmRealmObject) e, 0, i, map)));
        }
        if (superclass.equals(HourlyChimeObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.createDetachedCopy((HourlyChimeObject) e, 0, i, map)));
        }
        if (superclass.equals(CallBlackListRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.createDetachedCopy((CallBlackListRealmObject) e, 0, i, map)));
        }
        if (superclass.equals(VideoData.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.createDetachedCopy((VideoData) e, 0, i, map)));
        }
        if (superclass.equals(AppRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.createDetachedCopy((AppRealmObject) e, 0, i, map)));
        }
        if (superclass.equals(VideoRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.createDetachedCopy((VideoRealmObject) e, 0, i, map)));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }
}
