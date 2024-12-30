package org.seasar.doma.jdbc.dialect;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.seasar.doma.DomaNullPointerException;
import org.seasar.doma.expr.ExpressionFunctions;
import org.seasar.doma.internal.jdbc.dialect.StandardCountGettingTransformer;
import org.seasar.doma.internal.jdbc.dialect.StandardForUpdateTransformer;
import org.seasar.doma.internal.jdbc.dialect.StandardPagingTransformer;
import org.seasar.doma.internal.jdbc.sql.PreparedSqlBuilder;
import org.seasar.doma.internal.util.AssertionUtil;
import org.seasar.doma.internal.util.CharSequenceUtil;
import org.seasar.doma.jdbc.JdbcException;
import org.seasar.doma.jdbc.JdbcMappingFunction;
import org.seasar.doma.jdbc.JdbcMappingHint;
import org.seasar.doma.jdbc.JdbcMappingVisitor;
import org.seasar.doma.jdbc.JdbcUnsupportedOperationException;
import org.seasar.doma.jdbc.PreparedSql;
import org.seasar.doma.jdbc.ScriptBlockContext;
import org.seasar.doma.jdbc.SelectForUpdateType;
import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.jdbc.SelectOptionsAccessor;
import org.seasar.doma.jdbc.Sql;
import org.seasar.doma.jdbc.SqlLogFormatter;
import org.seasar.doma.jdbc.SqlLogFormattingFunction;
import org.seasar.doma.jdbc.SqlLogFormattingVisitor;
import org.seasar.doma.jdbc.SqlNode;
import org.seasar.doma.jdbc.criteria.metamodel.PropertyMetamodel;
import org.seasar.doma.jdbc.criteria.option.ForUpdateOption;
import org.seasar.doma.jdbc.criteria.query.AliasManager;
import org.seasar.doma.jdbc.criteria.query.CriteriaBuilder;
import org.seasar.doma.jdbc.id.AutoGeneratedKeysType;
import org.seasar.doma.jdbc.query.MultiInsertAssembler;
import org.seasar.doma.jdbc.query.MultiInsertAssemblerContext;
import org.seasar.doma.jdbc.query.UpsertAssembler;
import org.seasar.doma.jdbc.query.UpsertAssemblerContext;
import org.seasar.doma.jdbc.type.EnumType;
import org.seasar.doma.jdbc.type.JdbcType;
import org.seasar.doma.jdbc.type.JdbcTypes;
import org.seasar.doma.message.Message;
import org.seasar.doma.wrapper.ArrayWrapper;
import org.seasar.doma.wrapper.BigDecimalWrapper;
import org.seasar.doma.wrapper.BigIntegerWrapper;
import org.seasar.doma.wrapper.BlobWrapper;
import org.seasar.doma.wrapper.BooleanWrapper;
import org.seasar.doma.wrapper.ByteWrapper;
import org.seasar.doma.wrapper.BytesWrapper;
import org.seasar.doma.wrapper.ClobWrapper;
import org.seasar.doma.wrapper.DateWrapper;
import org.seasar.doma.wrapper.DoubleWrapper;
import org.seasar.doma.wrapper.EnumWrapper;
import org.seasar.doma.wrapper.FloatWrapper;
import org.seasar.doma.wrapper.IntegerWrapper;
import org.seasar.doma.wrapper.LocalDateTimeWrapper;
import org.seasar.doma.wrapper.LocalDateWrapper;
import org.seasar.doma.wrapper.LocalTimeWrapper;
import org.seasar.doma.wrapper.LongWrapper;
import org.seasar.doma.wrapper.NClobWrapper;
import org.seasar.doma.wrapper.ObjectWrapper;
import org.seasar.doma.wrapper.SQLXMLWrapper;
import org.seasar.doma.wrapper.ShortWrapper;
import org.seasar.doma.wrapper.StringWrapper;
import org.seasar.doma.wrapper.TimeWrapper;
import org.seasar.doma.wrapper.TimestampWrapper;
import org.seasar.doma.wrapper.UtilDateWrapper;
import org.seasar.doma.wrapper.Wrapper;

/** A standard implementation of {@link Dialect}. */
public class StandardDialect implements Dialect {

  /** the quotation mark of the start */
  protected static final char OPEN_QUOTE = '"';

