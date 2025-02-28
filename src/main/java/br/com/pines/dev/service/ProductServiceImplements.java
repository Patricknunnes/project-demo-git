package br.com.pines.dev.service;

import br.com.pines.dev.dto.ProductDto;
import br.com.pines.dev.exception.IdNotFoundException;
import br.com.pines.dev.model.Product;
import br.com.pines.dev.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImplements implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> get(Long id, String name, String description) {
        Page<Product> products = productRepository.findByFilters(id, name, description);
        return products;
    }

    @Override
    public Product getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(IdNotFoundException::new);
        return product;
    }

    @Override
    public Product save(ProductDto productDto) {

        return productRepository.save(Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .registrationDate(LocalDate.now())
                .uriImg(productDto.getUriImg())
                .build());
    }

    @Override
    public void delete(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.deleteById(id);
        } else {
            throw new IdNotFoundException();
        }
    }

    @Override
    public Product update(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(IdNotFoundException::new);

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setUriImg(productDto.getUriImg());

        return productRepository.save(product);
    }
}
