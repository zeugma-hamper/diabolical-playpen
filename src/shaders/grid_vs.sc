
$input a_position

$output wrld_p

uniform mat4 u_full_model_mat;


#include <bgfx_shader.sh>


void main()
{ //wrld_p = mul (u_model[0], vec4 (a_position, 1.0));
  wrld_p = mul (u_full_model_mat, vec4 (a_position, 1.0));
  gl_Position = mul (u_modelViewProj, vec4 (a_position, 1.0));
}
