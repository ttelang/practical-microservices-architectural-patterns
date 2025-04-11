/*
 * Copyright (c) 2019/2020 Binildas A Christudas, Apress Media, LLC. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of the author, publisher or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. AUTHOR, PUBLISHER AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL THE AUTHOR,
 * PUBLISHER OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA,
 * OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF THE AUTHOR, PUBLISHER HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */
package com.acme.ecom.product.controller;

import com.acme.ecom.product.model.Product;
import com.acme.ecom.product.api.ProductService;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:biniljava<[@>.]yahoo.co.in">Binildas C. A.</a>
 */
@CrossOrigin
@RestController
public class ProductRestController implements ProductService{

	@Autowired
	RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);
	private static String PRODUCT_SERVICE_URL = "http://product-service/products";

	@Autowired
	public ProductRestController(RestTemplate restTemplate){
		this.restTemplate = restTemplate;
	}


    //------------------- Retreive all Products --------------------------------------------------------
    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Resources<Resource<Product>>> getAllProducts() {

        LOGGER.info("Start");
		ParameterizedTypeReference<PagedResources<Product>> responseTypeRef = new ParameterizedTypeReference<PagedResources<Product>>() {

			};
		ResponseEntity<PagedResources<Product>> responseEntity = restTemplate.exchange(PRODUCT_SERVICE_URL, HttpMethod.GET,
				(HttpEntity<Product>) null, responseTypeRef);
		PagedResources<Product> resources = responseEntity.getBody();
		Collection<Product> products = resources.getContent();
		List<Product> productList = new ArrayList<Product>(products);

        Link links[]={linkTo(methodOn(ProductRestController.class).getAllProducts()).withSelfRel(),linkTo(methodOn(ProductRestController.class).getAllProducts()).withRel("getAllProducts")};
        if(products.isEmpty()){
        	LOGGER.debug("products.isEmpty()");
            return new ResponseEntity<Resources<Resource<Product>>>(HttpStatus.NOT_FOUND);
        }
        List<Resource<Product>> list=new ArrayList<Resource<Product>> ();
        for(Product product:products){
        	list.add(new Resource<Product>(product, linkTo(methodOn(ProductRestController.class).getProduct(product.getId())).withSelfRel()));
        }
        Resources<Resource<Product>> productResponse = new Resources<Resource<Product>>(list, links) ;
        LOGGER.info("Ending...");
        return new ResponseEntity<Resources<Resource<Product>>>(productResponse, HttpStatus.OK);
    }


    //------------------- Retreive a Product --------------------------------------------------------
    @RequestMapping(value = "/productsweb/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<Product>> getProduct(@PathVariable("id") String id) {

        LOGGER.info("Start");
		Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/" + id, Product.class);
        if (product == null) {
            LOGGER.debug("Product with id {} not found", id);
            return new ResponseEntity<Resource<Product>>(HttpStatus.NOT_FOUND);
        }
        Resource<Product> productResponse = new Resource<Product>(product, linkTo(methodOn(ProductRestController.class).getProduct(product.getId())).withSelfRel());
        LOGGER.info("Ending...");
        return new ResponseEntity<Resource<Product>>(productResponse, HttpStatus.OK);

    }

}
