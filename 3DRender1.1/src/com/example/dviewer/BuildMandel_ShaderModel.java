package com.example.dviewer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.opengl.GLES20;

//���غ�����塪����Я��������Ϣ����ɫ���
public class BuildMandel_ShaderModel
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id  
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������  
    int mresolutionXHandle;//resolution��������
    int mresolutionYHandle;//resolution��������
    int mtime;            //ʱ����������
    int maTexCoorHandle; //��������������������  
    String mVertexShader;//������ɫ������ű�    	 
    String mFragmentShader;//ƬԪ��ɫ������ű�    
	FloatBuffer   mVertexBuffer;//�����������ݻ���  
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;  
    public float getTime()
    {
    	SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss:SSS");
    	String currenttime=dateFormatGmt.format(new Date());
    	String[] f=currenttime.split(":");
    	float s=Float.valueOf(f[6]);//��ȡ����
    	float t=Float.valueOf(f[5]);//��ȡ��
    	float fianltime=s+t*1000;
    	return fianltime/1000;	
    } 
    public BuildMandel_ShaderModel(MySurfaceView mv,Model model)
    {    	
    	//��ʼ��������������ɫ����
    	initVertexData(model.v,model.vt);
    	//��ʼ��shader        
    	initShader(mv);
    }
    
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData(float[] vertices,float texCoors[])
    {
    	//�����������ݵĳ�ʼ��================begin============================
    	vCount=vertices.length/3;   
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================             
        
        //���������������ݵĳ�ʼ��================begin============================  
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoors.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoors);//�򻺳����з��붥��������������
        mTexCoorBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //���������������ݵĳ�ʼ��================end============================
    }
    
    //��ʼ��shader
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("mandelvertex", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("mandelfrag", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
      
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoord");   
        //��ȡ�ֱ�����������
        mresolutionXHandle=GLES20.glGetUniformLocation(mProgram, "resolutionX"); 
        //��ȡ�ֱ�����������
        mresolutionYHandle=GLES20.glGetUniformLocation(mProgram, "resolutionY"); 
        //��ȡʱ����������
        mtime=GLES20.glGetUniformLocation(mProgram, "time");      
    }

    //��ʼ��shader
    
    public void drawSelf(int texId)
    {        
    	 //�ƶ�ʹ��ĳ����ɫ������
    	 GLES20.glUseProgram(mProgram);
         //�����ձ任��������ɫ������
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
       
         GLES20.glUniform1f(mtime, getTime());
         //�ֱ������Դ�����ɫ��
         GLES20.glUniform1f(mresolutionXHandle,  720f);
         //�ֱ������Դ�����ɫ��
         GLES20.glUniform1f(mresolutionYHandle,  1280f);
         // ������λ�����ݴ�����Ⱦ����
         GLES20.glVertexAttribPointer  
         (
         		maPositionHandle,   
         		3, 
         		GLES20.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );       
        
         GLES20.glVertexAttribPointer  
         (
        		maTexCoorHandle, 
         		2, 
         		GLES20.GL_FLOAT, 
         		false,
                2*4,   
                mTexCoorBuffer
         );
         //���ö���λ�á���������������������
         GLES20.glEnableVertexAttribArray(maPositionHandle);  
     
         GLES20.glEnableVertexAttribArray(maTexCoorHandle); 
         //������
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         //���Ƽ��ص�����
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }    

}

