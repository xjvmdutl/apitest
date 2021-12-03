package com.programmers.apitest.orders;

import com.programmers.apitest.configures.web.SimplePageRequest;
import com.programmers.apitest.errors.NotFoundException;
import com.programmers.apitest.security.JwtAuthentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

import static com.programmers.apitest.utils.ApiUtils.ApiResult;
import static com.programmers.apitest.utils.ApiUtils.success;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public ApiResult<List<OrderDto>> findAll(SimplePageRequest simplePageRequest
            , @AuthenticationPrincipal JwtAuthentication authentication){
        return success(orderService.findAll(simplePageRequest,authentication.id).stream()
                .map(OrderDto::new)
                .collect(toList()));
    }

    @GetMapping(path = "{id}")
    public ApiResult<OrderDto> findById(@PathVariable Long id
            , @AuthenticationPrincipal JwtAuthentication authentication){
        return success(orderService.findById(id,authentication.id)
                .map(OrderDto::new)
                .orElseThrow(() -> new NotFoundException("Could not found Order for " + id)));
    }

    @PatchMapping(path = "{id}/accept")
    public ApiResult<Boolean> accept(@PathVariable Long id
            , @AuthenticationPrincipal JwtAuthentication authentication){
        return success(orderService.accept(id,authentication.id));
    }

    @PatchMapping(path = "{id}/reject")
    public ApiResult<Boolean> reject(@PathVariable Long id
            , @AuthenticationPrincipal JwtAuthentication authentication
            , @RequestBody(required = false) RejectRequest request){
        if(request == null || request.getMessage() == null)
            throw new InvalidParameterException();

        return success(orderService.reject(id,authentication.id,request));
    }

    @PatchMapping(path = "{id}/shipping")
    public ApiResult<Boolean> shipping(@PathVariable Long id
            , @AuthenticationPrincipal JwtAuthentication authentication){
        return success(orderService.shipping(id,authentication.id));
    }

    @PatchMapping(path = "{id}/complete")
    public ApiResult<Boolean> complete (@PathVariable Long id
            , @AuthenticationPrincipal JwtAuthentication authentication){
        return success(orderService.complete(id,authentication.id));
    }
}
