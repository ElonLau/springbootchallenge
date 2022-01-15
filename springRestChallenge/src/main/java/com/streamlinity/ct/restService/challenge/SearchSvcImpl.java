package com.streamlinity.ct.restService.challenge;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamlinity.ct.model.Item;

/*
 * Provide your implementation of the SearchSvcImpl here.
 * Also annotate your methods with Rest end point wrappers as required in the problem statement
 *
 * You can create any auxiliary classes or interfaces in this package if you need them.
 *
 * Do NOT add annotations as a Bean or Component or Service.   This is being handled in the custom Config class
 * PriceAdjustConfiguration
 */


public class SearchSvcImpl implements SearchSvcInterface {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchSvcImpl.class);
	
	private List<Item> itemsList;
	
	private ObjectMapper Objectmapper;
	
	@PostConstruct
	public void init() {
		this.Objectmapper = new ObjectMapper();
	}
    @Override
    public void init(String itemPriceJsonFileName) {
      try {
		this.itemsList =  Objectmapper.readValue(itemPriceJsonFileName, new TypeReference<List<Item>>() {});
        } catch (IOException exc) {
        logger.error("Cannot convert json", exc);
        }
    }

    @Override
    public void init(File itemPriceJsonFile) {

    	try {
    		this.itemsList =  Objectmapper.readValue(itemPriceJsonFile, new TypeReference<List<Item>>() {});
    	  } catch (IOException exc) {
    		 logger.error("Cannot convert json", exc);
    	  }
    }

    @Override
    public List<Item> getItems() {
    	
        return itemsList;
    }

    @Override
    public List<Item> getItems(String category) {
    	
        return itemsList.stream().filter(item->item.getCategory_short_name().equals(category)).collect(Collectors.toList());
    }

    @Override
    public List<Item> getItem(String itemShortName) {
    	
        return itemsList.stream().filter(item->item.getShort_name().equals(itemShortName)).collect(Collectors.toList());
    }
}
