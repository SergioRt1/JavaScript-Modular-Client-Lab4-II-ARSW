/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.ProductType;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/orders")
public class OrdersAPIController {

    @Autowired
    RestaurantOrderServices ros;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrderHandler() {
        try {
            return new ResponseEntity<>(ros.getOrders(), HttpStatus.ACCEPTED);
        } catch (OrderServicesException ex) {
            Logger.getLogger(OrderServicesException.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/{idtable}")
    public ResponseEntity<?> getOrderHandler(@PathVariable int idtable) {
        Order order = ros.getTableOrder(idtable);
        HttpStatus status = HttpStatus.ACCEPTED;
        if (order == null) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(order, status);

    }
    //Example code Linux
    //curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://localhost:8080/orders -d '{"orderAmountsMap":{"FISH":1,"POTATOES":5},"tableNumber":5}'
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postOrderHandler(@RequestBody Order order) {
        try {
            ros.addNewOrderToTable(order);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (OrderServicesException ex) {
            Logger.getLogger(OrderServicesException.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping("/{idtable}/total")
    public ResponseEntity<?> getTotalHandler(@PathVariable int idtable) {
        Integer total = null;
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            total = ros.calculateTableBill(idtable);
        } catch (OrderServicesException ex) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(total, status);

    }
    
    @RequestMapping("/products")
    public ResponseEntity<?> getProducts(){
        HttpStatus status = HttpStatus.ACCEPTED;
        Collection<RestaurantProduct> entry = null;
        try {
            entry = ros.getProducts();
        } catch (OrderServicesException ex) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(entry,HttpStatus.ACCEPTED);
    }
    //Example code Linux: 
    //curl -i -X PUT -HContent-Type:application/json -HAccept:application/json http://localhost:8080/orders/1 -d '{"price":20,"name":"MILK","type":"DRINK"}' 
    @PutMapping("/{idTable}")
    public ResponseEntity<?> putProductHandler(@RequestBody RestaurantProduct product, @PathVariable int idTable) {
        Order order = ros.getTableOrder(idTable);
        order.addDish(product.getName(), 1);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    
    @DeleteMapping("/{idTable}")
    public ResponseEntity<?> deleteProductHandler(@PathVariable int idTable) {
        HttpStatus status;
        try {
            ros.releaseTable(idTable);
            status = HttpStatus.ACCEPTED;
        } catch (OrderServicesException ex) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);

    }
    

}
