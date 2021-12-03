package com.programmers.apitest.products;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.programmers.apitest.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Product> findById(long id) {
        List<Product> results = jdbcTemplate.query(
                "SELECT * FROM products WHERE seq=?",
                mapper,
                id
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM products ORDER BY seq DESC",
                mapper
        );
    }

    @Override
    public boolean updateByReview(Long productId) {
        int updateReview = jdbcTemplate.update(
                "UPDATE products SET REVIEW_COUNT = REVIEW_COUNT+1 WHERE seq=?",
                productId
        );
        return updateReview == 1;
    }

    static RowMapper<Product> mapper = (rs, rowNum) ->
            new Product.Builder()
                    .seq(rs.getLong("seq"))
                    .name(rs.getString("name"))
                    .details(rs.getString("details"))
                    .reviewCount(rs.getInt("review_count"))
                    .createAt(dateTimeOf(rs.getTimestamp("create_at")))
                    .build();

}