package androidx.media.utils;

import android.annotation.SuppressLint;
import androidx.annotation.RestrictTo;

/* loaded from: classes.dex */
public final class MediaConstants {
    @SuppressLint({"IntentName"})
    public static final String BROWSER_ROOT_HINTS_KEY_MEDIA_ART_SIZE_PIXELS = "android.media.extras.MEDIA_ART_SIZE_HINT_PIXELS";
    @SuppressLint({"IntentName"})
    public static final String BROWSER_ROOT_HINTS_KEY_ROOT_CHILDREN_LIMIT = "androidx.media.MediaBrowserCompat.Extras.KEY_ROOT_CHILDREN_LIMIT";
    @SuppressLint({"IntentName"})
    public static final String BROWSER_ROOT_HINTS_KEY_ROOT_CHILDREN_SUPPORTED_FLAGS = "androidx.media.MediaBrowserCompat.Extras.KEY_ROOT_CHILDREN_SUPPORTED_FLAGS";
    @SuppressLint({"IntentName"})
    public static final String BROWSER_SERVICE_EXTRAS_KEY_SEARCH_SUPPORTED = "android.media.browse.SEARCH_SUPPORTED";
    @SuppressLint({"IntentName"})
    public static final String DESCRIPTION_EXTRAS_KEY_COMPLETION_STATUS = "android.media.extra.PLAYBACK_STATUS";
    @SuppressLint({"IntentName"})
    public static final String DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_BROWSABLE = "android.media.browse.CONTENT_STYLE_BROWSABLE_HINT";
    @SuppressLint({"IntentName"})
    public static final String DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_GROUP_TITLE = "android.media.browse.CONTENT_STYLE_GROUP_TITLE_HINT";
    @SuppressLint({"IntentName"})
    public static final String DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_PLAYABLE = "android.media.browse.CONTENT_STYLE_PLAYABLE_HINT";
    public static final int DESCRIPTION_EXTRAS_VALUE_COMPLETION_STATUS_FULLY_PLAYED = 2;
    public static final int DESCRIPTION_EXTRAS_VALUE_COMPLETION_STATUS_NOT_PLAYED = 0;
    public static final int DESCRIPTION_EXTRAS_VALUE_COMPLETION_STATUS_PARTIALLY_PLAYED = 1;
    public static final int DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_CATEGORY_GRID_ITEM = 4;
    public static final int DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_CATEGORY_LIST_ITEM = 3;
    public static final int DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_GRID_ITEM = 2;
    public static final int DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_LIST_ITEM = 1;
    @SuppressLint({"IntentName"})
    public static final String METADATA_KEY_CONTENT_ID = "androidx.media.MediaMetadatCompat.METADATA_KEY_CONTENT_ID";
    @SuppressLint({"IntentName"})
    public static final String METADATA_KEY_IS_ADVERTISEMENT = "android.media.metadata.ADVERTISEMENT";
    @SuppressLint({"IntentName"})
    public static final String METADATA_KEY_IS_EXPLICIT = "android.media.IS_EXPLICIT";
    @SuppressLint({"IntentName"})
    public static final String METADATA_KEY_NEXT_EPISODE_CONTENT_ID = "androidx.media.MediaMetadatCompat.METADATA_KEY_NEXT_EPISODE_CONTENT_ID";
    @SuppressLint({"IntentName"})
    public static final String METADATA_KEY_SERIES_CONTENT_ID = "androidx.media.MediaMetadatCompat.METADATA_KEY_SERIES_CONTENT_ID";
    public static final long METADATA_VALUE_ATTRIBUTE_PRESENT = 1;
    @SuppressLint({"IntentName"})
    public static final String PLAYBACK_STATE_EXTRAS_KEY_ERROR_RESOLUTION_ACTION_INTENT = "android.media.extras.ERROR_RESOLUTION_ACTION_INTENT";
    @SuppressLint({"IntentName"})
    public static final String PLAYBACK_STATE_EXTRAS_KEY_ERROR_RESOLUTION_ACTION_LABEL = "android.media.extras.ERROR_RESOLUTION_ACTION_LABEL";
    @SuppressLint({"IntentName"})
    public static final String PLAYBACK_STATE_EXTRAS_KEY_MEDIA_ID = "androidx.media.PlaybackStateCompat.Extras.KEY_MEDIA_ID";
    @SuppressLint({"IntentName"})
    public static final String SESSION_EXTRAS_KEY_ACCOUNT_NAME = "androidx.media.MediaSessionCompat.Extras.KEY_ACCOUNT_NAME";
    @SuppressLint({"IntentName"})
    public static final String SESSION_EXTRAS_KEY_ACCOUNT_TYPE = "androidx.media.MediaSessionCompat.Extras.KEY_ACCOUNT_TYPE";
    @SuppressLint({"IntentName"})
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String SESSION_EXTRAS_KEY_AUTHTOKEN = "androidx.media.MediaSessionCompat.Extras.KEY_AUTHTOKEN";
    @SuppressLint({"IntentName"})
    public static final String SESSION_EXTRAS_KEY_SLOT_RESERVATION_SKIP_TO_NEXT = "android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT";
    @SuppressLint({"IntentName"})
    public static final String SESSION_EXTRAS_KEY_SLOT_RESERVATION_SKIP_TO_PREV = "android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS";
    @SuppressLint({"IntentName"})
    public static final String TRANSPORT_CONTROLS_EXTRAS_KEY_LEGACY_STREAM_TYPE = "android.media.session.extra.LEGACY_STREAM_TYPE";
    @SuppressLint({"IntentName"})
    public static final String TRANSPORT_CONTROLS_EXTRAS_KEY_SHUFFLE = "androidx.media.MediaControllerCompat.TransportControls.extras.KEY_SHUFFLE";

    private MediaConstants() {
    }
}
