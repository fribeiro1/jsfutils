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
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public final class CalendarConverter implements Converter {
	public static final String CALENDAR_MESSAGE_ID = "br.com.upic.jsf.converters.CalendarConverter.CALENDAR";

	private static final String PATTERN_ATTRIBUTE_ID = "pattern";

	public static final String STRING_MESSAGE_ID = "br.com.upic.jsf.converters.CalendarConverter.STRING";

	private String pattern;

	public CalendarConverter() {
	}

	public CalendarConverter(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public Object getAsObject(final FacesContext context,
			final UIComponent component, final String value) {
		final DateFormat format = new SimpleDateFormat(
				(pattern == null) ? ((String) component.getAttributes().get(
						PATTERN_ATTRIBUTE_ID)) : (pattern));

		try {
			final Calendar cal = Calendar.getInstance();

			cal.setTime(format.parse(value));

			return cal;
		} catch (final Exception e) {
			final FacesMessage message = new FacesMessage();

			final Locale locale = context.getViewRoot().getLocale();

			final ClassLoader loader = Thread.currentThread()
					.getContextClassLoader();

			ResourceBundle bundle = ResourceBundle.getBundle(
					FacesMessage.FACES_MESSAGES, locale, loader);

			final Map<String, String> messageIdMap = new HashMap<String, String>();

			for (final Enumeration<String> keys = bundle.getKeys(); keys
					.hasMoreElements();) {
				final String key = keys.nextElement();

				messageIdMap.put(key, bundle.getString(key));
			}

			String baseName = context.getApplication().getMessageBundle();

			if (baseName != null) {
				bundle = ResourceBundle.getBundle(baseName, locale, loader);

				for (final Enumeration<String> keys = bundle.getKeys(); keys
						.hasMoreElements();) {
					final String key = keys.nextElement();

					messageIdMap.put(key, bundle.getString(key));
				}

			}

			final String clientId = component.getClientId(context);

			message.setSummary(MessageFormat.format(
					messageIdMap.get(CALENDAR_MESSAGE_ID), new Object[] {
							value, null, clientId }));

			message.setDetail(MessageFormat.format(
					messageIdMap.get(CALENDAR_MESSAGE_ID + "_detail"),
					new Object[] { value,
							format.format(Calendar.getInstance().getTime()),
							clientId }));

			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ConverterException(message, e);
		}

	}

	@Override
	public String getAsString(final FacesContext context,
			final UIComponent component, final Object value) {
		final DateFormat format = new SimpleDateFormat(
				(pattern == null) ? ((String) component.getAttributes().get(
						PATTERN_ATTRIBUTE_ID)) : (pattern));

		try {
			return format.format(((Calendar) value).getTime());
		} catch (final Exception e) {
			final FacesMessage message = new FacesMessage();

			final Locale locale = context.getViewRoot().getLocale();

			final ClassLoader loader = Thread.currentThread()
					.getContextClassLoader();

			ResourceBundle bundle = ResourceBundle.getBundle(
					FacesMessage.FACES_MESSAGES, locale, loader);

			final Map<String, String> messageIdMap = new HashMap<String, String>();

			for (final Enumeration<String> keys = bundle.getKeys(); keys
					.hasMoreElements();) {
				final String key = keys.nextElement();

				messageIdMap.put(key, bundle.getString(key));
			}

			String baseName = context.getApplication().getMessageBundle();

			if (baseName != null) {
				bundle = ResourceBundle.getBundle(baseName, locale, loader);

				for (final Enumeration<String> keys = bundle.getKeys(); keys
						.hasMoreElements();) {
					final String key = keys.nextElement();

					messageIdMap.put(key, bundle.getString(key));
				}

			}

			final String clientId = component.getClientId(context);

			final String summary = MessageFormat.format(
					messageIdMap.get(STRING_MESSAGE_ID), new Object[] { value,
							null, clientId });

			message.setSummary(summary);

			message.setDetail(summary);

			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ConverterException(message, e);
		}

	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

}