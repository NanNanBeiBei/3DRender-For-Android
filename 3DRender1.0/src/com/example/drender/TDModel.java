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
	String mVertexShader;								//顶点着色器脚本代码
	String mFragmentShader;								//片元着色器脚本代码
	int mProgram;										//自定义渲染管线着色程序id
	
	
	
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
		 * mVertextShader是顶点着色器脚本代码
		 * 调用工具类方法获取着色器脚本代码, 着色器脚本代码放在assets目录中
		 * 传入的两个参数是 脚本名称 和 应用的资源
		 * 应用资源Resources就是res目录下的那写文件
		 */

		
		/*
		 * 创建着色器程序, 传入顶点着色器脚本 和 片元着色器脚本 注意顺序不要错
		 */
	
		
	}
	
	
	
	public void draw(GL10 gl, float[] dColor,float[] aColor,float[] sColor,float[] amlight,float[] shlight,float[] dilight) {//利用顶点集合、索引集合以及渲染材质进行绘制
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
	
	//转化成Float[]类型
	private static float[] toPrimitiveArrayF(Vector<Float> vector){
		float[] f;
		f=new float[vector.size()];
		for (int i=0; i<vector.size(); i++){
			f[i]=vector.get(i);
		}
		return f;
	}

}
