$output v_uv

#include <bgfx_shader.sh>

uniform vec4 u_dimensions;
uniform vec4 u_matte_dimensions;

uniform vec4 u_flags;

uniform vec4 u_over;
uniform vec4 u_up;

#define MATTE 0.0
#define VIDEO 1.0

#define SIZE_REFERENT u_flags.x
#define ENABLE_MIX    u_flags.y

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
  vec2 ll, tr;
  vec2 uv, wh;

  if (SIZE_REFERENT == MATTE)
    {
      ll = u_matte_dimensions.xy / u_dimensions.xy;
      tr = u_matte_dimensions.zw / u_dimensions.xy;
      uv = ll + ((tr - ll) * uvs[gl_VertexID]);
      wh = u_matte_dimensions.zw - u_matte_dimensions.xy;
    }
  else //SIZE_REFERENT == VIDEO
    {
      uv = uvs[gl_VertexID];
      wh = u_dimensions.xy;
    }

  vec4 norm = vec4 (cross (u_over.xyz, u_up.xyz), 0.0);
  mat4 rot = mat4 (u_over, u_up, norm, vec4 (0.0, 0.0, 0.0, 1.0));

  vec4 aspect_ratio;
  if (wh.y < wh.x)
    aspect_ratio = vec4 (1.0, wh.y / wh.x, 1.0, 1.0);
  else
    aspect_ratio = vec4 (wh.x / wh.y, 1.0, 1.0, 1.0);

  gl_Position = mul (u_modelViewProj, mul (rot, positions[gl_VertexID] * aspect_ratio));
  v_uv = uv;
}
