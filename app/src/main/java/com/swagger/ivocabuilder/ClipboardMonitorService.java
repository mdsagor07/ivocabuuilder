package com.swagger.ivocabuilder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;;
import android.content.ClipboardManager;
import android.util.Log;
import android.widget.Toast;


public class ClipboardMonitorService extends Service {

    private ClipboardManager mClipboardManager;
    

  /*  @Override
    public void onCreate() {
        super.onCreate();

        mClipboardManager =
                (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mClipboardManager.addPrimaryClipChangedListener(
                mOnPrimaryClipChangedListener);
        Log.d("LOGSERVICE","Started");
    }
*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mClipboardManager != null) {
            mClipboardManager.removePrimaryClipChangedListener(
                    mOnPrimaryClipChangedListener);
        }
    }



    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    private ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener =
            new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {

                    String charSequence = mClipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
                    Log.d("Copied Link" , charSequence);
                    Toast.makeText(ClipboardMonitorService.this, charSequence, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("copiedLink", charSequence);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            };


}

