package com.example.drender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
public class OBJParser {
	int numVertices=0;
	int numFaces=0;
	Vector<Short> faces=new Vector<Short>();//三角面数组
	Vector<Short> vtPointer=new Vector<Short>();//纹理数组
	Vector<Short> vnPointer=new Vector<Short>();//法线数组
	Vector<Float> v=new Vector<Float>();//顶点坐标数组
	Vector<Float> vn=new Vector<Float>();//法线坐标数组
	Vector<Float> vt=new Vector<Float>();//纹理坐标数组
	Vector<TDModelPart> parts=new Vector<TDModelPart>();
	public OBJParser(){
	}

	public TDModel parseOBJ(InputStream is) {//obj文件解析
		BufferedReader reader=null;
		String line = null;
		try {//try to read lines of the file
			reader = new BufferedReader(new InputStreamReader(is));

			while((line = reader.readLine()) != null) {
				//Log.v("obj",line);
				if(line.startsWith("f")){//面
					processFLine(line);
				}
				else
					if(line.startsWith("vn")){//顶点法线坐标
						processVNLine(line);
					}
					else
						if(line.startsWith("vt")){//纹理坐标
							processVTLine(line);
						}
						else
							if(line.startsWith("v")){ //顶点坐标
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

	private void processVLine(String line){//顶点坐标处理，将顶点坐标存储到到顶点坐标数组
		String [] tokens=line.split("[ ]+"); //split the line at the spaces
		int c=tokens.length; 
		for(int i=1; i<c; i++){ //add the vertex to the vertex array
			v.add(Float.valueOf(tokens[i]));
		}
	}
	
	private void processVNLine(String line){//法线坐标处理，将法线坐标存储到法线坐标数组
		String [] tokens=line.split("[ ]+"); //split the line at the spaces
		int c=tokens.length; 
		for(int i=1; i<c; i++){ //add the vertex to the vertex array
			vn.add(Float.valueOf(tokens[i]));
		}
	}
	
	private void processVTLine(String line){//纹理坐标处理，将纹理坐标存储到纹理坐标数组
		String [] tokens=line.split("[ ]+"); //split the line at the spaces
		int c=tokens.length; 
		for(int i=1; i<c; i++){ //add the vertex to the vertex array
			vt.add(Float.valueOf(tokens[i]));
		}
	}
	//文件格式，只匹配一次
	int type=0;
	private void processFLine(String line){//面处理，解析每一个面由哪些顶点组成
		String [] tokens=line.split("[ ]+");
		int c=tokens.length;

		if(type==1 || (type==0 && tokens[1].matches("[0-9]+"))){ //文件格式为：f: v
			type=1;
			if(c==4){//3 faces
				for(int i=1; i<c; i++){
					Short s=Short.valueOf(tokens[i]);
					s--;//下标后移一位
					faces.add(s);
				}
			}
			else{//more faces
				Vector<Short> polygon=new Vector<Short>();//多边形
				for(int i=1; i<tokens.length; i++){
					Short s=Short.valueOf(tokens[i]);
					s--;
					polygon.add(s);
				}
				faces.addAll(Triangulator.triangulate(polygon));//triangulate the polygon and add the resulting faces
			}
		}
		else if(type==2 || (type==0 && tokens[1].matches("[0-9]+/[0-9]+"))){//文件格式为：if: v/vt
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
		else if(type==3 || (type==0 && tokens[1].matches("[0-9]+//[0-9]+"))){//文件格式为：f: v//vn
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
		else if(type==4 || (type==0 && tokens[1].matches("[0-9]+/[0-9]+/[0-9]+"))){//文件格式为：f: v/vt/vn
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
