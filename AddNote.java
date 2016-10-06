package com.example.android.database;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.android.database.MemoContract.*;

public class AddNote extends AppCompatActivity {
    private MemoDBHelper dbHelper;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Intent intent = getIntent();
        id = intent.getLongExtra("key1", -99);
        if (id != -99) {
            String[] projection = new String[]{
                    MemoEntry._ID,
                    MemoEntry.COLUMN_NAME_TITLE,
                    MemoEntry.COLUMN_NAME_TEXT1,
                    MemoEntry.COLUMN_NAME_TEXT2,
                    MemoEntry.COLUMN_NAME_TEXT3
            };
            dbHelper = new MemoDBHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MemoEntry.TABLE_NAME, projection, "_ID = ?", new String[]{"" + id}, null, null, null, null);
            cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndex(MemoEntry.COLUMN_NAME_TITLE));
            EditText etTitle = (EditText) findViewById(R.id.title);
            etTitle.setText(title);

            String text1 = cursor.getString(cursor.getColumnIndex(MemoEntry.COLUMN_NAME_TEXT1));
            EditText etNote = (EditText) findViewById(R.id.note1);
            etNote.setText(text1);

            String text2 = cursor.getString(cursor.getColumnIndex(MemoEntry.COLUMN_NAME_TEXT2));
            EditText etNote2 = (EditText) findViewById(R.id.note2);
            etNote2.setText(text2);

            String text3 = cursor.getString(cursor.getColumnIndex(MemoEntry.COLUMN_NAME_TEXT3));
            EditText etNote3 = (EditText) findViewById(R.id.note3);
            etNote3.setText(text3);
        }
    }

    public void save(View view) {


        dbHelper = new MemoDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        ContentValues values = new ContentValues();
        String title = ((EditText) findViewById(R.id.title)).getText().toString();
        values.put(MemoEntry.COLUMN_NAME_TITLE, title);

        String note1 =  ((EditText) findViewById(R.id.note1)).getText().toString();
        values.put(MemoEntry.COLUMN_NAME_TEXT1, note1);

        String note2 = ((EditText) findViewById(R.id.note2)).getText().toString();
        values.put(MemoEntry.COLUMN_NAME_TEXT2, note2);

        String note3 = ((EditText) findViewById(R.id.note3)).getText().toString();
        values.put(MemoEntry.COLUMN_NAME_TEXT3, note3);



        if (id == -99) {
            long newRowId = db.insert(
                    MemoEntry.TABLE_NAME,
                    null,
                    values);
        } else {
            db.update(MemoEntry.TABLE_NAME, values, "_ID = ?", new String[]{"" + id});
        }
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void delete (View view){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(MemoEntry.TABLE_NAME, "_ID = ?", new String[]{"" + id});
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();



    }



}
