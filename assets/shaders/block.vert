#version 400 core

in vec3 position;
in vec2 tcs;
in vec3 normals;

out vec2 pass_tcs;
out float visibility;

uniform mat4 tr_matrix;
uniform mat4 pr_matrix;
uniform mat4 vw_matrix;

const float density = 0.02;
const float gradient = 1.5;

void main() {
    vec4 world_position = tr_matrix * vec4(position, 1.0);
    vec4 positionRelativeToCam = vw_matrix * world_position;
    gl_Position = pr_matrix * positionRelativeToCam;
    pass_tcs = tcs;

    float distance = length(positionRelativeToCam.xyz);
    visibility = exp(-pow((distance * density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
}
