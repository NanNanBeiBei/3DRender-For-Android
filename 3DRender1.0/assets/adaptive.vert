
varying float V;
varying float LightIntensity;
 
void main()
{
    vec3 pos        = vec3(gl_ModelViewMatrix * gl_Vertex);
    vec3 tnorm      = normalize(gl_NormalMatrix * gl_Normal);
    vec3 lightVec   = normalize(vec3(0,0,4)- pos);

    LightIntensity = max(dot(lightVec, tnorm), 0.0);

   
	if(gl_MultiTexCoord0.s==0&&gl_MultiTexCoord0.t==0)
	      V = gl_Vertex.x;
	else
          V = gl_MultiTexCoord0.s;  
    gl_Position = ftransform();
}