  /** the quotation mark of the end */
  protected static final char CLOSE_QUOTE = '"';

  /** the set of {@literal SQLState} code that represents unique violation */
  protected static final Set<String> UNIQUE_CONSTRAINT_VIOLATION_STATE_CODES =
      new HashSet<>(Arrays.asList("23", "27", "44"));

  private static final CriteriaBuilder CRITERIA_SQL_BUILDER = new StandardCriteriaBuilder();

  /** the visitor that maps {@link Wrapper} to {@link JdbcType} */
  protected final JdbcMappingVisitor jdbcMappingVisitor;

  /** the visitor that maps {@link Wrapper} to {@link SqlLogFormatter} */
  protected final SqlLogFormattingVisitor sqlLogFormattingVisitor;

  /** the aggregation of expression functions */
  protected final ExpressionFunctions expressionFunctions;

  public StandardDialect() {
    this(
        new StandardJdbcMappingVisitor(),
        new StandardSqlLogFormattingVisitor(),
        new StandardExpressionFunctions());
  }

  public StandardDialect(JdbcMappingVisitor jdbcMappingVisitor) {
    this(
        jdbcMappingVisitor,
        new StandardSqlLogFormattingVisitor(),
        new StandardExpressionFunctions());
  }

  public StandardDialect(SqlLogFormattingVisitor sqlLogFormattingVisitor) {
    this(
        new StandardJdbcMappingVisitor(),
        sqlLogFormattingVisitor,
        new StandardExpressionFunctions());
  }

  public StandardDialect(ExpressionFunctions expressionFunctions) {
    this(
        new StandardJdbcMappingVisitor(),
        new StandardSqlLogFormattingVisitor(),
        expressionFunctions);
  }

  public StandardDialect(
      JdbcMappingVisitor jdbcMappingVisitor, SqlLogFormattingVisitor sqlLogFormattingVisitor) {
    this(jdbcMappingVisitor, sqlLogFormattingVisitor, new StandardExpressionFunctions());
  }

  public StandardDialect(
      JdbcMappingVisitor jdbcMappingVisitor,
      SqlLogFormattingVisitor sqlLogFormattingVisitor,
      ExpressionFunctions expressionFunctions) {
    if (jdbcMappingVisitor == null) {
      throw new DomaNullPointerException("jdbcMappingVisitor");
    }
    if (sqlLogFormattingVisitor == null) {
      throw new DomaNullPointerException("sqlLogFormattingVisitor");
    }
    if (expressionFunctions == null) {
      throw new DomaNullPointerException("expressionFunctions");
    }
    this.jdbcMappingVisitor = jdbcMappingVisitor;
    this.sqlLogFormattingVisitor = sqlLogFormattingVisitor;
    this.expressionFunctions = expressionFunctions;
  }

  @Override
  public String getName() {
    return "standard";
  }

  @Override
  public SqlNode transformSelectSqlNode(SqlNode sqlNode, SelectOptions options) {
    if (sqlNode == null) {
      throw new DomaNullPointerException("sqlNode");
    }
    if (options == null) {
      throw new DomaNullPointerException("options");
    }
    SqlNode transformed = sqlNode;
    if (SelectOptionsAccessor.isCount(options)) {
      transformed = toCountCalculatingSqlNode(sqlNode);
    }
    long offset = SelectOptionsAccessor.getOffset(options);
    long limit = SelectOptionsAccessor.getLimit(options);
    if (offset >= 0 || limit >= 0) {
      transformed = toPagingSqlNode(transformed, offset, limit);
    }
    SelectForUpdateType forUpdateType = SelectOptionsAccessor.getForUpdateType(options);
    if (forUpdateType != null) {
      String[] aliases = SelectOptionsAccessor.getAliases(options);
      if (!supportsSelectForUpdate(forUpdateType, false)) {
        switch (forUpdateType) {
          case NORMAL:
            throw new JdbcException(Message.DOMA2023, getName());
          case WAIT:
            throw new JdbcException(Message.DOMA2079, getName());
          case NOWAIT:
            throw new JdbcException(Message.DOMA2080, getName());
          default:
            AssertionUtil.assertUnreachable();
        }
      }
      if (aliases.length > 0) {
        if (!supportsSelectForUpdate(forUpdateType, true)) {
          switch (forUpdateType) {
            case NORMAL:
              throw new JdbcException(Message.DOMA2024, getName());
            case WAIT:
              throw new JdbcException(Message.DOMA2081, getName());
            case NOWAIT:
              throw new JdbcException(Message.DOMA2082, getName());
            default:
              AssertionUtil.assertUnreachable();
          }
        }
      }
      int waitSeconds = SelectOptionsAccessor.getWaitSeconds(options);
      transformed = toForUpdateSqlNode(transformed, forUpdateType, waitSeconds, aliases);
    }
    return transformed;
  }

