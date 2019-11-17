package com.altran.shoppingcart.service.impl;

import com.altran.shoppingcart.document.ShoppingCart;
import com.altran.shoppingcart.document.User;
import com.altran.shoppingcart.dto.UserDTO;
import com.altran.shoppingcart.error.BusinessException;
import com.altran.shoppingcart.repository.ShoppingCartRepository;
import com.altran.shoppingcart.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altran.shoppingcart.repository.UserRepository;
import com.altran.shoppingcart.util.Constant;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

/**
 *
 * @author priscilaluz
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO save(UserDTO userDTO) {
        validateUser(userDTO.getName(), userDTO.getEmail());
        User user = modelMapper.map(userDTO, User.class);
        if (user.getId() == null) {
            Optional<User> result = repository.findByEmail(user.getEmail());
            if (result.isPresent()) {
                throw new BusinessException(Constant.USER_ALREADY_EXISTS);
            }
            ShoppingCart shoppingCart = shoppingCartRepository.save(new ShoppingCart(null, BigDecimal.ZERO, new ArrayList<>()));
            user.setShoppingcart(shoppingCart);
        }
        return modelMapper.map(repository.save(user), UserDTO.class);
    }
    
    private void validateUser(String name, String email) {
        if (name == null) {
            throw new BusinessException(Constant.USER_NAME_REQUIRED);
        }
        if (email == null) {
            throw new BusinessException(Constant.USER_EMAIL_REQUIRED);
        }
    }

    @Override
    public UserDTO findById(String id) {
        Optional<User> result = repository.findById(id);
        if (!result.isPresent()) {
            throw new BusinessException(Constant.USER_NOT_FOUND);
        }
        return modelMapper.map(result.get(), UserDTO.class);
    }
    
    @Override
    public UserDTO findByNameAndEmail(String name, String email) {
        validateUser(name, email);
        Optional<User> result = repository.findByNameAndEmail(name, email);
        if (!result.isPresent()) {
            throw new BusinessException(Constant.USER_NOT_FOUND);
        }
        return modelMapper.map(result.get(), UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        Iterable<User> results = repository.findAll();
        List<User> users = new ArrayList<>();
        results.forEach(users::add);
        return users.stream()
          .map(user -> modelMapper.map(user, UserDTO.class))
          .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        Optional<User> result = repository.findById(id);
        if (result.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new BusinessException(Constant.USER_NOT_FOUND);
        }
    }
}
