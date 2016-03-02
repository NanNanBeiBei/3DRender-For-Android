package com.example.drender;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;


public class MyRenderer implements Renderer  {

	private MainActivity context;
	//光照设置
	//环境光设置
	private float[] lightAmbient = { 0.2f, 0.5f, 0.8f, 1.0f };
	public float[] getlightAmbient() {
		return lightAmbient;
	}
	public void setlightAmbient(float r, float g, float b,float a) {
		this.lightAmbient = new float[]{r,g,b,a};
	}
	public FloatBuffer getlightAmbientBuffer(float[] Ambient){   
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(Ambient.length * 4);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(Ambient);
		f.position(0);
		return f;
	}
	//镜面反射光设置
	private float[] lightShine = { 1.0f, 0f, 1.0f, 1.0f };
	public float[] getlightShine() {
		return lightShine;
	}
	public void setlightShine(float r, float g, float b,float a) {
		this.lightShine = new float[]{r,g,b,a};
	}
	public FloatBuffer getlightShineBuffer(float[] Shine){   
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(Shine.length * 4);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(Shine);
		f.position(0);
		return f;
	}
	//漫反射光设置				
    private float[] lightDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
	public float[] getlightDiffuse() {
		return lightDiffuse;
	}
	public void setlightDiffuse(float r, float g, float b,float a) {
		this.lightDiffuse = new float[]{r,g,b,a};
	}
	public FloatBuffer getlightDiffuseBuffer(float[] Diffuse){   
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(Diffuse.length * 4);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(Diffuse);
		f.position(0);
		return f;
	}
	
	//颜色设置
	private float[] diffuseColor={ 0f, 0f, 0f};
	
	public float[] getDiffuseColor() {
		return diffuseColor;
	}

	public void setDiffuseColor(float r, float g, float b) {
		this.diffuseColor = new float[]{r,g,b};
	}
	
   private float[] ambientColor={ 0f, 0f, 0.9f};
	
	public float[] getambientColor() {
		return ambientColor;
	}

	public void setambientColor(float r, float g, float b) {
		this.ambientColor = new float[]{r,g,b};
	}
	
	private float[] shineColor={ 0f, 0f, 0f};
		
	public float[] getShineColor() {
			return shineColor;
		}

	public void setShineColor(float r, float g, float b) {
			this.shineColor = new float[]{r,g,b};
		}
	
	//旋转参数设置
    private float scale=1.5f; // X Scale
	
	public float getscale(){
		return scale;
	}
	
	public void setscale(float x){
		this.scale=x;
	}
	
	private float xspeed; // X Rotation Speed ( NEW )
	public float getxspeed(){
		return xspeed;
	}
	
	public void setxspeed(float x){
		this.xspeed=x;
	}
	private float yspeed; // Y Rotation Speed ( NEW )
	public float getyspeed(){
		return yspeed;
	}
	
	public void setyspeed(float x){
		this.yspeed=x;
	}
	private float oldX;
	public float getoldX(){
		return oldX;
	}
	
	public void setoldX(float x){
		this.oldX=x;
	}
	private float oldY;
	public float getoldY(){
		return oldY;
	}
	
	public void setoldY(float x){
		this.oldY=x;
	}
	 private float xrot; // X Rotation
	public float getxrot(){
		return xrot;
	}
	public void setxrot(float xangle){
		this.xrot=xangle;
	}
	
	private float yrot; // Y Rotation
	public float getyrot(){
		return yrot;
	}
	public void setyrot(float yangle){
		this.yrot=yangle;
	}
	
	//深度设置
	private float z = 50.0f;
	public float getz(){
		return z;
	}
	public void setz(float x){
		this.z=x;
	} 

	public MyRenderer(MainActivity context){//构造函数

		this.context=context;
	}

 	public void onSurfaceCreated(GL10 gl, EGLConfig config) {//Render被创建的时候触发

		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(1f, 1f, 1f, 1f);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

 	}

	public void onDrawFrame(GL10 gl) {//Render开始执行模型绘制
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glTranslatef(0f, -3f, -40); 
		gl.glScalef(scale, scale, scale);
		gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); 
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); 
		if(context.getModel()!=null){
			context.getModel().draw(gl, diffuseColor, ambientColor, shineColor,lightAmbient,lightShine,lightDiffuse);//(gl,diffuseColor,ambientColor,shineColor); // Draw the square
		}
		gl.glLoadIdentity();
		xrot += xspeed;
		yrot += yspeed;
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {//当窗口发生变化的时候触发
		gl.glViewport(0, 0, width, height); // Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select The Projection Matrix
		gl.glLoadIdentity(); // Reset The Projection Matrix
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				500.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select The Modelview Matrix
		gl.glLoadIdentity(); // Reset The Modelview Matrix
	}
	
}

	


