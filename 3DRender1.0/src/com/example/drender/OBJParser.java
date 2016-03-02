package com.example.drender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
public class OBJParser {
	int numVertices=0;
	int numFaces=0;
	Vector<Short> faces=new Vector<Short>();//����������
	Vector<Short> vtPointer=new Vector<Short>();//��������
	Vector<Short> vnPointer=new Vector<Short>();//��������
	Vector<Float> v=new Vector<Float>();//������������
	Vector<Float> vn=new Vector<Float>();//������������
	Vector<Float> vt=new Vector<Float>();//������������
	Vector<TDModelPart> parts=new Vector<TDModelPart>();
	public OBJParser(){
	}

	public TDModel parseOBJ(InputStream is) {//obj�ļ�����
		BufferedReader reader=null;
		String line = null;
		try {//try to read lines of the file
			reader = new BufferedReader(new InputStreamReader(is));

			while((line = reader.readLine()) != null) {
				//Log.v("obj",line);
				if(line.startsWith("f")){//��
					processFLine(line);
				}
				else
					if(line.startsWith("vn")){//���㷨������
						processVNLine(line);
					}
					else
						if(line.startsWith("vt")){//��������
							processVTLine(line);
						}
						else
							if(line.startsWith("v")){ //��������
								processVLine(line);
							}
			}
		} 		
		catch(IOException e){
			System.out.println("wtf...");
		}

		if(faces!= null){//if not this is not the start of the first group
			TDModelPart model=new TDModelPart(faces, vtPointer, vnPointer,vn);
			parts.add(model);
		}
	
		TDModel t=new TDModel(v,vn,vt,parts);

		t.buildVertexBuffer();
	
		return t;
	}

	private void processVLine(String line){//�������괦������������洢����������������
		String [] tokens=line.split("[ ]+"); //split the line at the spaces
		int c=tokens.length; 
		for(int i=1; i<c; i++){ //add the vertex to the vertex array
			v.add(Float.valueOf(tokens[i]));
		}
	}
	
	private void processVNLine(String line){//�������괦������������洢��������������
		String [] tokens=line.split("[ ]+"); //split the line at the spaces
		int c=tokens.length; 
		for(int i=1; i<c; i++){ //add the vertex to the vertex array
			vn.add(Float.valueOf(tokens[i]));
		}
	}
	
	private void processVTLine(String line){//�������괦������������洢��������������
		String [] tokens=line.split("[ ]+"); //split the line at the spaces
		int c=tokens.length; 
		for(int i=1; i<c; i++){ //add the vertex to the vertex array
			vt.add(Float.valueOf(tokens[i]));
		}
	}
	//�ļ���ʽ��ֻƥ��һ��
	int type=0;
	private void processFLine(String line){//�洦������ÿһ��������Щ�������
		String [] tokens=line.split("[ ]+");
		int c=tokens.length;

		if(type==1 || (type==0 && tokens[1].matches("[0-9]+"))){ //�ļ���ʽΪ��f: v
			type=1;
			if(c==4){//3 faces
				for(int i=1; i<c; i++){
					Short s=Short.valueOf(tokens[i]);
					s--;//�±����һλ
					faces.add(s);
				}
			}
			else{//more faces
				Vector<Short> polygon=new Vector<Short>();//�����
				for(int i=1; i<tokens.length; i++){
					Short s=Short.valueOf(tokens[i]);
					s--;
					polygon.add(s);
				}
				faces.addAll(Triangulator.triangulate(polygon));//triangulate the polygon and add the resulting faces
			}
		}
		else if(type==2 || (type==0 && tokens[1].matches("[0-9]+/[0-9]+"))){//�ļ���ʽΪ��if: v/vt
			type=2;
			if(c==4){//3 faces
				for(int i=1; i<c; i++){
					Short s=Short.valueOf(tokens[i].split("/")[0]);
					s--;
					faces.add(s);
					s=Short.valueOf(tokens[i].split("/")[1]);
					s--;
					vtPointer.add(s);
				}
			}
			else{//triangulate
				Vector<Short> tmpFaces=new Vector<Short>();
				Vector<Short> tmpVt=new Vector<Short>();
				for(int i=1; i<tokens.length; i++){
					Short s=Short.valueOf(tokens[i].split("/")[0]);
					s--;
					tmpFaces.add(s);
					s=Short.valueOf(tokens[i].split("/")[1]);
					s--;
					tmpVt.add(s);
				}
				faces.addAll(Triangulator.triangulate(tmpFaces));
				vtPointer.addAll(Triangulator.triangulate(tmpVt));
			}
		}
		else if(type==3 || (type==0 && tokens[1].matches("[0-9]+//[0-9]+"))){//�ļ���ʽΪ��f: v//vn
			type=3;
			if(c==4){//3 faces
				for(int i=1; i<c; i++){
					Short s=Short.valueOf(tokens[i].split("//")[0]);
					s--;
					faces.add(s);
					s=Short.valueOf(tokens[i].split("//")[1]);
					s--;
					vnPointer.add(s);
				}
			}
			else{//triangulate
				Vector<Short> tmpFaces=new Vector<Short>();
				Vector<Short> tmpVn=new Vector<Short>();
				for(int i=1; i<tokens.length; i++){
					Short s=Short.valueOf(tokens[i].split("//")[0]);
					s--;
					tmpFaces.add(s);
					s=Short.valueOf(tokens[i].split("//")[1]);
					s--;
					tmpVn.add(s);
				}
				faces.addAll(Triangulator.triangulate(tmpFaces));
				vnPointer.addAll(Triangulator.triangulate(tmpVn));
			}
		}
		else if(type==4 || (type==0 && tokens[1].matches("[0-9]+/[0-9]+/[0-9]+"))){//�ļ���ʽΪ��f: v/vt/vn
			type=4;
			if(c==4){//3 faces
				for(int i=1; i<c; i++){
					Short s=Short.valueOf(tokens[i].split("/")[0]);
					s--;
					faces.add(s);
					s=Short.valueOf(tokens[i].split("/")[1]);
					s--;
					vtPointer.add(s);
					s=Short.valueOf(tokens[i].split("/")[2]);
					s--;
					vnPointer.add(s);
				}
			}
			else{//triangulate
				Vector<Short> tmpFaces=new Vector<Short>();
				Vector<Short> tmpVn=new Vector<Short>();
				for(int i=1; i<tokens.length; i++){
					Short s=Short.valueOf(tokens[i].split("/")[0]);
					s--;
					tmpFaces.add(s);
				}
				faces.addAll(Triangulator.triangulate(tmpFaces));
				vtPointer.addAll(Triangulator.triangulate(tmpVn));
				vnPointer.addAll(Triangulator.triangulate(tmpVn));
			}
		}
	}

}
