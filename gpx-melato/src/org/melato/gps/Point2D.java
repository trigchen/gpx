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

import java.io.Serializable;

/** A 2-dimensional geographical point with latitude and longitude. */
public class Point2D implements Serializable {
	public float lat;
	public float lon;
	private static final long serialVersionUID = 1L;

	public Point2D() {
	}

	public Point2D(float lat, float lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

	public Point2D(Point2D p) {
		this.lat = p.lat;
		this.lon = p.lon;
	}

	@Override
	public boolean equals(Object x) {
		if (x instanceof Point2D) {
			Point2D p = (Point2D) x;
			return lat == p.lat && lon == p.lon;
		}
		return false;
	}

	public float getLat() {
		return lat;
	}

	public float getLon() {
		return lon;
	}

	@Override
	public int hashCode() {
		return new Float(lat).hashCode() + new Float(lon).hashCode();
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return lat + ";" + lon;
	}
}
