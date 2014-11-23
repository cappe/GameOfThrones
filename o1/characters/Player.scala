package o1.characters

import o1.game._
import o1.items._
import o1.characters._

class Player(name: String, hp: Int, startingArea: Area) {
	
	private var currentLocation = startingArea
	private val itemsInPossession = Map[String, Item]()
	private val relativesRescued = Map[String, Relative]()
	
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