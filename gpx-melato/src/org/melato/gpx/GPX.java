/*-------------------------------------------------------------------------
 * Copyright (c) 2012,2013, Alex Athanasopoulos.  All Rights Reserved.
 * alex@melato.org
 *-------------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *-------------------------------------------------------------------------
 */
package org.melato.gpx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A GPX entity has:
 * A list of Routes.
 * A list of Tracks.
 * A list of Waypoints.
 */
public class GPX {
	private String name;

	private String desc;
	private Extensions extensions = new Extensions();
	List<Route> routes = new ArrayList<Route>();
	List<Track> tracks = new ArrayList<Track>();
	List<Waypoint> waypoints = new ArrayList<Waypoint>();
	public static final String GPX = "gpx";
	public static final String NAME = "name";

	public static final String DESC = "desc";
	public static final String SYM = "sym";
	public static final String LINK = "link";
	public static final String HREF = "href";
	public static final String TYPE = "type";
	public static final String TEXT = "text";

	public static final String LAT = "lat";
	public static final String LON = "lon";
	public static final String ELE = "ele";
	public static final String TIME = "time";
	public static final String SPEED = "gpx10:speed";
	public static final String COURSE = "gpx10:course";
	public static final String WPT = "wpt";
	public static final String RTE = "rte";

	public static final String RTEPT = "rtept";
	public static final String TRK = "trk";
	public static final String TRKSEG = "trkseg";
	public static final String TRKPT = "trkpt";
	public static final String EXTENSIONS = "extensions";
	public static final String METADATA = "metadata";

	public static GPX read(final File file) throws IOException {
		final GPXParser parser = new GPXParser();
		final GPX gpx = parser.parse(file);
		return gpx;
	}

	/** Return the total length, in meters. */
	public float distance() {
		float length = 0;
		for (final Sequence path : getPaths()) {
			length += path.distance();
		}
		return length;
	}

	/** Return the duration in seconds */
	public int duration() {
		int duration = 0;
		for (final Sequence path : getPaths()) {
			duration += path.duration();
		}
		return duration;
	}

	public float[] elevation() {
		float ascendElevations = 0.0F;
		float descendElevations = 0.0F;
		float maxElevation = (1.0F / -1.0F);
		float minElevation = (1.0F / 1.0F);
		for (final Sequence path : getPaths()) {
			final float[] eles = path.elevation();
			ascendElevations += eles[0];
			descendElevations += eles[1];
			if (eles[2] < minElevation) {
				minElevation = eles[2];
			}
			if (eles[3] > maxElevation) {
				maxElevation = eles[3];
			}
		}
		return new float[]{ascendElevations, descendElevations, minElevation, maxElevation};
	}

	public Date endTime() {
		final List<Sequence> paths = getPaths();
		for (int i = paths.size() - 1; i >= 0; i--) {
			final Date endTime = paths.get(i).endTime();
			if (endTime != null) {
				return endTime;
			}
		}
		return null;
	}

	public String getDesc() {
		return desc;
	}

	public Extensions getExtensions() {
		return extensions;
	}

	public String getName() {
		return name;
	}

	/**
	 * Get an list of all paths, including routes and tracks.
	 */
	public List<Sequence> getPaths() {
		final List<Sequence> paths = new ArrayList<Sequence>();
		for (final Route route : getRoutes()) {
			paths.add(route.path);
		}
		for (final Track track : getTracks()) {
			for (final Sequence path : track.getSegments()) {
				paths.add(path);
			}
		}
		return paths;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public List<Waypoint> getWaypoints() {
		return waypoints;
	}

	public float maxSpeed() {
		float maxSpeed = 0.0F;
		for (final Sequence path : getPaths()) {
			final float speed = path.maxSpeed();
			if (speed > maxSpeed) {
				maxSpeed = speed;
			}
		}
		return maxSpeed;
	}

	/** Merge a gpx file into this one. */
	public void merge(final GPX gpx) {
		// merge waypoints
		final List<Waypoint> waypoints = getWaypoints();
		for (final Waypoint p : gpx.getWaypoints()) {
			waypoints.add(p);
		}

		// merge routes
		for (final Route route : gpx.getRoutes()) {
			getRoutes().add(route.clone());
		}

		// merge Tracks
		for (final Track track : gpx.getTracks()) {
			getTracks().add(track.clone());
		}

	}

	public void setDesc(final String desc) {
		this.desc = desc;
	}

	public void setExtensions(final Extensions extensions) {
		this.extensions = extensions;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setWaypoints(final List<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}

	public Waypoint startPoint() {
		for (final Sequence path : getPaths()) {
			final List<Waypoint> points = path.getWaypoints();
			if (points.size() > 0) {
				return points.get(0);
			}
		}
		return null;
	}

	public Date startTime() {
		for (final Sequence path : getPaths()) {
			final Date start = path.startTime();
			if (start != null) {
				return start;
			}
		}
		return null;
	}
}
