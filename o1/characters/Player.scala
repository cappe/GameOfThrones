package o1.characters

import o1.game._

class Player(name: String, hp: Int, startingArea: Area) extends Character(name, hp, startingArea) {
	
	private var currentLocation = startingArea 
	
	def location = this.currentLocation
	
	def go(direction: String): String = {
		
		""
	}
	
	def get(itemName: String): String = {
		
		""
	}
	
	def has(itemName: String): Boolean = {
		
		false
	}
	
	def makeInventory(): String = {
		
		""
	}
	
	def examine(itemName: String): String = {
		
		""
	}
	
	def drop(itemName: String): String = {
		
		""
	}
	
	def quit(): String = {
		
		
		""
	}
	
	def fight(): String = ???
	

}