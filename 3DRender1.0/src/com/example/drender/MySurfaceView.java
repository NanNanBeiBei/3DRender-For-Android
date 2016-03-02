package com.example.drender;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE = 0.4f; 
	private static final int NONE = 0;  
    private static final int ROTATION = 1;  
    private static final int ZOOM = 2;  
	private int mode=NONE;
	float oldDistance;                    //双指触屏的初始距离
	@Override
	public void setRenderer(Renderer renderer) {
		this.renderer=(MyRenderer)renderer;
		super.setRenderer(renderer);
	}

	private MyRenderer renderer;
    
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
   
	//@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event){	
		float rx = event.getX();
		float ry = event.getY();
		//获取触屏事件类型
	    int command = event.getAction() & MotionEvent.ACTION_MASK;
		//int numpoint=event.getPointerCount();
		// If a touch is moved on the screen
		if (command == MotionEvent.ACTION_DOWN) { 
		
            mode = ROTATION;  
	    }
		if (command == MotionEvent.ACTION_POINTER_DOWN) {
		    //计算双指的初始距离 
			oldDistance = (float)Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)));
			 if (oldDistance > 1f) {    
                 mode = ZOOM;  
             }  
		}
	    if (command == MotionEvent.ACTION_UP) {
	    	mode=NONE;
		}
	    if (command == MotionEvent.ACTION_POINTER_UP) {
             mode=NONE;
		}
	    if (command == MotionEvent.ACTION_MOVE) {
			//旋转操作
			if(mode==ROTATION){
		
				float dx = rx -  renderer.getoldX();
				float dy = ry - renderer.getoldY();
			    renderer.setxrot(renderer.getxrot()+dy * TOUCH_SCALE);//xrot += dy * TOUCH_SCALE;
			    renderer.setyrot(renderer.getyrot()+dx * TOUCH_SCALE);//yrot += dx * TOUCH_SCALE;
			}
			//缩放操作
			else if(mode==ZOOM){
				  float newDistance;  
                  newDistance = (float)Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)));  
                  //放大
                  if(newDistance >oldDistance) {   
                      renderer.setscale(renderer.getscale()+(newDistance-oldDistance)/100);//比较适合的缩放倍数
                      oldDistance = newDistance;  
                  } 
                  //缩小
                  else{
                	  renderer.setscale(renderer.getscale()-(oldDistance-newDistance)/100);
                      oldDistance = newDistance;   
                  }
			}
	    }
	    //设置旋转后的位置
		renderer.setoldX(rx);
		renderer.setoldY(ry);
		return true;
  }
}