  protected SqlNode toCountCalculatingSqlNode(SqlNode sqlNode) {
    return sqlNode;
  }

  protected SqlNode toPagingSqlNode(SqlNode sqlNode, long offset, long limit) {
    StandardPagingTransformer transformer = new StandardPagingTransformer(offset, limit);
    return transformer.transform(sqlNode);
  }

  protected SqlNode toForUpdateSqlNode(
      SqlNode sqlNode, SelectForUpdateType forUpdateType, int waitSeconds, String... aliases) {
    StandardForUpdateTransformer transformer =
        new StandardForUpdateTransformer(forUpdateType, waitSeconds, aliases);
    return transformer.transform(sqlNode);
  }

  @Override
  public SqlNode transformSelectSqlNodeForGettingCount(SqlNode sqlNode) {
    if (sqlNode == null) {
      throw new DomaNullPointerException("sqlNode");
    }
    return toCountGettingSqlNode(sqlNode);
  }

  protected SqlNode toCountGettingSqlNode(SqlNode sqlNode) {
    StandardCountGettingTransformer transformer = new StandardCountGettingTransformer();
    return transformer.transform(sqlNode);
  }

  @Override
  public boolean isUniqueConstraintViolated(SQLException sqlException) {
    if (sqlException == null) {
      throw new DomaNullPointerException("sqlException");
    }
    String state = getSQLState(sqlException);
    if (state != null && state.length() >= 2) {
      String code = state.substring(0, 2);
      return UNIQUE_CONSTRAINT_VIOLATION_STATE_CODES.contains(code);
    }
    return false;
  }

  protected String getSQLState(SQLException sqlException) {
    SQLException cause = getCauseSQLException(sqlException);
    return cause.getSQLState();
  }

  protected int getErrorCode(SQLException sqlException) {
    SQLException cause = getCauseSQLException(sqlException);
    return cause.getErrorCode();
  }

  protected SQLException getCauseSQLException(SQLException sqlException) {
    SQLException cause = sqlException;
    for (Throwable t : sqlException) {
      if (t instanceof SQLException) {
        cause = (SQLException) t;
      }
    }
    return cause;
  }

  @Override
  public Throwable getRootCause(SQLException sqlException) {
    if (sqlException == null) {
      throw new DomaNullPointerException("sqlException");
    }
    Throwable cause = sqlException;
    for (Throwable t : sqlException) {
      cause = t;
    }
    return cause;
  }

  @Override
  public boolean supportsAutoGeneratedKeys() {
    return false;
  }

  @Override
  public boolean supportsBatchUpdateResults() {
    return true;
  }

  @Override
  public boolean supportsIdentity() {
    return false;
  }

