package edu.eci.arsw.myrestaurant.test;

import edu.eci.arsw.myrestaurant.beans.BillCalculator;
import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * 
 * @author sergio
 * 
 * Clases de equivalencia
 * 
 * CE1:
 *      Entrada: Se ingresa una orden de HOTDOG * 1, PIZZA * 1 y COKE *1
 *      Tipo: Comparación
 *      Valida: Se cobra el impuesto adecuado para la bebida del 0.16 y para las comidas del 0.19 (3000*1.19)+(10000*1.19)+(1300*1.16)=16978
 *      invalida: un valor diferente del esperado.
 * 
 * CE2:
 *      Entrada: Se ingresa una orden de PIZZA * 5 
 *      Tipo: Comparación
 *      Valida: Se cobra para las 5 pizzas el impuesto del 0.19 c/u (10000*1.19)*5= 59500
 *      Invalida: Un valor diferente del esperado.
 * 
 * CE3
 *      Entrada: Se ingresa una orden de BEER * 5
 *      Tipo: Comparación
 *      Valida: se cobra el impuesto adecuado para las 5 bebidas 0.16 c/u (2500*1.16)*5= 14500
 *      Invalida: Un valor diferente del esperado.
 */

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ApplicationServicesTests {

    @Autowired
    RestaurantOrderServicesStub ros;

    
    @Test
    public void CE1() throws OrderServicesException{
        int numOrder = 5;
        Order o = new Order(numOrder);
        o.addDish("HOTDOG", 1);
        o.addDish("PIZZA", 1);
        o.addDish("COKE", 1);
        ros.addNewOrderToTable(o);
        assertEquals(16978, ros.calculateTableBill(numOrder));
    }
    
    @Test
    public void CE2() throws OrderServicesException{
        int numOrder = 10;
        Order o = new Order(numOrder);
        o.addDish("PIZZA", 5);
        ros.addNewOrderToTable(o);
        assertEquals(59500, ros.calculateTableBill(numOrder));
    }
    
    @Test
    public void CE3() throws OrderServicesException{
        int numOrder = 99;
        Order o = new Order(numOrder);
        o.addDish("BEER", 5);
        ros.addNewOrderToTable(o);
        assertEquals(14500, ros.calculateTableBill(numOrder));
    }

}
