package com.vikas.todoapp.adapter;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.vikas.todoapp.Model.*;
import com.vikas.todoapp.activities.*;
import com.vikas.todoapp.database.*;
import java.util.*;
import android.support.v7.app.*;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> 
{

	private Context mContext;
	private List<ToDoModel> list;
	private int current_pos;

	public MyRecyclerViewAdapter(Context mContext, List<ToDoModel> list)
	{
		this.mContext = mContext;
		this.list = list;

	}



	@Override
	public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item, p1, false));
	}

	@Override
	public void onBindViewHolder(MyRecyclerViewAdapter.MyViewHolder p1, int p2)
	{
		ToDoModel tmp=list.get(p2);
		p1.todo_title.setText(tmp.getTodo_title());

		if (tmp.getTodo_description().length() > 75)
		{
			p1.todo_description.setText(tmp.getTodo_description().substring(0, 75) + "...");
		}
		else
		{
			p1.todo_description.setText(tmp.getTodo_description());
		}

		p1.todo_status.setChecked(tmp.getTodo_status().equals(ToDoModel.MFilter.COMPLETED.toString()));
		p1.setIsRecyclable(true);
	}

	@Override
	public int getItemCount()
	{
		return list.size();
	}

	public int getCurrentPosition()
	{
		return current_pos;
	}

	class MyViewHolder extends RecyclerView.ViewHolder
	{

		CheckBox todo_status;
		TextView todo_title;
		TextView todo_description;

		public MyViewHolder(View v)
		{
			super(v);
			todo_status = v.findViewById(R.id.todo_status);
			todo_title = v.findViewById(R.id.todo_title);
			todo_description = v.findViewById(R.id.todo_description);

			todo_status.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						final ToDoModel tmp=list.get(getPosition());
						boolean ischecked=((CheckBox) p1).isChecked();
						if (ischecked)
						{
							tmp.setTodo_status(ToDoModel.MFilter.COMPLETED.toString());
						}
						else
						{
							tmp.setTodo_status(ToDoModel.MFilter.PENDING.toString());
						}
						MyViewModel.getInstance().modifyData(tmp, getPosition());

					}
				});

			v.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1)
					{  
						current_pos = getPosition();
					    ToDoModel tmp=list.get(getPosition());
						Intent intent=new Intent(mContext, EditTodoActivity.class);
						intent.putExtra(DatabaseEntity.COLUMN_TITLE, tmp.getTodo_title());
						intent.putExtra(DatabaseEntity.COLUMN_DESCRIPTION, tmp.getTodo_description());

						((AppCompatActivity) mContext).startActivityForResult(intent, 291);
					}
				});

		}

	}

}


