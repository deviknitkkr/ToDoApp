package com.vikas.todoapp.adapter;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import com.vikas.todoapp.activities.*;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>
{

	private Context mContext;
	
	public MyRecyclerViewAdapter(Context mContext)
	{
		this.mContext=mContext;
	}
	
	@Override
	public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item,p1,false));
	}

	@Override
	public void onBindViewHolder(MyRecyclerViewAdapter.MyViewHolder p1, int p2)
	{
		// TODO: Implement this method
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return 0;
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder{
		
		CheckBox todo_status;
		TextView todo_title;
		TextView todo_description;
		
		public MyViewHolder(View v)
		{
			super(v);
			todo_status=v.findViewById(R.id.todo_status);
			todo_title=v.findViewById(R.id.todo_title);
			todo_description=v.findViewById(R.id.todo_description);
		}
	}
}
