package com.example.drender;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Material {
	
	float[] ambientColor; //颜色
	float[] diffuseColor;
	float[] specularColor;
	
	public Material(){
	
	}

    //环境颜色属性设置
	public float[] getAmbientColor() {
		return ambientColor;
	}
	public void setAmbientColor(float r, float g, float b) {
		ambientColor = new float[3];
		ambientColor[0]=r;
		ambientColor[1]=g;
		ambientColor[2]=b;
	}
	//提高性能 设置buffer
	public FloatBuffer getAmbientColorBuffer(){   
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(12);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(ambientColor);
		f.position(0);
		return f;
	}
	
    //漫反射颜色属性设置
	public float[] getDiffuseColor() {
		return diffuseColor;
	}
	public void setDiffuseColor(float r, float g, float b) {
		diffuseColor = new float[3];
		diffuseColor[0]=r;
		diffuseColor[1]=g;
		diffuseColor[2]=b;
	}
	//提高性能 设置buffer
	public FloatBuffer getDiffuseColorBuffer(){
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(12);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(diffuseColor);
		f.position(0);
		return f;
	}

    //镜面反射颜色设置
	public float[] getSpecularColor() {
		return specularColor;
	}
	public void setSpecularColor(float r, float g, float b) {
		specularColor = new float[3];
		specularColor[0]=r;
		specularColor[1]=g;
		specularColor[2]=b;
	}
	//提高性能 设置buffer
	public FloatBuffer getSpecularColorBuffer(){
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(12);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(specularColor);
		f.position(0);
		return f;
	}
	
	//光照设置
	private float[] lightAmbient = { 0.2f, 0.5f, 0.8f, 1.0f };
	public float[] getlightAmbient() {
		return lightAmbient;
	}
	public void setlightAmbient(float r, float g, float b,float a) {
		lightAmbient = new float[4];
		lightAmbient[0]=r;
		lightAmbient[1]=g;
		lightAmbient[2]=b;
		lightAmbient[3]=a;
	}
	public FloatBuffer getlightAmbientBuffer(){   
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(lightAmbient.length * 4);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(lightAmbient);
		f.position(0);
		return f;
	}
	
	private float[] lightShine = { 1.0f, 0f, 1.0f, 1.0f };
	public float[] getlightShine() {
		return lightShine;
	}
	public void setlightShine(float r, float g, float b,float a) {
		this.lightShine = new float[]{r,g,b,a};
	}
	public FloatBuffer getlightShineBuffer(){   
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(lightShine.length * 4);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(lightShine);
		f.position(0);
		return f;
	}
					
    private float[] lightDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
	public float[] getlightDiffuse() {
		return lightDiffuse;
	}
	public void setlightDiffuse(float r, float g, float b,float a) {
		this.lightDiffuse = new float[]{r,g,b,a};
	}
	public FloatBuffer getlightDiffuseBuffer(){   
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(lightDiffuse);
		f.position(0);
		return f;
	}
	
	private float[] lightPosition = { 0.0f, -3.0f, 2.0f, 1.0f };
	public float[] getlightPosition() {
		return lightPosition;
	}
	public void setlightPosition(float r, float g, float b,float a) {
		this.lightPosition = new float[]{r,g,b,a};
	}
	public FloatBuffer getlightPositionBuffer(){   
		FloatBuffer f;
		ByteBuffer b = ByteBuffer.allocateDirect(lightPosition.length * 4);
		b.order(ByteOrder.nativeOrder());
		f = b.asFloatBuffer();
		f.put(lightPosition);
		f.position(0);
		return f;
	}


}
