package com.fsck.k9.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.fsck.k9.Preferences;
import com.fsck.k9.controller.MessagingController;

public class LogcatActivity extends Activity {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        final Context context = getApplicationContext();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                MessagingController.getInstance(getApplication()).addLogcat(Preferences.getPreferences(context).getDefaultAccount(), null);

                return null;
            }
        }.execute();

        finish();
    }

    public static Intent LogcatIntent(Context context) {
        Intent intent = new Intent(context, LogcatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return intent;
    }
}
