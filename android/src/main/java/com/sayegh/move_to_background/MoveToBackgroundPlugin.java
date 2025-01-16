package com.sayegh.move_to_background;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

/** MoveToBackgroundPlugin */
public class MoveToBackgroundPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  private static final String CHANNEL_NAME = "move_to_background";
  private MethodChannel channel;
  private static Activity activity;

  // Attach to Flutter engine
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    channel = new MethodChannel(binding.getBinaryMessenger(), CHANNEL_NAME);
    channel.setMethodCallHandler(this);
  }

  // Detach from Flutter engine
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    if (channel != null) {
      channel.setMethodCallHandler(null);
      channel = null;
    }
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if ("moveTaskToBack".equals(call.method)) {
      if (activity != null) {
        activity.moveTaskToBack(true);
        result.success(true);
      } else {
        Log.e("MoveToBackgroundPlugin", "moveTaskToBack failed: activity is null");
        result.error("ACTIVITY_NOT_AVAILABLE", "Activity is not available", null);
      }
    } else {
      result.notImplemented();
    }
  }

  // Handle attaching to an activity
  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    activity = null;
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {
    activity = null;
  }
}
