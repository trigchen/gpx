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
package org.melato.gps;

/** A 3-dimensional geographical point in time with 2-dimensional speed. */
public class GpsPoint extends Point4D {
	public float speed = Float.NaN;
	public float bearing = Float.NaN;
	private static final long serialVersionUID = 1L;

	public GpsPoint() {
	}

	public GpsPoint(float lat, float lon) {
		super(lat, lon);
	}

	public GpsPoint(GpsPoint p) {
		super(p);
		speed = p.speed;
		bearing = p.bearing;
	}

	public GpsPoint(Point2D p) {
		super(p);
	}

	public GpsPoint(Point4D p) {
		super(p);
	}

	public GpsPoint(PointTime p) {
		super(p);
	}

	public float getBearing() {
		return bearing;
	}

	public float getSpeed() {
		return speed;
	}

	public void setBearing(float bearing) {
		this.bearing = bearing;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
