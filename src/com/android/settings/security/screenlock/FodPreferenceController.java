/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.security.screenlock;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

public class FodPreferenceController extends BasePreferenceController {

    private static final String FOD = "vendor.lineage.biometrics.fingerprint.inscreen";
    private static final String FOD_ANIMATION = "fod_icon_animation";
    private Context mContext;

    private FingerprintManager fpm;
    private PackageManager packageManager;

    private boolean mHasFod;

    public FodPreferenceController(Context context) {
        super(context, FOD_ANIMATION);
        mContext = context;

        fpm = Utils.getFingerprintManagerOrNull(context);
        packageManager = context.getPackageManager();
        mHasFod = packageManager.hasSystemFeature(FOD);
    }

    @Override
    public int getAvailabilityStatus() {
        if (fpm != null && fpm.isHardwareDetected() && fpm.hasEnrolledFingerprints()
            && packageManager.hasSystemFeature(FOD)) {
            return AVAILABLE;
        }
        return UNSUPPORTED_ON_DEVICE;
    }
}
