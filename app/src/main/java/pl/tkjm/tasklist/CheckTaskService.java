package pl.tkjm.tasklist;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class CheckTaskService extends Service {
    private Timer timer;
    private TimerTask task;
    private TaskDao taskDao;
    private NotificationCompat.Builder builder;

    public CheckTaskService() {
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            Calendar myCalendar = Calendar.getInstance();
            String myFormat = "yyyy-MM-dd HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
            String now = sdf.format(myCalendar.getTime());
            Log.i("czas", now);

            Date nowDate = sdf.parse(now, new ParsePosition(0));

            List<Task> taskList = taskDao.getAll();
            for(Task t : taskList) {
                Log.i("db", t.getDate());
                try {
                    Date taskDate = sdf.parse(t.getDate(), new ParsePosition(0));

                    if (nowDate.compareTo(taskDate) > 0){
                        builder.setContentText(getString(R.string.task_is) + t.getTitle());
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                        notificationManager.notify(11, builder.build());

                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SQLiteOpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        taskDao = new TaskDao(db);

        timer = new Timer();
        Toast.makeText(this, R.string.service_started, Toast.LENGTH_SHORT).show();

        long[] pattern = {500,500,500,500,500,500,500,500,500};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder = new NotificationCompat.Builder(getApplicationContext(), "null")
                .setSmallIcon(R.drawable.ic_stat_access_alarm)
                .setContentTitle(getString(R.string.alarm))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(pattern)
                .setSound(alarmSound);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(task != null){
            task.cancel();
            timer.purge();
        }
        task = new MyTimerTask();
        timer.scheduleAtFixedRate(task, 6000, 60000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException(getString(R.string.not_implemented));
    }
}