  @Override
  public boolean supportsSequence() {
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean supportsIdentityReservation() {
    return false;
  }

  @Override
  public boolean includesIdentityColumn() {
    return false;
  }

  @Override
  public boolean supportsSelectForUpdate(SelectForUpdateType type, boolean withTargets) {
    return false;
  }

  @Override
  public boolean supportsResultSetReturningAsOutParameter() {
    return false;
  }

  @Override
  public JdbcType<ResultSet> getResultSetType() {
    throw new JdbcUnsupportedOperationException(getClass().getName(), "getResultSetType");
  }

  @Override
  public Sql<?> getIdentitySelectSql(
      String catalogName,
      String schemaName,
      String tableName,
      String columnName,
      boolean isQuoteRequired,
      boolean isIdColumnQuoteRequired) {
    throw new JdbcUnsupportedOperationException(getClass().getName(), "getIdentitySelectSql");
  }

  @SuppressWarnings("deprecation")
  @Override
  public Sql<?> getIdentityReservationSql(
      String catalogName,
      String schemaName,
      String tableName,
      String columnName,
      boolean isQuoteRequired,
      boolean isIdColumnQuoteRequired,
      int reservationSize) {
    throw new JdbcUnsupportedOperationException(getClass().getName(), "getIdentityReservationSql");
  }

  @Override
  public PreparedSql getSequenceNextValSql(String qualifiedSequenceName, long allocationSize) {
    throw new JdbcUnsupportedOperationException(getClass().getName(), "getSequenceNextValString");
  }

  @Override
  public String applyQuote(String name) {
    return OPEN_QUOTE + name + CLOSE_QUOTE;
  }

  @Override
  public String removeQuote(String name) {
    if (name == null || name.length() <= 2) {
      return name;
    }
    char[] chars = name.toCharArray();
    if (chars[0] == OPEN_QUOTE && chars[chars.length - 1] == CLOSE_QUOTE) {
      return new String(chars, 1, chars.length - 2);
    }
    return name;
  }

  @Override
  public JdbcMappingVisitor getJdbcMappingVisitor() {
    return jdbcMappingVisitor;
  }

  @Override
  public SqlLogFormattingVisitor getSqlLogFormattingVisitor() {
    return sqlLogFormattingVisitor;
  }

  @Override
  public ExpressionFunctions getExpressionFunctions() {
    return expressionFunctions;
  }

  @Override
  public ScriptBlockContext createScriptBlockContext() {
    return new StandardScriptBlockContext();
  }

  @Override
  public String getScriptBlockDelimiter() {
    return null;
  }

  @Override
  public AutoGeneratedKeysType getAutoGeneratedKeysType() {
    return AutoGeneratedKeysType.DEFAULT;
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    return CRITERIA_SQL_BUILDER;
  }

  public static class StandardJdbcMappingVisitor implements JdbcMappingVisitor {

    @Override
    public Void visitArrayWrapper(ArrayWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.ARRAY);
    }

    @Override
    public Void visitBigDecimalWrapper(
        BigDecimalWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q) throws SQLException {
      return p.apply(wrapper, JdbcTypes.BIG_DECIMAL);
    }

    @Override
    public Void visitBigIntegerWrapper(
        BigIntegerWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q) throws SQLException {
      return p.apply(wrapper, JdbcTypes.BIG_INTEGER);
    }

    @Override
    public Void visitBlobWrapper(BlobWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.BLOB);
    }

    @Override
    public Void visitBooleanWrapper(
        BooleanWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q) throws SQLException {
      return p.apply(wrapper, JdbcTypes.BOOLEAN);
    }

