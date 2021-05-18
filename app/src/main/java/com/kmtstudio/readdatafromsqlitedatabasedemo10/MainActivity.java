package com.kmtstudio.readdatafromsqlitedatabasedemo10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameTxt, ageTxt, genderTxt;
    private Button addBtn, displayBtn;

    MDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MDbHelper(this);
        SQLiteDatabase liteDatabase = dbHelper.getWritableDatabase();

        nameTxt = findViewById(R.id.nameTxtID);
        ageTxt = findViewById(R.id.ageTxtID);
        genderTxt = findViewById(R.id.genderTxtID);

        addBtn = findViewById(R.id.addBtnID);
        displayBtn = findViewById(R.id.displayDataBtnID);

        addBtn.setOnClickListener(this);
        displayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String name = nameTxt.getText().toString();
        String age = ageTxt.getText().toString();
        String gender = genderTxt.getText().toString();

        if (v.getId() == R.id.addBtnID) {

            long rowID = dbHelper.insertData(name, age, gender);

            if (rowID == -1) {
                Toast.makeText(getApplicationContext(), "Row data insert unsuccessful", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Row " + rowID + " data inserted successfully", Toast.LENGTH_LONG).show();
            }
        }

        if (v.getId() == R.id.displayDataBtnID) {

            Cursor cursor = dbHelper.displayData();

            if (cursor.getCount() == 0) {

                //there is no data so we will show message
                showData("Error", "Data not Found");
            }

            StringBuffer stringBuffer = new StringBuffer();

            while (cursor.moveToNext()) {

                stringBuffer.append("ID : ").append(cursor.getString(0)).append("\n");
                stringBuffer.append("Name : ").append(cursor.getString(1)).append("\n");
                stringBuffer.append("Age : ").append(cursor.getString(2)).append("\n");
                stringBuffer.append("Gender : ").append(cursor.getString(3)).append("\n").append("\n");
            }

            showData("resultSet", stringBuffer.toString());
        }
    }

    public void showData(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}