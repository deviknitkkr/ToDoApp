package com.vikas.todoapp.activities;

import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.view.menu.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.vikas.todoapp.Model.*;
import com.vikas.todoapp.adapter.*;
import java.util.*;
import me.everything.android.ui.overscroll.*;

import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
	
	RecyclerView recyclerview;
	MyRecyclerViewAdapter adapter;
	List<ToDoModel> list;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		getSupportActionBar().setSubtitle("A app for your ToDo");
		
		startRecyclerView();
		
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
			
            }
        });
		
    }

	public void startRecyclerView()
	{
		
		MyViewModel model=MyViewModel.getInstance();
		list=model.loadData();
		//list.addAll(model.loadData());
		//list.addAll(model.loadData());
		recyclerview=findViewById(R.id.recyclerview);
		recyclerview.setLayoutManager(new LinearLayoutManager(this));
		adapter=new MyRecyclerViewAdapter(this,list);
		recyclerview.setAdapter(adapter);
		OverScrollDecoratorHelper.setUpOverScroll(recyclerview, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      
		((MenuBuilder) menu).setOptionalIconsVisible(true);
        getMenuInflater().inflate(R.menu.menu_main, menu);
		
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        int id = item.getItemId();
		CheckBox checkBox= (CheckBox) item.getActionView();
		item.setChecked(true);
		
		switch(id)
		{
			case R.id.filter_completed:
				list=MyViewModel.getInstance().applyFilter(ToDoModel.MFilter.COMPLETED);
				break;
			case R.id.filter_pending:
				list=MyViewModel.getInstance().applyFilter(ToDoModel.MFilter.PENDING);
				break;
			case R.id.filter_all:
				list=MyViewModel.getInstance().applyFilter(ToDoModel.MFilter.ALL);
				break;
			case R.id.sortby_name:
				list=MyViewModel.getInstance().applySort(ToDoModel.MSort.NAME);
				break;
			case R.id.sortby_date:
				list=MyViewModel.getInstance().applySort(ToDoModel.MSort.DATE);
				break;
			case R.id.sortby_size:
				list=MyViewModel.getInstance().applySort(ToDoModel.MSort.SIZE);
				break;
		}
		adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }
}
