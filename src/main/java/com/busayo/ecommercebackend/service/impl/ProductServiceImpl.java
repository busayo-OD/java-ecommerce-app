package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.product.*;
import com.busayo.ecommercebackend.exception.*;
import com.busayo.ecommercebackend.model.*;
import com.busayo.ecommercebackend.repository.*;
import com.busayo.ecommercebackend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository,
                              CategoryRepository categoryRepository,
                              ProductImageRepository productImageRepository,
                              OrderDetailsRepository orderDetailsRepository
    ) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productImageRepository = productImageRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public boolean addProduct(ProductDto productDto) {
        boolean existingName = productRepository.existsByName(productDto.getName().toUpperCase());

        if (existingName) {
            throw new ProductDuplicateException();
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setColour(productDto.getColour());
        product.setStatus("Active");

        product.setDescription(productDto.getDescription());

        Long categoryId = productDto.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.setCategory(category);

        Long brandId = productDto.getBrandId();
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        product.setBrand(brand);

        productRepository.save(product);
        return  true;
    }

    @Override
    public  ProductInfoDto getProduct (Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return mapToProductInfoDto(product);
    }

    @Override
    public ProductResponse2Dto getProductsWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByStatus("Active", pageable);

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
    public ProductResponse3Dto getProductsByCategoryIdWithPaginationAndSorting(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByCategoryIdAndStatus(categoryId, "Active", pageable);

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
    public boolean updateProduct(ProductDto productDto, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (productDto.getName() != null && !productDto.getName().isEmpty()) {
            product.setName(productDto.getName());
        }

        if (productDto.getStock() != 0) {
            product.setStock(productDto.getStock());
        }

        if (productDto.getPrice() != 0.0) {
            product.setPrice(productDto.getPrice());
        }

        if (productDto.getDescription() != null && !productDto.getDescription().isEmpty()) {
            product.setDescription(productDto.getDescription());
        }

        Long categoryId = productDto.getCategoryId();
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException(categoryId));
            product.setCategory(category);
        }

        Long brandId = productDto.getBrandId();
        if (brandId != null) {
            Brand brand = brandRepository.findById(brandId)
                    .orElseThrow(() -> new BrandNotFoundException(brandId));
            product.setBrand(brand);
        }

        productRepository.save(product);
        return true;
    }

    @Override
    public boolean uploadProductImages(Long productId, ProductImagesDto productImages){
        productImages.getImages().forEach(image -> {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));
            ProductImage productImage = new ProductImage();
            productImage.setImage(image.getImage());
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        });
        return true;
    }

    @Override
    public boolean deleteProduct(Long productId) {
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
    public ProductResponse2Dto productSearchByCategoryAndBrand(Long categoryId, Long brandId, int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByCategoryIdAndBrandIdAndStatus(categoryId, brandId, "Active", pageable);

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
    public ProductResponse2Dto filterProductsByCategoryAndBrand(Long categoryId, Long brandId, int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.filterProductsByCategoryAndBrand(categoryId, brandId, pageable);

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
    public ProductResponse2Dto customerPageGetProductByCategory(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Product> products = productRepository.findByCategoryIdAndStatus(categoryId, "Active", pageable);

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

    private ProductListDto mapToProductListDto(Product product){

        ProductListDto productListDto = new ProductListDto();
        productListDto.setId(product.getId());
        productListDto.setName(product.getName());

        Long brandId = product.getBrand().getId();
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        productListDto.setBrand(brand.getName());

        Long categoryId = product.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        productListDto.setCategory(category.getName());

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
        return productByCategory;
    }

    private ProductInfoDto mapToProductInfoDto (Product product) {
        ProductInfoDto productInfoDto = new ProductInfoDto();
        productInfoDto.setId(product.getId());
        productInfoDto.setName(product.getName());
        productInfoDto.setStock(product.getStock());
        productInfoDto.setImages(product.getImages().stream().map(this::mapToProductImageDto)
                .collect(Collectors.toList()));
        productInfoDto.setBrand(product.getBrand().getName());
        productInfoDto.setCategory(product.getCategory().getName());
        productInfoDto.setPrice(product.getPrice());
        productInfoDto.setDescription(product.getDescription());
        return productInfoDto;
    }

    private ProductImageDto mapToProductImageDto (ProductImage productImage){
        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setImage(productImage.getImage());
        return productImageDto;
    }

}
