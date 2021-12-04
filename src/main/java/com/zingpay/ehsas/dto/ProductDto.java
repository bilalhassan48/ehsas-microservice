package com.zingpay.ehsas.dto;

import com.zingpay.ehsas.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Getter
@Setter
public class ProductDto {
    private int id;
    private String code;
    private String name;

    private double price;
    private double totalPrice;
    private double discount;
    private int quantity;

    public static Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setCode(productDto.getCode());
        product.setName(productDto.getName());

        return product;
    }

    public static List<Product> convertToEntity(List<ProductDto> productDtos) {
        List<Product> products = new ArrayList<Product>();
        productDtos.forEach(productDto -> {
            products.add(convertToEntity(productDto));
        });

        return products;
    }
}
