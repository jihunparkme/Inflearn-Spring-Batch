package io.springbatch.springbatchlecture.example.batch.chunk.processor;

import io.springbatch.springbatchlecture.example.batch.domain.Product;
import io.springbatch.springbatchlecture.example.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;

import org.modelmapper.ModelMapper;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {

    @Override
    public Product process(ProductVO item) throws Exception {

        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(item, Product.class);


        return product;
    }
}
