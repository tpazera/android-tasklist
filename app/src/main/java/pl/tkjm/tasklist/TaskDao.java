package pl.tkjm.tasklist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class TaskDao implements Dao<Task> {
    private static final String INSERT =
        "INSERT INTO " + TaskTable.TABLE_NAME
            + "(" + TaskTable.TaskColumns.TITLE + ", " + TaskTable.TaskColumns.DATE
            + ", " + TaskTable.TaskColumns.DESCRIPTION + ", " + TaskTable.TaskColumns.DURATION
            + ") "
            + "VALUES (?, ?, ?, ?)";

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public TaskDao(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(TaskDao.INSERT);
    }

    @Override
    public long save(Task entity) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, entity.getTitle());
        insertStatement.bindString(2, entity.getDate());
        insertStatement.bindString(3, entity.getDescription());
        insertStatement.bindString(4, entity.getDuration());
        return insertStatement.executeInsert();
    }

    @Override
    public void update(Task entity) {
        final ContentValues values = new ContentValues();
        values.put(TaskTable.TaskColumns.TITLE, entity.getTitle());
        values.put(TaskTable.TaskColumns.DATE, entity.getDate());
        values.put(TaskTable.TaskColumns.DESCRIPTION, entity.getDescription());
        values.put(TaskTable.TaskColumns.DURATION, entity.getDuration());
        db.update(TaskTable.TABLE_NAME, values,
                BaseColumns._ID + " = ?", new String[] {
                        String.valueOf(entity.getId())
                });
    }

    @Override
    public void delete(Task entity) {
        if (entity.getId() > 0) {
            db.delete(TaskTable.TABLE_NAME,
    BaseColumns._ID + " = ?", new String[]
                { String.valueOf(entity.getId()) });
        }
    }

    @Override
    public Task get(long id) {
        Task task = null;
        Cursor c =
                db.query(TaskTable.TABLE_NAME,
                        new String[] {
                                BaseColumns._ID,
                                TaskTable.TaskColumns.TITLE,
                                TaskTable.TaskColumns.DATE,
                                TaskTable.TaskColumns.DESCRIPTION,
                            TaskTable.TaskColumns.DURATION,},
                        BaseColumns._ID + " = ?", new String[] { String.valueOf(id) },
                        null, null, null, "1");
        if (c.moveToFirst()) {
            task = this.buildTaskFromCursor(c);
        }
        if (!c.isClosed()) {
            c.close();
        }
        return task;
    }

    @Override
    public List<Task> getAll() {
        List<Task> list = new ArrayList<Task>();
        Cursor c =
                db.query(TaskTable.TABLE_NAME, new String[] {
                                BaseColumns._ID,
                                TaskTable.TaskColumns.TITLE,
                                TaskTable.TaskColumns.DATE,
                                TaskTable.TaskColumns.DESCRIPTION,
                                TaskTable.TaskColumns.DURATION },
                        null, null, null, null, TaskTable.TaskColumns._ID, null);
        if (c.moveToFirst()) {
            do {
                Task task = this.buildTaskFromCursor(c);
                if (task != null) {
                    list.add(task);
                }
            } while (c.moveToNext());
        }
        if (!c.isClosed()) {
            c.close();
        }
        return list;
    }

    private Task buildTaskFromCursor(Cursor c) {
        Task task = null;
        if (c != null) {
            task = new Task(c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4));
            task.setId(c.getLong(0));
            task.setTitle(c.getString(1));
            task.setDate(c.getString(2));
            task.setDescription(c.getString(3));
            task.setDuration(c.getString(4));
        }
        return task;
    }
}
