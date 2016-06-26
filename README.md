# gpx
Classes for reading and writing GPX files

Cloned from https://github.com/melato/gpx, and integrated with https://github.com/melato/gps and https://github.com/melato/xml.
So these classes are self-contained, no extra jar libraries needed.

Some improvements:
GPX.elevation(), return ascend elevations, descend elevations, minimum elevation, maximum elevation.
GPX.endTime(), return last time of waypoint.
GPX.maxSpeed(), return maximum speed of whole gpx.


