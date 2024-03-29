package com.aston_rest_api.model;

import java.time.LocalDateTime;

public class Sale extends AbstractEntity {
    private long buyerId;
    private long productId;
    private LocalDateTime dateOfSale;
}
