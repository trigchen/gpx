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

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.melato.gps.GpsPoint;
import org.melato.gps.Point2D;
import org.melato.gps.PointTime;

/** A GPX waypoint: geographical point with annotations. */
public class Waypoint extends GpsPoint implements Cloneable {
	public static class Link implements Serializable {
		public String href;
		public String type;
		public String text;
		private static final long serialVersionUID = 1L;

		public Link() {
			super();
		}

		public Link(String type, String text) {
			super();
			this.type = type;
			this.text = text;
		}
	}
	public String name;
	public String desc;
	public String type;
	public String sym;
	public List<Link> links = emptyLinks;

	private static final long serialVersionUID = 1L;

	private static List<Link> emptyLinks = Collections.emptyList();

	public Waypoint() {
	}

	public Waypoint(float lat, float lon) {
		super(lat, lon);
	}

	public Waypoint(Point2D p) {
		super(p);
	}

	public Waypoint(PointTime p) {
		super(p);
	}

	public Waypoint(Waypoint p) {
		super(p);
		sym = p.sym;
		name = p.name;
		desc = p.desc;
		type = p.type;
	}

	public Waypoint clone() {
		try {
			Waypoint p = (Waypoint) super.clone();
			return p;
		} catch (CloneNotSupportedException ex) {
			throw new RuntimeException(ex);
		}
	}

	public String getDesc() {
		return desc;
	}

	public String getLink() {
		return links.isEmpty() ? null : links.get(0).text;
	}

	public List<Link> getLinks() {
		return links;
	}

	public String getName() {
		return name;
	}

	public String getSym() {
		return sym;
	}

	public String getType() {
		return type;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSym(String sym) {
		this.sym = sym;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		if (name != null) {
			buf.append(name);
			buf.append(" ");
		}
		buf.append(lat);
		buf.append(';');
		buf.append(lon);
		if (type != null) {
			buf.append(" type=" + type);
		}
		if (sym != null) {
			buf.append(" sym=" + sym);
		}
		if (desc != null) {
			buf.append(" desc=" + desc);
		}
		return buf.toString();
	}
}
