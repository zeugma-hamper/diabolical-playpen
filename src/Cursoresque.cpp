
#include "Cursoresque.h"

#include <SinuZoft.h>


//using namespace charm;


Cursoresque::Cursoresque (f64 sz, i64 nv)  :  Alignifer (),
                                              re1 (new PolygonRenderable),
                                              re2 (new PolygonRenderable),
                                              cur_maes (NULL)
{ AppendRenderable (re1);
  AppendRenderable (re2);
  re1 -> SetFillColor (ZeColor (1.0, 0.5));
  re2 -> SetFillColor (ZeColor (1.0, 0.5));
  for (i64 w = 0  ;  w < 2  ;  ++w)
    for (i64 q = 0  ;  q < nv  ;  ++q)
      { f64 theeeta = 2.0 * M_PI / (f64)nv * (f64)q  +  w * M_PI;
        Vect radial = (0.5 * (w + 1.0))
          *  (cos (theeeta) * Vect::xaxis  +  sin (theeeta) * Vect::yaxis);
        SinuVect arm (0.065 * radial, 0.8 + 0.11 * drand48 (),
                      0.24 * (1.0 + 3.0 * (q%2)) * radial);
        (w > 0 ? re1 : re2) -> AppendVertex (arm);
      }
  ScaleZoft () = Vect (sz);
}


void Cursoresque::YankLeash (ZESpatialMoveEvent *e, GraphicsApplication *grap)
{ if (! grap)
    return;
  Vect hit;
  if (PlatonicMaes *emm
      = grap -> ClosestIntersectedMaes (e -> Loc (), e -> Aim (), &hit))
    LocZoft () . Set (hit);
}
