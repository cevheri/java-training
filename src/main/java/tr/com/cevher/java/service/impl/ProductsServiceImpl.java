package tr.com.cevher.java.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.cevher.java.domain.Products;
import tr.com.cevher.java.repository.ProductsRepository;
import tr.com.cevher.java.service.ProductsService;
import tr.com.cevher.java.service.dto.ProductsDTO;
import tr.com.cevher.java.service.mapper.ProductsMapper;

/**
 * Service Implementation for managing {@link Products}.
 */
@Service
@Transactional
public class ProductsServiceImpl implements ProductsService {

    private final Logger log = LoggerFactory.getLogger(ProductsServiceImpl.class);

    private final ProductsRepository productsRepository;

    private final ProductsMapper productsMapper;

    public ProductsServiceImpl(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    @Override
    public ProductsDTO save(ProductsDTO productsDTO) {
        log.debug("Request to save Products : {}", productsDTO);
        Products products = productsMapper.toEntity(productsDTO);
        products = productsRepository.save(products);
        return productsMapper.toDto(products);
    }

    @Override
    public Optional<ProductsDTO> partialUpdate(ProductsDTO productsDTO) {
        log.debug("Request to partially update Products : {}", productsDTO);

        return productsRepository
            .findById(productsDTO.getId())
            .map(existingProducts -> {
                productsMapper.partialUpdate(existingProducts, productsDTO);

                return existingProducts;
            })
            .map(productsRepository::save)
            .map(productsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductsDTO> findAll() {
        log.debug("Request to get all Products");
        return productsRepository.findAll().stream().map(productsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductsDTO> findOne(Long id) {
        log.debug("Request to get Products : {}", id);
        return productsRepository.findById(id).map(productsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Products : {}", id);
        productsRepository.deleteById(id);
    }
}
