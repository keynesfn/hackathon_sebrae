package core.db;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

public class Bean {
	private static String GET = "get";
	private static String IS = "is";
	private int status = 0;
	
	protected void setCreated() {
		status = 1;
	}
	
	protected int isCreated() {
		return status;
	}

	public boolean setAttribute(String attribute, Object value) {
		if (attribute == null) {
			return false;
		}

		// Split out property on dots ( "person.name.first" -> "person","name","first" -> getPerson().getName().getFirst() )
		StringTokenizer st = new StringTokenizer(attribute, ".");

		if (st.countTokens() == 0) {
			return false;
		}

		// Holder for Object at current depth along chain.
		Object current = this;

		// count of the tokens
		int n = st.countTokens() - 1;

		try {
			// Loop through properties in chain.
			for (int i = 0; st.hasMoreTokens(); i++) {
				String currentPropertyName = st.nextToken();

				if (i < n) {
					// This is a getter
					current = invokeProperty(current, currentPropertyName);
				} else {
					// Final property in chain, hence setter
					try {
						// Call setter
						PropertyDescriptor pd = new PropertyDescriptor(currentPropertyName, current.getClass());
						pd.getWriteMethod().invoke(current, new Object[] {value});

						return true;
					} catch (Exception e) {
						return false;
					}
				}
			}

			// Return holder Object
			return true;
		} catch (NullPointerException e) {
			// It is very likely that one of the properties returned null. If so, catch the exception and return null.
			return false;
		}
	}

	public Object getAttribute(String attribute) {
		if (attribute == null) {
			return null;
		}

		// Split out property on dots ( "person.name.first" -> "person","name","first" -> getPerson().getName().getFirst() )
		StringTokenizer st = new StringTokenizer(attribute, ".");

		if (st.countTokens() == 0) {
			return null;
		}

		// Holder for Object at current depth along chain.
		Object result = this;

		try {
			// Loop through properties in chain.
			while (st.hasMoreTokens()) {
				String currentPropertyName = st.nextToken();

				// Assign to holder the next property in the chain.
				result = invokeProperty(result, currentPropertyName);
			}

			// Return holder Object
			return result;
		} catch (NullPointerException e) {
//			LOG.warn(e.getMessage(), e);        	
			// It is very likely that one of the properties returned null. If so, catch the exception and return null.
			return null;
		}
	}


	private Object invokeProperty(Object obj, String property) {
		if ((property == null) || (property.length() == 0)) {
			return null; // just in case something silly happens.
		}

		Class<?> cls = obj.getClass();
		Object[] oParams = {};
		Class<?>[] cParams = {};

		try {
			// First try object.getProperty()
			Method method = cls.getMethod(createMethodName(GET, property), cParams);

			return method.invoke(obj, oParams);
		} catch (Exception e1) {
			try {
				// First try object.isProperty()
				Method method = cls.getMethod(createMethodName(IS, property), cParams);

				return method.invoke(obj, oParams);
			} catch (Exception e2) {
				try {
					// Now try object.property()
					Method method = cls.getMethod(property, cParams);

					return method.invoke(obj, oParams);
				} catch (Exception e3) {
					try {
						// Now try object.property()
						Field field = cls.getField(property);

						return field.get(obj);
					} catch (Exception e4) {
						// oh well
						return null;
					}
				}
			}
		}
	}

	private String createMethodName(String prefix, String propertyName) {
		return prefix + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
	}
}
