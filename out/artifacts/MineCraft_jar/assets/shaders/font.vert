#version 330 core

layout(location=0) in vec2 position;
layout(location=1) in vec3 colour;
layout(location=2) in vec2 tcs;

out vec3 pass_colour;
out vec2 pass_tcs;

void main() {
    pass_colour = colour;
    pass_tcs = tcs;
    gl_Position = vec4(position, 1.0, 1.0);
}
