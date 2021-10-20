#version 330 core

layout (location=0) in vec3 position;
layout (location=1) in vec4 colour;

out vec4 out_Colour;

uniform mat4 pr_matrix;
uniform mat4 vw_matrix;
uniform mat4 tr_matrix;

void main() {
    out_Colour = colour;
    gl_Position = pr_matrix * vw_matrix * tr_matrix * vec4(position, 1.0);
}
