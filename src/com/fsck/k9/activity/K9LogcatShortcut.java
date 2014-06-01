package com.fsck.k9.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.fsck.k9.Account;
import com.fsck.k9.K9;
import com.fsck.k9.Preferences;
import com.fsck.k9.R;

public class K9LogcatShortcut extends Activity {
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Log.i(K9.LOG_TAG, "shortcut start");
        Intent shortcutIntent = LogcatActivity.LogcatIntent(this);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_notify_check_mail);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "K9Logcat");

        setResult(RESULT_OK, intent);
        finish();
    }
}
