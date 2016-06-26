# gpx
Classes for reading and writing GPX files

Cloned from https://github.com/melato/gpx, and integrated with https://github.com/melato/gps and https://github.com/melato/xml.
So these classes are self-contained, no extra jar libraries needed.

## Some improvements:
1. GPX.elevation(), return ascend elevations, descend elevations, minimum elevation, maximum elevation.
2. GPX.endTime(), return last time of waypoint.
3. GPX.maxSpeed(), return maximum speed of whole gpx.

# gps
bare-bones objects for representing geographical and time data, like that provided by GPS devices.
Defines object for point and has provides the basic computations
for computing distance and bearing.

# xml
An XML library on top of SAX.

The main interface in this package is XMLElementHandler, which determines how to process a single XML element.  Child elements are delegated to other XMLElementHandlers.


