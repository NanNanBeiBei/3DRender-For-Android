package com.example.drender;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;


@SuppressLint("InflateParams")
public class MainActivity extends ActionBarActivity {

	private long exitTime = 0; 
	
	 OBJParser parser;
	 TDModel model;
	 //��������
	 
	 RadioGroup amRadioGroup;//��������ɫ��
	 RadioButton amRed;
	 RadioButton amGreen;
	 RadioButton amBlue;
	 RadioButton amCyan;
	 RadioButton amnone;
	 
	 RadioGroup diRadioGroup;//��������ɫ��
	 RadioButton diYellow;
	 RadioButton diPink;
	 RadioButton diWhite;
	 RadioButton diPurple;
	 RadioButton dinone;
	 
	 RadioGroup shRadioGroup;//���淴����ɫ��
	 RadioButton shCoral;
	 RadioButton shWheat;
	 RadioButton shGold;
	 RadioButton shWood;
	 RadioButton shnone;
	 RadioGroup light;//���淴����ɫ��
	 RadioButton lightone;
	 RadioButton lighttwo;
	 //ģ��ѡ��ؼ���
	 RadioGroup modelone;
	 RadioButton teapot;
	 RadioButton cube;
	 RadioButton cow;
	 RadioButton bunny;
	 RadioButton cylinder;
	 RadioButton torus;
	 RadioButton sphere;
	 RadioButton cactus;
	 
	 //����
	 Button lightSet;
	 Button colorSet;
	 TabHost  th;
	 View leftViewGroup;
	 
	 colorActivity cActivity;
	 String vert;
	 String frag;
	 public String getvert(){
		 return this.vert;
	 }
	 public void setvert(String v){
		  this.vert=v;
	 }
	 public String getfrag(){
		 return frag;
	 }
	 public void setfrag(String f){
		  this.frag=f;
	 }
	 
