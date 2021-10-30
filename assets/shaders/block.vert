#version 400 core

in vec3 position;
in vec2 tcs;
in vec3 normals;

out vec2 pass_tcs;
out float distance;

uniform mat4 tr_matrix;
uniform mat4 pr_matrix;
uniform mat4 vw_matrix;

void main() {
    vec4 world_position = tr_matrix * vec4(position, 1.0);
    vec4 positionRelativeToCam = vw_matrix * world_position;
    gl_Position = pr_matrix * positionRelativeToCam;
    pass_tcs = tcs;

    distance = length(positionRelativeToCam.xyz);
}
