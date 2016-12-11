package com.example.zzz;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DiaryDetail extends Activity {
	DiaryDBHelper mHelper;
	SQLiteDatabase db;
	int id;
	int mYear, mMonth, mDay, mHour, month;
	TextView txtdate;
	EditText txtsubject, txtcontents;
	String sql;
	Intent intent;

	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.screen_diary_detail);
	        
	        Intent intent=getIntent();
	        
	        Calendar cal=new GregorianCalendar();
	        mYear=cal.get(Calendar.YEAR);
	        mMonth=cal.get(Calendar.MONTH);
	        mDay=cal.get(Calendar.DAY_OF_MONTH);

	        id=intent.getIntExtra("id",0);
	        
	        txtdate=(TextView)findViewById(R.id.txtdate);
	        txtdate.setText(intent.getStringExtra("date"));
	        
	        txtsubject=(EditText)findViewById(R.id.edtsubject);
	        txtsubject.setText(intent.getStringExtra("subject"));
	        
	        txtcontents=(EditText)findViewById(R.id.edtcontents);
	        txtcontents.setText(intent.getStringExtra("contents"));
	        
	        mHelper=new DiaryDBHelper(this);
	        
	 }
	 
	 public void mOnClick(View v){
		 switch(v.getId()) {
		 case R.id.btnsave:
			 String date=(String)txtdate.getText();
			 Editable subject=(Editable)txtsubject.getText();
			 Editable contents=(Editable)txtcontents.getText();
			 db=mHelper.getWritableDatabase();
			 
			 sql="UPDATE diary SET wdate='"+date+"',subject='"+subject+"',contents='"+contents+"' "+"where _id="+id+";";
			 
			 db.execSQL(sql);
			 Toast.makeText(DiaryDetail.this, "수정되었습니다.", 0).show();
			 
			 mHelper.close();
			 intent=new Intent(DiaryDetail.this, DiaryList.class);
			 startActivity(intent);
			 break;
			 
		 case R.id.btndelete:
			 db=mHelper.getWritableDatabase();
			 
			 sql="DELETE FROM diary WHERE _id="+id+";";
			 
			 db.execSQL(sql);
			 Toast.makeText(DiaryDetail.this, "삭제되었습니다.", 0).show();
			 
			 mHelper.close();
			 intent=new Intent(DiaryDetail.this, DiaryList.class);
			 startActivity(intent);
			 break;
			 
		 case R.id.btncal:
			 new DatePickerDialog(DiaryDetail.this, mDateSetListener, mYear, mMonth, mDay).show();
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
