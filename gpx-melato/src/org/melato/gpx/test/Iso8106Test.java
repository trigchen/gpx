/*-------------------------------------------------------------------------
 * Copyright (c) 2012,2013,2014, Alex Athanasopoulos.  All Rights Reserved.
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
package org.melato.gpx.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;
import org.melato.gpx.Iso8106Date;

public class Iso8106Test {

	@Test
	public void parseDateLocal() {
		final String datestring = "2014-08-10T04:31:58";
		final Date date = Iso8106Date.parseDate(datestring);
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		// format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Assert.assertEquals("2014-08-10T04:31:58", format.format(date));
	}

	@Test
	public void parseDateTime() {
		final String datestring = "2011-10-01 10:20:30";
		final Date date = Iso8106Date.parseDate(datestring);
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Assert.assertEquals("2011-10-01 10:20:30", format.format(date));
	}

	@Test
	public void parseDateTZPlus() {
		final String datestring = "2011-10-1T10:20:30+05:00";
		final Date date = Iso8106Date.parseDate(datestring);
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Assert.assertEquals("2011-10-01T05:20:30", format.format(date));
	}

	@Test
	public void parseDateUTC() {
		final String datestring = "2011-09-25T13:24:35Z";
		final Date date = Iso8106Date.parseDate(datestring);
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Assert.assertEquals("2011-09-25T13:24:35", format.format(date));
	}

	@Test
	public void parseDateUTC1() {
		final String datestring = "2011-10-1T10:20:30Z";
		final Date date = Iso8106Date.parseDate(datestring);
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Assert.assertEquals("2011-10-01T10:20:30", format.format(date));
	}

	@Test
	public void parseTZ() {
		final String tzstring = "+05:00";
		final int minutes = Iso8106Date.parseTimeZone(tzstring);
		Assert.assertEquals(300, minutes);
	}
}
