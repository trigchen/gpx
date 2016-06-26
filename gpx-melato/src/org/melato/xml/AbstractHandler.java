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
package org.melato.xml;

import org.xml.sax.SAXException;

/**
 * An XMLElementHandler that does nothing.
 * 
 * @author Alex Athanasopoulos
 */
public class AbstractHandler implements XMLElementHandler {

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

	}

	@Override
	public void end() throws SAXException {

	}

	@Override
	public XMLElementHandler getHandler(XMLTag tag) {
		return null;
	}

	@Override
	public void start(XMLTag tag) throws SAXException {
	}

}
