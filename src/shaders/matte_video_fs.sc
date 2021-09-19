
$input v_uv

#include <bgfx_shader.sh>
#include <yuv.sh>

uniform vec4 u_adj_color;

SAMPLER2D (u_video_texture0, 0);
SAMPLER2D (u_video_texture1, 1);
SAMPLER2D (u_video_texture2, 2);
SAMPLER2D (u_video_matte, 3);

uniform vec4 u_flags;
uniform vec4 u_mix_color;

layout (location = 0) out vec4 out_color;

#define SIZE_REFERENT u_flags.x
#define ENABLE_MIX    (u_flags.y > 0.0)

//TODO: premultiplied alpha?
//TODO: gamma?
void main()
{
  float alpha = 1.0 - texture2D (u_video_matte, v_uv).r;
  if (alpha < 0.01 && ! ENABLE_MIX)
    discard;

  vec3 yuv = vec3 (texture2D (u_video_texture0, v_uv).r,
                   texture2D (u_video_texture1, v_uv).r,
                   texture2D (u_video_texture2, v_uv).r);

  vec4 oc = u_adj_color * vec4 (convert_bt601_scaled (yuv), alpha);
  if (ENABLE_MIX && alpha * alpha < 0.15)
    {
      oc = vec4 (u_mix_color.rgb * oc.rgb, 1.0);
    }

  out_color = oc;
}
