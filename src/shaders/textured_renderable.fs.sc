$input v_uv

#include <bgfx_shader.sh>

uniform vec4 u_adjc;

SAMPLER2D (u_texture, 0);

layout(location = 0) out vec4 out_color;

void main()
{
  vec4 tx_color = texture2D (u_texture, v_uv);
  if (tx_color.a < 0.01)
    discard;

  out_color = u_adjc * tx_color;
}
