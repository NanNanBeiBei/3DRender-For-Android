package com.example.dviewer;

public class Model {
	
    public float[] v;
    public float[] vt;
    public float[] vn;
	public Model(float[] v,float[] vn,float[] vt)
	{
		this.v=v;
		this.vn=vn;
		this.vt=vt;
	}
	public Model(float[] v,float[] vn)
	{
		this.v=v;
		this.vn=vn;
	}
}
