package main.project;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<?> getAllInventory(){
		try {
			ArrayList<Item> items = itemService.getAllItems();
			return new ResponseEntity<>(items, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("{id}")
	public ResponseEntity<?> getOneItem(@PathVariable("id") int id){
		try {
			ArrayList<Item> items = itemService.getAllItems();
			for (Item item : items) {
				if (item.getItemNumber() == id) {
					return new ResponseEntity<>(item, HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(new Error("Item Number " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<?> addItem(@RequestBody Item item) {
		try {
			Item itemAdded = itemService.addItem(item);
			if (itemAdded != null) {
				return new ResponseEntity<>(itemAdded, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(new Error("The " + item.getName() +" is exists, please try again"),  HttpStatus.BAD_REQUEST);
		} 
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@PatchMapping("reduce/{id}")
	public ResponseEntity<?> reducePartialItem(@PathVariable("id")int id, @RequestBody Item item){
		try {
			Item it = itemService.reduceItemAmount(id, item.getAmount());
			if (it != null) {
					return new ResponseEntity<>(it, HttpStatus.OK);
			}
			return new ResponseEntity<>(new Error("Amount reduction failed"),  HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@PatchMapping("deposit/{id}")
	public ResponseEntity<?> depositPartialItem(@PathVariable("id")int id, @RequestBody Item item){
		try {
			Item it = itemService.depositItemAmount(id, item.getAmount());
			if (it != null) {
					return new ResponseEntity<>(it, HttpStatus.OK);
			}
			return new ResponseEntity<>(new Error("Amount deposit failed"),  HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteItem(@PathVariable("id")int id) {
		try {
			boolean itemDeleted = itemService.deleteItem(id);
			if(itemDeleted == true) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(new Error("Item Number " + id + " not found."), HttpStatus.NOT_FOUND);
		} 
		
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}