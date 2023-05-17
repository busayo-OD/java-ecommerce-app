package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.cart.AddToCartDto;
import com.busayo.ecommercebackend.dto.cart.CartDto;
import com.busayo.ecommercebackend.dto.cart.CartItemDto;
import com.busayo.ecommercebackend.exception.CartItemNotFoundException;
import com.busayo.ecommercebackend.exception.ProductNotFoundException;
import com.busayo.ecommercebackend.exception.UserNotFoundException;
import com.busayo.ecommercebackend.model.Cart;
import com.busayo.ecommercebackend.model.Product;
import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.repository.CartRepository;
import com.busayo.ecommercebackend.repository.ProductRepository;
import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addProductToCart(AddToCartDto addToCartDto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Cart cart = new Cart();

        cart.setUser(user);
        Long productId = addToCartDto.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        cart.setProduct(product);
        cart.setQuantity(addToCartDto.getQuantity());
        cartRepository.save(cart);
    }

    public void updateCartItem(AddToCartDto addToCartDto, Long userId){
        Long cartId = addToCartDto.getId();
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartItemNotFoundException(cartId));

//        Long productId = addToCartDto.getProductId();
//        Product product = productRepository.findById(addToCartDto.getProductId())
//                .orElseThrow(() -> new ProductNotFoundException(productId));
        cart.setQuantity(addToCartDto.getQuantity());
        cartRepository.save(cart);
    }

    @Override
    public CartDto getMyCart(Long userId) {

        List<Cart> cartList = cartRepository.findByUserId(userId);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double total = 0;
        for (CartItemDto cartItemDto :cartItems){
            total += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,total);
    }

    public static CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }

    @Override
    public void deleteUserCartItems(Long userId) {

        cartRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteCartItem(Long id, Long userId) throws CartItemNotFoundException {
        if (!cartRepository.existsById(id))
            throw new CartItemNotFoundException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);
    }
}
