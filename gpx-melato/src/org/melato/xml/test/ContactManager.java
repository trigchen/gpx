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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.melato.xml.XMLDelegator;
import org.melato.xml.XMLMappingHandler;
import org.melato.xml.XMLStringHandler;
import org.melato.xml.XMLTag;
import org.xml.sax.SAXException;

/** Performs various operations for managing contacts. */
public class ContactManager {
	/**
	 * Read contacts from an xml file.
	 * 
	 * @param file
	 *            The xml file to read.
	 * @return A list of contacts.
	 */

	static class ContactHandler extends XMLMappingHandler {
		List<Contact> contacts = new ArrayList<Contact>();
		String name;
		String phone;
		String email;

		XMLStringHandler descriptionHandler = new XMLStringHandler();

		ContactHandler() {
			setHandler(DESCRIPTION, descriptionHandler);
		}

		public void end() {
			contacts.add(new Contact(name, phone, email, descriptionHandler.getText()));
		}

		public List<Contact> getContacts() {
			return contacts;
		}

		public void start(XMLTag tag) {
			name = tag.getRequiredAttribute(NAME);
			phone = tag.getRequiredAttribute(PHONE);
			email = tag.getRequiredAttribute(EMAIL);
		}
	}

	/** The root tag for contacts. */
	static String CONTACTS = "contacts";

	/** The contact element tag name. */
	static String CONTACT = "contact";

	/** The name attribute. */
	static String NAME = "name";

	/** The phone attribute. */
	static String PHONE = "phone";

	/** The email attribute. */
	static String EMAIL = "email";

	/** The description tag. */
	static String DESCRIPTION = "description";

	public static void main(String[] args) throws Exception {
		if (args.length != 1)
			throw new RuntimeException("Usage: <filename>");
		List<Contact> contacts = ContactManager.readXML(new File(args[0]));
		for (Contact c : contacts) {
			System.out.println(c);
		}
	}

	public static List<Contact> readXML(File file) throws IOException, SAXException {
		InputStream in = new FileInputStream(file);
		try {
			in = new BufferedInputStream(in);
			return readXML(in);
		}
		finally {
			in.close();
		}
	}

	public static List<Contact> readXML(InputStream input) throws IOException, SAXException {
		XMLMappingHandler rootHandler = new XMLMappingHandler();
		XMLMappingHandler contactsHandler = new XMLMappingHandler();
		ContactHandler contactHandler = new ContactHandler();
		rootHandler.setHandler(CONTACTS, contactsHandler);
		contactsHandler.setHandler(CONTACT, contactHandler);
		XMLDelegator.parse(rootHandler, input);
		return contactHandler.getContacts();
	}

}
