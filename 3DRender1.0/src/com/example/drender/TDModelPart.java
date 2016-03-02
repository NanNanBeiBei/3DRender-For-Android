package com.example.drender;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Vector;

//import com.example.objLoader.Material;

public class TDModelPart {
	
	Vector<Short> faces;
	Vector<Short> vtPointer;
	Vector<Short> vnPointer;
	private FloatBuffer normalBuffer;
	private ShortBuffer faceBuffer;
	
	public TDModelPart(Vector<Short> faces, Vector<Short> vtPointer,
			Vector<Short> vnPointer, Vector<Float> vn) {
		super();
		this.faces = faces;
		this.vtPointer = vtPointer;
		this.vnPointer = vnPointer;
		
		//������������buffer�����ڹ��ռ���
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vnPointer.size() * 4*3);
		byteBuf.order(ByteOrder.nativeOrder());
		normalBuffer = byteBuf.asFloatBuffer();
		for(int i=0; i<vnPointer.size(); i++){
			float x=vn.get(vnPointer.get(i)*3);
			float y=vn.get(vnPointer.get(i)*3+1);
			float z=vn.get(vnPointer.get(i)*3+2);
			normalBuffer.put(x);
			normalBuffer.put(y);
			normalBuffer.put(z);
		}
		normalBuffer.position(0);
		
        //��������������Buffer,��ģ�ͽ�������
		ByteBuffer fBuf = ByteBuffer.allocateDirect(faces.size() * 2);
		fBuf.order(ByteOrder.nativeOrder());
		faceBuffer = fBuf.asShortBuffer();
		faceBuffer.put(toPrimitiveArrayS(faces));
		faceBuffer.position(0);
	}
	
	public ShortBuffer getFaceBuffer(){
		return faceBuffer;
	}
	
	public FloatBuffer getNormalBuffer(){
		return normalBuffer;
	}
	//��Vector����ת����Float����
	private static short[] toPrimitiveArrayS(Vector<Short> vector){
		short[] s;
		s=new short[vector.size()];
		for (int i=0; i<vector.size(); i++){
			s[i]=vector.get(i);
		}
		return s;
	}
	
	public int getFacesCount(){
		return faces.size();
	}
	

}
