$output v_uv

#include <bgfx_shader.sh>

uniform vec4 u_location;
uniform vec4 u_flags;

void main()
{
  const vec3 positions[4] = vec3[4] (vec3 (-0.5,  0.5, 0.0),
                                     vec3 (-0.5, -0.5, 0.0),
                                     vec3 ( 0.5,  0.5, 0.0),
                                     vec3 ( 0.5, -0.5, 0.0));

  const vec2 uvs[4] = vec2[4] (vec2 (0.0, 0.0),
                               vec2 (0.0, 1.0),
                               vec2 (1.0, 0.0),
                               vec2 (1.0, 1.0));

  gl_Position = mul (u_modelViewProj, vec4 (u_location.xyz + (u_flags.x * positions[gl_VertexID]), 1.0));
  v_uv = (2.0 * uvs[gl_VertexID]) - 1.0;
}
