package com.zingpay.ehsas.controller;

import com.zingpay.ehsas.dto.FetchProductRequestDto;
import com.zingpay.ehsas.entity.Product;
import com.zingpay.ehsas.service.ProductService;
import com.zingpay.ehsas.util.Status;
import com.zingpay.ehsas.util.StatusMessage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@RestController
@RequestMapping("/product")
@Api(value = "product", description = "Contains methods related to product.")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Status getAll() {
        return new Status(StatusMessage.SUCCESS, Product.convertToDto(productService.getAll()));
    }

    @PostMapping
    public Status fetchDiscountedPrice(@RequestBody FetchProductRequestDto fetchProductRequestDto) {
        fetchProductRequestDto.setAccountId(getLoggedInUserAccountId());
        return productService.fetchDiscountedPrice(fetchProductRequestDto);
    }
}
