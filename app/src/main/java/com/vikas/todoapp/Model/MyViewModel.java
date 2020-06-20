package com.vikas.todoapp.Model;
import android.arch.lifecycle.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import com.vikas.todoapp.database.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import android.os.*;
import android.widget.*;

public class MyViewModel extends ViewModel
{
	SQLiteDatabase sqld;
	private static Context mContext;
	List<ToDoModel> list;
	ToDoModel.MFilter filter;
	ToDoModel.MSort sort;
	private static MyViewModel myviewmodel;
	
	public static void startWith(Context context)
	{
		mContext=context;
		myviewmodel=new MyViewModel(context);
	}
	
	public static MyViewModel getInstance()
	{
		return myviewmodel;
	}
	
	private MyViewModel(Context context)
	{
		mContext=context;
		init();
	}
	
	public void init()
	{
		DatabaseHelper dbhelper=new DatabaseHelper(mContext,null,null,0);
		sqld=dbhelper.getWritableDatabase();
		loadData();
	}
	
	public List<ToDoModel> loadData()
	{
		list=new ArrayList<ToDoModel>();
		list.clear();
		Cursor cursor=sqld.rawQuery("SELECT * FROM "+DatabaseEntity.TABLE_NAME,null);
		if(cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				ToDoModel todo=new ToDoModel(null,null,null);
				todo.setTodo_title(cursor.getString(cursor.getColumnIndex(DatabaseEntity.COLUMN_TITLE)));
				todo.setTodo_description(cursor.getString(cursor.getColumnIndex(DatabaseEntity.COLUMN_DESCRIPTION)));
				todo.setTodo_timestamp(cursor.getString(cursor.getColumnIndex(DatabaseEntity.COLUMN_TIMESTAMP)));
				todo.setTodo_status(cursor.getString(cursor.getColumnIndex(DatabaseEntity.COLUMN_STATUS)));
				list.add(todo);
				cursor.moveToNext();
			}
		}
		return list;
	}
	
	public void insertData(ToDoModel model)
	{
		list.add(model);
		ContentValues cv=new ContentValues();
		cv.put(DatabaseEntity.COLUMN_TITLE,model.getTodo_title());
		cv.put(DatabaseEntity.COLUMN_DESCRIPTION,model.getTodo_description());
		cv.put(DatabaseEntity.COLUMN_STATUS,model.getTodo_status());
	
		sqld.insert(DatabaseEntity.TABLE_NAME,null,cv);
	}
	
	public void modifyData(ToDoModel model,int position)
	{
		ContentValues cv=new ContentValues();
		cv.put(DatabaseEntity.COLUMN_TITLE,model.getTodo_title());
		cv.put(DatabaseEntity.COLUMN_DESCRIPTION,model.getTodo_description());
		cv.put(DatabaseEntity.COLUMN_STATUS,model.getTodo_status());
		sqld.update(DatabaseEntity.TABLE_NAME,cv,"_id="+(position+1),null);
	}
	
	public List<ToDoModel> applyFilter(final ToDoModel.MFilter filter)
	{
		this.filter=filter;
	
		if(filter.toString().equals(ToDoModel.MFilter.ALL))
		{
			return list;
		}
		Predicate filter_predicate=new Predicate<ToDoModel>(){

			@Override
			public boolean test(ToDoModel p1)
			{
				return (p1.getTodo_status().equals(filter.toString())) ? true :false;
			}
		};
		return list.stream().filter(filter_predicate).collect(Collectors.toList());
	}
	
	public ToDoModel.MFilter getCurrentFilter()
	{
		return this.filter;
	}
	
	public List<ToDoModel> applySort(final ToDoModel.MSort sort)
	{
		Toast.makeText(mContext,"Sort applied:"+sort.toString(),Toast.LENGTH_SHORT).show();
		this.sort=sort;
		Comparator comparator=new Comparator<ToDoModel>(){

			@Override
			public int compare(ToDoModel p1, ToDoModel p2)
			{
				if(sort.values().equals(ToDoModel.MSort.NAME))
				{
					return p1.getTodo_title().compareTo(p2.getTodo_title());
				}
				if(sort.values().equals(ToDoModel.MSort.DATE))
				{
					return p1.getTodo_timestamp().compareTo(p2.getTodo_timestamp());
				}
				if(sort.values().equals(ToDoModel.MSort.SIZE))
				{
					return p1.getTodo_description().length()-p2.getTodo_description().length();
				}
				return 0;
			}
		};
		
		return list.stream().sorted(comparator).collect(Collectors.toList());
	}
	
	public ToDoModel.MSort getCurrentSort()
	{
		return this.sort;
	}
	
	
}
