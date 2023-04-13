package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.dto.productType.ProductTypeNoOfProductsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Slf4j

@Repository
public class ProductType2RepositoryImpl implements  ProductType2Repository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<ProductTypeNoOfProductsDto> productTypeNoOfEachProduct() {
        String productTypeNoOfProduct_query = "SELECT pt.name, SUM(t.noOfproducts) " +
                "FROM product_types as pt, " +
                "( " +
                "SELECT b.name, p.stock AS noOfproducts, b.id AS productTypeId " +
                "FROM products AS p, product_types AS b WHERE p.product_type_id = b.id " +
                "ORDER BY b.name ASC " +
                ") AS t " +
                "WHERE pt.id = t.productTypeId GROUP BY pt.name ORDER BY pt.name ASC ";

        List<Object[]> tuples = entityManager.createNativeQuery(
                productTypeNoOfProduct_query).getResultList();

        List<ProductTypeNoOfProductsDto> productTypeNoOfProductsDtos = new ArrayList<>();
        for(Object[] tuple : tuples) {
            ProductTypeNoOfProductsDto productTypeNoOfProductsDto = new ProductTypeNoOfProductsDto();
//            productTypeNoOfProductsDto.setId((Long) tuple[0]);
            productTypeNoOfProductsDto.setName((String)tuple[0]);
            BigDecimal stock = (BigDecimal)tuple[1];
            String strVal = String.valueOf(stock);
            Integer stock_no = Integer.parseInt(strVal);
            productTypeNoOfProductsDto.setStock(stock_no);
            productTypeNoOfProductsDtos.add(productTypeNoOfProductsDto);
        }
        System.out.println("productTypeNoOfProduct_query => productTypeNoOfProductsDtos.size(): " + productTypeNoOfProductsDtos.size());
        return productTypeNoOfProductsDtos;
    }
}
