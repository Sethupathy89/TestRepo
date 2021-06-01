import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();
    private Map<String,Integer> itemMap = new HashMap<String, Integer>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
    	if(getCurrentTime().isAfter(openingTime) && getCurrentTime().isBefore(closingTime) ) {
        	return true;
        }else {
        	return false;
        }
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return menu;        
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }
    
    public int getTotalOrderValue(String items) {    
    	int totalCost = 0;    	
    	String[] itemsSplit = items.split(",");
    	this.convertListToMap();    	 
    	for(int i=0;i<itemsSplit.length;i++) {
    		if(itemMap.containsKey(itemsSplit[i])) {
    			totalCost = totalCost + itemMap.get(itemsSplit[i]);
    		}
    	}
    	return totalCost;
    }
    
    private void convertListToMap() {
    	for(Item itemList : menu) {
    		itemMap.put(itemList.getName(), itemList.getPrice());
    	}    	
    }

    public String getName() {
        return name;
    }

	public String getLocation() {
		return location;
	}

	
	
	

	

}
