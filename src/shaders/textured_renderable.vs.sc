$input a_position, a_texcoord0
$output v_uv

#include <bgfx_shader.sh>

#include <over_up.sh>

uniform vec4 u_over;
uniform vec4 u_up;
uniform vec4 u_wh;

// requires normalized orientation vectors
void main()
{
  vec4 norm = calculate_norm (u_over/u_wh.x, u_up/u_wh.y);
  mat4 rot = make_rot_matrix (u_over, u_up, norm);

  gl_Position = mul (u_modelViewProj, mul (rot, vec4 (a_position, 1.0)));
  v_uv = a_texcoord0;
}
