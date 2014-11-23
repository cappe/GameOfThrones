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
			itemList = "\nYou see here: " + this.items.keys.mkString(" ")
		}
		val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
		this.areaDescription + itemList + exitList
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