package com.mangopay.android.sdk.util;


import android.util.Log;

import com.mangopay.android.sdk.model.LogLevel;

public final class PrintLog {

  private static final String TAG = "MangoPay";
  private static LogLevel LOG_LEVEL = LogLevel.NONE;

  private PrintLog() {
  }


  /**
   * Set the level of the logger: 'NONE' or 'FULL'. by default it's 'NONE'.
   *
   * @param level The level of the logger
   */
  public static void setLogLevel(LogLevel level) {
    LOG_LEVEL = level;
  }

  /**
   * Send a DEBUG log message.
   *
   * @param message The message you would like logged.
   */
  public static void debug(String message) {
    if (LOG_LEVEL == LogLevel.NONE)
      return;

    if (message.length() > 4000) {
      Log.d(TAG, message.substring(0, 4000));
      debug(message.substring(4000));
    } else {
      Log.d(TAG, message);
    }
  }

  /**
   * Send a WARN log message.
   *
   * @param message The message you would like logged.
   */
  public static void warning(String message) {
    if (LOG_LEVEL == LogLevel.NONE)
      return;

    if (message.length() > 4000) {
      Log.w(TAG, message.substring(0, 4000));
      warning(message.substring(4000));
    } else {
      Log.w(TAG, message);
    }
  }

  /**
   * Send an ERROR log message.
   *
   * @param message The message you would like logged.
   */
  public static void error(String message) {
    if (LOG_LEVEL == LogLevel.NONE)
      return;

    if (message.length() > 4000) {
      Log.e(TAG, message.substring(0, 4000));
      error(message.substring(4000));
    } else {
      Log.e(TAG, message);
    }
  }

  /**
   * Send an INFO log message.
   *
   * @param message The message you would like logged.
   */
  public static void info(String message) {
    if (LOG_LEVEL == LogLevel.NONE)
      return;

    if (message.length() > 4000) {
      Log.i(TAG, message.substring(0, 4000));
      info(message.substring(4000));
    } else {
      Log.i(TAG, message);
    }
  }
}
