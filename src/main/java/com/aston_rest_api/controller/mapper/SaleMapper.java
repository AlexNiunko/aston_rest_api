package com.aston_rest_api.controller.mapper;

import com.aston_rest_api.controller.dto.SaleDto;
import com.aston_rest_api.model.Sale;

public interface SaleMapper {
    Sale map(SaleDto saleDto);
    SaleDto map(Sale sale);

}