    @Override
    public Void visitByteWrapper(ByteWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.BYTE);
    }

    @Override
    public Void visitBytesWrapper(BytesWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.BYTES);
    }

    @Override
    public Void visitClobWrapper(ClobWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.CLOB);
    }

    @Override
    public Void visitDateWrapper(DateWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.DATE);
    }

    @Override
    public Void visitDoubleWrapper(DoubleWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.DOUBLE);
    }

    @Override
    public Void visitFloatWrapper(FloatWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.FLOAT);
    }

    @Override
    public Void visitIntegerWrapper(
        IntegerWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q) throws SQLException {
      return p.apply(wrapper, JdbcTypes.INTEGER);
    }

    @Override
    public Void visitLocalDateWrapper(
        LocalDateWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q) throws SQLException {
      return p.apply(wrapper, JdbcTypes.LOCAL_DATE);
    }

    @Override
    public Void visitLocalDateTimeWrapper(
        LocalDateTimeWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.LOCAL_DATE_TIME);
    }

    @Override
    public Void visitLocalTimeWrapper(
        LocalTimeWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q) throws SQLException {
      return p.apply(wrapper, JdbcTypes.LOCAL_TIME);
    }

    @Override
    public Void visitLongWrapper(LongWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.LONG);
    }

    @Override
    public Void visitNClobWrapper(NClobWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.NCLOB);
    }

    @Override
    public Void visitShortWrapper(ShortWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.SHORT);
    }

    @Override
    public Void visitStringWrapper(StringWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.STRING);
    }

    @Override
    public Void visitSQLXMLWrapper(SQLXMLWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.SQLXML);
    }

    @Override
    public Void visitTimeWrapper(TimeWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      return p.apply(wrapper, JdbcTypes.TIME);
    }

    @Override
    public Void visitTimestampWrapper(
        TimestampWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q) throws SQLException {
      return p.apply(wrapper, JdbcTypes.TIMESTAMP);
    }

    @Override
    public <E extends Enum<E>> Void visitEnumWrapper(
        EnumWrapper<E> wrapper, JdbcMappingFunction p, JdbcMappingHint q) throws SQLException {
      return p.apply(wrapper, new EnumType<>(wrapper.getBasicClass()));
    }

    @Override
    public Void visitUtilDateWrapper(
        UtilDateWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q) throws SQLException {
      return p.apply(wrapper, JdbcTypes.UTIL_DATE);
    }

    @Override
    public Void visitObjectWrapper(ObjectWrapper wrapper, JdbcMappingFunction p, JdbcMappingHint q)
        throws SQLException {
      JdbcType<Object> jdbcType = q.getJdbcType().orElse(JdbcTypes.OBJECT);
      return p.apply(wrapper, jdbcType);
    }
  }

  public static class StandardSqlLogFormattingVisitor implements SqlLogFormattingVisitor {

    @Override
    public String visitArrayWrapper(ArrayWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.ARRAY);
    }

    @Override
    public String visitBigDecimalWrapper(
        BigDecimalWrapper wrapper, SqlLogFormattingFunction p, Void q) {
      return p.apply(wrapper, JdbcTypes.BIG_DECIMAL);
    }

    @Override
    public String visitBigIntegerWrapper(
        BigIntegerWrapper wrapper, SqlLogFormattingFunction p, Void q) throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.BIG_INTEGER);
    }

    @Override
    public String visitBlobWrapper(BlobWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.BLOB);
    }

    @Override
    public String visitBooleanWrapper(BooleanWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.BOOLEAN);
    }

    @Override
    public String visitByteWrapper(ByteWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.BYTE);
    }

    @Override
    public String visitBytesWrapper(BytesWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.BYTES);
    }

    @Override
    public String visitClobWrapper(ClobWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.CLOB);
    }

    @Override
    public String visitDateWrapper(DateWrapper wrapper, SqlLogFormattingFunction p, Void q) {
      return p.apply(wrapper, JdbcTypes.DATE);
    }

    @Override
    public String visitDoubleWrapper(DoubleWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.DOUBLE);
    }

    @Override
    public String visitFloatWrapper(FloatWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.FLOAT);
    }

    @Override
    public String visitIntegerWrapper(IntegerWrapper wrapper, SqlLogFormattingFunction p, Void q) {
      return p.apply(wrapper, JdbcTypes.INTEGER);
    }

    @Override
    public String visitLocalDateWrapper(
        LocalDateWrapper wrapper, SqlLogFormattingFunction p, Void q) throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.LOCAL_DATE);
    }

    @Override
    public String visitLocalDateTimeWrapper(
        LocalDateTimeWrapper wrapper, SqlLogFormattingFunction p, Void q) throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.LOCAL_DATE_TIME);
    }

    @Override
    public String visitLocalTimeWrapper(
        LocalTimeWrapper wrapper, SqlLogFormattingFunction p, Void q) throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.LOCAL_TIME);
    }

    @Override
    public String visitLongWrapper(LongWrapper wrapper, SqlLogFormattingFunction p, Void q) {
      return p.apply(wrapper, JdbcTypes.LONG);
    }

    @Override
    public String visitNClobWrapper(NClobWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.NCLOB);
    }

    @Override
    public String visitShortWrapper(ShortWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.SHORT);
    }

    @Override
    public String visitStringWrapper(StringWrapper wrapper, SqlLogFormattingFunction p, Void q) {
      return p.apply(wrapper, JdbcTypes.STRING);
    }

    @Override
    public String visitSQLXMLWrapper(SQLXMLWrapper wrapper, SqlLogFormattingFunction p, Void q)
        throws RuntimeException {
      return p.apply(wrapper, JdbcTypes.SQLXML);
    }

    @Override
    public String visitTimeWrapper(TimeWrapper wrapper, SqlLogFormattingFunction p, Void q) {
      return p.apply(wrapper, JdbcTypes.TIME);
    }

    @Override
    public String visitTimestampWrapper(
        TimestampWrapper wrapper, SqlLogFormattingFunction p, Void q) {
      return p.apply(wrapper, JdbcTypes.TIMESTAMP);
    }

    @Override
    public <E extends Enum<E>> String visitEnumWrapper(
        EnumWrapper<E> wrapper, SqlLogFormattingFunction p, Void q) throws RuntimeException {
      return p.apply(wrapper, new EnumType<>(wrapper.getBasicClass()));
    }

    @Override
    public String visitUtilDateWrapper(
        UtilDateWrapper wrapper, SqlLogFormattingFunction p, Void q) {
      return p.apply(wrapper, JdbcTypes.UTIL_DATE);
    }

    @Override
    public String visitObjectWrapper(ObjectWrapper wrapper, SqlLogFormattingFunction p, Void q) {
      return p.apply(wrapper, JdbcTypes.OBJECT);
    }
  }

  /** A standard implementation of {@link ExpressionFunctions}. */
  public static class StandardExpressionFunctions implements ExpressionFunctions {

    /** the default escape character */
    private static final char DEFAULT_ESCAPE_CHAR = '$';

    /** the default wild card characters */
    private static final char[] DEFAULT_WILDCARDS = {'%', '_'};

    /** the escape character for the SQL LIKE operator */
    protected final char escapeChar;

    /** the wild card characters for the SQL LIKE operator */
    protected final char[] wildcards;

    /** the default pattern for escape wild card characters */
    protected final Pattern defaultWildcardReplacementPattern;

    /** the default replacement string for wild card characters */
    protected final String defaultReplacement;

    protected StandardExpressionFunctions() {
      this(DEFAULT_WILDCARDS);
    }

    /**
     * @param wildcards the wild card characters for the SQL LIKE operator
     */
    protected StandardExpressionFunctions(char[] wildcards) {
      this(DEFAULT_ESCAPE_CHAR, wildcards);
    }

    /**
     * @param escapeChar the escape character for the SQL LIKE operator
     * @param wildcards the wild card characters for the SQL LIKE operator
     */
    protected StandardExpressionFunctions(char escapeChar, char[] wildcards) {
      this.escapeChar = escapeChar;
      this.wildcards = wildcards != null ? wildcards : DEFAULT_WILDCARDS;
      this.defaultWildcardReplacementPattern =
          createWildcardReplacementPattern(escapeChar, this.wildcards);
      this.defaultReplacement = createWildcardReplacement(escapeChar);
    }

    /**
     * @param escapeChar the escape character for the SQL LIKE operator
     * @param wildcards the wild card characters for the SQL LIKE operator
     * @param defaultWildcardReplacementPattern the default pattern for escape wild card characters
     * @param defaultReplacement the default replacement string for wild card characters
     */
    protected StandardExpressionFunctions(
        char escapeChar,
        char[] wildcards,
        Pattern defaultWildcardReplacementPattern,
        String defaultReplacement) {
      this.escapeChar = escapeChar;
      this.wildcards = wildcards != null ? wildcards : DEFAULT_WILDCARDS;
      this.defaultWildcardReplacementPattern = defaultWildcardReplacementPattern;
      this.defaultReplacement = defaultReplacement;
    }

    @Override
    public String escape(CharSequence text, char escapeChar) {
      if (text == null) {
        return null;
      }
      return escapeWildcard(text, escapeChar);
    }

    @Override
    public String escape(CharSequence text) {
      if (text == null) {
        return null;
      }
      return escapeWildcard(defaultWildcardReplacementPattern, text, defaultReplacement);
    }

    @Override
    public String prefix(CharSequence text) {
      if (text == null) {
        return null;
      }
      String escaped = escapeWildcard(defaultWildcardReplacementPattern, text, defaultReplacement);
      return escaped + "%";
    }

    @Override
    public String prefix(CharSequence text, char escapeChar) {
      if (text == null) {
        return null;
      }
      return escapeWildcard(text, escapeChar) + "%";
    }

    @Override
    public String suffix(CharSequence text) {
      if (text == null) {
        return null;
      }
      String escaped = escapeWildcard(defaultWildcardReplacementPattern, text, defaultReplacement);
      return "%" + escaped;
    }

    @Override
    public String suffix(CharSequence text, char escapeChar) {
      if (text == null) {
        return null;
      }
      return "%" + escapeWildcard(text, escapeChar);
    }

    @Override
    public String infix(CharSequence text) {
      if (text == null) {
        return null;
      }
      if (text.length() == 0) {
        return "%";
      }
      String escaped = escapeWildcard(defaultWildcardReplacementPattern, text, defaultReplacement);
      return "%" + escaped + "%";
    }

    @Override
    public String infix(CharSequence text, char escapeChar) {
      if (text == null) {
        return null;
      }
      if (text.length() == 0) {
        return "%";
      }
      return "%" + escapeWildcard(text, escapeChar) + "%";
    }

    /**
     * Escapes wild card characters.
     *
     * @param input the target string for escaping
     * @param escapeChar the escape character
     * @return the escaped string
     */
    protected String escapeWildcard(CharSequence input, char escapeChar) {
      Pattern pattern = createWildcardReplacementPattern(escapeChar, wildcards);
      String replacement = createWildcardReplacement(escapeChar);
      return escapeWildcard(pattern, input, replacement);
    }

    /**
     * Escapes wild card characters with the regular expression.
     *
     * @param pattern the regular expression pattern
     * @param input the target string for escaping
     * @param replacement the replacement string
     * @return the escaped string
     */
    protected String escapeWildcard(Pattern pattern, CharSequence input, String replacement) {
      Matcher matcher = pattern.matcher(input);
      return matcher.replaceAll(replacement);
    }

    @Override
    public java.util.Date roundDownTimePart(java.util.Date date) {
      if (date == null) {
        return null;
      }
      Calendar calendar = makeRoundedDownCalendar(date);
      return new java.util.Date(calendar.getTimeInMillis());
    }

    @Override
    public Date roundDownTimePart(Date date) {
      if (date == null) {
        return null;
      }
      Calendar calendar = makeRoundedDownCalendar(date);
      return new Date(calendar.getTimeInMillis());
    }

    @Override
    public Timestamp roundDownTimePart(Timestamp timestamp) {
      if (timestamp == null) {
        return null;
      }
      Calendar calendar = makeRoundedDownCalendar(timestamp);
      return new Timestamp(calendar.getTimeInMillis());
    }

    @Override
    public LocalDateTime roundDownTimePart(LocalDateTime localDateTime) {
      if (localDateTime == null) {
        return null;
      }
      return localDateTime.truncatedTo(ChronoUnit.DAYS);
    }

    protected Calendar makeRoundedDownCalendar(java.util.Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      return calendar;
    }

    @Override
    public java.util.Date roundUpTimePart(java.util.Date date) {
      if (date == null) {
        return null;
      }
      Calendar calendar = makeRoundedUpCalendar(date);
      return new java.util.Date(calendar.getTimeInMillis());
    }

    @Override
    public Date roundUpTimePart(Date date) {
      if (date == null) {
        return null;
      }
      Calendar calendar = makeRoundedUpCalendar(date);
      return new Date(calendar.getTimeInMillis());
    }

    @Override
    public Timestamp roundUpTimePart(Timestamp timestamp) {
      if (timestamp == null) {
        return null;
      }
      Calendar calendar = makeRoundedUpCalendar(timestamp);
      return new Timestamp(calendar.getTimeInMillis());
    }

    @Override
    public LocalDate roundUpTimePart(LocalDate localDate) {
      if (localDate == null) {
        return null;
      }
      return localDate.plusDays(1);
    }

    @Override
    public LocalDateTime roundUpTimePart(LocalDateTime localDateTime) {
      if (localDateTime == null) {
        return null;
      }
      return localDateTime.plusDays(1).truncatedTo(ChronoUnit.DAYS);
    }

    protected Calendar makeRoundedUpCalendar(java.util.Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.DATE, 1);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      return calendar;
    }

    /**
     * Creates the regular expression pattern that is represents the wild card characters .
     *
     * @param escapeChar the escape character
     * @param wildcards the wild card character
     * @return the pattern
     */
    protected Pattern createWildcardReplacementPattern(char escapeChar, char[] wildcards) {
      StringBuilder buf = new StringBuilder();
      buf.append("[");
      for (char wildcard : wildcards) {
        if (escapeChar == '[' || escapeChar == ']') {
          buf.append("\\");
        }
        buf.append(Matcher.quoteReplacement(String.valueOf(escapeChar)));
        if (wildcard == '[' || wildcard == ']') {
          buf.append("\\");
        }
        buf.append(wildcard);
      }
      buf.append("]");
      return Pattern.compile(buf.toString());
    }

    /**
     * Create the replacement string that replaces wild card characters.
     *
     * @param escapeChar the escape character
     * @return the replacement string
     */
    protected String createWildcardReplacement(char escapeChar) {
      return Matcher.quoteReplacement(String.valueOf(escapeChar)) + "$0";
    }

    @Override
    public boolean isEmpty(CharSequence charSequence) {
      return CharSequenceUtil.isEmpty(charSequence);
    }

    @Override
    public boolean isNotEmpty(CharSequence charSequence) {
      return CharSequenceUtil.isNotEmpty(charSequence);
    }

    @Override
    public boolean isBlank(CharSequence charSequence) {
      return CharSequenceUtil.isBlank(charSequence);
    }

    @Override
    public boolean isNotBlank(CharSequence charSequence) {
      return CharSequenceUtil.isNotBlank(charSequence);
    }
  }

  /** A standard implementation of {@link ScriptBlockContext}. */
  public static class StandardScriptBlockContext implements ScriptBlockContext {

    /** the key wards that represents the start of a block */
    protected final List<List<String>> sqlBlockStartKeywordsList = new ArrayList<>();

    /** the key words */
    protected final List<String> keywords = new ArrayList<>();

    /** {@code true} if this context is inside of a block */
    protected boolean inBlock;

    @Override
    public void addKeyword(String keyword) {
      if (!inBlock) {
        keywords.add(keyword);
        check();
      }
    }

    /** Whether this context is inside of a block. */
    protected void check() {
      for (List<String> startKeywords : sqlBlockStartKeywordsList) {
        if (startKeywords.size() > keywords.size()) {
          continue;
        }
        Iterator<String> startKeywordsIt = startKeywords.iterator();
        Iterator<String> keywordsIt = keywords.iterator();
        while (startKeywordsIt.hasNext()) {
          String word1 = startKeywordsIt.next();
          String word2 = keywordsIt.next();
          inBlock = word1.equalsIgnoreCase(word2);
          if (!inBlock) {
            break;
          }
        }
        if (inBlock) {
          break;
        }
      }
    }

    @Override
    public boolean isInBlock() {
      return inBlock;
    }
  }

  public static class StandardCriteriaBuilder implements CriteriaBuilder {

    @Override
    public void concat(PreparedSqlBuilder buf, Runnable leftOperand, Runnable rightOperand) {
      buf.appendSql("concat(");
      leftOperand.run();
      buf.appendSql(", ");
      rightOperand.run();
      buf.appendSql(")");
    }

    @Override
    public void offsetAndFetch(PreparedSqlBuilder buf, int offset, int limit) {
      buf.appendSql(" offset ");
      buf.appendSql(Integer.toString(offset));
      buf.appendSql(" rows");
      if (limit > 0) {
        buf.appendSql(" fetch first ");
        buf.appendSql(Integer.toString(limit));
        buf.appendSql(" rows only");
      }
    }

    @Override
    public void lockWithTableHint(
        PreparedSqlBuilder buf, ForUpdateOption option, Consumer<PropertyMetamodel<?>> column) {}

    @Override
    public void forUpdate(
        PreparedSqlBuilder buf,
        ForUpdateOption option,
        Consumer<PropertyMetamodel<?>> column,
        AliasManager aliasManager) {
      option.accept(
          new ForUpdateOption.Visitor() {
            @Override
            public void visit(ForUpdateOption.Basic basic) {
              appendSql();
            }

            @Override
            public void visit(ForUpdateOption.NoWait noWait) {
              appendSql();
            }

            @Override
            public void visit(ForUpdateOption.Wait wait) {
              appendSql();
            }

            private void appendSql() {
              buf.appendSql(" for update");
            }
          });
    }
  }

  @Override
  public UpsertAssembler getUpsertAssembler(UpsertAssemblerContext context) {
    throw new JdbcUnsupportedOperationException(getClass().getName(), "getUpsertBuilder");
  }

  @Override
  public <ENTITY> MultiInsertAssembler getMultiInsertAssembler(
      MultiInsertAssemblerContext<ENTITY> context) {
    return new StandardMultiInsertAssembler<>(context);
  }
}
