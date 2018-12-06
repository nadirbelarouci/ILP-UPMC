#******************************************************************
# ILP9 - Implantation d'un langage de programmation.
# by Christian.Queinnec@paracamplus.com
# See http://mooc.paracamplus.com/ilp9
# GPL version 3
#****************************************************************** 

# Clean the directory in order to ease porting to another type of
# machine. (MacOSX to Linux for instance).

all: libilp.a gc.a HOSTTYPE

HOSTTYPE:
	uname -s -r -v -m > HOSTTYPE	

clean :
	rm -rf *.o *~ libilp.a gc.a include HOSTTYPE gc-7.2

# Libraries will be compiled in debug mode to help students.

CC	=	gcc

# NOTE by Antoine Miné
# I removed options such as -pedantic and -std=c99 that make the GC fail to
# compile
# I also removed glib which is not used, and also causes problems the GC
CFLAGS	=	-Wall -g

# Compile every C file

CFILES	=	$(shell ls ilp*.c)

ARFILES	=	ilp.o

# Don't forget ranlib, this is useful on MacOSX.

libilp.a : ${CFILES:.c=.o}
	ar cvr libilp.a ${ARFILES}
	-ranlib libilp.a

# Other dependencies:

ilp.o : ilp.c ilp.h

# The +gc switch in compileThenRun.sh means that we want to use Boehm's GC:

gc.a : ../C/gc-7.2f.tgz
	tar xzf ../C/gc-7.2f.tgz
	cd gc-7.2/ && ./configure && make && make check
	cd gc-7.2/ && make -f Makefile.direct
	rm -f gc.a include
	ln -s gc-7.2/gc.a .
	ln -s gc-7.2/include .

# end of Imakefile
