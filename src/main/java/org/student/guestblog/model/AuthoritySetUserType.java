package org.student.guestblog.model;

import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class AuthoritySetUserType implements UserType {

  protected static final int[] SQL_TYPES = {Types.ARRAY};

  @Override
  public int[] sqlTypes() {
    return new int[]{Types.ARRAY};
  }

  @Override
  public Class<Set> returnedClass() {
    return Set.class;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    if (x == null) {
      return y == null;
    }
    return x.equals(y);
  }

  @Override
  public int hashCode(Object x) throws HibernateException {
    return x.hashCode();
  }

  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
      throws HibernateException, SQLException {
    if (rs.wasNull()) {
      return null;
    }
    if (rs.getArray(names[0]) == null) {
      return new HashSet<>();
    }

    Array array = rs.getArray(names[0]);
    return Arrays.stream((String[]) array.getArray())
        .map(Authority::valueOf)
        .collect(HashSet::new, HashSet::add, HashSet::addAll);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
      throws HibernateException, SQLException {
    Connection connection = st.getConnection();
    if (value == null) {
      st.setNull(index, SQL_TYPES[0]);
    } else {
      HashSet<String> castObject = ((Set<Authority>) value).stream()
          .map(Authority::name)
          .collect(HashSet::new, HashSet::add, HashSet::addAll);
      Array array = connection.createArrayOf("varchar", castObject.toArray());
      st.setArray(index, array);
    }
  }

  @Override
  public Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  @Override
  public boolean isMutable() {
    return true;
  }

  @Override
  public Serializable disassemble(Object value) throws HibernateException {
    return (HashSet) this.deepCopy(value);
  }

  @Override
  public Object assemble(Serializable cached, Object owner) throws HibernateException {
    return this.deepCopy(cached);
  }

  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return original;
  }
}
