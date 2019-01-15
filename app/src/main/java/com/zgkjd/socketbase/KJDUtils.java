package com.zgkjd.socketbase;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;


public class KJDUtils {
  static final int THREAD_LEAK_CLEANING_MS = 1000;

  /**
   * 是否在线（网络有效）
   *
   * @param context
   * @return
   */
  @SuppressLint("MissingPermission") public static boolean isOnline(Context context) {
    ConnectivityManager connMgr = (ConnectivityManager)
        context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connMgr == null) {
      return false;
    }
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    return (networkInfo != null && networkInfo.isConnected());
  }

  public static void flushStackLocalLeaks(Looper looper) {
    Handler handler = new Handler(looper) {
      @Override
      public void handleMessage(Message msg) {
        sendMessageDelayed(obtainMessage(), THREAD_LEAK_CLEANING_MS);
      }
    };
    handler.sendMessageDelayed(handler.obtainMessage(), THREAD_LEAK_CLEANING_MS);
  }

  /**
   * 是否在中文环境
   *
   * @return
   */
  public static boolean isCN() {
    return TextUtils.equals(Locale.getDefault().getCountry(), "CN");
  }

  public static boolean hasPermission(Context context, String permission) {
    return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  public static boolean isAirplaneModeOn(Context context) {
    ContentResolver contentResolver = context.getContentResolver();
    try {
      return Settings.System.getInt(contentResolver, Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    } catch (NullPointerException e) {
      // https://github.com/square/picasso/issues/761, some devices might crash here, assume that
      // airplane mode is off.
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T getService(Context context, String service) {
    return (T) context.getSystemService(service);
  }

  public static File getDiskCacheDir(Context context, String uniqueName) {
    String cachePath;
//    boolean test = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
//    boolean test2 = !Environment.isExternalStorageRemovable();
//    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//        || !Environment.isExternalStorageRemovable()) {
//      cachePath = context.getExternalCacheDir().getPath();
//    } else {
    cachePath = context.getCacheDir().getPath();
//    }
    return new File(cachePath + File.separator + uniqueName);
  }

  @SuppressLint("MissingPermission") public static String getPhoneId(Context context) {
    final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    final String tmDevice, tmSerial, tmPhone, androidId;
    tmDevice = "" + tm.getDeviceId();
    tmSerial = "" + tm.getSimSerialNumber();
    androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    UUID deviceUuid
        = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
    return deviceUuid.toString();
  }

  /**
   * md5加密
   *
   * @param string
   * @return md5后的字符串
   */
  public static String md5(String string) {
    byte[] hash;
    try {
      hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Huh, MD5 should be supported?", e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Huh, UTF-8 should be supported?", e);
    }

    StringBuilder hex = new StringBuilder(hash.length * 2);
    for (byte b : hash) {
      if ((b & 0xFF) < 0x10) hex.append("0");
      hex.append(Integer.toHexString(b & 0xFF));
    }
    return hex.toString();
  }

  /**
   * 调用这个方法，仅仅是因为原来 JSONObject的put方法要try/catch，很烦，通过这样调用好看多啦
   */
  public static void putJSON(JSONObject jsonObject, String key, Object values) {
    try {
      jsonObject.put(key, values);
    } catch (JSONException e) {

    }
  }

  public static void putJSONArray(JSONArray jsonArray, int index, Object values) {
    try {
      jsonArray.put(index, values);
    } catch (JSONException e) {

    }
  }

  public static boolean latchAvailable(CountDownLatch latch) {
    return latch != null && latch.getCount() > 0;
  }


  /**
   * 百分比 转换 为 最大值为FF 的 16进制数
   *
   * @param percent 百分比
   * @return
   */
  public static String percentToFF(double percent) {
    int alpha = (int) Math.round(percent * 255);
    String hex = Integer.toHexString(alpha).toUpperCase();
    if (hex.length() == 1) {
      hex = "0" + hex;
    }
    return hex;
  }

}
