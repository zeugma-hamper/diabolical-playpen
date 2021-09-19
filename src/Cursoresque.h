
#include <Alignifer.h>
#include <PolygonRenderable.h>

#include <ZESpatialEvent.h>

#include <GraphicsApplication.hpp>


using namespace zeugma;


class Cursoresque  :  public Alignifer
{ public:
  PolygonRenderable *re1,  *re2;
  PlatonicMaes *cur_maes;

  Cursoresque (f64 sz, i64 nv = 6);

  void Invisify ()
    { re1 -> SetShouldDraw (false);  re2 -> SetShouldDraw (false); }
  void Visibloy ()
    { re1 -> SetShouldDraw (true);  re2 -> SetShouldDraw (true); }

  void YankLeash (ZESpatialMoveEvent *e, GraphicsApplication *grap);
};

