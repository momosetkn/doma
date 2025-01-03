/*
 * Copyright Doma Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.seasar.doma.internal.jdbc.mock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Arrays;

@SuppressWarnings("RedundantThrows")
public class MockStatement extends MockWrapper implements Statement {

  public boolean closed;

  public int addBatchCount;

  public int updatedRows = 1;

  @Override
  public void addBatch(String sql) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void cancel() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void clearBatch() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void clearWarnings() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void close() throws SQLException {
    closed = true;
  }

  @Override
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public boolean execute(String sql) throws SQLException {
    return false;
  }

  @Override
  public int[] executeBatch() throws SQLException {
    int[] results = new int[addBatchCount];
    Arrays.fill(results, updatedRows);
    addBatchCount = 0;
    return results;
  }

  @Override
  public ResultSet executeQuery(String sql) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int executeUpdate(String sql) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public Connection getConnection() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int getFetchDirection() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int getFetchSize() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int getMaxFieldSize() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int getMaxRows() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public boolean getMoreResults() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public boolean getMoreResults(int current) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int getQueryTimeout() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public ResultSet getResultSet() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int getResultSetConcurrency() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int getResultSetHoldability() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int getResultSetType() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public int getUpdateCount() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return closed;
  }

  @Override
  public boolean isPoolable() throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void setCursorName(String name) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void setEscapeProcessing(boolean enable) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void setMaxFieldSize(int max) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void setMaxRows(int max) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void setPoolable(boolean poolable) throws SQLException {
    throw new AssertionError();
  }

  @Override
  public void setQueryTimeout(int seconds) throws SQLException {}

  @SuppressWarnings("all")
  public void closeOnCompletion() throws SQLException {
    throw new AssertionError();
  }

  @SuppressWarnings("all")
  public boolean isCloseOnCompletion() throws SQLException {
    throw new AssertionError();
  }

  @SuppressWarnings("all")
  public long getLargeUpdateCount() throws SQLException {
    throw new AssertionError();
  }

  @SuppressWarnings("all")
  public void setLargeMaxRows(long max) throws SQLException {
    throw new AssertionError();
  }

  @SuppressWarnings("all")
  public long getLargeMaxRows() throws SQLException {
    throw new AssertionError();
  }

  @SuppressWarnings("all")
  public long[] executeLargeBatch() throws SQLException {
    throw new AssertionError();
  }

  @SuppressWarnings("all")
  public long executeLargeUpdate(String sql) throws SQLException {
    throw new AssertionError();
  }

  @SuppressWarnings("all")
  public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    throw new AssertionError();
  }

  @SuppressWarnings("all")
  public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
    throw new AssertionError();
  }

  @SuppressWarnings("all")
  public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
    throw new AssertionError();
  }
}
