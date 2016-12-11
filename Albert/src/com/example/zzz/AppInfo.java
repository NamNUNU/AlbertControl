package com.example.zzz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AppInfo extends Activity {
	private ListView listView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_app_info);
        
        listView=(ListView)findViewById(R.id.list);
        
        ArrayList<AppInfoBean> list=new ArrayList<AppInfoBean>();
        
        list.add(new AppInfoBean(0, "애플리케이션 정보", null, null));
        
        try {
        	PackageInfo pi=getPackageManager().getPackageInfo(getPackageName(), 0);
        	list.add(new AppInfoBean(1, null, "패키지 명칭", pi.packageName));
        	list.add(new AppInfoBean(1, null, "버전 코드", String.valueOf(pi.versionCode)));
        	list.add(new AppInfoBean(1, null, "버전 명칭", pi.versionName));
        } catch (NameNotFoundException e) {
        	e.printStackTrace();
        }
        
        list.add(new AppInfoBean(0, "단말 정보", null,null));
        list.add(new AppInfoBean(1, null, "기기 명칭", Build.MODEL));
        
        String osVersion = null;
        switch(Build.VERSION.SDK_INT) {
        case Build.VERSION_CODES.ECLAIR:
        	osVersion="2.0 (ECLAIR)";
        	break;
        case Build.VERSION_CODES.ECLAIR_0_1:
        	osVersion="2.0.1 (ECLAIR)";
        	break;
        case Build.VERSION_CODES.ECLAIR_MR1:
        	osVersion="2.2 (FROYO)";
        	break;
        case Build.VERSION_CODES.GINGERBREAD:
        	osVersion="2.3 (GINGERBREAD)";
        	break;
        case Build.VERSION_CODES.GINGERBREAD_MR1:
        	osVersion="2.3.3 (GINGERBREAD)";
        	break;
        default:
        	osVersion="알수없음";
        	break;
        }
        
        list.add(new AppInfoBean(1,null,"OS 버전", osVersion));
        
        AppInfoAdapter adapter=new AppInfoAdapter(this,list);
        listView.setAdapter(adapter);
	}
	
	private class AppInfoAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<AppInfoBean> list;
		
		public AppInfoAdapter(Context context, ArrayList<AppInfoBean> list) {
			this.context=context;
			this.list=list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			AppInfoBean appInfoBean = list.get(position);
			switch(appInfoBean.type) {
			case 0:
				convertView=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_header, parent, false);
				
				((TextView)convertView.findViewById(R.id.label)).setText(appInfoBean.label);
				break;
			case 1:
				convertView=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_text_two_line, parent, false);
				
				((TextView)convertView.findViewById(R.id.title)).setText(appInfoBean.title);
				((TextView)convertView.findViewById(R.id.content)).setText(appInfoBean.contents);
			}
			return convertView; 
		}
		
		
	}
	
	private class AppInfoBean {
		private int type;
		private String label;
		private String title;
		private String contents;
		
		public AppInfoBean(int type, String label, String title, String contents) {
			this.type=type;
			this.label=label;
			this.title=title;
			this.contents=contents;
		}
	}

}
