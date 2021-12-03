package com.programmers.apitest.orders;

import com.programmers.apitest.errors.NotFoundException;
import com.programmers.apitest.security.JwtAuthentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.InvalidParameterException;

import static com.programmers.apitest.utils.ApiUtils.ApiResult;
import static com.programmers.apitest.utils.ApiUtils.success;

@RestController
@RequestMapping("api/orders")
public class ReviewRestController {

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(path = "{id}/review")
    public ApiResult<ReviewDto> review(@PathVariable Long id
                    ,@AuthenticationPrincipal JwtAuthentication authentication
                    ,@Valid @RequestBody ReviewRequest request
    ) {
        if (request == null || request.getContent() == null || request.getContent().isEmpty())
            throw new InvalidParameterException();
        return success(reviewService.review(id,authentication.id,request)
                .map(ReviewDto::new)
                .orElseThrow(() -> new NotFoundException("Could not found Review for " + id)));
    }

}
