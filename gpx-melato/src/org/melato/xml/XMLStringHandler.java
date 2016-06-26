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
 * An XML Element handler that collects the tag content text as a string.
 * 
 * @author Alex Athanasopoulos
 * @date Dec 1, 2007
 */
public class XMLStringHandler extends XMLNullHandler {
	protected StringBuilder buf;
	private boolean recursive;
	private boolean append;
	private int level;

	public XMLStringHandler() {
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (buf == null)
			buf = new StringBuilder();
		buf.append(ch, start, length);
	}

	public void clear() {
		buf = null;
	}

	@Override
	public void end() throws SAXException {
		level--;
	}

	@Override
	public XMLElementHandler getHandler(XMLTag tag) {
		if (recursive) {
			return this;
		}
		return super.getHandler(tag);
	}

	/** Return the content text of the XML Element, as a string */
	public String getText() {
		if (buf == null)
			return null;
		return buf.toString();
	}

	public boolean isTopLevel() {
		return level == 0;
	}

	/**
	 * Enable append mode: keep appending to the string until explicitly cleared.
	 * The default is false: clear the string at the beginning of the tag.
	 * 
	 * @param append
	 */
	public void setAppend(boolean append) {
		this.append = append;
	}

	/**
	 * In recursive mode, the body text of children tags are also included, recursively.
	 * The default is non-recursive.
	 * 
	 * @param recursive
	 */
	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	@Override
	public void start(XMLTag tag) throws SAXException {
		if (!append && level++ == 0) {
			clear();
		}
	}
}
