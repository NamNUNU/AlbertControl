package com.example.zzz;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DiaryWrite extends Activity {
	DiaryDBHelper mHelper;
	Cursor cursor;
	SQLiteDatabase db;
	int mYear, mMonth, mDay, mHour, month;
	TextView txtdate;
	EditText txtsubject, txtcontents;
	Intent intent;
	
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_diary_write);
        
        Calendar cal=new GregorianCalendar();
        mYear=cal.get(Calendar.YEAR);
        mMonth=cal.get(Calendar.MONTH);
        mDay=cal.get(Calendar.DAY_OF_MONTH);
        month=mMonth+1;
        
        txtdate=(TextView)findViewById(R.id.txtdate);
        txtdate.setText(mYear+ "/"+month+"/"+mDay);
        
        txtsubject=(EditText)findViewById(R.id.edtsubject);
        txtcontents=(EditText)findViewById(R.id.edtcontents);
        
        mHelper=new DiaryDBHelper(this);
        db=mHelper.getWritableDatabase();
	}
	
	public void mOnClick(View v) {
		switch(v.getId()) {
		case R.id.btnsave:
			String date=(String)txtdate.getText();
			Editable subject=(Editable)txtsubject.getText();
			Editable contents=(Editable)txtcontents.getText();
			
			db.execSQL("INSERT INTO diary VALUES(null, '" +date+ "', '"+subject+"','"+contents+"');");
			
			mHelper.close();
			
			Toast.makeText(DiaryWrite.this, "저장되었습니다.", 0).show();
			
			intent = new Intent(DiaryWrite.this, DiaryList.class);
			startActivity(intent);
			break;
		case R.id.btncancel:
			intent = new Intent(DiaryWrite.this, DiaryList.class);
			startActivity(intent);
			break;
		case R.id.btncal:
			new DatePickerDialog(DiaryWrite.this, mDateSetListener, mYear, mMonth, mDay).show();
			break;
		}
	}
	
	DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int month,
				int day) {
			mYear=year;
			mMonth=month;
			mDay=day;
			month=mMonth+1;
			txtdate.setText(mYear+"/"+month+"/"+mDay);
			
		}
	};
        

}
