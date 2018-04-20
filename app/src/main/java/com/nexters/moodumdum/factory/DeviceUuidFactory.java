package com.nexters.moodumdum.factory;

/**
 * Created by kimhyehyeon on 2018. 2. 18..
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class DeviceUuidFactory {

    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected volatile static UUID uuid;

    public DeviceUuidFactory(Context context) {
        if (uuid == null) {
            synchronized (DeviceUuidFactory.class) {
                if (uuid == null) {
                    final SharedPreferences prefs = context
                            .getSharedPreferences(PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null);
                    if (id != null) {
                        uuid = UUID.fromString(id);
                    } else {
//                        final String androidId = Secure.getString(
//                                context.getContentResolver(), Secure.ANDROID_ID);
//                        try {
//                            if (!"9774d56d682e549c".equals(androidId)) {
//                                uuid = UUID.nameUUIDFromBytes(androidId
//                                        .getBytes("utf8"));
//                            } else {
//
//
//                                final  String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
//                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
//                            }
//                        } catch (SecurityException e) { //사용자가 permission 사용 허가를 하지 않았을 경우
//                            throw new SecurityException(e); // 경고장 띄우기로 바꾸기
//                        } catch (UnsupportedEncodingException e) {
//                            throw new RuntimeException(e);
//                        }
                        uuid = UUID.randomUUID();
                    }
                }
            }
        }
    }
    public UUID getDeviceUuid() {
        return uuid;
    }
}
