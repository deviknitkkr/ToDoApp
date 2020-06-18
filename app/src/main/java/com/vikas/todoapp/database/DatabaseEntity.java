package com.vikas.todoapp.database;
import android.provider.*;

public class DatabaseEntity implements BaseColumns
{
	public final static int DATABASE_VERSION=1;
	public final static String DATABASE_NAME="mydatabase.db";
	public final static String TABLE_NAME="todo";
	public final static String COLUMN_TITLE="title";
	public final static String COLUMN_DESCRIPTION="description";
	public final static String COLUMN_STATUS="status";
	public final static String COLUMN_TIMESTAMP="timestamp";
}
