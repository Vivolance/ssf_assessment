package Batch02.SSF.Assessment.services;

import java.util.List;
import java.util.UUID;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import Batch02.SSF.Assessment.models.ShoppingCart;
import Batch02.SSF.Assessment.models.Quotation;

@Service
public class QuotationService {

    public Quotation getQuotations(List<String> items) throws Exception {

        return getQuotations(items);
    }
    
}
