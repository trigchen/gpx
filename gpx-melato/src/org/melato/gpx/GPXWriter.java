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
import java.io.OutputStream;

import org.melato.xml.XMLWriter;

/** Writes gpx data to gpx (XML) files. */
public class GPXWriter {
	public void write(GPX gpx, File file) throws IOException {
		write(gpx, new XMLWriter(file));
	}

	public void write(GPX gpx, OutputStream stream) throws IOException {
		write(gpx, new XMLWriter(stream));
	}

	private void write(GPX gpx, XMLWriter xml) throws IOException {
		try {
			xml.printHeader();
			GPXSerializer writer = new GPXXmlWriter(xml);
			writer.addGPX(gpx);
		}
		finally {
			xml.close();
		}
	}

}
