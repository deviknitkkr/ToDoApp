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
import com.vikas.todoapp.adapter.*;
import com.vikas.todoapp.database.*;
import java.util.*;

import android.support.v7.widget.Toolbar;

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
					startActivityForResult(new Intent(MainActivity.this, NewTodoActivity.class), MY_REQUEST_CODE_NEW_TODO);
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
		//OverScrollDecoratorHelper.setUpOverScroll(recyclerview, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
	}

	public void startSharedPrefs()
	{
		list = new ArrayList<ToDoModel>();
		list.addAll(MyViewModel.getInstance().loadData());
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{

		((MenuBuilder) menu).setOptionalIconsVisible(true);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.filter_pending).setChecked(true);
		menu.findItem(R.id.sortby_date).setChecked(true);
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
				MyViewModel.getInstance().applyFilter(ToDoModel.MFilter.COMPLETED.toString());
				break;
			case R.id.filter_pending:
				MyViewModel.getInstance().applyFilter(ToDoModel.MFilter.PENDING.toString());
				break;
			case R.id.filter_all:
				MyViewModel.getInstance().applyFilter(ToDoModel.MFilter.ALL.toString());
				break;
			case R.id.sortby_name:
				MyViewModel.getInstance().applySort(ToDoModel.MSort.NAME.toString());
				break;
			case R.id.sortby_date:
				MyViewModel.getInstance().applySort(ToDoModel.MSort.DATE.toString());
				break;
			case R.id.sortby_size:
				MyViewModel.getInstance().applySort(ToDoModel.MSort.SIZE.toString());
				break;
			default:
			    return true;
		}
		list.clear();
		list.addAll(MyViewModel.getInstance().loadData());
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
		
			MyViewModel.getInstance().insertData(tmp);
			list.clear();
			list.addAll(MyViewModel.getInstance().loadData());
			adapter.notifyDataSetChanged();
			
		}

		if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE__EDIT_TODO)
		{
			ToDoModel tmp=new ToDoModel(data.getStringExtra(DatabaseEntity.COLUMN_TITLE),
			                            data.getStringExtra(DatabaseEntity.COLUMN_DESCRIPTION),
										ToDoModel.MFilter.PENDING.toString());
			tmp.setId(Integer.parseInt(data.getStringExtra(DatabaseEntity._ID)));
			tmp.setTodo_status(data.getStringExtra(DatabaseEntity.COLUMN_STATUS));
			MyViewModel.getInstance().modifyData(tmp,tmp.getId());
			list.clear();
			list.addAll(MyViewModel.getInstance().loadData());
			adapter.notifyDataSetChanged();
		}
	}
	
}
