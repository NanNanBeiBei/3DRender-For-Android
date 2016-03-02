package com.example.drender;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;


//import com.example.objLoader.Material;
//import com.example.objLoader.TDModelPart;

public class TDModel {
	MainActivity dsf;
	String mVertexShader;								//������ɫ���ű�����
	String mFragmentShader;								//ƬԪ��ɫ���ű�����
	int mProgram;										//�Զ�����Ⱦ������ɫ����id
	
	
	
	Vector<Float> v;
	Vector<Float> vn;
	Vector<Float> vt;
	Vector<TDModelPart> parts;
	FloatBuffer vertexBuffer;


	public TDModel(Vector<Float> v, Vector<Float> vn, Vector<Float> vt,
			Vector<TDModelPart> parts) {
		super();
		this.v = v;
		this.vn = vn;
		this.vt = vt;
		this.parts = parts;

	}

	
	
	
	public void initShader(String a,String b){
		/*
		 * mVertextShader�Ƕ�����ɫ���ű�����
		 * ���ù����෽����ȡ��ɫ���ű�����, ��ɫ���ű��������assetsĿ¼��
		 * ��������������� �ű����� �� Ӧ�õ���Դ
		 * Ӧ����ԴResources����resĿ¼�µ���д�ļ�
		 */

		
		/*
		 * ������ɫ������, ���붥����ɫ���ű� �� ƬԪ��ɫ���ű� ע��˳��Ҫ��
		 */
	
		
	}
	
	
	
	public void draw(GL10 gl, float[] dColor,float[] aColor,float[] sColor,float[] amlight,float[] shlight,float[] dilight) {//���ö��㼯�ϡ����������Լ���Ⱦ���ʽ��л���
		//mVertexShader = dsf.loadFromAssetsFile("adaptive.vert");
		//mVertexShader = dsf.loadFromAssetsFile("adapt.frag");
		//mProgram = shaders.createProgram(dsf.getvert(), dsf.getfrag());
		GLES20.glUseProgram(mProgram);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		for(int i=0; i<parts.size(); i++){
			TDModelPart t=parts.get(i);
			Material m=new Material();
			m.setAmbientColor(aColor[0],aColor[1],aColor[2]);
			m.setDiffuseColor(dColor[0],dColor[1],dColor[2]);
			m.setSpecularColor(sColor[0],sColor[1],sColor[2]);
			m.setlightAmbient(amlight[0], amlight[1], amlight[2], amlight[3]);
			m.setlightShine(shlight[0], shlight[1], shlight[2], shlight[3]);
			m.setlightDiffuse(dilight[0], dilight[1], dilight[2], dilight[3]);
			if(m!=null){
				FloatBuffer amColor=m.getAmbientColorBuffer();
				FloatBuffer diColor=m.getDiffuseColorBuffer();
				FloatBuffer shColor=m.getSpecularColorBuffer();
				FloatBuffer aml=m.getlightAmbientBuffer();
				FloatBuffer shl=m.getlightShineBuffer();
				FloatBuffer dil=m.getlightDiffuseBuffer();
				FloatBuffer amp=m.getlightPositionBuffer();
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT,amColor);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,shColor);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE,diColor);
				gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, aml);
				gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, dil);
				gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, shl);
				gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, amp);
				gl.glEnable(GL10.GL_LIGHT0);
			}
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, t.getNormalBuffer());
			gl.glDrawElements(GL10.GL_TRIANGLES,t.getFacesCount(),GL10.GL_UNSIGNED_SHORT,t.getFaceBuffer());
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
			
		}
	}
	
	public void buildVertexBuffer(){
		ByteBuffer vBuf = ByteBuffer.allocateDirect(v.size() * 4);
		vBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = vBuf.asFloatBuffer();
		vertexBuffer.put(toPrimitiveArrayF(v));
		vertexBuffer.position(0);
	
	}
	
	//ת����Float[]����
	private static float[] toPrimitiveArrayF(Vector<Float> vector){
		float[] f;
		f=new float[vector.size()];
		for (int i=0; i<vector.size(); i++){
			f[i]=vector.get(i);
		}
		return f;
	}

}
