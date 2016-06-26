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

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;
import org.melato.xml.XMLWriter;

public class XMLWriterTest {
	public @Test void attribute() {
		testAttribute("plain", "plain");
		testAttribute("", "");
		testAttribute("\"", "&quote;");
		testAttribute("a\"", "a&quote;");
		testAttribute("\"b", "&quote;b");
		testAttribute("a\"b", "a&quote;b");
	}

	public @Test void attributes() {
		StringWriter writer = new StringWriter();
		XMLWriter xml = new XMLWriter(writer);
		xml.tagOpen("a", false);
		xml.tagAttribute("x", "1");
		xml.tagClose();
		Assert.assertEquals("<a x=\"1\">", writer.toString());
	}

	public @Test void escape() throws IOException {
		StringWriter writer = new StringWriter();
		XMLWriter xml = new XMLWriter(writer);
		xml.text(">");
		Assert.assertEquals("&gt;", writer.toString());
	}

	public @Test void single() {
		StringWriter writer = new StringWriter();
		XMLWriter xml = new XMLWriter(writer);
		xml.tagOpen("a");
		xml.text("test");
		xml.tagEnd("a");
		Assert.assertEquals("<a>test</a>", writer.toString());
	}

	private void testAttribute(String value, String expected) {
		StringWriter writer = new StringWriter();
		XMLWriter xml = new XMLWriter(writer);
		xml.tagOpen("a", false);
		xml.tagAttribute("x", value);
		xml.tagClose();
		Assert.assertEquals(("<a x=\"" + expected + "\">"), writer.toString());
	}

}
