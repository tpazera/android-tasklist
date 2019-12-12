package pl.tkjm.tasklist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private List<Task> list;
    private Context context;
    private TaskDao taskDao;


    public MyCustomAdapter(List<Task> list, Context context, TaskDao taskDao) {
        this.list = list;
        this.context = context;
        this.taskDao = taskDao;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @SuppressLint("ShowToast")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_lsit_view, null);
        }

        TextView listItemText = view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getTitle());

        Button deleteBtn = view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(v -> {
            taskDao.delete(list.get(position));
            list.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Task finished", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}