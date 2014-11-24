package o1.game

import scala.collection.mutable.Map
import o1.items._
import o1.characters._

class Area(var areaName: String, var areaDescription: String) {
	
	private val neighbors = Map[String, Area]()
	private val items = Map[String, Item]()
	private val characters = Map[String, Character]()
	
	def fullDescription = {
		val itemList =getItemList()
		val characterList = getCharacterList()
		val exitList = getExitList()
		
		val separationLines = "-" * this.areaName.length()
		
		"\nYou are now in " + this.areaName + ".\n" +
			this.areaDescription + "\n" + separationLines +
				"\n" + itemList + "\n" + characterList + "\n\n" + exitList
	}
	
	private def getExitList(): String = {
		var exitList = "Places you can go to:"
		this.neighbors.foreach(f => exitList += "\n" + f._1.capitalize + " (" + f._2.areaName + ")")
		exitList
	}
	
	private def getCharacterList(): String = {
		var characterList = ""
		if (!this.characters.isEmpty) {
			characterList = "Characters you see here: " + this.characters.keys.mkString(" ")
		} else {
			characterList = "There is no people in " + this.areaName + "."
		}
		characterList
	}

	private def getItemList(): String = {
		var itemList = ""
		if (!this.items.isEmpty) {
			itemList = "Items you see here: " + this.items.keys.mkString(", ").capitalize
		} else {
			itemList = "You don't see any items lying around."
		}
		itemList
	}

	def setNeighbors(exits: Vector[(String, Area)]) = {
		this.neighbors ++= exits
	}
	
	def addItem(item: Item) = {
		this.items += item.name.toLowerCase() -> item
	}
	
	def addCharacter(character: Character*) = {
		character.foreach(f => (this.characters += f.name -> f))
	}
	
	def neighbor(direction: String) = {
		var newDirection = this.neighbors.get(direction)
		if (newDirection.isEmpty) {
			newDirection = this.neighbors.find(_._2.areaName.toLowerCase() == direction).map(_._2)
		}
		newDirection
	}
	
	def contains(itemName: String): Boolean = {
		this.items.contains(itemName)
	}
	
	def removeItem(itemName: String): Option[Item] = {
		this.items.remove(itemName)
	}

}