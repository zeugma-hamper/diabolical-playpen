$output v_uv

#include <bgfx_shader.sh>

void main()
{
  const vec4 positions[4] = vec4[4] (vec4 (-0.5,  0.5, 0.0, 1.0),
                                     vec4 (-0.5, -0.5, 0.0, 1.0),
                                     vec4 ( 0.5,  0.5, 0.0, 1.0),
                                     vec4 ( 0.5, -0.5, 0.0, 1.0));

  const vec2 uvs[4] = vec2[4] (vec2 (0.0, 0.0),
                               vec2 (0.0, 1.0),
                               vec2 (1.0, 0.0),
                               vec2 (1.0, 1.0));

  gl_Position = mul (u_modelViewProj, positions[gl_VertexID]);
  v_uv = uvs[gl_VertexID];
}
