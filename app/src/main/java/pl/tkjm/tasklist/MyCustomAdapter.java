package pl.tkjm.tasklist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

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

        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetails fragment1 = new TaskDetails();
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("description", list.get(position).getDescription());
                bundle.putString("date", list.get(position).getDate());
                bundle.putString("duration", list.get(position).getDuration());

                fragment1.setArguments(bundle);
                transaction.replace(R.id.frameLayout, fragment1);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        MediaPlayer mp;
        mp = MediaPlayer.create(context, R.raw.finish);


        Button deleteBtn = view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(v -> {
            mp.start();
            taskDao.delete(list.get(position));
            list.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Task finished", Toast.LENGTH_SHORT).show();
        });


        return view;
    }
}
