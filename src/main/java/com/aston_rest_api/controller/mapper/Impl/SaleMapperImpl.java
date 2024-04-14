package com.aston_rest_api.controller.mapper.Impl;

import com.aston_rest_api.controller.dto.SaleDto;
import com.aston_rest_api.controller.mapper.SaleMapper;
import com.aston_rest_api.model.Sale;

import java.time.LocalDate;

public class SaleMapperImpl implements SaleMapper {
    private static SaleMapperImpl mapper=new SaleMapperImpl();
    private SaleMapperImpl() {
    }
    public static SaleMapperImpl getMapper(){
        return mapper;
    }

    @Override
    public Sale map(SaleDto saleDto) {
        return new Sale.SaleBuilder(saleDto.getSaleId())
                .setProductId(saleDto.getProductId())
                .setBuyerId(saleDto.getBuyerId())
                .setDateOfSale(LocalDate.parse(saleDto.getDateOfSale()))
                .setAmountSale(Integer.parseInt(saleDto.getAmountOfSale()))
                .build();
    }

    @Override
    public SaleDto map(Sale sale) {
        return new SaleDto.SaleDtoBuilder(sale.getId())
                .setProductId(sale.getProductId())
                .setBuyerId(sale.getBuyerId())
                .setAmountOfSale(String.valueOf(sale.getAmountSale()))
                .setDateOfSale(sale.getDateOfSale().toString())
                .build();
    }
}
