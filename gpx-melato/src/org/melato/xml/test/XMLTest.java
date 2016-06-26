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

/*
 * $Header: c:\\cvsroot/dev/java/aa/xml/test/XMLTest.java,v 1.1 2007/12/01 22:57:03 athana Exp $
 * Copyright (c) Alex Athanasopoulos 2007
 */
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class XMLTest {
	public @Test void testContacts() throws Exception {
		InputStream stream = getClass().getResourceAsStream("contacts.xml");
		try {
			List<Contact> contacts = ContactManager.readXML(stream);
			Assert.assertEquals(1, contacts.size());
			Contact c = contacts.get(0);
			Assert.assertEquals("Alex Athanasopoulos", c.getName());
			Assert.assertEquals("206-291-8105", c.getPhone());
			Assert.assertEquals("aathanasopoulos@yahoo.com", c.getEmail());
			Assert.assertEquals("Example Contact", c.getDescription());
		}
		finally {
			stream.close();
		}
	}
}
