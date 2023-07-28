package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.product.*;
import com.busayo.ecommercebackend.model.*;
import com.busayo.ecommercebackend.exception.ProductNotFoundException;
import com.busayo.ecommercebackend.repository.*;
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
    private final ProductImageRepository productImageRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository,
                              CategoryRepository categoryRepository,
                              ProductTypeRepository productTypeRepository,
                              ProductImageRepository productImageRepository,
                              OrderDetailsRepository orderDetailsRepository
    ) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productTypeRepository = productTypeRepository;
        this.productImageRepository = productImageRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public Boolean addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setColour(productDto.getColour());
        product.setStatus("Active");

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

        productRepository.save(product);

        productDto.getImages().forEach(image -> {
            ProductImage productImage = new ProductImage();
            productImage.setImage(image.getImage());
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        });
        return  true;
    }

    @Override
    public  ProductDto getProduct (Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return mapToProductDto(product);
    }

    @Override
    public ProductResponse2Dto getProductsWithPaginationAndSorting(String status, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByStatus(status, pageable);

        List<Product> productList = products.getContent();

        List<ProductListDto> content = productList.stream().map(this::mapToProductListDto).collect(Collectors.toList());

        ProductResponse2Dto productResponseDto = new ProductResponse2Dto();
        productResponseDto.setContent(content);
        productResponseDto.setPageNo(products.getNumber());
        productResponseDto.setPageSize(products.getSize());
        productResponseDto.setTotalElements(products.getTotalElements());
        productResponseDto.setTotalPages(products.getTotalPages());
        productResponseDto.setLast(products.isLast());

        return productResponseDto;
    }


    private final List<Product> products = new ArrayList<>();

    @Override
    public List<ProductListDto> getAllProducts() {
        products.clear();
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            if (product.getStatus().trim().equals("Active")) {
                products.add(product);
            }
        }
        return products.stream().map(this::mapToProductListDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse3Dto getProductsByCategoryIdWithPaginationAndSorting(Long categoryId, String status,int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByCategoryIdAndStatus(categoryId, status, pageable);

        List<Product> productList = products.getContent();

        List<ProductByCategoryDto> content = productList.stream().map(this::mapToProductByCategoryDto).collect(Collectors.toList());

        ProductResponse3Dto productResponse = new ProductResponse3Dto();
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());
        return productResponse;


    }

    @Override
    public ProductResponse3Dto getProductsByCategoryAndProductType(Long categoryId, Long productTypeId, String status, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByCategoryIdAndProductTypeIdAndStatus(categoryId, productTypeId, status, pageable);

        List<Product> productList = products.getContent();

        List<ProductByCategoryDto> content = productList.stream().map(this::mapToProductByCategoryDto).collect(Collectors.toList());

        ProductResponse3Dto productResponse = new ProductResponse3Dto();
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());
        return productResponse;
    }

    private final List<Product> productsByCategory = new ArrayList<>();
    @Override
    public List<ProductByCategoryDto> getProductsByCategoryId(Long categoryId) {
        productsByCategory.clear();
        List<Product> allProductsByCategory = productRepository.findByCategoryId(categoryId);
        for (Product product : allProductsByCategory) {
            if (product.getStatus().trim().equals("Active")) {
                productsByCategory.add(product);
            }
        }
        return productsByCategory.stream().map(this::mapToProductByCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse2Dto getProductsByProductType(Long productTypeId, String status, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByProductTypeIdAndStatus(productTypeId, status, pageable);

        List<Product> productList = products.getContent();

        List<ProductListDto> content = productList.stream().map(this::mapToProductListDto).collect(Collectors.toList());

        ProductResponse2Dto productResponse = new ProductResponse2Dto();
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
        return products.stream().map(this::mapToProductByProductTypeDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean updateProduct(ProductDto productDto, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());



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

        productRepository.save(product);

        productDto.getImages().forEach(image -> {
            ProductImage existingImage = productImageRepository.findByImage(image.getImage());
            if(existingImage == null){
                ProductImage productImage = new ProductImage();
                productImage.setImage(image.getImage());
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }
            else if ( !existingImage.getProduct().getId().equals(product.getId())) {
                ProductImage productImage = new ProductImage();
                productImage.setImage(image.getImage());
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }
        });
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

    @Override
    public ProductResponse2Dto searchProducts(String query, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.searchProductsSQL(query, pageable);

        List<Product> productList = products.getContent();

        List<ProductListDto> content = productList.stream().map(this::mapToProductListDto).collect(Collectors.toList());

        ProductResponse2Dto productResponseDto = new ProductResponse2Dto();
        productResponseDto.setContent(content);
        productResponseDto.setPageNo(products.getNumber());
        productResponseDto.setPageSize(products.getSize());
        productResponseDto.setTotalElements(products.getTotalElements());
        productResponseDto.setTotalPages(products.getTotalPages());
        productResponseDto.setLast(products.isLast());
        return productResponseDto;
    }

    @Override
    public ProductResponse5Dto getProductsByTotalQuantityDescending(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Product> products = orderDetailsRepository.findProductQuantitiesSumOrderByDescendingQuantity(pageable);

        List<Product> productList = products.getContent();

        ProductResponse5Dto productResponseDto = new ProductResponse5Dto();
        productResponseDto.setContent(productList);
        productResponseDto.setPageNo(products.getNumber());
        productResponseDto.setPageSize(products.getSize());
        productResponseDto.setTotalElements(products.getTotalElements());
        productResponseDto.setTotalPages(products.getTotalPages());
        productResponseDto.setLast(products.isLast());
        return productResponseDto;
    }
    public ProductResponse5Dto getReviewedProducts(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findReviewedProducts(pageable);

        List<Product> productList = products.getContent();

        ProductResponse5Dto productResponseDto = new ProductResponse5Dto();
        productResponseDto.setContent(productList);
        productResponseDto.setPageNo(products.getNumber());
        productResponseDto.setPageSize(products.getSize());
        productResponseDto.setTotalElements(products.getTotalElements());
        productResponseDto.setTotalPages(products.getTotalPages());
        productResponseDto.setLast(products.isLast());
        return productResponseDto;
    }

    @Override
    public ProductResponse2Dto productSearchByCategoryAndTypeAndBrand(String categoryName, String productTypeName, String brandName, String status, int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Category category = categoryRepository.findByName(categoryName.trim().toUpperCase());
        Long categoryId = category.getId();

        ProductType productType = productTypeRepository.findByName(productTypeName.trim().toUpperCase());
        Long productTypeId = productType.getId();

        Brand brand = brandRepository.findByName(brandName.trim().toUpperCase());
        Long brandId = brand.getId();

        Page<Product> products = productRepository.findByCategoryIdAndProductTypeIdAndBrandIdAndStatus(categoryId, productTypeId, brandId, status, pageable);

        List<Product> productList = products.getContent();

        List<ProductListDto> content = productList.stream().map(this::mapToProductListDto).collect(Collectors.toList());

        ProductResponse2Dto productResponseDto = new ProductResponse2Dto();
        productResponseDto.setContent(content);
        productResponseDto.setPageNo(products.getNumber());
        productResponseDto.setPageSize(products.getSize());
        productResponseDto.setTotalElements(products.getTotalElements());
        productResponseDto.setTotalPages(products.getTotalPages());
        productResponseDto.setLast(products.isLast());
        return productResponseDto;
    }

//    @Override
//    public List<Product> searchProducts(String query) {
//        List<Product> products = productRepository.searchProductsSQL(query);
//        return products;
//    }

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
        productListDto.setImages(product.getImages().stream().map(this::mapToProductImageDto)
                .collect(Collectors.toList()));
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
        productByProductType.setImages(product.getImages().stream().map(this::mapToProductImageDto)
                .collect(Collectors.toList()));
        productByProductType.setPrice(product.getPrice());
        productByProductType.setDescription(product.getDescription());
        return productByProductType;
    }

    private ProductDto mapToProductDto (Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setStock(product.getStock());
        productDto.setImages(product.getImages().stream().map(this::mapToProductImageDto)
                .collect(Collectors.toList()));
        productDto.setBrand(product.getBrand().getName());
        productDto.setProductType(product.getProductType().getName());
        productDto.setCategory(product.getCategory().getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        return productDto;
    }

    private ProductImageDto mapToProductImageDto (ProductImage productImage){
        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setImage(productImage.getImage());
        return productImageDto;
    }

}
