package com.example.dviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.Toast;



@SuppressLint("InflateParams")
public class MainActivity extends Activity {
	 TabHost  th;
	 ArrayList name;  
	 SimpleAdapter adapter =null;
     //Model model=null;
     static BufferedReader br;
	 
	 
	private long exitTime = 0; 
	static MySurfaceView surfaceView;
    static boolean textured=true;
    static boolean lighted=false;
    static boolean tunneled=false;
    static boolean twisted=false;
    static boolean mandeled=false;
    static Model model=null;
    public static Model getModel()
    {
    	return model;
    }
	public static void setModel(Model mod) {
		model=mod;	
	}
    public static boolean getTextured()
    {
    	return textured;
    }
    public static void setTextured(boolean tf)
    {
    	textured=tf;
    }
    public static boolean getLighted()
    {
    	return lighted;
    }
    public static void setLighted(boolean tf)
    {
    	lighted=tf;
    }
    public static boolean getTunneled()
    {
    	return tunneled;
    }
    public static void setTunneled(boolean tf)
    {
    	tunneled=tf;
    }
    public static boolean getTwisted()
    {
    	return twisted;
    }
    public static void setTwisted(boolean tf)
    {
    	twisted=tf;
    }
    public static boolean getMandeled()
    {
    	return mandeled;
    }
    public static void setMandeled(boolean tf)
    {
    	mandeled=tf;
    }
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
	
		
		final ListView lv=(ListView)findViewById(R.id.list);  
	  
 
		surfaceView = new MySurfaceView(this);
        //surfaceView.setId(1);

	
		setContentView(surfaceView);
     	// 获取选项卡组 
   
       
       
		model=LoadObjUtil.loadFromFile("ch_t.obj",this.getResources());
		 
			
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.texture) {
			setTextured(true);
			setLighted(false);
			setTunneled(false);
			setTwisted(false);
			setMandeled(false);
			return true;
		}
		else if (id == R.id.light) {
			setLighted(true);
			setTextured(false);
			setTunneled(false);
			setTwisted(false);
			setMandeled(false);
			return true;
		}
		else if (id == R.id.tunnel) {
			setTunneled(true);
			setTextured(false);
			setLighted(false);
			setTwisted(false);
			setMandeled(false);
			return true;
		}
		else if (id == R.id.twist) {
			setTwisted(true);
			setMandeled(false);
			setTunneled(false);
			setTextured(false);
			setLighted(false);	
			return true;
		}
		else if (id == R.id.mandel) {
			setMandeled(true);
			setTwisted(false);
			setTunneled(false);
			setTextured(false);
			setLighted(false);	
			return true;
		}
		else if (id == R.id.activity) {
			Intent d=new Intent(this,ObjFileScanActivity.class);
			startActivity(d);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    @Override
	protected void onResume() {
	    super.onResume();
	    surfaceView.onResume();
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    surfaceView.onPause();
	}    

	//程序退出
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){ 
	if((System.currentTimeMillis()-exitTime) > 2000){ 
	Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show(); 
	exitTime = System.currentTimeMillis(); 
	} else { 
	finish(); 
	System.exit(0); 
	} 
	return true; 
	} 
	return super.onKeyDown(keyCode, event); 
	}	
	

    @SuppressWarnings("unused")
	private String getFileName(File[] files) { 
    	String path = null;
    	if (files != null) {// 先判断目录是否为空，否则会报空指针   
            for (File file : files) {  
                if (file.isDirectory()) {               
                    getFileName(file.listFiles());  
                } else {  
                    String fileName = file.getName();  
                  
                    if (fileName.endsWith(".obj")) { 
                    	path= file.getAbsolutePath();
                        HashMap map = new HashMap();  
                        //文件名称
                        map.put("Name", fileName.substring(0,  
                                fileName.lastIndexOf(".")));  
                        //文件路径
                        map.put("Path", path);
                        name.add(map);  
                    }  
                }  
            }  
        }  
        return path;
    }  

    public BufferedReader readSDFile(String path) { 
    	File file = new File(path); 
    	BufferedReader br =null;
    	try { 
            br = new BufferedReader(new FileReader(file));     
    	} 
    	catch (IOException e) 
    	{ 
    	e.printStackTrace(); 
    	} 
    	   return br;
} 


}

	

