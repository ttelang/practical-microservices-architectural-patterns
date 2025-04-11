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
package com.acme.ecom.cart.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.ecom.cart.dto.CustomerCartDTO;

/**
 * @author <a href="mailto:biniljava<[@>.]yahoo.co.in">Binildas C. A.</a>
 */
@RestController
public class CustomerCartApplicationController {

	 private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCartApplicationController.class);

	 @RequestMapping(path="/",method=RequestMethod.GET)
     public HttpEntity<ResourceSupport> getCustomerCartInfo(){

		 LOGGER.info("Start");
		 ResourceSupport resourceSupport=new ResourceSupport();
		 resourceSupport.add(linkTo(methodOn(CustomerCartApplicationController.class).getCustomerCartInfo()).withSelfRel());
		 resourceSupport.add(linkTo(methodOn(CustomerCartController.class).updateCustomerCartToCache(new CustomerCartDTO())).withRel("updateCustomerCart"));
		 Link link = linkTo(CustomerCartController.class).slash("/{userId}").withRel("getCustomerCart");
		 resourceSupport.add(new CustomLink(link));
		 ResponseEntity<ResourceSupport> responseEntity = new ResponseEntity<ResourceSupport>(resourceSupport, HttpStatus.OK);
		 LOGGER.info("Ending");
		 return responseEntity;
	  }
}
