package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.product.*;
import com.busayo.ecommercebackend.model.*;
import com.busayo.ecommercebackend.exception.ProductNotFoundException;
import com.busayo.ecommercebackend.repository.BrandRepository;
import com.busayo.ecommercebackend.repository.CategoryRepository;
import com.busayo.ecommercebackend.repository.ProductRepository;
import com.busayo.ecommercebackend.repository.ProductTypeRepository;
import com.busayo.ecommercebackend.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductTypeRepository productTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository,
                              CategoryRepository categoryRepository,
                              ProductTypeRepository productTypeRepository
                              ) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public Boolean addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setStatus("Active");
        product.setImage(productDto.getImage());
        product.setDescription(productDto.getDescription());

        String categoryName = productDto.getCategory().toUpperCase();
        Category category = categoryRepository.findByName(categoryName);
        product.setCategory(category);

        String productTypeName = productDto.getProductType().toUpperCase();
        ProductType productType = productTypeRepository.findByName(productTypeName);
        product.setProductType(productType);

        String brandName = productDto.getBrand().toUpperCase();
        Brand brand = brandRepository.findByName(brandName);
        product.setBrand(brand);

//        Brand brand = brandRepository.findByName(productDto.getBrand().trim().toUpperCase());
//        if (brand != null) {
//            product.setBrand(brand);
//        }
//        else {
//            Brand brand1 = new Brand();
//            brand1.setName(productDto.getBrand().trim().toUpperCase());
//            brand1.setStatus("Active");
//            brandRepository.save(brand1);
//            product.setBrand(brand1);
//        }
        productRepository.save(product);
        return  true;
    }

    @Override
    public  ProductDto getProduct (Long productId) {
        Product product = productRepository.findById(productId)

                .orElseThrow(() -> new ProductNotFoundException(productId));
        return mapToProductDto(product);
    }

    @Override
    public ProductReviewsDto getProductReviews(Long productId) {
        Product product = productRepository.findById(productId)

                .orElseThrow(() -> new ProductNotFoundException(productId));
        return mapToProductReviewsDto(product);
    }

    @Override
    public ProductResponse2Dto getAllProductsWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findAll(pageable);

        List<Product> productList = products.getContent();

        List<ProductListDto> content = productList.stream().map(product -> mapToProductListDto(product)).collect(Collectors.toList());

        ProductResponse2Dto productResponseDto = new ProductResponse2Dto();
        productResponseDto.setContent(content);
        productResponseDto.setPageNo(products.getNumber());
        productResponseDto.setPageSize(products.getSize());
        productResponseDto.setTotalElements(products.getTotalElements());
        productResponseDto.setTotalPages(products.getTotalPages());
        productResponseDto.setLast(products.isLast());

        return productResponseDto;
    }

    private List<Product> products = new ArrayList<>();

    @Override
    public List<ProductListDto> getAllProducts() {
        products.clear();
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            if ((allProducts != null) && (product.getStatus().trim().equals("Active"))) {
                products.add(product);
            }
        }
        return products.stream().map((product) -> mapToProductListDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse3Dto getProductsByCategoryIdWithPaginationAndSorting(Long categoryId,int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);

        List<Product> productList = products.getContent();

        List<ProductByCategoryDto> content = productList.stream().map(product1 -> mapToProductByCategoryDto(product1)).collect(Collectors.toList());

        ProductResponse3Dto productResponse = new ProductResponse3Dto();
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());
        return productResponse;


    }

    private List<Product> productsByCategory = new ArrayList<>();
    @Override
    public List<ProductByCategoryDto> getProductsByCategoryId(Long categoryId) {
        productsByCategory.clear();
        List<Product> allProductsByCategory = productRepository.findByCategoryId(categoryId);
        for (Product product : allProductsByCategory) {
            if ((allProductsByCategory != null) && (product.getStatus().trim().equals("Active"))) {
                productsByCategory.add(product);
            }
        }
        return productsByCategory.stream().map((product) -> mapToProductByCategoryDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse4Dto getProductsByProductTypeIdWithPaginationAndSorting(Long productTypeId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByProductTypeId(productTypeId, pageable);

        List<Product> productList = products.getContent();

        List<ProductByProductTypeDto> content = productList.stream().map(product1 -> mapToProductByProductTypeDto(product1)).collect(Collectors.toList());

        ProductResponse4Dto productResponse = new ProductResponse4Dto();
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());
        return productResponse;

    }

    @Override
    public List<ProductByProductTypeDto> getProductsByProductTypeId(Long productTypeId) {
        List<Product> products = productRepository.findByProductTypeId(productTypeId);
        return products.stream().map((product) -> mapToProductByProductTypeDto(product))
                .collect(Collectors.toList());
    }

//    @Override
//    public ProductResponse1Dto getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir) {
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//
//
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//
//        Page<Product> products = productRepository.findAll(pageable);
//
//        List<Product> productList = products.getContent();
//
//
//        List<Product> content = productList;
//
//        ProductResponse1Dto productResponse1Dto = new ProductResponse1Dto();
//        productResponse1Dto.setContent(content);
//        productResponse1Dto.setPageNo(products.getNumber());
//        productResponse1Dto.setPageSize(products.getSize());
//        productResponse1Dto.setTotalElements(products.getTotalElements());
//        productResponse1Dto.setTotalPages(products.getTotalPages());
//        productResponse1Dto.setLast(products.isLast());
//
//        return productResponse1Dto;
//    }

    @Override
    public Boolean updateProduct(ProductDto productDto, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        product.setDescription(productDto.getDescription());

        String categoryName = productDto.getCategory().toUpperCase();
        Category category = categoryRepository.findByName(categoryName);
        product.setCategory(category);

        String productTypeName = productDto.getProductType().toUpperCase();
        ProductType productType = productTypeRepository.findByName(productTypeName);
        product.setProductType(productType);

        String brandName = productDto.getBrand().toUpperCase();
        Brand brand = brandRepository.findByName(brandName);
        product.setBrand(brand);

//        Brand brand = brandRepository.findByName(productDto.getBrand().trim().toUpperCase());
//        if (brand != null) {
//            product.setBrand(brand);
//        }
//        else {
//            Brand brand1 = new Brand();
//            brand1.setName(productDto.getBrand().trim().toUpperCase());
//            brand1.setStatus("Active");
//
////            brand1.setProductType(productTypeRepository.findByName(productDto.getProductType()));
//            brandRepository.save(brand1);
//        }
        productRepository.save(product);
        return true;
    }

    @Override
    public Boolean deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.setStatus("Deleted");
        productRepository.save(product);
        return true;
    }

    private ProductListDto mapToProductListDto(Product product){

        ProductListDto productListDto = new ProductListDto();
        productListDto.setId(product.getId());
        productListDto.setName(product.getName());

        Brand brand = brandRepository.findById(product.getBrand().getId()).get();
        productListDto.setBrand(brand.getName());

        Category category = categoryRepository.findById(product.getCategory().getId()).get();
        productListDto.setCategory(category.getName());

        ProductType productType = productTypeRepository.findById(product.getProductType().getId()).get();
        productListDto.setProductType(productType.getName());

        productListDto.setPrice(product.getPrice());
        productListDto.setImage(product.getImage());
        productListDto.setStock(product.getStock());
        return productListDto;
    }

    private ProductByCategoryDto mapToProductByCategoryDto (Product product) {
        ProductByCategoryDto productByCategory = new ProductByCategoryDto();
        productByCategory.setId(product.getId());
        productByCategory.setProductName(product.getName());
        productByCategory.setStock(product.getStock());
        productByCategory.setProductType(product.getProductType().getName());
        return productByCategory;
    }

    private ProductByProductTypeDto mapToProductByProductTypeDto (Product product) {
        ProductByProductTypeDto productByProductType = new ProductByProductTypeDto();
        productByProductType.setId(product.getId());
        productByProductType.setName(product.getName());
        productByProductType.setStock(product.getStock());
        productByProductType.setBrand(product.getBrand().getName());
        productByProductType.setCategory(product.getCategory().getName());
        productByProductType.setImage(product.getImage());
        productByProductType.setPrice(product.getPrice());
        productByProductType.setDescription(product.getDescription());
        return productByProductType;
    }

    private ProductDto mapToProductDto (Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setStock(product.getStock());
        productDto.setImage(product.getImage());
        productDto.setBrand(product.getBrand().getName());
        productDto.setProductType(product.getProductType().getName());
        productDto.setCategory(product.getCategory().getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        return productDto;
    }

    private ProductReviewsDto mapToProductReviewsDto(Product product) {
        ProductReviewsDto productReviewsDto = new ProductReviewsDto();
        productReviewsDto.setReviews(new ArrayList<>(product.getReviews()));
        return productReviewsDto;
    }
}
