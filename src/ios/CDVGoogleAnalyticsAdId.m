/*
 CDVGoogleAnalyticsAdId.m
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
 
#import "CDVGoogleAnalyticsAdId.h"
#import <AdSupport/ASIdentifierManager.h>

@interface CDVGoogleAnalyticsAdId()

- (int) _getAdId: (NSString**)aid;

@end

@implementation CDVGoogleAnalyticsAdId

- (void) startAdIdTrackerWithId: (CDVInvokedUrlCommand*)command {
    CDVPluginResult* pluginResult = nil;
    NSString* accountId = [command.arguments objectAtIndex:0];
    
    [GAI sharedInstance].dispatchInterval = 30;
    [[GAI sharedInstance].logger setLogLevel:kGAILogLevelError];
    
    id<GAITracker> tracker = [[GAI sharedInstance] trackerWithTrackingId:accountId];
    tracker.allowIDFACollection = YES;
    [CDVGoogleAnalytics setTracker:tracker];
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    /* NSLog(@"successfully started GAI tracker"); */
}

- (void) getAdId: (CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *result = nil;
        CDVPluginResult* pluginResult = nil;
        
        if ([self _getAdId:&result] == 0) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                             messageAsString:result];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR
                                             messageAsString:result];
        }
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (int) _getAdId: (NSString**)aid {
    NSString *uuid = [[[ASIdentifierManager sharedManager] advertisingIdentifier] UUIDString];
    if (uuid) {
        *aid = uuid;
        return 0;
    }
    else {
        *aid = @"No Advertising Identifier for device";
        return -1;
    }
}

@end
