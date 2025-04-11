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
package com.acme.ecom.order.commandhandler;

import java.util.Random;

import org.axonframework.commandhandling.CommandHandler;
//import org.axonframework.commandhandling.model.Repository;
import org.axonframework.modelling.command.Repository;
//import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.modelling.command.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.acme.ecom.order.api.command.NewOrderCommand;
import com.acme.ecom.order.model.Order;
import com.acme.ecom.order.model.OrderStatusEnum;
import com.acme.ecom.product.model.Product;

/**
 * @author <a href="mailto:biniljava<[@>.]yahoo.co.in">Binildas C. A.</a>
 */
@Component
public class OrderCommandHandler {

    @Autowired
    @Qualifier("orderRepository")
    //private GenericJpaRepository<Order> orderRepository;
    private Repository<Order> orderRepository;

    @Autowired
    @Qualifier("productRepository")
    //private GenericJpaRepository<Product> productRepository;
    private Repository<Product> productRepository;


    @CommandHandler
    public void handle(NewOrderCommand newOrderCommand) throws Exception{

    	//AnnotatedAggregate<Product>  productAggregate = productRepository.load(newOrderCommand.getProductId().toString()).getWrappedAggregate();
    	Aggregate<Product>  productAggregate = productRepository.load(newOrderCommand.getProductId().toString());
    	//Product product = productAggregate.getAggregateRoot();
    	//product.depreciateStock(newOrderCommand.getNumber());

		Product product =  productAggregate.invoke(
				(Product p)->{p.depreciateStock(newOrderCommand.getNumber()); return p;}
			);

    	orderRepository.newInstance(()->new Order(new Random().nextInt(),
    		newOrderCommand.getPrice(), newOrderCommand.getNumber(), OrderStatusEnum.NEW, product));
    }

}
