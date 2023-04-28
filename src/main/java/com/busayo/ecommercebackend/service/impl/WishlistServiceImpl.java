package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.wishlist.MyWishlistDto;
import com.busayo.ecommercebackend.dto.wishlist.WishlistDto;
import com.busayo.ecommercebackend.dto.wishlist.WishlistInfoDto;
import com.busayo.ecommercebackend.dto.wishlist.WishlistInfoResponseDto;
import com.busayo.ecommercebackend.exception.ProductNotFoundException;
import com.busayo.ecommercebackend.exception.UserNotFoundException;
import com.busayo.ecommercebackend.model.Product;
import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.model.Wishlist;
import com.busayo.ecommercebackend.repository.ProductRepository;
import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.repository.WishlistRepository;
import com.busayo.ecommercebackend.service.WishlistService;
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
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Boolean addProductToWishlist(WishlistDto wishlistDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        Wishlist wishlist = new Wishlist();

        Long productId = wishlistDto.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        wishlist.setProduct(product);
        wishlist.setUser(user);
        wishlistRepository.save(wishlist);
        return true;
    }

    private List<Wishlist> wishlists = new ArrayList<>();
    @Override
    public List<MyWishlistDto> getMyWishList(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        wishlists.clear();
        List<Wishlist> allWishlists = wishlistRepository.findAll();
        for (Wishlist wishlist : allWishlists) {
            if ((allWishlists != null) && (wishlist.getUser().equals(user))) {
                wishlists.add(wishlist);
            }
        }
        return wishlists.stream().map((wishlist) -> mapToMyWishlistDto(wishlist))
                .collect(Collectors.toList());
    }

    @Override
    public WishlistInfoResponseDto getAllWishlist( int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Wishlist> wishlists = wishlistRepository.findAll(pageable);

        List<Wishlist> wishlistList = wishlists.getContent();

        List<WishlistInfoDto> content = wishlistList.stream().map(wishlist -> mapToWishlistInfoDto(wishlist)).collect(Collectors.toList());

        WishlistInfoResponseDto wishlistInfoResponse = new WishlistInfoResponseDto();
        wishlistInfoResponse.setContent(content);
        wishlistInfoResponse.setPageNo(wishlists.getNumber());
        wishlistInfoResponse.setPageSize(wishlists.getSize());
        wishlistInfoResponse.setTotalElements(wishlists.getTotalElements());
        wishlistInfoResponse.setTotalPages(wishlists.getTotalPages());
        wishlistInfoResponse.setLast(wishlists.isLast());
        return wishlistInfoResponse;

    }


    private MyWishlistDto mapToMyWishlistDto (Wishlist wishlist) {

        MyWishlistDto myWishlistDto = new MyWishlistDto();
        myWishlistDto.setId(wishlist.getId());
        myWishlistDto.setProduct(wishlist.getProduct());

        return myWishlistDto;
    }

    private WishlistInfoDto mapToWishlistInfoDto (Wishlist wishlist) {

        WishlistInfoDto wishlistInfoDto = new WishlistInfoDto();
        wishlistInfoDto.setId(wishlist.getId());
        wishlistInfoDto.setProduct(wishlist.getProduct());
        wishlistInfoDto.setUsername(wishlist.getUser().getUsername());

        return wishlistInfoDto;
    }
}
