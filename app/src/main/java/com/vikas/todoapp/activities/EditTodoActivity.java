package com.vikas.todoapp.activities;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import me.everything.android.ui.overscroll.*;

import android.support.v7.widget.Toolbar;
import android.content.*;
import com.vikas.todoapp.database.*;

public class EditTodoActivity extends AppCompatActivity
{

	EditText title;
	EditText description;

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
		checkIntent();
	}

	public void checkIntent()
	{
		Intent intent=getIntent();
		if (intent != null)
		{
			title.setText(intent.getStringExtra(DatabaseEntity.COLUMN_TITLE));
			description.setText(intent.getStringExtra(DatabaseEntity.COLUMN_DESCRIPTION));
		}
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
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}


}
