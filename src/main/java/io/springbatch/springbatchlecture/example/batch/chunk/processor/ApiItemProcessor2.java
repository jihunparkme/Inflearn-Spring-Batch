package io.springbatch.springbatchlecture.example.batch.chunk.processor;

import io.springbatch.springbatchlecture.example.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.example.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ApiItemProcessor2 implements ItemProcessor<ProductVO, ApiRequestVO> {

    @Override
    public ApiRequestVO process(ProductVO productVO) throws Exception {

        return ApiRequestVO.builder()
                .id(productVO.getId())
                .productVO(productVO)
                .build();
    }
}