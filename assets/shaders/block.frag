#version 400 core

in vec2 pass_tcs;
in float visibility;

out vec4 out_Colour;

uniform sampler2D tex;
uniform vec3 skyColour;

void main() {
    out_Colour = texture(tex,pass_tcs);
    out_Colour = mix(vec4(skyColour,1.0), out_Colour, visibility);
}
