package com.example.drender;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class lightActivity extends Activity  {

	MyRenderer render;
	//参数设置
	 
	 RadioGroup amRadioGroup;//环境光光照
	 RadioButton amHard;
	 RadioButton amModerate;
	 RadioButton amLow;
	 RadioButton amNone;
	 
	 RadioGroup diRadioGroup;//漫反射光照
	 RadioButton diHard;
	 RadioButton diModerate;
	 RadioButton diLow;
	 RadioButton diNone;
	 
	 RadioGroup shRadioGroup;//镜面反射光照
	 RadioButton shHard;
	 RadioButton shModerate;
	 RadioButton shLow;
	 RadioButton shNone;
	 
	 SharedPreferences sp;
	 Editor editor;
	
	
	@Override
 
    protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        sp=getSharedPreferences("radiocheck", MODE_PRIVATE);//保存上次状态
        this.setContentView(R.layout.light);
        
        amRadioGroup=(RadioGroup)findViewById(R.id.ambientLight);
        amHard=(RadioButton)findViewById(R.id.amHigh);
        amHard.setChecked(sp.getBoolean("amHard", false));
        amModerate=(RadioButton)findViewById(R.id.amModerate);
        amModerate.setChecked(sp.getBoolean("amModerate", false));
        amLow=(RadioButton)findViewById(R.id.amLow);
        amLow.setChecked(sp.getBoolean("amLow", false));
        amNone=(RadioButton)findViewById(R.id.amNone);
        amNone.setChecked(sp.getBoolean("amNone", false));
      
        diRadioGroup=(RadioGroup)findViewById(R.id.diffuseLight);
        diHard=(RadioButton)findViewById(R.id.diHigh);
        diHard.setChecked(sp.getBoolean("diHard", false));
        diModerate=(RadioButton)findViewById(R.id.diModerate);
        diModerate.setChecked(sp.getBoolean("diModerate", false));
        diLow=(RadioButton)findViewById(R.id.diLow);
        diLow.setChecked(sp.getBoolean("diLow", false));
        diNone=(RadioButton)findViewById(R.id.diNone);
        diNone.setChecked(sp.getBoolean("diNone", false));
        
        shRadioGroup=(RadioGroup)findViewById(R.id.shLight);
        shHard=(RadioButton)findViewById(R.id.shHigh);
        shHard.setChecked(sp.getBoolean("shHard", false));
        shModerate=(RadioButton)findViewById(R.id.shModerate);
        shModerate.setChecked(sp.getBoolean("shModerate", false));
        shLow=(RadioButton)findViewById(R.id.shLow);
        shLow.setChecked(sp.getBoolean("shLow", false));
        shNone=(RadioButton)findViewById(R.id.shNone); 
        shNone.setChecked(sp.getBoolean("shNone", false));
        editor=sp.edit();
        //环境颜色选择
        amRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    @Override
	    public void onCheckedChanged(RadioGroup group, int checkedId) {
	     		
	           
	     		if(checkedId==amHard.getId())
	     		{
	     			float[] hard={1f,1f, 1f,1f};
	     			Intent amHard = new Intent();  
	     			amHard.putExtra("amHard", hard);  
	                setResult(lightEnum.AMHARD.ordinal(), amHard );  
	                editor.putBoolean("amHard", true);
	                editor.putBoolean("amModerate", false);
	                editor.putBoolean("amLow", false);
	                editor.putBoolean("amNone", false);
	     		}
	     		else if(checkedId==amModerate.getId())
	     		{
	     			float[] Moderate={0.5f,0.5f, 0.5f,1f};
	     			Intent amModerate = new Intent();  
	     			amModerate.putExtra("amModerate", Moderate);  
	                setResult(lightEnum.AMMODERATE.ordinal(), amModerate ); 
	                editor.putBoolean("amModerate", true);
	                editor.putBoolean("amHard", false);
	                editor.putBoolean("amLow", false);
	                editor.putBoolean("amNone", false);
	     		}
	     		else if(checkedId==amLow.getId())
	    		{
	     			float[] low={0.1f,0.2f, 0.3f,1f};
	     			Intent amLow = new Intent();  
	     			amLow.putExtra("amLow", low);  
	                setResult(lightEnum.AMLOW.ordinal(), amLow ); 
	                editor.putBoolean("amLow", true);
	                editor.putBoolean("amModerate", false);
	                editor.putBoolean("amHard", false);
	                editor.putBoolean("amNone", false);
	     		}
	     		else if(checkedId==amNone.getId())
	    		{
	     			float[] none={0f,0f, 0f,0f};
	     			Intent amNone = new Intent();  
	     			amNone.putExtra("amNone", none);  
	                setResult(lightEnum.AMNONE.ordinal(), amNone ); 
	                editor.putBoolean("amNone", true);
	                editor.putBoolean("amLow", false);
	                editor.putBoolean("amModerate", false);
	                editor.putBoolean("amHard", false);
	     		}
	     	 	 editor.commit();
	     		}
	
	     });  
	    //漫反射颜色选择
        diRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	     	   @Override
	     		public void onCheckedChanged(RadioGroup group, int checkedId) {
	     		   
	     		  if(checkedId==diHard.getId())
		     		{
		     			float[] hard={1f,1f, 1f,1f};
		     			Intent diHard = new Intent();  
		     			diHard.putExtra("diHard", hard);  
		                setResult(lightEnum.DIHARD.ordinal(), diHard ); 
		                editor.putBoolean("diHard", true);
		                editor.putBoolean("diModerate", false);
		                editor.putBoolean("diLow", false);
		                editor.putBoolean("diNone", false);
		     		}
		     		else if(checkedId==diModerate.getId())
		     		{
		     			float[] Moderate={0.5f,0.5f, 0.5f,1f};
		     			Intent diModerate = new Intent();  
		     			diModerate.putExtra("diModerate", Moderate);  
		                setResult(lightEnum.DIMODERATE.ordinal(), diModerate ); 
		                editor.putBoolean("diModerate", true);
		                editor.putBoolean("diHard", false);
		                editor.putBoolean("diLow", false);
		                editor.putBoolean("diNone", false);
		     		}
		     		else if(checkedId==diLow.getId())
		    		{
		     			float[] low={0.1f,0.2f, 0.3f,1f};
		     			Intent diLow = new Intent();  
		     			diLow.putExtra("diLow", low);  
		                setResult(lightEnum.DILOW.ordinal(), diLow ); 
		                editor.putBoolean("diLow", true);
		                editor.putBoolean("diModerate", false);
		                editor.putBoolean("diHard", false);
		                editor.putBoolean("diNone", false);
		     		}
		     		else if(checkedId==diNone.getId())
		    		{
		     			float[] none={0f,0f, 0f,0f};
		     			Intent diNone = new Intent();  
		     			diNone.putExtra("diNone", none);  
		                setResult(lightEnum.DINONE.ordinal(), diNone ); 
		                editor.putBoolean("diNone", true);
		                editor.putBoolean("diLow", false);
		                editor.putBoolean("diModerate", false);
		                editor.putBoolean("diHard", false);
		     		}
	     		 editor.commit();
	     		}
	     });   
	    //镜面反射颜色       
        shRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	     	   @Override
	     		public void onCheckedChanged(RadioGroup group, int checkedId) {

	     		  if(checkedId==shHard.getId())
		     		{
		     			float[] hard={1f,1f, 1f,1f};
		     			Intent shHard = new Intent();  
		     			shHard.putExtra("shHard", hard); 
		                setResult(lightEnum.SHHARD.ordinal(), shHard ); 
		                editor.putBoolean("shHard", true);
		                editor.putBoolean("shModerate", false);
		                editor.putBoolean("shLow", false);
		                editor.putBoolean("shNone", false);
		                
		     		}
		     		else if(checkedId==shModerate.getId())
		     		{
		     			float[] Moderate={0.5f,0.5f, 0.5f,1f};
		     			Intent shModerate = new Intent();  
		     			shModerate.putExtra("shModerate", Moderate);  
		                setResult(lightEnum.SHMODERATE.ordinal(),shModerate ); 
		                editor.putBoolean("shModerate", true);
		                editor.putBoolean("shHard", false);
		                editor.putBoolean("shLow", false);
		                editor.putBoolean("shNone", false);
		     		}
		     		else if(checkedId==shLow.getId())
		    		{
		     			float[] low={0.1f,0.2f, 0.3f,1f};
		     			Intent shLow = new Intent();  
		     			shLow.putExtra("shLow", low);  
		                setResult(lightEnum.SHLOW.ordinal(), shLow ); 
		                editor.putBoolean("shLow", true);
		                editor.putBoolean("shModerate", false);
		                editor.putBoolean("shHard", false);
		                editor.putBoolean("shNone", false);
		     		}
		     		else if(checkedId==shNone.getId())
		    		{
		     			float[] none={0f,0f, 0f,0f};
		     			Intent shNone = new Intent();  
		     			shNone.putExtra("shNone", none);  
		                setResult(lightEnum.SHNONE.ordinal(), shNone ); 
		                editor.putBoolean("shNone", true);
		                editor.putBoolean("shModerate", false);
		                editor.putBoolean("shHard", false);
		                editor.putBoolean("shLow", false);
		     		}
	     		 editor.commit();
	     		}
	     }); 
        

    }
/*	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	if(keyCode == KeyEvent.KEYCODE_BACK){ 
		
	return true;
	}
	return super.onKeyDown(keyCode, event); 
	}*/
 




}
