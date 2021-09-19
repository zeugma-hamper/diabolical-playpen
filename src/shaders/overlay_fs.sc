$input v_uv

#include <bgfx_shader.sh>

uniform vec4 u_adj_color;
uniform vec4 u_dimensions;
uniform vec4 u_flags;
uniform vec4 u_rois[10];

layout(location = 0) out vec4 out_color;

float sdfCircle (vec2 p, float r)
{
  return length (p) - r;
}

void main()
{
  int count = int (u_flags.x);
  float radius = u_flags.y / max (u_dimensions.x, u_dimensions.y);

  vec3 color = vec3_splat (0.0);
  vec2 aspect_ratio =  u_dimensions.xy / max (u_dimensions.x, u_dimensions.y);
  for (int i = 0; i < count; ++i)
    {
      vec2 center = 0.5 * (u_rois[i].zw + u_rois[i].xy);
      if (sdfCircle (aspect_ratio * (center - v_uv), radius) <= 0.0)
        color = vec3_splat (1.0);
    }

  if (color == vec3_splat (0.0))
    discard;

  out_color = vec4 (u_adj_color.rgb * color, u_adj_color.a);
}
