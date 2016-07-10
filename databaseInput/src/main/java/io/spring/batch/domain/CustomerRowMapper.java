package io.spring.batch.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public final Customer mapRow(final ResultSet resultSet, final int i) throws SQLException {
        return new Customer(resultSet.getLong("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getDate("birthdate"));
    }
}
