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
package org.melato.gpx.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.melato.gpx.GPX;
import org.melato.gpx.GPXBuilder;
import org.melato.gpx.GPXParser;
import org.melato.gpx.GPXWriter;
import org.melato.gpx.GPXXmlWriter;
import org.melato.gpx.KeyValue;
import org.melato.gpx.Route;
import org.melato.gpx.Track;
import org.melato.gpx.Waypoint;
import org.melato.xml.XMLWriter;

public class GPXParserTest {
	public @Test void builderTest() throws IOException {
		InputStream input = getClass().getResourceAsStream("test.gpx");
		GPXParser parser = new GPXParser();
		GPX gpx = parser.parse(input);
		GPXBuilder builder = new GPXBuilder();
		builder.addGPX(gpx);
		gpx = builder.getGpx();
	}

	public @Test void parse() throws IOException {
		InputStream input = getClass().getResourceAsStream("test.gpx");
		GPXParser parser = new GPXParser();
		GPX gpx = parser.parse(input);
		verifyTest(gpx);
	}

	public @Test void parseMetadata() throws IOException {
		InputStream input = getClass().getResourceAsStream("gpx_metadata.gpx");
		GPXParser parser = new GPXParser();
		GPX gpx = parser.parse(input);
		Assert.assertEquals("a", gpx.getName());
		Assert.assertEquals("b", gpx.getDesc());
		Assert.assertEquals("3", gpx.getExtensions().getValue("c"));
	}

	public @Test void parseNaN() {
		String nan = String.valueOf(Float.NaN);
		float f = Float.parseFloat(nan);
		Assert.assertTrue(Float.isNaN(f));
	}

	public @Test void parseOpenGpsTracker() throws IOException {
		InputStream input = getClass().getResourceAsStream("ogs.gpx");
		GPXParser parser = new GPXParser();
		GPX gpx = parser.parse(input);
		Assert.assertEquals(1, gpx.getTracks().size());
		Track track = gpx.getTracks().get(0);
		List<Waypoint> waypoints = track.getSegments().get(0).getWaypoints();
		Waypoint p = waypoints.get(waypoints.size() - 1);
		Assert.assertEquals(6.0f, p.getSpeed(), 0.001f);
	}

	public @Test void parseRouteExtensions() throws IOException {
		InputStream input = getClass().getResourceAsStream("route_extensions.gpx");
		GPXParser parser = new GPXParser();
		GPX gpx = parser.parse(input);
		Assert.assertEquals(1, gpx.getRoutes().size());
		Route route = gpx.getRoutes().get(0);
		KeyValue[] extensions = route.getExtensions().getValues();
		Assert.assertEquals(2, extensions.length);
		KeyValue kv = extensions[0];
		Assert.assertEquals("color", kv.getKey());
		Assert.assertEquals("blue", kv.getValue());
		kv = extensions[1];
		Assert.assertEquals("fare", kv.getKey());
		Assert.assertEquals("1.20", kv.getValue());
		Assert.assertEquals("blue", route.getExtensions().getValue("color"));
		Assert.assertEquals("1.20", route.getExtensions().getValue("fare"));
	}

	public @Test void parseRteTrack() throws IOException {
		InputStream input = getClass().getResourceAsStream("rtetrack.gpx");
		GPXParser parser = new GPXParser();
		GPX gpx = parser.parse(input);
		Assert.assertEquals(1, gpx.getTracks().size());
		Assert.assertEquals(1, gpx.getRoutes().size());
	}

	public @Test void readWriteRead() throws IOException {
		GPXParser parser = new GPXParser();
		GPX gpx = parser.parse(getClass().getResourceAsStream("test.gpx"));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLWriter xml = new XMLWriter(out);
		xml.printHeader();
		GPXXmlWriter writer = new GPXXmlWriter(xml);
		writer.addGPX(gpx);
		xml.close();
		gpx = parser.parse(new ByteArrayInputStream(out.toByteArray()));
		verifyTest(gpx);
	}

	private void verifyTest(GPX gpx) {
		Assert.assertEquals(2, gpx.getRoutes().size());
		Route route = gpx.getRoutes().get(0);
		Assert.assertEquals("Route1", route.name);
		List<Waypoint> waypoints = route.path.getWaypoints();
		Assert.assertEquals(3, waypoints.size());
		Assert.assertEquals(10.1f, waypoints.get(0).lat, 0.001f);
		waypoints = gpx.getWaypoints();
		Assert.assertEquals(1, waypoints.size());
		Waypoint p = waypoints.get(0);
		Assert.assertEquals(1.1f, p.lat, 0.001f);
		Assert.assertEquals("P1", p.getName());
		Assert.assertEquals("link1", p.getLink());

		List<Track> tracks = gpx.getTracks();
		Assert.assertEquals(1, tracks.size());
		Track track = tracks.get(0);
		Assert.assertEquals("Track1", track.getName());
		Assert.assertEquals("Desc1", track.getDesc());
	}

	public @Test void writeMetadata() throws IOException {
		GPX gpx = new GPX();
		gpx.setName("a1");
		gpx.setDesc("a2");
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		GPXWriter writer = new GPXWriter();
		writer.write(gpx, buf);

		GPXParser parser = new GPXParser();
		gpx = parser.parse(new ByteArrayInputStream(buf.toByteArray()));
		Assert.assertEquals("a1", gpx.getName());
		Assert.assertEquals("a2", gpx.getDesc());
	}

}
