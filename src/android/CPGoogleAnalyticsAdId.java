/*
 CPGoogleAnalytics.java
 Copyright 2014 AppFeel. All rights reserved.
 http://www.appfeel.com
 
 Google Analytics with Advertising Id enabled Cordova Plugin (com.analytics.adid.google)
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to
 deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 sell copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.appfeel.cordova.analytics.adid;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

import com.appfeel.cordova.analytics.CPGoogleAnalytics;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger.LogLevel;

public class CPGoogleAnalyticsAdId extends CordovaPlugin {
  public static final String ACTION_START_ADID_TRACKER = "startAdIdTrackerWithId";
  public static final String ACTION_GET_ADID = "getAdId";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    PluginResult result = null;

    if (ACTION_START_ADID_TRACKER.equals(action)) {
      String id = args.getString(0);
      result = executeStartAdIdTracker(id, callbackContext);

    } else if (ACTION_GET_ADID.equals(action)) {
      result = execGetAdId(callbackContext);

    } else {
      return false;
    }

    if (result != null) {
      callbackContext.sendPluginResult(result);
    }

    return true;
  }

  private PluginResult executeStartAdIdTracker(String id, CallbackContext callbackContext) {
    PluginResult result = null;

    if (null != id && id.length() > 0) {
      CPGoogleAnalytics.tracker = GoogleAnalytics.getInstance(this.cordova.getActivity()).newTracker(id);
      GoogleAnalytics.getInstance(this.cordova.getActivity()).setLocalDispatchPeriod(30);
      GoogleAnalytics.getInstance(this.cordova.getActivity()).getLogger().setLogLevel(LogLevel.ERROR);
      CPGoogleAnalytics.tracker.enableAdvertisingIdCollection(true);
      result = new PluginResult(Status.OK);

    } else {
      result = new PluginResult(Status.ERROR, "Invalid tracker");
    }

    return result;
  }

  private PluginResult execGetAdId(final CallbackContext callbackContext) {
    cordova.getThreadPool().execute(new Runnable() {
      @Override
      public void run() {
        AdvertisingIdClient.Info advId;
        try {
          advId = AdvertisingIdClient.getAdvertisingIdInfo(cordova.getActivity());
          callbackContext.success(advId.getId());

        } catch (Exception e) {
          callbackContext.error(e.getMessage());
        }
      }
    });

    return null;
  }

}