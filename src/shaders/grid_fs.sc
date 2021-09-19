
$input wrld_p


#include <bgfx_shader.sh>


uniform vec4 u_color;
uniform vec4 u_warp;
uniform vec4 u_weft;
uniform vec4 u_cntrad;
uniform vec4 u_span_frac;


layout(location = 0) out vec4 out_color;


void main()
{ float tx = mod (dot (wrld_p, u_warp) - 0.5, 1.0);
  float ty = mod (dot (wrld_p, u_weft) - 0.5, 1.0);
  float fade = 1.0;

  if (u_cntrad.w  >=  0.0)
    { vec4 center = vec4 (u_cntrad.xyz, 0.0);
      fade = distance (center, wrld_p) / u_cntrad.w;
      fade = clamp (1.0 - fade, 0.0, 1.0);
    }

  if (u_span_frac.x  <  1.0)
    { float frc = 0.5 * u_span_frac.x;
      if (tx < 0.5 - frc  ||  tx > 0.5 + frc)
        fade = 0.0;
      else if (ty < 0.5 - frc  ||  ty > 0.5 + frc)
        fade = 0.0;
    }

  out_color = max (smoothstep (0.35, 0.50, tx)  -  smoothstep (0.50, 0.65, tx),
                   smoothstep (0.35, 0.50, ty)  -  smoothstep (0.50, 0.65, ty))
    * vec4 (1.0, 1.0, 1.0, fade) * u_color;
}
