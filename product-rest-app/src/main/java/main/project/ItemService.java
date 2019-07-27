package main.project;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public ArrayList<Item> getAllItems(){	
		ArrayList<Item> allItems = new ArrayList<>();
		itemRepository.findAll().forEach(items-> allItems.add(items));
		return allItems;	
	}
	
	public Item addItem(Item item) {
		ArrayList<Item> allItems = getAllItems();
		if (allItems.isEmpty()) {
			itemRepository.save(item);
			return item;
		}
		else {
			for (Item it : allItems) {
				if (!item.getName().equals(it.getName())) {
					itemRepository.save(item);
					return item;
				}
			}
		}
		return null;
	}
	
	public Item reduceItemAmount(int itemNu, int amount) {
		Item item = getItemById(itemNu);
		int originalAmount = item.getAmount();
		if (item != null) {
			if (amount < originalAmount) {
				item.setAmount(amount);
				itemRepository.save(item);
				return item;
			}
		}
		return null;
	}
	
	public Item depositItemAmount(int itemNu, int amount) {
		Item item = getItemById(itemNu);
		int originalAmount = item.getAmount();
		if (item != null) {
			if (amount > originalAmount) {
				item.setAmount(amount);
				itemRepository.save(item);
				return item;
			}
		}
		return null;
	}

	public Item getItemById(int itemNu) {
		Optional <Item> optionalItemRepository = itemRepository.findById(itemNu);
		if (optionalItemRepository.isPresent()) {
			return optionalItemRepository.get();
		}
		return null;
	}

	public boolean deleteItem(int itemNu) {
		if(itemRepository.existsById(itemNu)) {
			itemRepository.deleteById(itemNu);
			return true;
		}
		return false;
	}
}