package com.example.zzz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDBHelper extends SQLiteOpenHelper {
	public DiaryDBHelper(Context context) {
		super(context, "diary.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE diary (_id INTEGER PRIMARY KEY AUTOINCREMENT," +"wdate TEXT, subject TEXT, contents TEXT);");
        db.execSQL("INSERT INTO diary VALUES(null, '2016/06/01', '�ȳ��ϼ���!','�ȳ��Ͻʴϱ�?');");
        db.execSQL("INSERT INTO diary VALUES(null, '2016/06/02', '�˹�Ʈ','�˹�Ʈ ���ƿ�');");
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS diary");
		onCreate(db);
	}

}
