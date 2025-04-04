package com.ujjaval.ecommerce.selleraccountservice.service;


import com.ujjaval.ecommerce.selleraccountservice.entity.nosql.SellerBulkInfo;
import com.ujjaval.ecommerce.selleraccountservice.entity.sql.SellerInfo;

public interface SellerAccountDataService {

    SellerInfo findSellerById(Integer sellerId);

    void save();

    void saveInMongo();

    SellerBulkInfo findMongoAddressById();
}

