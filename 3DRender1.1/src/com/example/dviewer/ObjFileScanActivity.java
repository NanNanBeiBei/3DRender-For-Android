package com.example.dviewer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ObjFileScanActivity extends Activity {
	ListView lv;  
    ArrayList name;  
    SimpleAdapter adapter =null;
    Model model=null;
    static BufferedReader br;
	@Override
	 public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.objlist);  
        lv = (ListView) findViewById(R.id.listtd);  
        name = new ArrayList();  
       
       if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED)) {  
    	   
            File path = Environment.getExternalStorageDirectory();// 获得SD卡路径    
            File[] files = path.listFiles();// 读取文件目录
            getFileName(files);//读取文件名和文件路径
        }  
        adapter = new SimpleAdapter(ObjFileScanActivity.this, name, R.layout.item,  
                new String[] { "Name" }, new int[] { R.id.itemtext});  
        lv.setAdapter(adapter);  
        lv.setOnItemClickListener(new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			HashMap<String,String> tem = (HashMap<String,String>)lv.getItemAtPosition(arg2); 
       		String path=tem.get("Path");
       	    br=	readSDFile(path);
       	    model=LoadObjUtil.loadFromFile(ObjFileScanActivity.br);
       	    MainActivity.setModel(model);
		}	
       });

 }  

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
