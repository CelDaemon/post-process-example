#version 150

//#moj_import <minecraft:globals.glsl> //GameTime

in vec2 texCoord;

uniform sampler2D DiffuseSampler;
uniform float EffectStrength;

out vec4 fragColor;

void main()
{
  vec3 val = texture(DiffuseSampler, texCoord).xyz;
  float old = val.x;
  float new = mix(old, 1 - old, EffectStrength);
  fragColor = vec4(new, val.yz, 1.0);
}
