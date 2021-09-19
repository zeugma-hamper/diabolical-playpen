$input v_uv

#include <bgfx_shader.sh>
#include <yuv.sh>

uniform vec4 u_adj_color;

SAMPLER2D (u_video_texture0, 0);
SAMPLER2D (u_video_texture1, 1);
SAMPLER2D (u_video_texture2, 2);

layout(location = 0) out vec4 out_color;

void main()
{
  vec3 yuv = vec3 (texture2D (u_video_texture0, v_uv).r,
                   texture2D (u_video_texture1, v_uv).r,
                   texture2D (u_video_texture2, v_uv).r);

  out_color = u_adj_color * vec4 (convert_bt601_scaled (yuv), 1.0);
}
