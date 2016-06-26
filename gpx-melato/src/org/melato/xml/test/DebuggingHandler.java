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
package org.melato.xml.test;

import java.io.File;

import org.melato.xml.XMLDelegator;
import org.melato.xml.XMLElementHandler;
import org.melato.xml.XMLNullHandler;
import org.melato.xml.XMLTag;
import org.xml.sax.SAXException;

/**
 * An XMLElementHandler that prints its calls to stdout.
 * 
 * @author Alex Athanasopoulos
 * @date Dec 1, 2007
 */
public class DebuggingHandler extends XMLNullHandler {

	public static void main(String[] args) throws Exception {
		if (args.length != 1)
			throw new RuntimeException("Usage: <filename>");
		XMLDelegator.parse(new DebuggingHandler(), new File(args[0]));
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		System.out.print("characters: ");
		System.out.print(new String(ch, start, length));
		System.out.println();
	}

	public void end() {
		System.out.println("end");
	}

	public XMLElementHandler getHandler(XMLTag tag) {
		return this;
	}

	public void start(XMLTag tag) {
		System.out.println("start " + tag.getName());
	}
}
