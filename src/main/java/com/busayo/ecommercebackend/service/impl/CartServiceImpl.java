package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.cart.CartDto;
import com.busayo.ecommercebackend.dto.cart.MyCartDto;
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
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean addProductToCart(CartDto cartDto, Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        Cart cart = new Cart();

        cart.setUser(user);
        Product product = productRepository.findById(cartDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(id));
        cart.setProduct(product);
        cart.setStatus("Active");
        cart.setQuantity(cartDto.getQuantity());

        return true;
    }

    private List<Cart> cartItems = new ArrayList<>();

    @Override
    public List<MyCartDto> getMyCart(Long id) {
        cartItems.clear();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        List<Cart> allCartItems = cartRepository.findAll();
        for (Cart cartItem : allCartItems) {
            if ((allCartItems != null) && (cartItem.getUser().equals(user))) {
                cartItems.add(cartItem);
            }
        }
        return cartItems.stream().map((cart) -> mapToMyCartDto(cart))
                .collect(Collectors.toList());
    }

    private MyCartDto mapToMyCartDto (Cart cart) {

        MyCartDto myCartDto = new MyCartDto();
        myCartDto.setId(cart.getId());
        myCartDto.setProduct(cart.getProduct());

        return myCartDto;
    }
}

