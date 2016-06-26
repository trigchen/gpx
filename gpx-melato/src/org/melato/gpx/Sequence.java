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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.melato.gps.Earth;
import org.melato.gps.PointTime;

/**
 * A sequence of waypoints. May be used for track segments, routes, etc.
 */
public class Sequence implements Cloneable {
	List<Waypoint> waypoints;

	public static float distance(final List<Waypoint> waypoints) {
		double length = 0;
		Waypoint p0 = null;
		for (final Waypoint p : waypoints) {
			if (p0 != null) {
				length += Earth.distance(p0, p);
			}
			p0 = p;
		}
		return (float) length;
	}

	/** Return the duration in seconds */
	public static int duration(final List<Waypoint> waypoints) {
		return (int) (durationMillis(waypoints) / 1000);
	}

	/** Return the duration in milliseconds */
	public static long durationMillis(final List<Waypoint> waypoints) {
		if (waypoints.size() < 2) {
			return 0;
		}
		final Waypoint p1 = waypoints.get(0);
		final Waypoint p2 = waypoints.get(waypoints.size() - 1);
		return PointTime.timeDifferenceMillis(p1, p2);
	}

	public Sequence() {
		waypoints = new ArrayList<Waypoint>();
	}

	public Sequence(final List<Waypoint> waypoints) {
		super();
		this.waypoints = waypoints;
	}

	@Override
	public Sequence clone() throws CloneNotSupportedException {
		final List<Waypoint> list = new ArrayList<Waypoint>(waypoints.size());
		list.addAll(waypoints);
		return new Sequence(list);
	}

	/** Return the length of the sequence, in meters. */
	public float distance() {
		return distance(waypoints);
	}

	/** Return the duration in seconds */
	public int duration() {
		return duration(waypoints);
	}

	/** Return the duration in milliseconds */
	public long durationMillis() {
		return durationMillis(waypoints);
	}

	public float[] elevation() {
		float ascendElevations = 0.0F;
		float descendElevations = 0.0F;
		float maxElevation = (1.0F / -1.0F);
		float minElevation = (1.0F / 1.0F);
		final List<Waypoint> points = getWaypoints();
		Waypoint p0 = null;
		for (final Waypoint p : points) {
			final float ele = p.getElevation();
			if (ele > maxElevation) {
				maxElevation = ele;
			}
			if (ele < minElevation) {
				minElevation = ele;
			}
			if (p0 != null) {
				final float delta = p.getElevation() - p0.getElevation();
				if (delta > 0.0F) {
					ascendElevations += delta;
				}
				else if (delta < 0.0F) {
					descendElevations += -delta;
				}
			}
			p0 = p;
		}
		return new float[]{ascendElevations, descendElevations, minElevation, maxElevation};
	}

	public Date endTime() {
		final int size = waypoints.size();
		if (size == 0) {
			return null;
		}
		return waypoints.get(size - 1).getDate();
	}

	public List<Waypoint> getWaypoints() {
		return waypoints;
	}

	public float maxSpeed() {
		float maxSpeed = 0.0F;
		final List<Waypoint> points = getWaypoints();
		final int size = points.size();
		if (size > 1) {
			for (int i = 0; i < (size - 1); i++) {
				final float distance = Earth.distance(points.get(i), points.get(i + 1));
				final float duration = PointTime.timeDifference(points.get(i), points.get(i + 1));
				if (duration > 0.0F) {
					final float speed = (distance / duration) * 3.6F;
					if (speed > maxSpeed) {
						maxSpeed = speed;
					}
				}
			}
		}
		return maxSpeed;
	}

	public void reverse() {
		Collections.reverse(waypoints);
	}

	public Date startTime() {
		if (waypoints.isEmpty()) {
			return null;
		}
		return waypoints.get(0).getDate();
	}

}
