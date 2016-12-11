package com.example.zzz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DiaryList extends Activity {
	DiaryDBHelper mHelper;
	Cursor cursor;
	SQLiteDatabase db;
	ArrayList<MyDiary> DiaryDataList;
	Intent intent;
	ListView list;
	int id;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.screen_diary_list);
	        
	        DiaryDataList=createDiaryData();
	        DiaryListAdapter adapter=new DiaryListAdapter(this, R.layout.item_diary_list, DiaryDataList);
	        list=(ListView)findViewById(R.id.listdiary);
	        list.setAdapter(adapter);
	        
	        registerForContextMenu(list);
	        
	        list.setOnItemClickListener(new OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
	        		Intent intent=new Intent(DiaryList.this, DiaryDetail.class);
	        		
	        		id=DiaryDataList.get(position).id;
	        		
	        		Bundle extras=new Bundle();
	        		extras.putInt("id", DiaryDataList.get(position).id);
	        		extras.putString("date", DiaryDataList.get(position).wdate);
	        		extras.putString("subject", DiaryDataList.get(position).subject);
	        		extras.putString("contents", DiaryDataList.get(position).contents);
	        		intent.putExtras(extras);
	        		
	        		startActivity(intent);
	        	}
	        });
	 }
	 
	 public ArrayList<MyDiary>createDiaryData() {
		 mHelper=new DiaryDBHelper(this);
		 db=mHelper.getWritableDatabase();
		 
		 cursor=db.rawQuery("SELECT * FROM diary", null);
		 
		 DiaryDataList = new ArrayList<MyDiary>();
		 while(cursor.moveToNext()) {
			 int id=cursor.getInt(0);
			 String wdate=cursor.getString(1);
			 String subject=cursor.getString(2);
			 String contents=cursor.getString(3);
			 
			 MyDiary DiaryData=new MyDiary(id,wdate,subject,contents);
			 DiaryDataList.add(DiaryData);
			 
		 }
		 cursor.close();
		 mHelper.close();
		 
		 return DiaryDataList;
	 }
	 
	 class MyDiary {
		 int id;
		 String wdate;
		 String subject;
		 String contents;
		 
		 MyDiary(int aid, String awdate, String asubject, String acontents){
			 id=aid;
			 wdate=awdate;
			 subject=asubject;
			 contents=acontents;
		 }
	 } 
	 
	 public class DiaryListAdapter extends BaseAdapter {

		Context acontext;
		LayoutInflater Inflater;
		ArrayList<MyDiary> aDiaryDataList;
		int alayout;
		
		public DiaryListAdapter(Context context, int layout, ArrayList<MyDiary> DiaryDataList) {
			acontext = context;
			Inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			aDiaryDataList=DiaryDataList;
			alayout=layout;
		}
		@Override
		public int getCount() {
			return aDiaryDataList.size();
		}

		@Override
		public Object getItem(int position) {

			return position;
		}

		@Override
		public long getItemId(int position) {

			return aDiaryDataList.get(position).id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if(convertView==null) {
				convertView=Inflater.inflate(alayout, parent, false);
			}
			
			TextView txtdate=(TextView)convertView.findViewById(R.id.txtdate);
			txtdate.setText(aDiaryDataList.get(position).wdate);
			
			TextView txtsubject=(TextView)convertView.findViewById(R.id.txtsubject);
			txtsubject.setText(aDiaryDataList.get(position).subject);
			
			return convertView;
		}
		 
	 }
	 
	 public void mOnClick(View v) {
		 Intent intent = new Intent(this, DiaryWrite.class);
		 startActivity(intent);
	 }
	 public void onBackPressed() {
		 Intent intent = new Intent(this, Main.class);
		 startActivity(intent);
	 }
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
		 super.onCreateOptionsMenu(menu);
		 
		 menu.add(0,1,0,"검색");
		 menu.add(0,2,0,"추가");
		 SubMenu sub1=menu.addSubMenu("정렬");
		 sub1.add(0,3,0,"날짜");
		 sub1.add(0,4,0,"제목");
		 return true;
	 }
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
		 switch(item.getItemId()) {
		 case 1:
			 intent=new Intent(this, Main.class);
			 startActivity(intent);
			 return true;
		 case 2:
			 intent=new Intent(this, DiaryWrite.class);
			 startActivity(intent);
			 return true;
		 case 3:
			 DiaryDBHelper mHelper=new DiaryDBHelper(this);
			 db=mHelper.getWritableDatabase();
			 
			 String sql="SELECT * FROM diary order by wdate";
			 db.execSQL(sql);
			 Toast.makeText(DiaryList.this,"삭제되었습니다.", 0).show();
			 
			 intent = new Intent(this, DiaryList.class);
			 startActivity(intent);
			 return true;
			 
		 case 4:
			 mHelper=new DiaryDBHelper(this);
			 db=mHelper.getWritableDatabase();
			 
			 cursor=db.rawQuery("SELECT * FROM diary order by title", null);
			 
			 DiaryDataList = new ArrayList<MyDiary>();
			 while(cursor.moveToNext()) {
				 int id=cursor.getInt(0);
				 String wdate=cursor.getString(1);
				 String subject=cursor.getString(2);
				 String contents=cursor.getString(3);
				 
				 MyDiary DiaryData=new MyDiary(id,wdate,subject,contents);
				 DiaryDataList.add(DiaryData);
				 
			 }
			 cursor.close();
			 mHelper.close();
			 Toast.makeText(DiaryList.this,"제목순으로 정렬되었습니다.", 0).show();
			 
			 intent = new Intent(this, DiaryList.class);
			 startActivity(intent);
			 return true;
		 }
		 return false;
	 }
	 
	 public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		 super.onCreateContextMenu(menu, v, menuInfo);
		 if(v==list) {
			 menu.setHeaderTitle("선택");
			 menu.add(0,1,0,"삭제");
		 }
	 }
	 
	 public boolean onContextItemSelected(MenuItem item) {
		 super.onContextItemSelected(item);
		 AdapterView.AdapterContextMenuInfo menuInfo=
				 (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		 
		 int index=menuInfo.position;
		 id=DiaryDataList.get(index).id;
		 
		 switch(item.getItemId()) {
		 case 1:
			 DiaryDBHelper mHelper=new DiaryDBHelper(this);
			 db=mHelper.getWritableDatabase();
			 
			 String sql="DELETE FROM diary WHERE _id="+ id + ";";
			 db.execSQL(sql);
			 Toast.makeText(DiaryList.this,"삭제되었습니다.", 0).show();
			 
			 intent = new Intent(this, DiaryList.class);
			 startActivity(intent);
			 return true;
		 }
		 
		 return true;
	 }

}
