package org.seasar.doma.internal.apt.annot;

import static org.seasar.doma.internal.util.AssertionUtil.assertNonNullValue;

import java.util.List;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.VariableElement;
import org.seasar.doma.internal.apt.AptIllegalStateException;
import org.seasar.doma.internal.apt.util.AnnotationValueUtil;
import org.seasar.doma.jdbc.SqlLogType;
import org.seasar.doma.jdbc.query.DuplicateKeyType;

public class MultiInsertAnnot extends AbstractAnnot {

  private static final String QUERY_TIMEOUT = "queryTimeout";
  private static final String INCLUDE = "include";

  private static final String EXCLUDE = "exclude";

  private static final String SQL_LOG = "sqlLog";

  private static final String DUPLICATE_KEY_TYPE = "duplicateKeyType";

  private static final String DUPLICATE_KEYS = "duplicateKeys";

  private final AnnotationValue queryTimeout;
  private final AnnotationValue include;

  private final AnnotationValue exclude;

  private final AnnotationValue sqlLog;

  private final AnnotationValue duplicateKeyType;

  private final AnnotationValue duplicateKeys;

  MultiInsertAnnot(AnnotationMirror annotationMirror, Map<String, AnnotationValue> values) {
    super(annotationMirror);

    // non null values
    this.queryTimeout = assertNonNullValue(values, QUERY_TIMEOUT);
    this.sqlLog = assertNonNullValue(values, SQL_LOG);

    // nullable values
    this.include = values.get(INCLUDE);
    this.exclude = values.get(EXCLUDE);
    this.duplicateKeyType = values.get(DUPLICATE_KEY_TYPE);
    this.duplicateKeys = values.get(DUPLICATE_KEYS);
  }

  public AnnotationValue getQueryTimeout() {
    return queryTimeout;
  }

  public AnnotationValue getInclude() {
    return include;
  }

  public AnnotationValue getExclude() {
    return exclude;
  }

  public AnnotationValue getDuplicateKeys() {
    return duplicateKeys;
  }

  public AnnotationValue getSqlLog() {
    return sqlLog;
  }

  public int getQueryTimeoutValue() {
    Integer value = AnnotationValueUtil.toInteger(queryTimeout);
    if (value == null) {
      throw new AptIllegalStateException(QUERY_TIMEOUT);
    }
    return value;
  }

  public List<String> getIncludeValue() {
    return AnnotationValueUtil.toStringList(include);
  }

  public List<String> getExcludeValue() {
    return AnnotationValueUtil.toStringList(exclude);
  }

  public DuplicateKeyType getDuplicateKeyValue() {
    VariableElement enumConstant = AnnotationValueUtil.toEnumConstant(duplicateKeyType);
    if (enumConstant == null) {
      throw new AptIllegalStateException(DUPLICATE_KEY_TYPE);
    }
    return DuplicateKeyType.valueOf(enumConstant.getSimpleName().toString());
  }

  public List<String> getDuplicateKeysValue() {
    return AnnotationValueUtil.toStringList(duplicateKeys);
  }

  public SqlLogType getSqlLogValue() {
    VariableElement enumConstant = AnnotationValueUtil.toEnumConstant(sqlLog);
    if (enumConstant == null) {
      throw new AptIllegalStateException(SQL_LOG);
    }
    return SqlLogType.valueOf(enumConstant.getSimpleName().toString());
  }
}
