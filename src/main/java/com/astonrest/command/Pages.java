package com.astonrest.command;

public enum Pages {
    USER_PAGE("user_page.jsp"),
    INDEX_PAGE("index.jsp"),
    PURCHASES_PAGE("purchases_page.jsp"),
    UPDATE_PROFILE_PAGE("update_profile_page.jsp"),
    ADD_NEW_PRODUCT("add_new_product_page.jsp"),
    ADMIN_PAGE("admin_page.jsp"),
    UPDATE_PRODUCT_PAGE("update_product_page.jsp"),
    SHOW_PRODUCT_SALES("show_product_sales.jsp"),
    SHOW_ALL_PRODUCTS("show_all_products.jsp"),
    DELETE_PRODUCT("delete_product.jsp"),
    BUY_PRODUCT("buy_product.jsp"),
    SALES_PAGE("sales_page.jsp"),
    FIND_SALES_BY_DATE("find_sales_by_date_page.jsp"),
    UPDATE_SALE("update_sale.jsp");
    private String value;

    public String getValue() {
        return value;
    }

    Pages(java.lang.String value) {
        this.value = value;
    }
}
