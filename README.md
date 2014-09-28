Jay3D
=====

><p>Just a personal project to start working on a functional 3D engine using OpenGL/LWJGL.</p>

><p>README will be updated after I actually start implementing demos to test lighting, shaders etc. to pick out bugs and features.</p>

Guide
-----
 1. In order to use this engine you must have [LWJGL](http://lwjgl.org/download.php) downloaded and libraries imported into    the project.
   * You need to also set the VM options to direct it to the LWJGL natives according to your OS of choice 
   using *-Djava.library.path= "Path/LWJGL/Natives/MyOS (put your actual path)*
   
 2. You also need [SlickUtil](http://slick.ninjacave.com/slick-util/) *(Will make it SlickUtil independant soon enough)* 

 3. You will also need [Java](https://java.com/en/download/index.jsp) installed on your computer of use.


ADDED:
-----
 - Mesh Model loader(only supports .obj right now)
 - Basic temporary shaders(will implement proper ones later)
 - Full VBO/IBO Mesh rendering implemented
 - Bunch of other crap that I forgot to document, feel free to check the commits
 - Added free camera movement
 - Added textures and texture loader
 - Added lighting(Exclude mapping, haven't done that yet)
  - Pointlights, Spotlights, Ambient lights etc.
