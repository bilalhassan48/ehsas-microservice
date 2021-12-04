package com.zingpay.ehsas.entity;

import com.zingpay.ehsas.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public static ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCode(product.getCode());
        productDto.setName(product.getName());

        return productDto;
    }

    public static List<ProductDto> convertToDto(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<ProductDto>();
        products.forEach(product -> {
            productDtos.add(convertToDto(product));
        });

        return productDtos;
    }
}
