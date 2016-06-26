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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;
import org.melato.xml.XMLDelegator;
import org.melato.xml.XMLStringHandler;
import org.melato.xml.XMLWriter;
import org.xml.sax.SAXException;

public class EncodingTest {
	private static String readXmlString(InputStream input) throws IOException, SAXException {
		XMLStringHandler stringHandler = new XMLStringHandler();
		stringHandler.setRecursive(true);
		try {
			XMLDelegator.parse(stringHandler, input);
			String text = stringHandler.getText();
			return text;
		}
		finally {
			input.close();
		}
	}

	public @Test void parseGreek() throws IOException, SAXException {
		InputStream input = getClass().getResourceAsStream("abc.xml");
		String text = readXmlString(input);
		/*
		 * char[] cc = text.toCharArray();
		 * for( char c: cc ) {
		 * System.out.println( Integer.toHexString(c));
		 * }
		 */
		Assert.assertEquals("ΑΒΓαβγ", text);
	}

	public @Test void writeGreekFile() throws IOException, SAXException {
		File file = new File(getClass().getName() + ".out");
		XMLWriter xml = new XMLWriter(file);
		xml.printHeader();
		xml.tagOpen("greek");
		xml.text("ΑΒΓαβγ");
		xml.tagEnd("greek");
		xml.close();
		String text = readXmlString(new FileInputStream(file));
		Assert.assertEquals("ΑΒΓαβγ", text);
		file.delete();
	}

	public @Test void writeGreekString() throws IOException {
		StringWriter writer = new StringWriter();
		XMLWriter xml = new XMLWriter(writer);
		xml.tagOpen("a");
		xml.text("ΑΒΓαβγ");
		xml.tagEnd("a");
		Assert.assertEquals("<a>ΑΒΓαβγ</a>", writer.toString());
	}
}
