package com.programmers.apitest.orders;

import com.programmers.apitest.configures.web.SimplePageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.programmers.apitest.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String FIND_ALL_QUERY = "SELECT ORDERS.SEQ AS seq, ORDERS.USER_SEQ AS userId , ORDERS.PRODUCT_SEQ  AS productId, REVIEWS.SEQ as reviewSeq,REVIEWS.USER_SEQ as reviewUserId, REVIEWS.PRODUCT_SEQ as reviewProductId, REVIEWS.CONTENT  as reviewContent, REVIEWS.CREATE_AT  as reviewCreateAt, ORDERS.STATE AS state, ORDERS.REQUEST_MSG AS requestMessage, ORDERS.REJECT_MSG AS rejectMessage, ORDERS.COMPLETED_AT AS completedAt, ORDERS.REJECTED_AT AS  rejectedAt, ORDERS.CREATE_AT AS  createAt FROM ORDERS LEFT JOIN REVIEWS ON ORDERS.REVIEW_SEQ  = REVIEWS.SEQ WHERE ORDERS.USER_SEQ = ? ORDER BY seq DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    @Override
    public List<Order> findAll(SimplePageRequest simplePageRequest, Long userId) {
        return jdbcTemplate.query(
                FIND_ALL_QUERY,
                mapper,
                userId,
                simplePageRequest.getOffset(),
                simplePageRequest.getSize()
        );
    }
    private final String FIND_BY_ID_QUERY = "SELECT ORDERS.SEQ AS seq, ORDERS.USER_SEQ AS userId , ORDERS.PRODUCT_SEQ  AS productId, REVIEWS.SEQ as reviewSeq,REVIEWS.USER_SEQ as reviewUserId, REVIEWS.PRODUCT_SEQ as reviewProductId, REVIEWS.CONTENT  as reviewContent, REVIEWS.CREATE_AT  as reviewCreateAt, ORDERS.STATE AS state, ORDERS.REQUEST_MSG AS requestMessage, ORDERS.REJECT_MSG AS rejectMessage, ORDERS.COMPLETED_AT AS completedAt, ORDERS.REJECTED_AT AS  rejectedAt, ORDERS.CREATE_AT AS  createAt FROM ORDERS LEFT JOIN REVIEWS ON ORDERS.REVIEW_SEQ  = REVIEWS.SEQ WHERE ORDERS.USER_SEQ = ? AND ORDERS.SEQ = ?";
    @Override
    public Optional<Order> findById(Long id, Long userId) {
        List<Order> results = jdbcTemplate.query(
                FIND_BY_ID_QUERY,
                mapper,
                userId,
                id
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }
    private final String UPDATE_BY_REVIEW_ID_QUERY = "UPDATE ORDERS SET REVIEW_SEQ = ? WHERE SEQ = ?";
    @Override
    public boolean updateByReviewId(Long id,Long reviewId) {
        int reviewCount = jdbcTemplate.update(
                UPDATE_BY_REVIEW_ID_QUERY,
                reviewId,
                id
        );
        return reviewCount == 1;
    }


    private final String UPDATE_BY_ACCEPT_QUERY = "UPDATE ORDERS SET STATE = ? WHERE SEQ = ?";

    @Override
    public Boolean updateByAccept(Long id) {

        int updateCount = jdbcTemplate.update(
                UPDATE_BY_ACCEPT_QUERY,
                String.valueOf(State.ACCEPTED),
                id
        );
        return updateCount == 1;
    }


    private final String UPDATE_BY_REJECT_QUERY = "UPDATE ORDERS SET STATE = ?, REJECTED_AT = now(), REJECT_MSG = ? WHERE SEQ = ?";
    @Override
    public Boolean updateByReject(Long id, RejectRequest request) {
        int updateCount = jdbcTemplate.update(
                UPDATE_BY_REJECT_QUERY,
                String.valueOf(State.REJECTED),
                request.getMessage(),
                id
        );
        return updateCount == 1;
    }


    private final String UPDATE_BY_SHIPPING_QUERY = "UPDATE ORDERS SET STATE = ? WHERE SEQ = ?";
    @Override
    public Boolean updateByShipping(Long id) {

        int updateCount = jdbcTemplate.update(
                UPDATE_BY_SHIPPING_QUERY,
                String.valueOf(State.SHIPPING),
                id
        );
        return updateCount == 1;
    }


    private final String UPDATE_BY_COMPLETE_QUERY = "UPDATE ORDERS SET STATE = ?, COMPLETED_AT = now()  WHERE SEQ = ?";
    @Override
    public Boolean updateByComplete(Long id) {

        int updateCount = jdbcTemplate.update(
                UPDATE_BY_COMPLETE_QUERY,
                String.valueOf(State.COMPLETED),
                id
        );
        return updateCount == 1;
    }


    static RowMapper<Order> mapper = (rs, rowNum) ->
            new Order.Builder()
                    .seq(rs.getLong("seq"))
                    .userId(rs.getLong("userId"))
                    .productId(rs.getLong("productId"))
                    .review( new Review.Builder()
                                .seq(rs.getLong("reviewSeq"))
                                .userId(rs.getLong("reviewUserId"))
                                .productId(rs.getLong("reviewProductId"))
                                .content(rs.getString("reviewContent"))
                                .createAt(dateTimeOf(rs.getTimestamp("reviewCreateAt")))
                                .build()
                            )
                    .state(State.valueOf(rs.getString("state")))
                    .requestMessage(rs.getString("requestMessage"))
                    .rejectMessage(rs.getString("rejectMessage"))
                    .completedAt(dateTimeOf(rs.getTimestamp("completedAt")))
                    .rejectedAt(dateTimeOf(rs.getTimestamp("rejectedAt")))
                    .createAt(dateTimeOf(rs.getTimestamp("createAt")))
                    .build();


}