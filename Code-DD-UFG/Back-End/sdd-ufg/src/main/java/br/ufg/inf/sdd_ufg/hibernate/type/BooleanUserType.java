package br.ufg.inf.sdd_ufg.hibernate.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.annotations.TypeDef;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

@TypeDef(defaultForType = Boolean.class, typeClass = BooleanUserType.class)
public class BooleanUserType implements UserType {
	private final String STRING_TRUE  = "Yes"; 
	private final String STRING_FALSE = "No"; 

	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	public Class<?> returnedClass() {
		return Boolean.class;
	}

	public boolean isMutable() {
		return false;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) return true;
		if (x == null || y == null) return false;
		return x.equals(y);
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		String str = resultSet.getString(names[0]);
		return resultSet.wasNull() ? Boolean.FALSE : Boolean.valueOf(str.equals(STRING_TRUE));
	}

	public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			statement.setString(index, STRING_FALSE);
		} else {
			statement.setString(index, (Boolean) value ? STRING_TRUE : STRING_FALSE);
		}
	}
}