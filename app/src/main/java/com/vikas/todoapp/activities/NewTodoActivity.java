package com.vikas.todoapp.activities;
import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.vikas.todoapp.database.*;
import me.everything.android.ui.overscroll.*;

import android.support.v7.widget.Toolbar;

public class NewTodoActivity extends AppCompatActivity
{

	EditText title;
	EditText description;
	int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edittodo);

		Toolbar toolbar=findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ScrollView scrollview=findViewById(R.id.my_scrollview);
		OverScrollDecoratorHelper.setUpOverScroll(scrollview);

		title = findViewById(R.id.edit_title);
		description = findViewById(R.id.edit_description);
		
	}

	@Override
	public void onBackPressed()
	{
		String mTitle=title.getText().toString().trim();
		String mDescription=description.getText().toString().trim();
		if (mTitle.equals("") || mDescription.equals(""))
		{
			Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent data=new Intent();
		data.putExtra(DatabaseEntity.COLUMN_TITLE, mTitle);
		data.putExtra(DatabaseEntity.COLUMN_DESCRIPTION, mDescription);
		
		setResult(RESULT_OK, data);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.editor_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			super.onBackPressed();
		}
		if(item.getItemId()==R.id.editor_save)
		{
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}


}
