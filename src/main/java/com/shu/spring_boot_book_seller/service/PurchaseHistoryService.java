package com.shu.spring_boot_book_seller.service;

import com.shu.spring_boot_book_seller.model.PurchaseHistory;
import com.shu.spring_boot_book_seller.repository.IPurchaseHistoryRepository;
import com.shu.spring_boot_book_seller.repository.projection.IPurchaseItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseHistoryService implements IPurchaseHistoryService{

    @Autowired
    private IPurchaseHistoryRepository iPurchaseHistoryRepository;

    @Override
    @Transactional
    public PurchaseHistory savePurchaseHistory(PurchaseHistory purchaseHistory){
        purchaseHistory.setPurchaseTime(LocalDateTime.now());
        return iPurchaseHistoryRepository.save(purchaseHistory);
    }

    @Override
    public List<IPurchaseItem> findPurchasedItemsOfUser(Long userId){

        return iPurchaseHistoryRepository.findAllPurchaseOfUser(userId);
    }
}
