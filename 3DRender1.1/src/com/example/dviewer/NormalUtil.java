package com.example.dviewer;

import java.util.Set;
//��ʾ���������࣬�����һ�������ʾһ��������
public class NormalUtil 
{
   public static final float DIFF=0.0000001f;//�ж������������Ƿ���ͬ����ֵ
   //��������XYZ���ϵķ���
   float nx;
   float ny;
   float nz;
   
   public NormalUtil(float nx,float ny,float nz)
   {
	   this.nx=nx;
	   this.ny=ny;
	   this.nz=nz;
   }
   
   @Override 
   public boolean equals(Object o)
   {
	   if(o instanceof  NormalUtil)
	   {//������������XYZ���������ĲС��ָ������ֵ����Ϊ���������������
		   NormalUtil tn=(NormalUtil)o;
		   if(Math.abs(nx-tn.nx)<DIFF&&
			  Math.abs(ny-tn.ny)<DIFF&&
			  Math.abs(ny-tn.ny)<DIFF
             )
		   {
			   return true;
		   }
		   else
		   {
			   return false;
		   }
	   }
	   else
	   {
		   return false;
	   }
   }
   
   //����Ҫ�õ�HashSet�����һ��Ҫ��дhashCode����
   @Override
   public int hashCode()
   {
	   return 1;
   }
   
   //������ƽ��ֵ�Ĺ��߷���
   public static float[] getAverage(Set<NormalUtil> sn)
   {
	   //��ŷ������͵�����
	   float[] result=new float[3];
	   //�Ѽ��������еķ��������
	   for(NormalUtil n:sn)
	   {
		   result[0]+=n.nx;
		   result[1]+=n.ny;
		   result[2]+=n.nz;
	   }	   
	   //����ͺ�ķ��������
	   return LoadObjUtil.vectorNormal(result);
   }
}
