#version 330 core

in vec3 pass_colour;
in vec2 pass_tcs;

out vec4 out_Colour;

uniform sampler2D tex;

void main() {
    out_Colour = vec4(texture(tex, pass_tcs), 1.0);
}
