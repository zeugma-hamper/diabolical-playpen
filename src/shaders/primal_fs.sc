
#include <bgfx_shader.sh>


uniform vec4 u_color;


layout(location = 0) out vec4 out_color;


void main()
{
  out_color = u_color;
}
