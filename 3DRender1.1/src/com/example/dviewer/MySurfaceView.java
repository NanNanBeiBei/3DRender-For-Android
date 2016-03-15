package com.example.dviewer;
import java.io.IOException;
import java.io.InputStream;

import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Build;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;




import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

@SuppressLint("NewApi")
class MySurfaceView extends GLSurfaceView 
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private SceneRenderer mRenderer;//场景渲染器    
    Model model;
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标
    float yAngle;//绕Y轴旋转的角度
	float xAngle; //绕X轴旋转的角度
	int textureId;//系统分配的纹理id
	int tunnelId;//系统分配的纹理id
	int twistId;//系统分配的纹理id
	int mandelId;//系统分配的纹理id
	private static final int NONE = 0;  
    private static final int ROTATION = 1;  
    private static final int ZOOM = 2;  
	private int mode=NONE;
	float oldDistance;    
	private float scale=1.0f;
	public float getScale(){
		return scale;
	}
	public void setScale(float sc){
	 this.scale=sc;;
	}

	
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	@SuppressLint("NewApi")
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //设置使用OPENGL ES2.0
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);						        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
	
	//触摸事件回调方法
    @Override 
    public boolean onTouchEvent(MotionEvent event) 
    {	
    	float rx = event.getX();
		float ry = event.getY();
		//获取触屏事件类型
	    int command = event.getAction() & MotionEvent.ACTION_MASK;
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
		
				float dx = rx -  mPreviousX;
				float dy = ry -  mPreviousY;
				yAngle += dx * TOUCH_SCALE_FACTOR;//设置绕y轴旋转角度
		        xAngle += dy * TOUCH_SCALE_FACTOR;//设置绕x轴旋转角度
		        
			}
			//缩放操作
			else if(mode==ZOOM){
				  float newDistance;  
                  newDistance = (float)Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)));  
                  //放大
                  if(newDistance >oldDistance) {   
                      this.setScale(this.getScale()+(newDistance-oldDistance)/100);//比较适合的缩放倍数
                      oldDistance = newDistance;  
                  } 
                  //缩小
                  else{
                	  this.setScale(this.getScale()-(oldDistance-newDistance)/100);
                      oldDistance = newDistance;   
                  }
			}
	    }
	    mPreviousY = ry;//记录触控笔位置
        mPreviousX = rx;//记录触控笔位置
        return true;
    }
   
	@SuppressLint("InlinedApi")
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {  
	
        public void onDrawFrame(GL10 gl) 
        { 
        	//清除深度缓冲与颜色缓冲
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
           
            BuildTexture_ShaderModel textureModel = null;
        	BuildLight_ShaderModel lightModel=null;
        	BuildTunnel_ShaderModel tunnelModel=null;
        	BuildTwist_ShaderModel twistModel=null;
        	BuildMandel_ShaderModel mandelModel=null;
            if(MainActivity.getTextured()&&LoadObjUtil.getSTFlag()==true)
            {
            	textureModel=new BuildTexture_ShaderModel(MySurfaceView.this,MainActivity.getModel());
            }
            else if(MainActivity.getLighted()||LoadObjUtil.getSTFlag()==false)
            {
            	lightModel=new BuildLight_ShaderModel(MySurfaceView.this,MainActivity.getModel());
            }
            else if(MainActivity.getTunneled()&&LoadObjUtil.getSTFlag()==true)
            {
            	tunnelModel=new BuildTunnel_ShaderModel(MySurfaceView.this,MainActivity.getModel());
            	
            }
            else if(MainActivity.getTwisted()&&LoadObjUtil.getSTFlag()==true)
            {
            	twistModel=new BuildTwist_ShaderModel(MySurfaceView.this,MainActivity.getModel());
            	
            }
            else if(MainActivity.getMandeled()&&LoadObjUtil.getSTFlag()==true)
            {
            	mandelModel=new BuildMandel_ShaderModel(MySurfaceView.this,MainActivity.getModel());
            	
            }
            else
            {
            	lightModel=new BuildLight_ShaderModel(MySurfaceView.this,MainActivity.getModel());
            }
            MatrixState.pushMatrix();
            //平移
            MatrixState.translate(0, -1f, -60f);   //ch.obj
            //绕Y轴、x轴旋转
            MatrixState.rotate(yAngle, 0, 1, 0);
            MatrixState.rotate(xAngle, 1, 0, 0);
            //缩放
            MatrixState.scale(scale, scale, scale);         
            //若加载的物体不为空则绘制物体
            if(textureModel!=null)
            {
            	textureModel.drawSelf(textureId);
            }   
            else if(lightModel!=null)
            {
            	lightModel.drawSelf();
            }
            else if(tunnelModel!=null)
            {
            	tunnelModel.drawSelf(tunnelId);
            }
            else if(twistModel!=null)
            {
            	twistModel.drawSelf(twistId);
            }
            else if(mandelModel!=null)
            {
            	mandelModel.drawSelf(mandelId);
            }
            MatrixState.popMatrix();    
        
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置 
        	GLES20.glViewport(0, 0, width, height); 
        	//计算GLSurfaceView的宽高比
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 100);
            //调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0,0,0,0f,0f,-1f,0f,1.0f,0.0f);
        }

        @SuppressLint({ "InlinedApi", "NewApi" })
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);    
            //打开深度检测
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
             
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //初始化变换矩阵
            MatrixState.setInitStack();
          //初始化光源位置
            MatrixState.setLightLocation(40, 10, 20);
            //加载要绘制的物体
          //  model=MainActivity.getModel();
            //model=LoadObjUtil.loadFromFile(ObjFileScanActivity.br, MySurfaceView.this.getResources());
            twistId=initTexture(R.drawable.twist);
            textureId=initTexture(R.drawable.ghxp);
            tunnelId=initTexture(R.drawable.tunnel);
            mandelId=initTexture(R.drawable.mandel);
        }
    }
	 
    public int initTexture(int drawableId)//textureId
		{
			//生成纹理ID
			int[] textures = new int[1];
			GLES20.glGenTextures
			(
					1,          //产生的纹理id的数量
					textures,   //纹理id的数组
					0           //偏移量
			);    
			int textureId=textures[0];    
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);
	        
	        //通过输入流加载图片===============begin===================
	        InputStream is = this.getResources().openRawResource(drawableId);
	        Bitmap bitmapTmp;
	        try 
	        {
	        	bitmapTmp = BitmapFactory.decodeStream(is);
	        } 
	        finally 
	        {
	            try 
	            {
	                is.close();
	            } 
	            catch(IOException e) 
	            {
	                e.printStackTrace();
	            }
	        }
	        //通过输入流加载图片===============end===================== 
		   	GLUtils.texImage2D
		    (
		    		GLES20.GL_TEXTURE_2D, //纹理类型
		     		0, 
		     		GLUtils.getInternalFormat(bitmapTmp), 
		     		bitmapTmp, //纹理图像
		     		GLUtils.getType(bitmapTmp), 
		     		0 //纹理边框尺寸
		     );
		    bitmapTmp.recycle(); 		  //纹理加载成功后释放图片
	        return textureId;
		}
}
