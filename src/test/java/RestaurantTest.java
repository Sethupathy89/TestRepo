import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {	
	
	Restaurant mockRestaurant;
	Restaurant restaurant;	
	LocalTime openingTime;
	LocalTime closingTime;
	int initialMenuSize;
	
	@BeforeEach
	public void setUp() {
		openingTime = LocalTime.parse("10:30:00");
		closingTime = LocalTime.parse("22:00:00");
		restaurant  = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
		restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        initialMenuSize = restaurant.getMenu().size();
		mockRestaurant = Mockito.spy(restaurant);
	}

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() throws Exception{  
    	LocalTime local = LocalTime.parse("13:00:00");;
    	Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(local);
    	assertTrue(mockRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() throws Exception{
    	LocalTime local = LocalTime.parse("23:00:00");
    	Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(local);
    	assertFalse(mockRestaurant.isRestaurantOpen());
    }
    
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){        
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    
    @Test
    public void get_total_order_value_of_3_items_selected_by_customer() {
    	restaurant.addToMenu("Sizzling brownie",319);
    	int actualTotalOrderValue= restaurant.getTotalOrderValue("Sweet corn soup,Sizzling brownie,Vegetable lasagne");
    	int expectedTotalOrderValue = 707;
    	assertEquals(expectedTotalOrderValue,actualTotalOrderValue);
    }
    
    @Test
    public void get_total_order_value_of_2_items_selected_by_customer() {
    	int actualTotalOrderValue= restaurant.getTotalOrderValue("Sweet corn soup,Vegetable lasagne");
    	int expectedTotalOrderValue = 388;
    	assertEquals(expectedTotalOrderValue,actualTotalOrderValue);
    }
    
    @Test
    public void get_total_order_value_of_1_item_selected_by_customer() {
    	int actualTotalOrderValue= restaurant.getTotalOrderValue("Sweet corn soup");
    	int expectedTotalOrderValue = 119;
    	assertEquals(expectedTotalOrderValue,actualTotalOrderValue);
    }
    
}