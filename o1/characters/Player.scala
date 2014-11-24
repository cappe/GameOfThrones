package o1.characters

import o1.game._
import o1.items._
import o1.characters._

import scala.collection.mutable.Map

class Player(val name: String, var hp: Int, var startingArea: Area) {
	
	private var currentLocation = startingArea
	private val itemsInPossession = Map[String, Item]()
	private val relativesRescued = Map[String, Relative]()
	
	def location = this.currentLocation
	
	def go(direction: String): String = {
		val destination = this.location.neighbor(direction)
		this.currentLocation = destination.getOrElse(this.currentLocation)
		if (destination.isDefined) "You go to " + destination.get.areaName + "." else "You can't go there."
	}
	
	def get(itemName: String): String = {
		var description = ""
		if (this.currentLocation.contains(itemName)) {
			val itemInHands = this.currentLocation.removeItem(itemName)
			if(itemInHands.isDefined) {
				this.itemsInPossession += itemInHands.get.name -> itemInHands.get
				description = "You picked up the " + itemInHands.get.name + "."
			}
		} else {
			description = "There is no " + itemName + " here to pick up."
		}
		description
	}
	
	def rescuedRelative(relativeName: String): Boolean = {
		this.relativesRescued.contains(relativeName)
	}
	
	def hasItem(itemName: String): Boolean = {
		this.itemsInPossession.contains(itemName)
	}
	
	def makeInventory(): String = {
		var description = ""
		if (this.itemsInPossession.isEmpty)
			description = "You are empty-handed."
		else {
			description = "You are carrying:"
			this.itemsInPossession.foreach(description += "\n" + _._1)
		}
		description
	}
	
	def ask(name: String): String = {
		val enemy = this.currentLocation.getCharacterByName(name)
		var answer = ""
		if(enemy.isDefined) {
			answer = enemy.get.ask()
		} else {
			answer = "No " + name.capitalize + " here."
		}
		answer
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