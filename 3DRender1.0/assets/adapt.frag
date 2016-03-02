

varying float V;                 
varying float LightIntensity;



void main()
{
    float sawtooth = fract(V *16);
    float triangle = abs(2.0 * sawtooth - 1.0);
    float dp = length(vec2(dFdx(V), dFdy(V)));
    float edge = dp *16* 2.0;
    float square = smoothstep(0.5 - edge, 0.5 + edge, triangle);
    gl_FragColor = vec4(vec3(square), 1.0) * LightIntensity;
}