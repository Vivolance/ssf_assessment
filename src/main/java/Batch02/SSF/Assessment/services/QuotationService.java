package Batch02.SSF.Assessment.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import Batch02.SSF.Assessment.models.Quotation;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class QuotationService {
    private static final String QUOTATION_SERVICE_URL = "https://quotation.chuklee.com/quotation";

    public static Quotation createQuotation(String json) throws IOException {
        Quotation q = new Quotation();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())){
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            q.setQuoteId(o.getString("quoteId"));
            JsonObject mainObj = o.getJsonObject("quotations");
            HashMap<String, Float> quotations = new HashMap<>();
            for (String key : mainObj.keySet()) {
                quotations.put(key, (float) mainObj.getJsonNumber(key).doubleValue());
            }
            q.setQuotations(quotations);
        }
        return q;
    }

    public Quotation getQuotations(List<String> items) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(new ArrayList(Collections.singletonList(MediaType.APPLICATION_JSON)));
        JSONArray jsonArray = new JSONArray(items);
        HttpEntity<String> entity = new HttpEntity<>(jsonArray.toString(), headers);
        String quotationUrl = UriComponentsBuilder
                .fromUriString(QUOTATION_SERVICE_URL)
                .toUriString();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(quotationUrl, HttpMethod.POST, entity, String.class);
        if (resp.getStatusCode() == HttpStatus.OK) {
            String result = resp.getBody();
            return QuotationService.createQuotation(result);
        } else {
            JsonObject errorObject = Json.createReader(new ByteArrayInputStream(resp.getBody().getBytes()))
                    .readObject();
            String errorMessage = errorObject.getString("error");
            throw new Exception(errorMessage);
        }
    }
}