#version 400 core

in vec2 pass_tcs;
in float distance;

out vec4 out_Colour;

uniform sampler2D tex;
uniform vec3 skyColour;

vec4 linear_fog(vec4 inColor, float vertexDistance, float fogStart, float fogEnd, vec4 fogColor) {
    if (vertexDistance <= fogStart) {
        return inColor;
    }

    float fogValue = vertexDistance < fogEnd ? smoothstep(fogStart, fogEnd, vertexDistance) : 1.0;
    return vec4(mix(inColor.rgb, fogColor.rgb, fogValue * fogColor.a), inColor.a);
}

void main() {
    if (distance > 64)
        discard;

    out_Colour = texture(tex, pass_tcs);
    out_Colour = linear_fog(out_Colour, distance, 50, 64, vec4(skyColour, 1.0));
}
