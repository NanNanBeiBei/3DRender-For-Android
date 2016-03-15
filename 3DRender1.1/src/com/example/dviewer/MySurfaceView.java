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
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��    
    Model model;
    private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��X����
    float yAngle;//��Y����ת�ĽǶ�
	float xAngle; //��X����ת�ĽǶ�
	int textureId;//ϵͳ���������id
	int tunnelId;//ϵͳ���������id
	int twistId;//ϵͳ���������id
	int mandelId;//ϵͳ���������id
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
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);						        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
	
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent event) 
    {	
    	float rx = event.getX();
		float ry = event.getY();
		//��ȡ�����¼�����
	    int command = event.getAction() & MotionEvent.ACTION_MASK;
		if (command == MotionEvent.ACTION_DOWN) { 
		
            mode = ROTATION;  
	    }
		if (command == MotionEvent.ACTION_POINTER_DOWN) {
		    //����˫ָ�ĳ�ʼ���� 
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
			//��ת����
			if(mode==ROTATION){
		
				float dx = rx -  mPreviousX;
				float dy = ry -  mPreviousY;
				yAngle += dx * TOUCH_SCALE_FACTOR;//������y����ת�Ƕ�
		        xAngle += dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
		        
			}
			//���Ų���
			else if(mode==ZOOM){
				  float newDistance;  
                  newDistance = (float)Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)));  
                  //�Ŵ�
                  if(newDistance >oldDistance) {   
                      this.setScale(this.getScale()+(newDistance-oldDistance)/100);//�Ƚ��ʺϵ����ű���
                      oldDistance = newDistance;  
                  } 
                  //��С
                  else{
                	  this.setScale(this.getScale()-(oldDistance-newDistance)/100);
                      oldDistance = newDistance;   
                  }
			}
	    }
	    mPreviousY = ry;//��¼���ر�λ��
        mPreviousX = rx;//��¼���ر�λ��
        return true;
    }
   
	@SuppressLint("InlinedApi")
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {  
	
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
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
            //ƽ��
            MatrixState.translate(0, -1f, -60f);   //ch.obj
            //��Y�ᡢx����ת
            MatrixState.rotate(yAngle, 0, 1, 0);
            MatrixState.rotate(xAngle, 1, 0, 0);
            //����
            MatrixState.scale(scale, scale, scale);         
            //�����ص����岻Ϊ�����������
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
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 100);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0,0,0,0f,0f,-1f,0f,1.0f,0.0f);
        }

        @SuppressLint({ "InlinedApi", "NewApi" })
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);    
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
             
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
          //��ʼ����Դλ��
            MatrixState.setLightLocation(40, 10, 20);
            //����Ҫ���Ƶ�����
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
			//��������ID
			int[] textures = new int[1];
			GLES20.glGenTextures
			(
					1,          //����������id������
					textures,   //����id������
					0           //ƫ����
			);    
			int textureId=textures[0];    
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);
	        
	        //ͨ������������ͼƬ===============begin===================
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
	        //ͨ������������ͼƬ===============end===================== 
		   	GLUtils.texImage2D
		    (
		    		GLES20.GL_TEXTURE_2D, //��������
		     		0, 
		     		GLUtils.getInternalFormat(bitmapTmp), 
		     		bitmapTmp, //����ͼ��
		     		GLUtils.getType(bitmapTmp), 
		     		0 //����߿�ߴ�
		     );
		    bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
	        return textureId;
		}
}
