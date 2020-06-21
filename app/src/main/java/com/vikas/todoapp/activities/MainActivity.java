package com.vikas.todoapp.activities;

import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.view.menu.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.vikas.todoapp.Model.*;
import com.vikas.todoapp.adapter.MyRecyclerViewAdapter;
import com.vikas.todoapp.database.*;
import java.util.*;
import me.everything.android.ui.overscroll.*;
import android.support.v7.widget.Toolbar;
import android.text.format.*;
import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity
{

	RecyclerView recyclerview;
	MyRecyclerViewAdapter adapter;
	List<ToDoModel> list;
	private int MY_REQUEST_CODE_NEW_TODO=290;
	private int MY_REQUEST_CODE__EDIT_TODO=291;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		getSupportActionBar().setSubtitle("A app for your ToDo");

		startRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					//startActivity(new Intent(MainActivity.this,EditTodoActivity.class));
					startActivityForResult(new Intent(MainActivity.this, EditTodoActivity.class), MY_REQUEST_CODE_NEW_TODO);
				}
			});

    }

	public void startRecyclerView()
	{
		startSharedPrefs();
		recyclerview = findViewById(R.id.recyclerview);
		recyclerview.setLayoutManager(new LinearLayoutManager(this));
		adapter = new MyRecyclerViewAdapter(this, list);
		recyclerview.setAdapter(adapter);
		OverScrollDecoratorHelper.setUpOverScroll(recyclerview, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

	}

	public void startSharedPrefs()
	{
		MyViewModel model=MyViewModel.getInstance();
		list = new ArrayList<ToDoModel>();

//		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
//		if (prefs.contains("Filter"))
//		{
//			model.applyFilter(prefs.getString("Filter", ""));
//		}
//
//	   if (prefs.contains("Sort"))
//		{
//			list.addAll(model.applySort(prefs.getString("Sort", "")));
//		}
//
//		else
//		{
		model.applyFilter(ToDoModel.MFilter.PENDING.toString());
		list.addAll(model.applySort(ToDoModel.MSort.DATE.toString()));
		//	}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{

		((MenuBuilder) menu).setOptionalIconsVisible(true);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{

        int id = item.getItemId();
		CheckBox checkBox= (CheckBox) item.getActionView();
		item.setChecked(true);

		switch (id)
		{
			case R.id.filter_completed:
				list.clear();
				list.addAll(MyViewModel.getInstance().applyFilter(ToDoModel.MFilter.COMPLETED.toString()));
				break;
			case R.id.filter_pending:
				list.clear();
				list.addAll(MyViewModel.getInstance().applyFilter(ToDoModel.MFilter.PENDING.toString()));
				break;
			case R.id.filter_all:
				list.clear();
				list.addAll(MyViewModel.getInstance().applyFilter(ToDoModel.MFilter.ALL.toString()));
				break;
			case R.id.sortby_name:
				list.clear();
				list.addAll(MyViewModel.getInstance().applySort(ToDoModel.MSort.NAME.toString()));
				break;
			case R.id.sortby_date:
				list.clear();
				list.addAll(MyViewModel.getInstance().applySort(ToDoModel.MSort.DATE.toString()));
				break;
			case R.id.sortby_size:
				list.clear();
				list.addAll(MyViewModel.getInstance().applySort(ToDoModel.MSort.SIZE.toString()));
				break;
		}
		adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE_NEW_TODO)
		{
			ToDoModel tmp=new ToDoModel(data.getStringExtra(DatabaseEntity.COLUMN_TITLE),
			                            data.getStringExtra(DatabaseEntity.COLUMN_DESCRIPTION),
										ToDoModel.MFilter.PENDING.toString());
			Date date=new Date();
			long time=date.getTime();
			Timestamp ts=new Timestamp(time);
		    tmp.setTodo_timestamp(ts.toString());
			
			list.add(tmp);
			adapter.notifyDataSetChanged();
			MyViewModel.getInstance().insertData(tmp);
		}

		if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE__EDIT_TODO)
		{
			ToDoModel tmp=new ToDoModel(data.getStringExtra(DatabaseEntity.COLUMN_TITLE),
			                            data.getStringExtra(DatabaseEntity.COLUMN_DESCRIPTION),
										ToDoModel.MFilter.PENDING.toString());
			int position=adapter.getCurrentPosition();
			list.remove(position);
			list.add(position,tmp);
			adapter.notifyDataSetChanged();
			MyViewModel.getInstance().modifyData(tmp,position);

		}
	}


}
