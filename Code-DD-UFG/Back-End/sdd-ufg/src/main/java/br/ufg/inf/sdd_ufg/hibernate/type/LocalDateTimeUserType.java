package br.ufg.inf.sdd_ufg.hibernate.type;

import org.hibernate.annotations.TypeDef;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime;
import org.joda.time.LocalDateTime;

@TypeDef(defaultForType = LocalDateTime.class, typeClass = LocalDateTimeUserType.class)
public class LocalDateTimeUserType extends PersistentLocalDateTime {
	private static final long serialVersionUID = 2655545011460945170L;
}