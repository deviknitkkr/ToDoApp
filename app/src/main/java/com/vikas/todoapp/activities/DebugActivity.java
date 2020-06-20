package com.vikas.todoapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import android.widget.TextView;
import android.widget.*;
import android.support.v7.app.*;

public class DebugActivity extends AppCompatActivity {

	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_layout);

		Intent intent = getIntent();
        String errormessage=intent.getStringExtra("error");
		
        EditText textview=findViewById(R.id.error_message);
        textview.setText(errormessage);

    }
}
