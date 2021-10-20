#version 330 core

in vec4 out_Colour;
in vec2 pass_tcs;

out vec4 colour;

uniform sampler2D tex;

void main() {
    colour = texture(tex,pass_tcs);
}
