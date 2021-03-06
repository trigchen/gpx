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

import java.util.AbstractCollection;
import java.util.Iterator;

public abstract class GPXSerializer extends AbstractCollection<Waypoint> {
	@Override
	public abstract boolean add(Waypoint p);

	public void addGPX(GPX gpx) {
		openGpx(gpx);
		addAll(gpx.getWaypoints());
		for (Route route : gpx.getRoutes()) {
			openRoute(route);
			addAll(route.path.getWaypoints());
			closeRoute();
		}
		for (Track track : gpx.getTracks()) {
			openTrack(track);
			for (Sequence path : track.getSegments()) {
				openSegment();
				addAll(path.getWaypoints());
				closeSegment();
			}
			closeTrack();
		}
		closeGpx();
	}

	public abstract void closeGpx();

	public abstract void closeRoute();

	public abstract void closeSegment();

	public abstract void closeTrack();

	@Override
	public Iterator<Waypoint> iterator() {
		throw new UnsupportedOperationException();
	}

	public abstract void openGpx(GPX gpx);

	public abstract void openRoute(Route route);

	public abstract void openSegment();

	public abstract void openTrack(Track track);

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}
}
