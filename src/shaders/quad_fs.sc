$input v_color

#include <bgfx_shader.sh>

layout (location = 0) out vec4 out_color;

void main()
{
  out_color = v_color;
}