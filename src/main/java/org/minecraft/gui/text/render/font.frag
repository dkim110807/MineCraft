#version 330

in vec2 pass_tcs;

out vec4 out_Colour;

uniform vec3 colour;
uniform sampler2D fontAtlas;
uniform float width;
uniform float edge;
uniform float borderWidth;
uniform float borderEdge;
uniform vec3 outlineColour;

void main() {

    float distance = 1.0 - texture(fontAtlas, pass_tcs).a;
    float alpha = 1.0 - smoothstep(width, width+edge, distance);

    float distance2 = 1.0 - texture(fontAtlas, pass_tcs).a;
    float outlineAlpha = 1.0 - smoothstep(borderWidth, borderWidth+borderEdge, distance2);

    float overallAlpha = alpha + (1.0-alpha) * outlineAlpha;
    vec3 overallColour = mix(outlineColour,colour,alpha/overallAlpha);

    out_Colour = vec4(overallColour,overallAlpha);
}
