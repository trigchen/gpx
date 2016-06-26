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

import org.junit.Assert;
import org.junit.Test;
import org.melato.xml.XMLElementHandler;
import org.melato.xml.XMLStringHandler;
import org.melato.xml.XMLTag;
import org.xml.sax.SAXException;

/*
 * $Header: c:\\cvsroot/dev/java/aa/xml/test/XMLTest.java,v 1.1 2007/12/01 22:57:03 athana Exp $
 * Copyright (c) Alex Athanasopoulos 2007
 */

public class XMLStringHandlerTest {
	static void put(XMLElementHandler handler, String text) throws SAXException {
		handler.characters(text.toCharArray(), 0, text.length());
	}

	public @Test void append() throws SAXException {
		XMLStringHandler h = new XMLStringHandler();
		h.setAppend(true);
		XMLTag tag = new XMLTag("x");
		h.start(tag);
		put(h, "a");
		h.end();
		h.start(tag);
		put(h, "b");
		h.end();
		Assert.assertEquals("ab", h.getText());
	}

	public @Test void notAppend() throws SAXException {
		XMLStringHandler h = new XMLStringHandler();
		XMLTag tag = new XMLTag("x");
		h.start(tag);
		put(h, "a");
		h.end();
		h.start(tag);
		put(h, "b");
		h.end();
		Assert.assertEquals("b", h.getText());
	}

	public @Test void noText() throws SAXException {
		XMLStringHandler h = new XMLStringHandler();
		Assert.assertNull(h.getText());
	}

	public @Test void notRecursive() throws SAXException {
		XMLStringHandler h = new XMLStringHandler();
		XMLTag tag = new XMLTag("x");
		h.start(tag);
		put(h, "a");
		XMLElementHandler h2 = h.getHandler(tag);
		h2.start(tag);
		put(h2, "b");
		h2.end();
		h.end();
		Assert.assertEquals("a", h.getText());
	}

	public @Test void plain() throws SAXException {
		XMLStringHandler h = new XMLStringHandler();
		XMLTag tag = new XMLTag("x");
		h.start(tag);
		put(h, "plain");
		h.end();
		Assert.assertEquals("plain", h.getText());
	}

	public @Test void recursive() throws SAXException {
		XMLStringHandler h = new XMLStringHandler();
		h.setRecursive(true);
		XMLTag tag = new XMLTag("x");
		h.start(tag);
		put(h, "a");
		XMLElementHandler h2 = h.getHandler(tag);
		h2.start(tag);
		put(h2, "b");
		h2.end();
		h.end();
		Assert.assertEquals("ab", h.getText());
	}

	public @Test void repeatCall() throws SAXException {
		XMLStringHandler h = new XMLStringHandler();
		XMLTag tag = new XMLTag("x");
		h.start(tag);
		put(h, "repeat");
		h.end();
		Assert.assertEquals("repeat", h.getText());
		Assert.assertEquals("repeat", h.getText());
	}

}
