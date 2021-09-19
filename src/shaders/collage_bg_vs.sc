#include <bgfx_shader.sh>

uniform vec4 u_over;
uniform vec4 u_up;

void main()
{
  const vec4 positions[4] = vec4[4] (vec4 (-0.5,  0.5, 0.0, 1.0),
                                     vec4 (-0.5, -0.5, 0.0, 1.0),
                                     vec4 ( 0.5,  0.5, 0.0, 1.0),
                                     vec4 ( 0.5, -0.5, 0.0, 1.0));

  vec4 len = vec4 (length (u_over), length (u_up), 1.0, 1.0);

  vec4 norm = vec4 (cross (u_over.xyz/len.x, u_up.xyz/len.y), 0.0);
  mat4 rot = mat4 (u_over/len.x, u_up/len.y, norm, vec4 (0.0, 0.0, 0.0, 1.0));

  gl_Position = mul (u_modelViewProj, mul (rot, positions[gl_VertexID] * len));
}
