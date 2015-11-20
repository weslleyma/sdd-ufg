package br.ufg.inf.sdd_ufg.hibernate.type;

import org.hibernate.annotations.TypeDef;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDate;
import org.joda.time.LocalDate;

@TypeDef(defaultForType = LocalDate.class, typeClass = LocalDateUserType.class)
public class LocalDateUserType extends PersistentLocalDate {
	private static final long serialVersionUID = -4222229387273227744L;
}