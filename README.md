# Skyline

![Skyline demo](http://i1278.photobucket.com/albums/y519/nitrile/skyline-demo_zpsltxoq9du.png)

Skyline displays an animated, side-scrolling, randomly-generated skyline. It was written in Java for an AP Computer Science class project.

### Download and run
This application requires [Java](http://java.com/inc/BrowserRedirect1.jsp?locale=en), so please ensure it is installed. Then, download and extract the .zip file from the [latest release of Skyline](https://github.com/squarespirit/skyline/releases). Finally, run Skyline.jar, which may be a little tricky:
* Double clicking on the file may work.
* Right-click the file, look for an "Open With" option, and select the Java runtime. (Such an option may not exist on all operating systems.)
* Open a terminal and enter `java -jar`, followed by the path to Skyline.jar. For example, if Skyline.jar is located in `C:\Documents\Skyline_v1.0.0`, enter `java -jar C:\Documents\Skyline_v1.0.0\Skyline.jar`.

### Under the hood
Random building generation is handled by the `BuildingEngine` class. Here's how it works:
* `buildings` is a list of all buildings in the skyline. 
* A value called `genPosition` keeps track of the x-coordinate of the left edge of the next building to be generated.
* Each frame, all buildings are translated left by a small amount to create the side-scrolling effect, and `genPosition` is also translated left by the same amount. Then, a call is made to `generate()`.
* `generate()` works as follows: while `genPosition` is onscreen (i.e. its value is less than the width of the screen), a new building is generated with left edge at an x-coordinate of `genPosition`, and `genPosition` is incremented by a random value. When `genPosition` is so large that it is offscreen to the right, the method ends.
* In the `BuildingEngine` constructor, `genPosition` starts at 0--the left edge of the screen--and `generate()` is called. Thus, the skyline is filled with buildings from the very beginning.
* Newly generated buildings have random widths, heights, and colors. They are also inserted into `buildings` at random positions. Since the order of buildings in `buildings` is the order in which they are painted, this allows for random overlap of buildings.
