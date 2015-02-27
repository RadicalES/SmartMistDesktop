/*
 * Copyright (C) 2012-2015 Radical Electronic Systems, South Africa
 *
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
 */

package com.radicales.sm100.apps;

import java.text.ParseException;
import java.util.StringTokenizer;
import javax.swing.text.DefaultFormatter;

/**
 * IP Address Formatter
 * Formats an IP Address
 *
 * @author
 * Jan Zwiegers,
 * <a href="mailto:jan@radicalsystems.co.za">jan@radicalsystems.co.za</a>,
 * <a href="http://www.radicalsystems.co.za">www.radicalsystems.co.za</a>
 *
 * @version
 * <b>1.0 01/11/2014</b><br>
 * Original release.
 */
public class IPAddressFormatter extends DefaultFormatter {

   @Override
   public String valueToString(Object value) throws ParseException
   {
      if (!(value instanceof byte[])) {
          throw new ParseException("Not a byte[]", 0);
      }
      byte[] a = (byte[]) value;
      if (a.length != 4) {
          throw new ParseException("Length != 4", 0);
      }
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < 4; i++)
      {
         int b = a[i];
         if (b < 0) {
             b += 256;
         }
         builder.append(String.valueOf(b));
         if (i < 3) {
             builder.append('.');
         }
      }
      return builder.toString();
   }

   @Override
   public Object stringToValue(String text) throws ParseException
   {
      StringTokenizer tokenizer = new StringTokenizer(text, ".");
      byte[] a = new byte[4];
      for (int i = 0; i < 4; i++)
      {
         int b = 0;
         if (!tokenizer.hasMoreTokens()) {
             throw new ParseException("Too few bytes", 0);
         }
         try
         {
            b = Integer.parseInt(tokenizer.nextToken());
         }
         catch (NumberFormatException e)
         {
            throw new ParseException("Not an integer", 0);
         }
         if (b < 0 || b >= 256) {
             throw new ParseException("Byte out of range", 0);
         }
         a[i] = (byte) b;
      }
      if (tokenizer.hasMoreTokens()) {
          throw new ParseException("Too many bytes", 0);
      }
      return a;
   }
}

