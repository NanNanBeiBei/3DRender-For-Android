package com.example.drender;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class colorActivity extends  FragmentActivity  {
	 
	
	 MyRenderer render;
	//参数设置
	 
	 RadioGroup amRadioGroup;//环境光颜色组
	 RadioButton amRed;
	 RadioButton amGreen;
	 RadioButton amBlue;
	 RadioButton amCyan;
	 RadioButton amNone;
	 
	 RadioGroup diRadioGroup;//漫反射颜色组
	 RadioButton diYellow;
	 RadioButton diPink;
	 RadioButton diWhite;
	 RadioButton diPurple;
	 RadioButton diNone;
	 
	 RadioGroup shRadioGroup;//镜面反射颜色组
	 RadioButton shCoral;
	 RadioButton shWheat;
	 RadioButton shGold;
	 RadioButton shWood;
	 RadioButton shNone;
	
	 SharedPreferences sp;
	 Editor editor;
	
	@Override
 
    protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        sp=getSharedPreferences("radiocheck", MODE_PRIVATE);//保存上次状态
        this.setContentView(R.layout.color);
        
        amRadioGroup=(RadioGroup)findViewById(R.id.AmbientColor);
        amRed=(RadioButton)findViewById(R.id.amred);
        amRed.setChecked(sp.getBoolean("amRed", false));
        amGreen=(RadioButton)findViewById(R.id.amgreen);
        amGreen.setChecked(sp.getBoolean("amGreen", false));
        amBlue=(RadioButton)findViewById(R.id.amblue);
        amBlue.setChecked(sp.getBoolean("amBlue", false));
        amCyan=(RadioButton)findViewById(R.id.amcyan);
        amCyan.setChecked(sp.getBoolean("amCyan", false));
        amNone=(RadioButton)findViewById(R.id.amnone);
        amNone.setChecked(sp.getBoolean("amNone", false));
      
        diRadioGroup=(RadioGroup)findViewById(R.id.DiffuseColor);
        diYellow=(RadioButton)findViewById(R.id.diYellow);
        diYellow.setChecked(sp.getBoolean("diYellow", false));
        diPink=(RadioButton)findViewById(R.id.diPink);
        diPink.setChecked(sp.getBoolean("diPink", false));
        diWhite=(RadioButton)findViewById(R.id.diWhite);
        diWhite.setChecked(sp.getBoolean("diWhite", false));
        diPurple=(RadioButton)findViewById(R.id.diPurple);
        diPurple.setChecked(sp.getBoolean("diPurple", false));
        diNone=(RadioButton)findViewById(R.id.dinone);
        diNone.setChecked(sp.getBoolean("diNone", false));
        
        shRadioGroup=(RadioGroup)findViewById(R.id.ShineColor);
        shCoral=(RadioButton)findViewById(R.id.shCoral);
        shCoral.setChecked(sp.getBoolean("shCoral", false));
        shWheat=(RadioButton)findViewById(R.id.shWheat);
        shWheat.setChecked(sp.getBoolean("shWheat", false));
        shGold=(RadioButton)findViewById(R.id.shGold);
        shGold.setChecked(sp.getBoolean("shGold", false));
        shWood=(RadioButton)findViewById(R.id.shWood);
        shWood.setChecked(sp.getBoolean("shWood", false));
        shNone=(RadioButton)findViewById(R.id.shnone); 
        shNone.setChecked(sp.getBoolean("shNone", false));
        
        editor=sp.edit();
        //环境颜色选择
        amRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    @Override
	    public void onCheckedChanged(RadioGroup group, int checkedId) {
	     		
	     		if(checkedId==amRed.getId())
	     		{
	     			float[] red={1f,0f, 0f};
	     			Intent amred = new Intent();  
	     			amred.putExtra("red", red);  
	                setResult(colorEnum.AMRED.ordinal(), amred ); 
	                editor.putBoolean("amRed", true);
	                editor.putBoolean("amGreen", false);
	                editor.putBoolean("amBlue", false);
	                editor.putBoolean("amCyan", false);
	                editor.putBoolean("amNone", false);
	                
	     		}
	     		else if(checkedId==amGreen.getId())
	     		{
	     			float[] green={0f,1f, 0f};
	     			Intent amgreen = new Intent();  
	     			amgreen.putExtra("green", green);  
	                setResult(colorEnum.AMGREEN.ordinal(), amgreen ); 
	                editor.putBoolean("amGreen", true);
	                editor.putBoolean("amRed", false);
	                editor.putBoolean("amBlue", false);
	                editor.putBoolean("amCyan", false);
	                editor.putBoolean("amNone", false);
	     		}
	     		else if(checkedId==amBlue.getId())
	    		{
	     			float[] blue={0f,0f, 1f};
	     			Intent amblue = new Intent();  
	     			amblue.putExtra("blue", blue);  
	                setResult(colorEnum.AMBLUE.ordinal(), amblue ); 
	                editor.putBoolean("amBlue", true);
	                editor.putBoolean("amRed", false);
	                editor.putBoolean("amGreen", false);
	                editor.putBoolean("amCyan", false);
	                editor.putBoolean("amNone", false);
	     		}
	     		else if(checkedId==amCyan.getId())
	    		{
	     			float[] cyan={0f,1f, 1f};
	     			Intent amCyan = new Intent();  
	     			amCyan.putExtra("cyan", cyan);  
	                setResult(colorEnum.AMCYAN.ordinal(), amCyan ); 
	                editor.putBoolean("amCyan", true);
	                editor.putBoolean("amRed", false);
	                editor.putBoolean("amGreen", false);
	                editor.putBoolean("amBlue", false);
	                editor.putBoolean("amNone", false);
	     		}
	     		else if(checkedId==amNone.getId())
	    		{
	     			float[] none={0f,0f, 0f};
	     			Intent amNone = new Intent();  
	     			amNone.putExtra("amNone", none);  
	                setResult(colorEnum.AMNONE.ordinal(), amNone ); 
	                editor.putBoolean("amNone", true);
	                editor.putBoolean("amRed", false);
	                editor.putBoolean("amGreen", false);
	                editor.putBoolean("amBlue", false);
	                editor.putBoolean("amCyan", false);
	     		}
	     		 editor.commit();
	     		}
	     });  
	    //漫反射颜色选择
        diRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	     	   @Override
	     		public void onCheckedChanged(RadioGroup group, int checkedId) {
	     		
	     		  if(checkedId==diYellow.getId())
	     			{
	     			    float[] yellow={1f,1f, 0f};
		     			Intent diYellow = new Intent();  
		     			diYellow.putExtra("yellow", yellow);  
		                setResult(colorEnum.DIYELLOW.ordinal(), diYellow ); 
		                editor.putBoolean("diYellow", true);
		                editor.putBoolean("diPink", false);
		                editor.putBoolean("diWhite", false);
		                editor.putBoolean("diPurple", false);
		                editor.putBoolean("diNone", false);
	     			}
	     			else if(checkedId==diPink.getId())
	     			{
	     				float[] pink={1f,0.08f, 0.60f};
		     			Intent diPink = new Intent();  
		     			diPink.putExtra("pink", pink);  
		                setResult(colorEnum.DIPINK.ordinal(), diPink ); 
		                editor.putBoolean("diPink", true);
		                editor.putBoolean("diYellow", false);
		                editor.putBoolean("diWhite", false);
		                editor.putBoolean("diPurple", false);
		                editor.putBoolean("diNone", false);
	     			}	
	     			else if(checkedId==diWhite.getId())
	     			{
	     				float[] white={0.76f,0.14f, 0.14f};
		     			Intent diWhite = new Intent();  
		     			diWhite.putExtra("white", white);  
		                setResult(colorEnum.DIWHITE.ordinal(), diWhite ); 
		                editor.putBoolean("diWhite", true);
		                editor.putBoolean("diYellow", false);
		                editor.putBoolean("diPink", false);
		                editor.putBoolean("diPurple", false);
		                editor.putBoolean("diNone", false);
	     			}
	     			else if(checkedId==diPurple.getId())
	     			{
	     				float[] purple={0.61f,0.19f, 1f};
		     			Intent diPurple = new Intent();  
		     			diPurple.putExtra("purple", purple);  
		                setResult(colorEnum.DIPURPLE.ordinal(), diPurple ); 
		                editor.putBoolean("diPurple", true);
		                editor.putBoolean("diYellow", false);
		                editor.putBoolean("diPink", false);
		                editor.putBoolean("diWhite", false);
		                editor.putBoolean("diNone", false);
	     			}
	     			else if(checkedId==diNone.getId())
	     			{
	     				float[] none={0f,0f, 0f};
		     			Intent diNone = new Intent();  
		     			diNone.putExtra("dinone", none);  
		                setResult(colorEnum.DINONE.ordinal(), diNone ); 
		                editor.putBoolean("diNone", true);
		                editor.putBoolean("diYellow", false);
		                editor.putBoolean("diPink", false);
		                editor.putBoolean("diWhite", false);
		                editor.putBoolean("diPurple", false);
	     			}
	     		 editor.commit();
	     		}
	     });   
	    //镜面反射颜色       
        shRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	     	   @Override
	     		public void onCheckedChanged(RadioGroup group, int checkedId) {
	     		
	     	      if(checkedId==shCoral.getId())
	     			{
	     	    	    float[] coral={1f,0.5f, 0.3f};
		     			Intent shCoral = new Intent();  
		     			shCoral.putExtra("coral", coral);  
		                setResult(colorEnum.SHCORAL.ordinal(), shCoral ); 
		                editor.putBoolean("shCoral", true);
		                editor.putBoolean("shWheat", false);
		                editor.putBoolean("shGold", false);
		                editor.putBoolean("shWood", false);
		                editor.putBoolean("shNone", false);
	     			}
	     			else if(checkedId==shWheat.getId())
	     			{
	     				float[] wheat={0.96f,0.87f, 0.17f};
		     			Intent shWheat = new Intent();  
		     			shWheat.putExtra("wheat", wheat);  
		                setResult(colorEnum.SHWHEAT.ordinal(), shWheat ); 
		                editor.putBoolean("shWheat", true);
		                editor.putBoolean("shCoral", false);
		                editor.putBoolean("shGold", false);
		                editor.putBoolean("shWood", false);
		                editor.putBoolean("shNone", false);
	     			}
	     			else if(checkedId==shGold.getId())
	     			{
	     				float[] gold={1f,0.84f, 0f};
		     			Intent shGold = new Intent();  
		     			shGold.putExtra("gold", gold);  
		                setResult(colorEnum.SHGOLD.ordinal(), shGold ); 
		                editor.putBoolean("shGold", true);
		                editor.putBoolean("shCoral", false);
		                editor.putBoolean("shWheat", false);
		                editor.putBoolean("shWood", false);
		                editor.putBoolean("shNone", false);
	     			}
	     			else if(checkedId==shWood.getId())
	     			{
	     				float[] wood={1f,0.83f, 0.61f};
		     			Intent shWood = new Intent();  
		     			shWood.putExtra("wood", wood);  
		                setResult(colorEnum.SHWOOD.ordinal(), shWood ); 
		                editor.putBoolean("shWood", true);
		                editor.putBoolean("shCoral", false);
		                editor.putBoolean("shWheat", false);
		                editor.putBoolean("shGold", false);
		                editor.putBoolean("shNone", false);
	     			}
	     			else if(checkedId==shNone.getId())
	     			{
	     				float[] shnone={0f,0f,0f};
		     			Intent shNone = new Intent();  
		     			shNone.putExtra("shNone", shnone);  
		                setResult(colorEnum.SHNONE.ordinal(), shNone ); 
		                editor.putBoolean("shNone", true);
		                editor.putBoolean("shCoral", false);
		                editor.putBoolean("shWheat", false);
		                editor.putBoolean("shGold", false);
		                editor.putBoolean("shWood", false);
	     			}
	     	     editor.commit();
	     		}
	     }); 
    }
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


}
