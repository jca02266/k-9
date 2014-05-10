package com.fsck.k9.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.fsck.k9.Account;
import com.fsck.k9.K9;
import com.fsck.k9.Preferences;
import com.fsck.k9.controller.MessagingController;

public class LogcatActivity extends Activity {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        final Context context = getApplicationContext();
        final Account account = Preferences.getPreferences(context).getDefaultAccount();
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return MessagingController.getInstance(getApplication()).addLogcat(account, null);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Toast.makeText(K9.app,
                            String.format("Saved a logcat message to the %s folder", account.getErrorFolderName()),
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(K9.app, "Can't save a logcat message", Toast.LENGTH_LONG).show();
                }
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
