//	Persistence of Vision Raytracer Version 3.5 Scene Description File

/*
The official POV-Ray Logo
Designed by Chris Colefax

This logo was the one that won the POV-Ray Logo Contest
held and organized by Rune S. Johansen in year 2000 and
monitored by the POV-Team.

Comment from Chris Colefax:

"This logo (or symbol, rather) attempts to encapsulate a
number of ideas. Firstly, the symbol represents an eye with
a ray being traced from it, capturing the fundamentally
visual nature of POV-Ray and the rendering process POV-Ray
uses.  More obviously,  it's a P, and both an O and a V can
be seen in it as well, making the symbol specific to (and
immediately identifiable with) POV-Ray, rather than
raytracing in general.  The basic symbol itself is a simple
CSG construction composed of five POV-Ray primitives.  The
simplicity of the symbol means that it can be presented
recognisably in a range of ways, from a graphical 2D logo
to a full blown rendering, or incorporated discreetly into
other designs and images."


Prism version and bevel version by Rune S. Johansen.

*/

#include "shapes.inc"
#include "textures.inc"

global_settings { ambient_light rgb <1, 1, 1> }

background { rgb <1, 1, 1> }

light_source { <2, 2, -3> rgb <1, 1, 1> }

camera {
    perspective
    location <0,0,-2.2>
    direction <0,0,1>
    right 1.33*x
    up y
    sky <0,1,0>
  }


// The original version is made of various objects.
#declare Povray_Logo =
merge {
   sphere {2*y, 1}
   difference {
      cone {2*y, 1, -4*y, 0}
      sphere {2*y, 1.4 scale <1,1,2>}
   }
   difference {
      sphere {0, 1 scale <2.6, 2.2, 1>}
      sphere {0, 1 scale <2.3, 1.8, 2> translate <-0.35, 0, 0>}
      rotate z*30 translate 2*y
   }
   rotate <0, 0, -25>
   translate <-0.5,-0.35,0>
   scale 1/4
}



object { Povray_Logo
  texture { Pink_Granite  
    scale 0.25
  }
}
