package com.vikas.todoapp.activities;

import android.content.*;
import android.database.sqlite.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.view.menu.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.vikas.todoapp.database.*;

import android.support.v7.widget.Toolbar;
import android.database.*;

public class MainActivity extends AppCompatActivity {

	SQLiteDatabase sqld;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

		getSupportActionBar().setSubtitle("A app for your ToDo");
       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();
				cv.put(DatabaseEntity.COLUMN_TITLE,"Changed todo");
				cv.put(DatabaseEntity.COLUMN_DESCRIPTION,"No description provided");
				cv.put(DatabaseEntity.COLUMN_STATUS,"not completed");
			    
				sqld.update(DatabaseEntity.TABLE_NAME,cv,"_id=1",null);
				Cursor cursor=sqld.rawQuery("SELECT * FROM "+DatabaseEntity.TABLE_NAME,null);
				if(cursor.moveToFirst())
				{
					while(!cursor.isAfterLast())
					{
						String timestamp=cursor.getString(cursor.getColumnIndex(DatabaseEntity.COLUMN_TITLE));
						Toast.makeText(MainActivity.this,timestamp,Toast.LENGTH_SHORT).show();
						cursor.moveToNext();
					}
				}
				
				
				//sqld.insert(DatabaseEntity.TABLE_NAME,null, cv);
				//Toast.makeText(MainActivity.this,"inserted",Toast.LENGTH_SHORT).show();
				
            }
        });
		
	try
	{
		DatabaseHelper dbhelper=new DatabaseHelper(this,null,null,0);
		sqld=dbhelper.getWritableDatabase();
		Toast.makeText(this,"created",Toast.LENGTH_SHORT).show();
	}catch(Exception e){Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
		
		if(menu instanceof MenuBuilder){
			
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        int id = item.getItemId();

		switch(id)
		{
			case R.id.action_filter:
				return true;
			case R.id.action_sort:
				return true;
		}
        return super.onOptionsItemSelected(item);
    }
}
