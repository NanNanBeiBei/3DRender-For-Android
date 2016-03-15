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
    	   
            File path = Environment.getExternalStorageDirectory();// ���SD��·��    
            File[] files = path.listFiles();// ��ȡ�ļ�Ŀ¼
            getFileName(files);//��ȡ�ļ������ļ�·��
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
    	if (files != null) {// ���ж�Ŀ¼�Ƿ�Ϊ�գ�����ᱨ��ָ��   
            for (File file : files) {  
                if (file.isDirectory()) {               
                    getFileName(file.listFiles());  
                } else {  
                    String fileName = file.getName();  
                  
                    if (fileName.endsWith(".obj")) { 
                    	path= file.getAbsolutePath();
                        HashMap map = new HashMap();  
                        //�ļ�����
                        map.put("Name", fileName.substring(0,  
                                fileName.lastIndexOf(".")));  
                        //�ļ�·��
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
