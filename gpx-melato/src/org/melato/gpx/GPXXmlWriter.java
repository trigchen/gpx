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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.melato.gpx.Waypoint.Link;
import org.melato.xml.XMLWriter;

/**
 * Writes gpx data to gpx (XML) files.
 * Newline Formatting:
 * before each tag
 * before each closing tag, if there are inner tags.
 * after the last tag.
 */
public class GPXXmlWriter extends GPXSerializer {
	private DateFormat format;
	private XMLWriter xml;
	private String waypointTag = GPX.WPT;
	protected int waypointCount = 0;
	private boolean innerTags;

	public GPXXmlWriter() {
		format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public GPXXmlWriter(XMLWriter xml) {
		this();
		setXmlWriter(xml);
	}

	@Override
	public boolean add(Waypoint p) {
		waypointCount++;
		innerTags = false;
		xml.println();
		xml.tagOpen(waypointTag, false);
		xml.tagAttribute("lat", String.valueOf(p.getLat()));
		xml.tagAttribute("lon", String.valueOf(p.getLon()));
		xml.tagClose();
		if (!Float.isNaN(p.elevation)) {
			write(xml, GPX.ELE, String.valueOf(p.elevation));
		}
		float speed = p.getSpeed();
		float bearing = p.getBearing();
		if (!Float.isNaN(speed) || !Float.isNaN(bearing)) {
			xml.tagOpen(GPX.EXTENSIONS);
			write(xml, GPX.SPEED_EXT, speed);
			write(xml, GPX.COURSE_EXT, bearing);
			xml.tagEnd(GPX.EXTENSIONS);
		}
		write(xml, GPX.TYPE, p.getType());
		write(xml, GPX.NAME, p.getName());
		write(xml, GPX.DESC, p.getDesc());
		write(xml, GPX.SYM, p.getSym());
		if (p.hasTime())
			write(xml, GPX.TIME, formatDate(p.getDate()));
		for (Link link : p.getLinks()) {
			xml.println();
			xml.tagOpen(GPX.LINK);
			write(xml, GPX.TYPE, link.type);
			write(xml, GPX.TEXT, link.text);
			xml.println();
			xml.tagEnd(GPX.LINK);
		}
		if (innerTags) {
			xml.println();
		}
		xml.tagEnd(waypointTag);
		return true;
	}

	@Override
	public void closeGpx() {
		xml.println();
		xml.tagEnd(GPX.GPX);
		xml.println();
		xml.close();
	}

	@Override
	public void closeRoute() {
		xml.println();
		xml.tagEnd(GPX.RTE);
		waypointTag = GPX.WPT;
	}

	@Override
	public void closeSegment() {
		xml.println();
		xml.tagEnd(GPX.TRKSEG);
		waypointTag = GPX.WPT;
	}

	@Override
	public void closeTrack() {
		xml.println();
		xml.tagEnd(GPX.TRK);
		waypointTag = GPX.WPT;
	}

	private String formatDate(Date date) {
		return format.format(date) + "Z";
	}

	@Override
	public void openGpx(GPX gpx) {
		waypointCount = 0;
		xml.tagOpen(GPX.GPX, false);
		xml.tagAttribute("version", "1.1");
		xml.tagAttribute("creator", "melato.org - http://melato.org");
		xml.tagAttribute("xmlns", "http://www.topografix.com/GPX/1/1");
		xml.tagAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		xml.tagAttribute("xsi:schemaLocation",
				"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd");
		xml.tagClose();

		String name = gpx.getName();
		String desc = gpx.getDesc();
		if (name != null || desc != null) {
			xml.println();
			xml.tagOpen(GPX.METADATA);
			write(xml, GPX.NAME, name);
			write(xml, GPX.DESC, desc);
			xml.println();
			writeExtensions(xml, gpx.getExtensions());
			xml.tagEnd(GPX.METADATA);
		}
	}

	@Override
	public void openRoute(Route route) {
		xml.println();
		xml.tagOpen(GPX.RTE);
		write(xml, GPX.NAME, route.getName());
		writeExtensions(xml, route.getExtensions());
		waypointTag = GPX.RTEPT;
	}

	@Override
	public void openSegment() {
		xml.println();
		xml.tagOpen(GPX.TRKSEG);
		waypointTag = GPX.TRKPT;
	}

	@Override
	public void openTrack(Track track) {
		xml.println();
		xml.tagOpen(GPX.TRK);
		write(xml, GPX.NAME, track.getName());
		write(xml, GPX.DESC, track.getDesc());
	}

	public void setXmlWriter(XMLWriter xml) {
		this.xml = xml;
	}

	@Override
	public int size() {
		return waypointCount;
	}

	public void write(GPX gpx) throws IOException {
		try {
			xml.printHeader();
			super.addGPX(gpx);
		}
		finally {
			xml.close();
		}
	}

	private void write(XMLWriter xml, String tag, float value) {
		if (!Float.isNaN(value)) {
			write(xml, tag, String.valueOf(value));
		}
	}

	private void write(XMLWriter xml, String tag, String text) {
		if (text == null)
			return;
		innerTags = true;
		xml.println();
		xml.tagOpen(tag);
		xml.text(text);
		xml.tagEnd(tag);
	}

	void writeExtensions(XMLWriter xml, Extensions extensions) {
		KeyValue[] values = extensions.getValues();
		if (values.length > 0) {
			xml.println();
			xml.tagOpen(GPX.EXTENSIONS);
			for (KeyValue kv : values) {
				write(xml, kv.getKey(), kv.getValue());
			}
			xml.println();
			xml.tagEnd(GPX.EXTENSIONS);
		}
	}

}
