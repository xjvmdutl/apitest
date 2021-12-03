package com.programmers.apitest.orders;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.programmers.apitest.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcReviewRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final String SAVE_QUERY = "INSERT INTO reviews(seq,user_seq,product_seq,content) VALUES (null,?,?,?)";

    @Override
    public boolean save(Long productId, Long userId, ReviewRequest request) {
        int reviewCount = jdbcTemplate.update(
                SAVE_QUERY,
                userId,
                productId,
                request.getContent()
        );
        return reviewCount == 1;
    }


    private final String FIND_BY_PRODUCT_ID_AND_USER_ID_QUERY = "SELECT SEQ as seq, USER_SEQ as userId, PRODUCT_SEQ as productId, CONTENT as content, CREATE_AT as createAt FROM reviews WHERE USER_SEQ = ? AND PRODUCT_SEQ = ?";

    @Override
    public Optional<Review> findByProductIdAndUserId(Long productId, Long userId) {
        List<Review> results = jdbcTemplate.query(
                FIND_BY_PRODUCT_ID_AND_USER_ID_QUERY,
                mapper,
                userId,
                productId
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    static RowMapper<Review> mapper = (rs, rowNum) ->
            new Review.Builder()
                    .seq(rs.getLong("seq"))
                    .userId(rs.getLong("userId"))
                    .productId(rs.getLong("productId"))
                    .content(rs.getString("content"))
                    .createAt(dateTimeOf(rs.getTimestamp("createAt")))
                    .build();


}