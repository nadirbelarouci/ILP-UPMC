/* $Id$ */

#include "ilpSin.h"
//#include "ilpBasicError.h"
#include "ilp.h"

ILP_Object
ILP_sinus (ILP_Object o)
{
    if ( ! ILP_isFloat(o) )
        return ILP_make_float(sin(o->_content.asFloat));
    else if ( ! ILP_isInteger (o) )
        return ILP_make_float(sin(o->_content.asInteger));
    else 
      return ILP_domain_error("Not a number", o);
}

/* end of ilpSin.c */
