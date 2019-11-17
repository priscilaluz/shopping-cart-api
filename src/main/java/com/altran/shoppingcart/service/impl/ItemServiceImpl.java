package com.altran.shoppingcart.service.impl;

import com.altran.shoppingcart.document.Item;
import com.altran.shoppingcart.dto.ItemDTO;
import com.altran.shoppingcart.error.BusinessException;
import com.altran.shoppingcart.service.ItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altran.shoppingcart.repository.ItemRepository;
import com.altran.shoppingcart.util.Constant;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

/**
 *
 * @author priscilaluz
 */
@Service
public class ItemServiceImpl implements ItemService {
    

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        Item item = modelMapper.map(itemDTO, Item.class);
        if (item.getName() == null) {
            throw new BusinessException(Constant.ITEM_NAME_REQUIRED);
        }
        if (item.getValue() == null) {
            throw new BusinessException(Constant.ITEM_VALUE_REQUIRED);
        }
        if (item.getId() == null && repository.findByName(item.getName()).isPresent()) {
            throw new BusinessException(Constant.ITEM_ALREADY_EXISTS);
        }
        if (item.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(Constant.ITEM_VALUE_CANNOT_BE_LESS_EQUAL_ZERO);
        }
        item = repository.save(item);
        return modelMapper.map(item, ItemDTO.class);
    }

    @Override
    public ItemDTO findById(String id) {
        Optional<Item> result = repository.findById(id);
        if (!result.isPresent()) {
            throw new BusinessException(Constant.ITEM_NOT_FOUND);
        }
        return modelMapper.map(result.get(), ItemDTO.class);
    }

    @Override
    public List<ItemDTO> findAll() {
        Iterable<Item> results = repository.findAll();
        List<Item> items = new ArrayList<>();
        results.forEach(items::add);
        return items.stream()
          .map(item -> modelMapper.map(item, ItemDTO.class))
          .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        Optional<Item> result = repository.findById(id);
        if (result.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new BusinessException(Constant.ITEM_NOT_FOUND);
        }
    }
}
