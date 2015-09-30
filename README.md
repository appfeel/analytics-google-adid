Google Analytics Plugin
=======================

This plugin depends on [com.analytics.google](https://github.com/appfeel/analytics-google).
Cordova (PhoneGap) 3.0+ Plugin to connect to Google Analytics native SDK.
Enables [advertising id collection (IDFA)](https://support.google.com/analytics/answer/2444872) to get enhanced analytics (Demographics and Interests).

**iOS CAUTION:** Ensure to check advertising id option when you publish your app. Otherwise it will be rejected.

Prerequisites:
* A Cordova 3.0+ project for iOS and/or Android
* A Mobile App property through the Google Analytics Admin Console

---
## Platform SDK supported ##

* iOS, using Google Analytics SDK for iOS, v3.10
* Android, using Google Play Services for Android, v6.1

---
## Quick start ##

To install this plugin, follow the [Command-line Interface Guide](http://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-line%20Interface). You can use one of the following command lines:

* `cordova plugin add cordova-plugin-analytics-adid`
* `cordova plugin add https://github.com/appfeel/analytics-google-adid.git`

Make sure to review the Google Analytics [terms](http://www.google.com/analytics/terms/us.html) and [SDK Policy](https://developers.google.com/analytics/devguides/collection/protocol/policy)

---
## Javascript API ##

*Note:* All success callbacks are in the form `'function () {}'`, and all failure callbacks are in the form `'function (err) {}'` where `err` is a String explaining the error reason.

### Start Google Analytics ###
#### startAdIdTrackerWithId(id, success, fail);
Start Analtytics tracker with advertising id collection enabled:

* {String}     id:      (Required) your Google Analytics Universal code: UA-XXXXXXX-X.
* {function()} success: (Optional) success callback.
* {function()} failure: (Optional) failure callback.

### Advertising id ###
#### getAdId(success, error);
Gets the advertising id (IDFA).

* {String}     screenName: (Required) the name of the screen to track.
* {Object}     options:    (Optional) JSON object with additional options ([see options](#options)).
* {function()} success:    (Optional) success callback with advertising id (IDFA) as string argument.
* {function()} failure:    (Optional) failure callback.
