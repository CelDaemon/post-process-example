#version 150

//#moj_import <minecraft:globals.glsl> //GameTime

in vec2 texCoord;

uniform sampler2D DiffuseSampler;
uniform float EffectStrength;

out vec4 fragColor;

void main()
{
  vec2 leftCoord = texCoord * vec2(1.0 + EffectStrength, 1.0);
  vec2 rightCoord = leftCoord - vec2(EffectStrength, 0.0);

  vec3 left_color = texture(DiffuseSampler, leftCoord).xyz;
  vec3 right_color = texture(DiffuseSampler, rightCoord).xyz;

  fragColor = vec4(0.5 * (left_color + right_color), 1.0);
}
