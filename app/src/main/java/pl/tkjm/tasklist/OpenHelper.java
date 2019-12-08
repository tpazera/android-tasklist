package pl.tkjm.tasklist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class OpenHelper extends SQLiteOpenHelper {

    private Context context;
    public OpenHelper(final Context context) {
        super(context, "task", null,5);
        this.context = context;
    }
    @Override
    public void onOpen(final SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TaskTable.onCreate(db);
        TaskTable.onCreate(db);
        TaskDao taskDao = new TaskDao(db);

        InputStream iStream = context.getResources().openRawResource(R.raw.simpletasks);
        ByteArrayOutputStream byteStream = null;

        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s =  byteStream.toString();
        String[] split = s.split("\n");

        for(String el: split) {
            String[] values = el.split(", ");
            Task t = new Task(values[0], values[1], values[2], values[3]);
            taskDao.save(t);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TaskTable.onUpgrade(db, oldVersion, newVersion);
    }
}
