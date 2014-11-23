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
		val destination = this.location.neighbor(direction)
		this.currentLocation = destination.getOrElse(this.currentLocation)
		if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + "."
	}
	
	def get(itemName: String): String = {
		
		""
	}
	
	def rescuedRelative(relativeName: String): Boolean = {
		this.relativesRescued.contains(relativeName)
	}
	
	def hasItem(itemName: String): Boolean = {
		this.itemsInPossession.contains(itemName)
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
	
	def isAlive(): Boolean = {
		this.hp > 0
	}
	

}