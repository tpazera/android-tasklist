package pl.tkjm.tasklist;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class TaskTable {
    public static final String TABLE_NAME = "task";

    public static class TaskColumns implements BaseColumns {
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String DESCRIPTION = "description";
        public static final String DURATION = "duration";
    }
    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + TaskTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(TaskColumns.TITLE + " TEXT, ");
        sb.append(TaskColumns.DATE + " TEXT, ");
        sb.append(TaskColumns.DESCRIPTION + " TEXT, ");
        sb.append(TaskColumns.DURATION + " TEXT ");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db,
                                 int oldVersion,
                                 int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "
                + TaskTable.TABLE_NAME);
        TaskTable.onCreate(db);
    }
}
