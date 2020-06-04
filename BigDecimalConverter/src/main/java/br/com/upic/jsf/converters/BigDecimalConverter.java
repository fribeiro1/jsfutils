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

import java.text.DecimalFormat;
import java.text.MessageFormat;
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

public class BigDecimalConverter implements Converter {
	public static String BIG_DECIMAL_MESSAGE_ID = "br.com.upic.jsf.converters.BigDecimalConverter.BIG_DECIMAL";

	private static String PATTERN_ATTRIBUTE_ID = "pattern";

	public static String STRING_MESSAGE_ID = "br.com.upic.jsf.converters.BigDecimalConverter.STRING";

	@Override
	public Object getAsObject(FacesContext context,
			UIComponent component, String value) {

		try {
			DecimalFormat format = new DecimalFormat((String) component
					.getAttributes().get(PATTERN_ATTRIBUTE_ID));

			format.setParseBigDecimal(true);

			return format.parse(value);
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
					messageIdMap.get(BIG_DECIMAL_MESSAGE_ID), new Object[] {
							value, null, clientId }));

			message.setDetail(MessageFormat.format(
					messageIdMap.get(BIG_DECIMAL_MESSAGE_ID + "_detail"),
					new Object[] { value, "1.1", clientId }));

			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ConverterException(message);
		}

	}

	@Override
	public String getAsString(FacesContext context,
			UIComponent component, Object value) {

		try {
			DecimalFormat format = new DecimalFormat((String) component
					.getAttributes().get(PATTERN_ATTRIBUTE_ID));

			return format.format(value);
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

			throw new ConverterException(message);
		}

	}

}