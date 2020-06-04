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
package br.com.upic.jsf.validators;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class BigDecimalRangeValidator implements Validator {
	private static String MAXIMUM_ATTRIBUTE_ID = "maximum";

	public static String MAXIMUM_MESSAGE_ID = "br.com.upic.jsf.validators.BigDecimalRangeValidator.MAXIMUM";

	private static String MINIMUM_ATTRIBUTE_ID = "minimum";

	public static String MINIMUM_MESSAGE_ID = "br.com.upic.jsf.validators.BigDecimalRangeValidator.MINIMUM";

	public static String NOT_IN_RANGE_MESSAGE_ID = "br.com.upic.jsf.validators.BigDecimalRangeValidator.NOT_IN_RANGE";

	public static String TYPE_MESSAGE_ID = "br.com.upic.jsf.validators.BigDecimalRangeValidator.TYPE";

	private BigDecimal maximum;

	private BigDecimal minimum;

	public BigDecimalRangeValidator() {
	}

	public BigDecimalRangeValidator(BigDecimal minimum,
			BigDecimal maximum) {
		this.minimum = minimum;

		this.maximum = maximum;
	}

	public void setMaximum(BigDecimal maximum) {
		this.maximum = maximum;
	}

	public void setMinimum(BigDecimal minimum) {
		this.minimum = minimum;
	}

	@Override
	public void validate(FacesContext context,
			UIComponent component, Object value)
			throws ValidatorException {

		if (value == null)
			return;

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

		if (!(value instanceof BigDecimal)) {
			FacesMessage message = new FacesMessage();

			message.setSummary(messageIdMap.get(TYPE_MESSAGE_ID));

			String clientId = component.getClientId(context);

			message.setDetail(MessageFormat.format(
					messageIdMap.get(TYPE_MESSAGE_ID + "_detail"),
					new Object[] { clientId }));

			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(message);
		}

		Map<String, Object> attributes = component.getAttributes();

		BigDecimal minimum = attributes.containsKey(MINIMUM_ATTRIBUTE_ID) ? (BigDecimal) attributes
				.get(MINIMUM_ATTRIBUTE_ID) : this.minimum;

		BigDecimal maximum = attributes.containsKey(MAXIMUM_ATTRIBUTE_ID) ? (BigDecimal) attributes
				.get(MAXIMUM_ATTRIBUTE_ID) : this.maximum;

		if ((minimum != null) && (maximum != null)
				&& (((BigDecimal) value).compareTo(minimum) < 0)
				&& (((BigDecimal) value).compareTo(maximum) > 0)) {
			FacesMessage message = new FacesMessage();

			message.setSummary(messageIdMap.get(NOT_IN_RANGE_MESSAGE_ID));

			String clientId = component.getClientId(context);

			message.setDetail(MessageFormat.format(
					messageIdMap.get(NOT_IN_RANGE_MESSAGE_ID + "_detail"),
					new Object[] { minimum, maximum, clientId }));

			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(message);
		} else if ((minimum != null)
				&& ((((BigDecimal) value).compareTo(minimum) < 0))) {
			FacesMessage message = new FacesMessage();

			message.setSummary(messageIdMap.get(MINIMUM_MESSAGE_ID));

			String clientId = component.getClientId(context);

			message.setDetail(MessageFormat.format(
					messageIdMap.get(MINIMUM_MESSAGE_ID + "_detail"),
					new Object[] { minimum, clientId }));

			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(message);
		} else if ((maximum != null)
				&& (((BigDecimal) value).compareTo(maximum) > 0)) {
			FacesMessage message = new FacesMessage();

			message.setSummary(messageIdMap.get(MAXIMUM_MESSAGE_ID));

			String clientId = component.getClientId(context);

			message.setDetail(MessageFormat.format(
					messageIdMap.get(MAXIMUM_MESSAGE_ID + "_detail"),
					new Object[] { maximum, clientId }));

			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(message);
		}

	}

}