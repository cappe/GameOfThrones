package o1.game

import scala.collection.mutable.Map
import o1.items._
import o1.characters._

class Area(var areaName: String, var areaDescription: String) {
	
	private val neighbors = Map[String, Area]()
	private val items = Map[String, Item]()
	private val characters = Map[String, Character]()
	
	def fullDescription = {
		var itemList = ""
		if(!this.items.isEmpty) {
			itemList = "You see here: " + this.items.keys.mkString(" ")
		} else {
			itemList = "You don't see any items lying around."
		}
		val exitList = "Exits available: " + this.neighbors.keys.mkString(" ")
		val separationLines = "-" * this.areaName.length()
		
		"\nYou are now in " + this.areaName + ".\n" +
			this.areaDescription + "\n" + separationLines +
				"\n" + itemList + "\n\n" + exitList
	}

	def setNeighbors(exits: Vector[(String, Area)]) = {
		this.neighbors ++= exits
	}
	
	def addItem(item: Item) = {
		this.items += item.name -> item
	}
	
	def addCharacter(character: Character*) = {
		character.foreach(f => (this.characters += f.name -> f))
	}
	
	def neighbor(direction: String) = {
		this.neighbors.get(direction)
	}

}