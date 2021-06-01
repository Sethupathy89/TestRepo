import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantServiceTest {

	RestaurantService service = new RestaurantService();
	Restaurant restaurant;
	LocalTime openingTime;
	LocalTime closingTime;
	int initialNumberOfRestaurants;

	@BeforeEach
	public void setUp() {
		openingTime = LocalTime.parse("10:30:00");
		closingTime = LocalTime.parse("22:00:00");
		restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
		restaurant.addToMenu("Sweet corn soup", 119);
		restaurant.addToMenu("Vegetable lasagne", 269);
		initialNumberOfRestaurants = service.getRestaurants().size();
	}

	@Test
	public void searching_for_existing_restaurant_should_return_expected_restaurant_object()
			throws restaurantNotFoundException {
		Restaurant actualRestaurant = service.findRestaurantByName("Amelie's cafe");
		String returnedRestaurantName = actualRestaurant.getName();
		String location = actualRestaurant.getLocation();
		String openingTime = actualRestaurant.openingTime.toString();
		String closingTime = actualRestaurant.closingTime.toString();
		assertNotNull(actualRestaurant);
		assertTrue(actualRestaurant instanceof Restaurant);
		assertEquals("Amelie's cafe", returnedRestaurantName);
		assertEquals("Chennai", location);
		assertEquals("10:30", openingTime);
		assertEquals("22:00", closingTime);
	}

	@Test
	public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
		assertThrows(restaurantNotFoundException.class, () -> service.findRestaurantByName("Pantry d'or"));		
	}

	@Test
	public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
		service.removeRestaurant("Amelie's cafe");
		assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
	}

	@Test
	public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
		assertThrows(restaurantNotFoundException.class, () -> service.removeRestaurant("Pantry d'or"));
	}

	@Test
	public void add_restaurant_should_increase_list_of_restaurants_size_by_1() {
		service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
		assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
	}

}