package org.hapjs.features.channel.vui.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
/* loaded from: classes5.dex */
public class Location {
    @JsonProperty("longitude")
    private String a;
    @JsonProperty("latitude")
    private String b;

    public String getLongitude() {
        return this.a;
    }

    public void setLongitude(String str) {
        this.a = str;
    }

    public String getLatitude() {
        return this.b;
    }

    public void setLatitude(String str) {
        this.b = str;
    }

    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error parse";
        }
    }
}
