package com.example.zzz;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Main extends Activity {
	
	Intent intent;
	boolean mFlag=false;
	Handler mHandler;
	private long lastTimeBackPressed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_main);
		
	    mHandler = new Handler() {
	    	public void hadleMessage(Message msg) {
	    		mFlag=false;
	    	}
	    };

	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	   public void onBackPressed() {
		      if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
		      {
		         finishAffinity();
		         return;
		      }
		      Toast.makeText(Main.this, "뒤로 버튼을 두번 더 누르시면 종료됩니다", 0).show();
		      lastTimeBackPressed = System.currentTimeMillis();
		   }

	public void mOnClick(View v) {
			switch(v.getId()) {
			case R.id.btnapp:
				intent = new Intent(this, AppInfo.class);
				break;
			case R.id.btndiary:
				intent = new Intent(this, DiaryList.class);
				break;
			case R.id.btnimu:
				intent = new Intent(this, ImuControl.class);
				break;
			case R.id.btnmode:
				intent = new Intent(this, ModeSet.class);
				break;
			case R.id.btnspeech:
				intent = new Intent(this, SpeechToText.class);
				break;
			case R.id.btncontroler:
				intent = new Intent(this, Controler.class);
				break;
			case R.id.btnmic:
				intent = new Intent(this, Mic.class);
				break;
			}
			
		
		startActivityForResult(intent, 1);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
