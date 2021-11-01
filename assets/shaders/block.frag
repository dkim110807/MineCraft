#version 400 core
#include <include/fog.glsl>

in vec2 pass_tcs;
in float distance;

out vec4 out_Colour;

uniform sampler2D tex;
uniform vec3 skyColour;

void main() {
    if (distance > 64)
        discard;

    out_Colour = texture(tex, pass_tcs);
    out_Colour = linear_fog(out_Colour, distance, 50, 64, vec4(skyColour, 1.0));
}
