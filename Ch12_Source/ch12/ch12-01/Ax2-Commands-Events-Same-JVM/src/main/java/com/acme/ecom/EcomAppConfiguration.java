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
package com.acme.ecom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.repository.GenericJpaRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acme.ecom.order.model.Order;
import com.acme.ecom.product.model.Product;

/**
 * @author <a href="mailto:biniljava<[@>.]yahoo.co.in">Binildas C. A.</a>
 */
@Configuration
public class EcomAppConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public SimpleCommandBus commandBus() {

        return new SimpleCommandBus();
    }

    @Bean
    public SimpleEventBus eventBus() {

        return new SimpleEventBus();
    }

    @Bean
    AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {

        AnnotationCommandHandlerBeanPostProcessor handler = new AnnotationCommandHandlerBeanPostProcessor();
        handler.setCommandBus(commandBus());
        return handler;
    }

    @Bean
    AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {

        AnnotationEventListenerBeanPostProcessor listener = new AnnotationEventListenerBeanPostProcessor();
        listener.setEventBus(eventBus());
        return listener;
    }

    @Bean
    public DefaultCommandGateway commandGateway() {

        return new DefaultCommandGateway(commandBus());
    }

    @Bean
    @Qualifier("productRepository")
    public GenericJpaRepository<Product> productJpaRepository() {

        SimpleEntityManagerProvider entityManagerProvider = new SimpleEntityManagerProvider(entityManager);
        GenericJpaRepository<Product> genericJpaRepository = new GenericJpaRepository(entityManagerProvider, Product.class);
        genericJpaRepository.setEventBus(eventBus());
        return genericJpaRepository;
    }

    @Bean
    @Qualifier("orderRepository")
    public GenericJpaRepository<Order> orderJpaRepository() {

        SimpleEntityManagerProvider entityManagerProvider = new SimpleEntityManagerProvider(entityManager);
        GenericJpaRepository<Order> genericJpaRepository = new GenericJpaRepository(entityManagerProvider, Order.class);
        genericJpaRepository.setEventBus(eventBus());
        return genericJpaRepository;
    }
}
