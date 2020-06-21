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
import android.preference.*;

public class MyViewModel extends ViewModel
{
	private SQLiteDatabase sqld;
	private static Context mContext;
	private List<ToDoModel> list;
	private Comparator comparator;
	private Predicate filter_predicate;
	private static MyViewModel myviewmodel;
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefs_editor;
	
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
	
	private void init()
	{
		DatabaseHelper dbhelper=new DatabaseHelper(mContext,null,null,0);
		sqld=dbhelper.getWritableDatabase();
		loadData();
		prefs=PreferenceManager.getDefaultSharedPreferences(mContext);
		prefs_editor=prefs.edit();
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
	
	public List<ToDoModel> applyFilter(final String filter)
	{
		prefs_editor.putString("Filter",filter);
		
		 filter_predicate=new Predicate<ToDoModel>(){

			@Override
			public boolean test(ToDoModel p1)
			{
				if(filter.toString().equals(ToDoModel.MFilter.COMPLETED.toString()))
				{
					return p1.getTodo_status().equals(ToDoModel.MFilter.COMPLETED.toString());
				}
				if(filter.toString().equals(ToDoModel.MFilter.PENDING.toString()))
				{
					return p1.getTodo_status().equals(ToDoModel.MFilter.PENDING.toString());
				}
				if(filter.toString().equals(ToDoModel.MFilter.ALL.toString()))
				{
					return true;
				}
				return true;
			}
		};
		return list.stream().filter(filter_predicate).sorted(getCurrentSortComparator()).collect(Collectors.toList());
	}
	
	
	public Predicate getCurrentFilterPredicate()
	{
		if(filter_predicate!=null)
		{
			return filter_predicate;
		}
		return filter_predicate=new Predicate<ToDoModel>(){

			@Override
			public boolean test(ToDoModel p1)
			{
				return true;
			}
		};
	}
	
	
	public List<ToDoModel> applySort(final String sort)
	{
		prefs_editor.putString("Sort",sort);
		 comparator=new Comparator<ToDoModel>(){

			@Override
			public int compare(ToDoModel p1, ToDoModel p2)
			{
				if(sort.equals(ToDoModel.MSort.NAME.toString()))
				{
					return p1.getTodo_title().compareTo(p2.getTodo_title());
				}
				if(sort.equals(ToDoModel.MSort.DATE.toString()))
				{
					return -(p1.getTodo_timestamp().compareTo(p2.getTodo_timestamp()));
				}
				if(sort.equals(ToDoModel.MSort.SIZE.toString()))
				{
					return p1.getTodo_description().length()-p2.getTodo_description().length();
				}
				return 0;
			}
		};
		
		return list.stream().filter(getCurrentFilterPredicate()).sorted(comparator).collect(Collectors.toList());
	}
	
	public Comparator getCurrentSortComparator()
	{
		if(comparator!=null)
		{
			return comparator;
		}
		return comparator=new Comparator<ToDoModel>(){

			@Override
			public int compare(ToDoModel p1, ToDoModel p2)
			{
				return 0;
			}
		};
	}
	
	
}
