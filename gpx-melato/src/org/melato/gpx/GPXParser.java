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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.melato.xml.XMLDelegator;
import org.melato.xml.XMLMappingHandler;
import org.xml.sax.SAXException;

/**
 * Read an xml gpx file.
 *
 * @author alex
 *
 */
public class GPXParser {
	/**
	 * Parse a GPX file and return the parsed GPX object.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public GPX parse(final File file) throws IOException {
		try {
			final InputStream input = new FileInputStream(file);
			return parse(input);
		} catch (final IOException e) {
			throw exception(file, e);
		} catch (final RuntimeException e) {
			throw exception(file, e);
		}
	}

	/**
	 * Parse a GPX file (stream) and return the parsed GPX object.
	 *
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public GPX parse(final InputStream input) throws IOException {
		final XMLMappingHandler rootHandler = new XMLMappingHandler();
		final XMLMappingHandler gpxHandler = new XMLMappingHandler();
		rootHandler.setHandler(GPX.GPX, gpxHandler);
		final RouteHandler routeHandler = new RouteHandler();
		final List<Waypoint> waypoints = new ArrayList<Waypoint>();
		final WaypointHandler waypointHandler = new WaypointHandler(waypoints);
		final TrackHandler trackHandler = new TrackHandler();
		final GPX gpx = new GPX();
		final MetadataHandler metadataHandler = new MetadataHandler(gpx);
		gpxHandler.setHandler(GPX.RTE, routeHandler);
		gpxHandler.setHandler(GPX.TRK, trackHandler);
		gpxHandler.setHandler(GPX.WPT, waypointHandler);
		gpxHandler.setHandler(GPX.METADATA, metadataHandler);
		try {
			XMLDelegator.parse(rootHandler, input);
			gpx.routes = routeHandler.getRoutes();
			gpx.tracks = trackHandler.getTracks();
			gpx.waypoints = waypoints;
			return gpx;
		} catch (final SAXException e) {
			throw new RuntimeException(e);
		} finally {
			input.close();
		}
	}

	/**
	 * Parse a file from a URL
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public GPX parse(final URL url) throws IOException {
		try {
			final InputStream input = url.openStream();
			return parse(input);
		} catch (final IOException e) {
			throw exception(url, e);
		} catch (final RuntimeException e) {
			throw exception(url, e);
		}
	}

	/**
	 * Parse the waypoints from a GPX file and add them to the given collection.
	 * This method can be used for a one-pass parsing of a large GPX file that does
	 * not store the result in memory, if the user-provided collection does not
	 * store the items added to it. Use to do a linear search from a large set of
	 * waypoints stored in a file. Only the plain waypoints are parsed without being
	 * stored in memory. It parses all three types of waypoints (waypoints, routes,
	 * track segments).
	 *
	 * @param input
	 *            The file (stream) to parse
	 * @param result
	 *            The collection to add the waypoints to.
	 * @throws IOException
	 */
	public void parseWaypoints(final InputStream input, final Collection<Waypoint> result) throws IOException {
		final XMLMappingHandler rootHandler = new XMLMappingHandler();
		final XMLMappingHandler gpxHandler = new XMLMappingHandler();
		rootHandler.setHandler(GPX.GPX, gpxHandler);
		final RouteHandler routeHandler = new RouteHandler();
		final WaypointHandler waypointHandler = new WaypointHandler(result);
		final TrackHandler trackHandler = new TrackHandler();
		gpxHandler.setHandler(GPX.RTE, routeHandler);
		gpxHandler.setHandler(GPX.TRK, trackHandler);
		gpxHandler.setHandler(GPX.WPT, waypointHandler);
		try {
			XMLDelegator.parse(rootHandler, input);
			for (final Route route : routeHandler.getRoutes()) {
				for (final Waypoint p : route.path.getWaypoints()) {
					result.add(p);
				}
			}
			for (final Track route : trackHandler.getTracks()) {
				for (final Sequence sequence : route.getSegments()) {
					for (final Waypoint p : sequence.getWaypoints()) {
						result.add(p);
					}
				}
			}
		} catch (final SAXException e) {
			throw new RuntimeException(e);
		} finally {
			input.close();
		}
	}

	private IOException exception(final Object file, final Exception e) {
		return new IOException("Cannot parse " + file + " exception=" + e);
	}

}