	 GLSurfaceView surfaceView;
	 MyRenderer render;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        //��ʼ��Tabhost�ؼ�
		View tab1=(View)getLayoutInflater().inflate(R.layout.tab1, null);
		View tab2=(View)getLayoutInflater().inflate(R.layout.tab2, null);
		View tab3=(View)getLayoutInflater().inflate(R.layout.tab3, null);
        th=(TabHost)getLayoutInflater().inflate(R.layout.tabhost, null);
		th.setup();  
		th.addTab(th.newTabSpec("tab1").setIndicator(tab1).setContent(R.id.tab1));
		th.addTab(th.newTabSpec("tab2").setIndicator(tab2).setContent(R.id.tab2));
		th.addTab(th.newTabSpec("tab3").setIndicator(tab3).setContent(R.id.tab3));
        //��ʼ��GLSurfaceView
	    surfaceView = new MySurfaceView(this);
	    render=new MyRenderer(this);
        surfaceView.setRenderer(render); 
        surfaceView.setId(1);
        LinearLayout li=(LinearLayout)th.findViewById(R.id.tab1);
        li.addView(surfaceView);
		setContentView(th);
       //ģ��ѡ��ѡ�����
	   th.getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//ģ��ѡ��ؼ����Ҽ��¼�����
	        modelone=(RadioGroup)findViewById(R.id.modelone);
	        teapot=(RadioButton)findViewById(R.id.teapot);
	        cow=(RadioButton)findViewById(R.id.cow);
	        cube=(RadioButton)findViewById(R.id.cube);
	        bunny=(RadioButton)findViewById(R.id.bunny);
	        torus=(RadioButton)findViewById(R.id.torus);
	        sphere=(RadioButton)findViewById(R.id.sphere);
	        cylinder=(RadioButton)findViewById(R.id.cylinder);
	        cactus=(RadioButton)findViewById(R.id.cactus);
	        modelone.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     @Override
		     		public void onCheckedChanged(RadioGroup group, int checkedId) {
		     		
		     			if(checkedId==teapot.getId())
		     			{
		     				setModel("teapot.obj");
		     			}
		     			else if(checkedId==cow.getId())
		     			{
		     				setModel("cow.obj");
		     			}
		     			else if(checkedId==cube.getId())
		     			{
		     				setModel("cube.obj");
		     			}
		     			else if(checkedId==bunny.getId())
		     			{
		     				setModel("bu_head.obj");
		     			}
		     			else if(checkedId==cactus.getId())
			     		{
			     			setModel("cactus.obj");
			     		}
			     		else if(checkedId==torus.getId())
			     		{
			     			setModel("torus.obj");
			     		}
			     		else if(checkedId==cylinder.getId())
			     		{
			     			setModel("cylinder.obj");
			     		}
			     		else if(checkedId==sphere.getId())
			     		{
			     			setModel("sphere.obj");
			     		}
		     		}
		     });    
	        th.setCurrentTab(1);	
		 }		   
	   });
	   //�������ô���ѡ�
	   th.getTabWidget().getChildAt(2).setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//ģ��ѡ��ؼ����Ҽ��¼�����
	        lightSet=(Button)findViewById(R.id.light);
	        colorSet=(Button)findViewById(R.id.color);
	        
	        lightSet.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent setup = new Intent();
					setup.setClass(MainActivity.this,lightActivity.class);
					MainActivity.this.startActivityForResult(setup, 0);				

				}	
	        });
	        colorSet.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent setup = new Intent();
					setup.setClass(MainActivity.this,colorActivity.class);
					MainActivity.this.startActivityForResult(setup, 1);
					
				}
	        	
	        });
	        th.setCurrentTab(2);
		 }		   
	   });
	
	    setModel("teapot.obj");
		
	}
	
	 @Override  
	protected void onActivityResult( int requestCode, int resultCode, Intent data )  
	    {  
		 //��������
		 if(requestCode==0&&resultCode!=0){//�����κδ����ʱ��resultCodeĬ�Ϸ���ֵΪ0
			 switch ( resultCode ) {  
             case 1: 
               float[]amHard= data.getExtras().getFloatArray("amHard");
               render.setlightAmbient(amHard[0], amHard[1], amHard[2],amHard[3]);
                break; 
             case 2:  
                 float[]amModerate= data.getExtras().getFloatArray("amModerate");
                 render.setlightAmbient(amModerate[0], amModerate[1], amModerate[2],amModerate[3]);
                    break; 
             case 3:  
                 float[]amLow= data.getExtras().getFloatArray("amLow");
                 render.setlightAmbient(amLow[0], amLow[1], amLow[2],amLow[3]);
                    break; 
             case 4:  
                 float[]amNone= data.getExtras().getFloatArray("amNone");
                 render.setlightAmbient(amNone[0], amNone[1], amNone[2],amNone[3]);
                    break; 
             case 5:  
                 float[]diHard= data.getExtras().getFloatArray("diHard");
                 render.setlightDiffuse(diHard[0], diHard[1], diHard[2],diHard[3]);
                    break; 
             case 6:  
                 float[]diModerate= data.getExtras().getFloatArray("diModerate");
                 render.setlightDiffuse(diModerate[0], diModerate[1], diModerate[2],diModerate[3]);
                    break; 
             case 7:  
                 float[]diLow= data.getExtras().getFloatArray("diLow");
                 render.setlightDiffuse(diLow[0], diLow[1], diLow[2],diLow[3]);
                    break; 
             case 8:  
                 float[]diNone= data.getExtras().getFloatArray("diNone");
                 render.setlightDiffuse(diNone[0], diNone[1], diNone[2],diNone[3]);
                    break;
             case 9:  
                 float[]shHard= data.getExtras().getFloatArray("shHard");
                 render.setlightShine(shHard[0], shHard[1], shHard[2],shHard[3]);
                    break;
             case 10:  
                 float[]shModerate= data.getExtras().getFloatArray("shModerate");
                 render.setlightShine(shModerate[0], shModerate[1], shModerate[2],shModerate[3]);
                    break;
             case 11:  
                 float[]shLow= data.getExtras().getFloatArray("shLow");
                 render.setlightShine(shLow[0], shLow[1], shLow[2],shLow[3]);
                    break;
             case 12:  
                 float[]shNone= data.getExtras().getFloatArray("shNone");
                 render.setlightShine(shNone[0], shNone[1], shNone[2],shNone[3]);
                    break;  
                    default:
                    	break;
                 }
		 }
		 //��ɫ����
		  if(requestCode==1&&resultCode!=0){
	        switch ( resultCode ) {  
	             case 1:  
	             float[]amRed= data.getExtras().getFloatArray("red");
	             render.setambientColor(amRed[0], amRed[1], amRed[2]);
	                break; 
	             case 2:  
		         float[]amGreen= data.getExtras().getFloatArray("green");
		         render.setambientColor(amGreen[0], amGreen[1], amGreen[2]);
		            break; 
	             case 3:  
			         float[]amBlue= data.getExtras().getFloatArray("blue");
			         render.setambientColor(amBlue[0], amBlue[1], amBlue[2]);
			         break; 
	             case 4:  
			         float[]amCyan= data.getExtras().getFloatArray("cyan");
			         render.setambientColor(amCyan[0], amCyan[1], amCyan[2]); 
			         break; 
	             case 5:  
			         float[]amNone= data.getExtras().getFloatArray("amNone");
			         render.setambientColor(amNone[0], amNone[1], amNone[2]); 
			         break; 
	             case 6:  
			         float[]diYellow= data.getExtras().getFloatArray("yellow");
			         render.setDiffuseColor(diYellow[0], diYellow[1], diYellow[2]); 
			         break; 
	             case 7:  
			         float[]diPink= data.getExtras().getFloatArray("pink");
			         render.setDiffuseColor(diPink[0], diPink[1], diPink[2]); 
			         break; 
	             case 8:  
			         float[]diWhite= data.getExtras().getFloatArray("white");
			         render.setDiffuseColor(diWhite[0], diWhite[1], diWhite[2]); 
			         break; 
	             case 9:  
			         float[]diPurple= data.getExtras().getFloatArray("purple");
			         render.setDiffuseColor(diPurple[0], diPurple[1], diPurple[2]); 
			         break; 
	             case 10:  
			         float[]diNone= data.getExtras().getFloatArray("dinone");
			         render.setDiffuseColor(diNone[0], diNone[1], diNone[2]); 
			         break; 
	             case 11:  
			         float[]shCoral= data.getExtras().getFloatArray("coral");
			         render.setShineColor(shCoral[0], shCoral[1], shCoral[2]); 
			         break; 
	             case 12:  
			         float[]shWheat= data.getExtras().getFloatArray("wheat");
			         render.setShineColor(shWheat[0], shWheat[1], shWheat[2]); 
			         break; 
	             case 13:  
			         float[]shGold= data.getExtras().getFloatArray("gold");
			         render.setShineColor(shGold[0], shGold[1], shGold[2]); 
			         break; 
	             case 14:  
			         float[]shWood= data.getExtras().getFloatArray("wood");
			         render.setShineColor(shWood[0], shWood[1], shWood[2]); 
			         break; 
	             case 15:  
			         float[]shNone= data.getExtras().getFloatArray("shNone");
			         render.setShineColor(shNone[0], shNone[1], shNone[2]); 
			         break;  
	        }  
	          
	    }  
	    }

	public TDModel getModel(){
		return model;
	}
		
	public void setModel(String modelName){
		try{
			InputStream stream=getAssets().open(modelName);
			parser = new OBJParser();//ʵ��������obj�ļ���
			model = parser.parseOBJ(stream);//���ý���obj�ļ�����
		}catch(Exception e){
			
		}
	}
	
	public String loadFromAssetsFile(String fileName){
		String result = null;
		try {
			//1. ��assetsĿ¼�ж�ȡ�ļ���������, �൱�ڴ�����һ���ļ����ֽ�������
			InputStream is =getAssets().open(fileName);
			int ch = 0;
			//2. ����һ�����������������, ÿ�ζ�ȡһ���ֽ�, ע�������ֽڶ�ȡ�õ���int����
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//3. ����ֽڶ�ȡ����, ������ȡ�����ݷ��뻺������
			while((ch = is.read()) != -1){
				baos.write(ch);
			}
			//4. ���������е�����תΪ�ֽ�����, �����ֽ�����ת��Ϊ�ַ���
			byte[] buffer = baos.toByteArray();
			baos.close();
			is.close();
			result = new String(buffer, "UTF-8");
			result = result.replaceAll("\\r\\n", "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//�����˳�
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){ 
	if((System.currentTimeMillis()-exitTime) > 2000){ 
	Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show(); 
	exitTime = System.currentTimeMillis(); 
	} else { 
	finish(); 
	System.exit(0); 
	} 
	return true; 
	} 
	return super.onKeyDown(keyCode, event); 
	}

	
}
