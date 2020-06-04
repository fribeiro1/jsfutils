/*
 * Copyright 2012 Upic
 * 
 * This file is part of JSF Utils.
 *
 * JSF Utils is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * JSF Utils is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with JSF Utils. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.upic.jsf.converters;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarConverter implements Converter {
	private static String PATTERN_ATTRIBUTE_ID = "pattern";

	public static String STRING_MESSAGE_ID = "br.com.upic.jsf.converters.XMLGregorianCalendarConverter.STRING";

	public static String XML_GREGORIAN_CALENDAR_MESSAGE_ID = "br.com.upic.jsf.converters.XMLGregorianCalendarConverter.XML_GREGORIAN_CALENDAR";

	private String pattern;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public XMLGregorianCalendarConverter() {
	}

	public XMLGregorianCalendarConverter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public Object getAsObject(FacesContext context,
			UIComponent component, String value) {
		DatatypeFactory factory = null;

		try {
			factory = DatatypeFactory.newInstance();

			return factory.newXMLGregorianCalendar(value);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage();

			Locale locale = context.getViewRoot().getLocale();

			ClassLoader loader = Thread.currentThread()
					.getContextClassLoader();

			ResourceBundle bundle = ResourceBundle.getBundle(
					FacesMessage.FACES_MESSAGES, locale, loader);

			Map<String, String> messageIdMap = new HashMap<String, String>();

			for (Enumeration<String> keys = bundle.getKeys(); keys
					.hasMoreElements();) {
				String key = keys.nextElement();

				messageIdMap.put(key, bundle.getString(key));
			}

			String baseName = context.getApplication().getMessageBundle();

			if (baseName != null) {
				bundle = ResourceBundle.getBundle(baseName, locale, loader);

				for (Enumeration<String> keys = bundle.getKeys(); keys
						.hasMoreElements();) {
					String key = keys.nextElement();

					messageIdMap.put(key, bundle.getString(key));
				}

			}

			String clientId = component.getClientId(context);

			message.setSummary(MessageFormat.format(
					messageIdMap.get(XML_GREGORIAN_CALENDAR_MESSAGE_ID),
					new Object[] { value, null, clientId }));

			if (factory != null)
				message.setDetail(MessageFormat.format(
						messageIdMap.get(XML_GREGORIAN_CALENDAR_MESSAGE_ID
								+ "_detail"),
						new Object[] {
								value,
								factory.newXMLGregorianCalendar(
										new GregorianCalendar()).toXMLFormat(),
								clientId }));

			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ConverterException(message, e);
		}

	}

	@Override
	public String getAsString(FacesContext context,
			UIComponent component, Object value) {
		DateFormat format = new SimpleDateFormat(
				(pattern == null) ? ((String) component.getAttributes().get(
						PATTERN_ATTRIBUTE_ID)) : (pattern));

		try {
			return format.format(((XMLGregorianCalendar) value)
					.toGregorianCalendar().getTime());
		} catch (Exception e) {
			FacesMessage message = new FacesMessage();

			Locale locale = context.getViewRoot().getLocale();

			ClassLoader loader = Thread.currentThread()
					.getContextClassLoader();

			ResourceBundle bundle = ResourceBundle.getBundle(
					FacesMessage.FACES_MESSAGES, locale, loader);

			Map<String, String> messageIdMap = new HashMap<String, String>();

			for (Enumeration<String> keys = bundle.getKeys(); keys
					.hasMoreElements();) {
				String key = keys.nextElement();

				messageIdMap.put(key, bundle.getString(key));
			}

			String baseName = context.getApplication().getMessageBundle();

			if (baseName != null) {
				bundle = ResourceBundle.getBundle(baseName, locale, loader);

				for (Enumeration<String> keys = bundle.getKeys(); keys
						.hasMoreElements();) {
					String key = keys.nextElement();

					messageIdMap.put(key, bundle.getString(key));
				}

			}

			String clientId = component.getClientId(context);

			String summary = MessageFormat.format(
					messageIdMap.get(STRING_MESSAGE_ID), new Object[] { value,
							null, clientId });

			message.setSummary(summary);

			message.setDetail(summary);

			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ConverterException(message, e);
		}

	}

}