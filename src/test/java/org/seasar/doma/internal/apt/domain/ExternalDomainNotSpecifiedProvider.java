package org.seasar.doma.internal.apt.domain;

import org.seasar.doma.DomainConverters;

@DomainConverters({MondayConverter.class, TuesdayConverter.class, WednesdayConverter.class})
public class ExternalDomainNotSpecifiedProvider {}
