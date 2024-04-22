package com.astonrest.controller.mapper;

import com.astonrest.controller.dto.SaleDto;
import com.astonrest.model.Sale;

public interface SaleMapper {
    Sale map(SaleDto saleDto);

    SaleDto map(Sale sale);

}
