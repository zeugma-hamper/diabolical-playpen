$input v_uv

#include <bgfx_shader.sh>

uniform vec4 u_adj_color;

layout(location = 0) out vec4 out_color;

float sdfCircle (vec2 p, float r)
{
  return length (p) - r;
}

void main()
{
  if (sdfCircle (v_uv, 1.0) > 0.0)
    discard;

  out_color = u_adj_color;
}
