package pl.tkjm.tasklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Objects;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (Objects.requireNonNull(intent.getAction())) {
            case "android.media.VOLUME_CHANGED_ACTION":
                Toast.makeText(context, "ZMIENIONO GŁOŚNOŚĆ ", Toast.LENGTH_SHORT).show();
                break;
            case "android.media.MASTER_MUTE_CHANGED_ACTION":
                Toast.makeText(context, "WYCISZONO", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
