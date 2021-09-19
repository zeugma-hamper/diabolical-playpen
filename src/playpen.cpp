
#include <GraphicsApplication.hpp>

#include <TexturedRenderable.hpp>

#include <Frontier.hpp>

#include <InterpZoft.h>
#include <LoopZoft.h>
#include <SinuZoft.h>

#include <RoGrappler.h>

#include <GeomFumble.h>

#include <ZESpatialEvent.h>
#include <ZEYowlEvent.h>

#include <bgfx_utils.hpp>

#include "Cursoresque.h"


using namespace zeugma;


struct ImageSplatter  :  public Alignifer, public ZESpatialPhagy
{ TexturedRenderable *texre;
  InterpVect perky_loc;
  InterpVect mopey_sca;
  RoGrappler *dorky_rot;
  std::string grab_prov;
  Vect grab_offset;

  ImageSplatter (const char *fname)  :  Alignifer ()
    { TextureParticulars tepa = CreateTexture2D (fname, DefaultTextureFlags);
      texre = new TexturedRenderable (tepa);
      AppendRenderable (texre);

      Scale (mopey_sca);
      mopey_sca . SetHard (Vect (1.0));
      dorky_rot = dynamic_cast <RoGrappler *>
        (Rotate (ZoftVect (Vect::zaxis), ZoftFloat (0.0)));
      Translate (perky_loc);

      SetFrontier (new RectRenderableFrontier (texre, Vect::zerov, 1.0, 1.0));
    }

  i64 ZESpatialHarden (ZESpatialHardenEvent *e)  override
    { Vect hit;
      Frontier *fro = GetFrontier ();
      if (fro  &&  fro -> CheckHit (G::Ray (e->loc, e->aim), &hit))
        { grab_prov = e -> Provenance ();
          Matrix44 const m = from_glm (GetAbsoluteTransformation ().model);
          grab_offset = m . TransformVect (Vect::zerov)  -  hit;
        }
      return 0;
    }
  i64 ZESpatialSoften (ZESpatialSoftenEvent *e)  override
    { if (e -> Provenance ()  ==  grab_prov)
        grab_prov . clear ();
      return 0;
    }
  i64 ZESpatialMove (ZESpatialMoveEvent *e)  override
    { if (e -> Provenance ()  ==  grab_prov)
        { Vect hit;
          Frontier *fro = GetFrontier ();
          if (fro  &&  fro -> CheckPlaneHit (G::Ray (e->loc, e->aim), &hit))
            perky_loc . SetHard (hit + grab_offset);
        }
      return 0;
    }
};



struct Laufstall  :  public GraphicsApplication, public Zeubject,
                     public ZESpatialPhagy, public ZEYowlPhagy
{ Cursoresque *cur;
  ImageSplatter *dmth;
  ImageSplatter *aynr;

  void HitchUpWagonsAndGo ()
    { StartUp ();

      CMVTrefoil *tref = this -> NthRenderLeaf (0);
      PlatonicMaes *frnt = tref->maes;
      Layer *sheet = new Layer;
      Node *saloon = sheet -> GetRootNode ();
      this -> AppendSceneLayer (sheet);
      tref->layers . push_back (sheet);

      dmth = new ImageSplatter ("../snacks/DragMeToHell.jpg");
      dmth->perky_loc . Set (frnt -> Loc ()
                             +  0.25 * frnt -> Over () * frnt -> Width ());
      dmth->dorky_rot -> InstallAngle
        (LoopFloat (0.0, 2.0 * M_PI * (0.05 + 0.15 * drand48 ())));
      dmth->mopey_sca . Set (Vect (0.3 * frnt -> Height ()));
      saloon -> AppendChild (dmth);

      aynr = new ImageSplatter ("../snacks/aynrand.tif");
      aynr->perky_loc . Set (frnt -> Loc ()
                             -  0.25 * frnt -> Over () * frnt -> Width ()
                             -  0.25 * frnt -> Up () * frnt -> Height ());
      aynr->mopey_sca . Set (Vect (0.4 * frnt -> Height ()));
      aynr -> Translate (SinuVect (Vect (0.0, 0.0, 200.0), 0.3));
      saloon -> AppendChild (aynr);

      cur = new Cursoresque (0.05 * frnt -> Height ());
      cur -> LocZoft () = frnt -> Loc ();
      saloon -> AppendChild (cur);

// register this selfsame object for spatial events. the idiom
// perhaps needs fixin'; let's think on't.
      ch_ptr <Laufstall> much_self (this);
      AppendSpatialPhage (& GetSprinkler (), much_self);

      ch_ptr <ImageSplatter> otra_hell (dmth);
      AppendSpatialPhage (& GetSprinkler (), otra_hell);

      Run ();
    }

  i64 ZESpatialMove (ZESpatialMoveEvent *e)  override
    { cur -> YankLeash (e, this);
      return 0;
    }
};


int main (int ac, char **av)
{ Laufstall grap;

  grap . HitchUpWagonsAndGo ();

  return 0;
}
