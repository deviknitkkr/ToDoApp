package com.vikas.todoapp.database;

import android.content.*;
import android.database.sqlite.*;
import android.os.*;

public class DatabaseHelper extends SQLiteOpenHelper
{

	public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,  int version)
	{
		super(context,DatabaseEntity.DATABASE_NAME, null, DatabaseEntity.DATABASE_VERSION);

	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String command="CREATE TABLE "+
		DatabaseEntity.TABLE_NAME+" ( "+
		DatabaseEntity._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
		DatabaseEntity.COLUMN_TITLE+" TEXT NOT NULL, "+
		DatabaseEntity.COLUMN_DESCRIPTION+" TEXT NOT NULL, "+
		DatabaseEntity.COLUMN_STATUS+" TEXT, "+
		DatabaseEntity.COLUMN_TIMESTAMP+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+
		" );";
		
		db.execSQL(command);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		// TODO: Implement this method
	}


}
