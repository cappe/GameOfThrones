package o1.game

import scala.collection.mutable.Map
import o1.items._
import o1.characters._
import o1.game._

class Area(var areaName: String, var areaDescription: String) {
	
	private val neighbors = Map[String, Area]()
	private val items = Map[String, Item]()
	private val characters = Map[String, Character]()
	
	def fullDescription = {
		val itemList =getItemDescription()
		val characterList = getEnemyDescription()
		val exitList = getExitDescription()
		
		val separationLines = "-" * this.areaName.length()
		
		"\nYou are now in " + this.areaName + ".\n" +
			this.areaDescription + "\n" + separationLines +
				"\n" + itemList + "\n" + characterList + "\n\n" + exitList
	}
	
	private def getExitDescription(): String = {
		var exitList = "Places you can go to:"
		this.neighbors.foreach(f => exitList += "\n" + f._2.areaName + " (" + f._1.capitalize + ")")
		exitList
	}
	
	private def getEnemyDescription(): String = {
		var enemyDescription = ""
		var enemy = getCharacterByRelationship(Game.enemy)
		if (enemy.isDefined) {
			var fullName = enemy.get.fullName
			var firstName = enemy.get.firstName
			enemyDescription = "\nWatch out! Your enemy " + fullName + " is here.\n" +
						"He might have captured one of your relatives.\n" + 
						"Ask " + firstName + " about this by typing 'Ask " + firstName + "'."
		} else {
			enemyDescription = Area.noEnemies
		}
		enemyDescription
	}

	def getCharacterByName(name: String): Option[Character] = {
		var possibleCharacter = this.characters.find(_._2.firstName.toLowerCase() == name)
		possibleCharacter.map(_._2)
	}

	def getCharacterByRelationship(relationShip: String): Option[Character] = {
		var possibleCharacter = this.characters.find(_._2.relationShip == relationShip)
		possibleCharacter.map(_._2)
	}

	private def getItemDescription(): String = {
		var itemList = ""
		if (!this.items.isEmpty) {
			itemList = "Items you see here: " + this.items.keys.mkString(", ").capitalize
		} else {
			itemList = Area.noItems
		}
		itemList
	}

	def setNeighbors(exits: Vector[(String, Area)]) = {
		this.neighbors ++= exits
	}
	
	def addItem(items: Item*) = {
		items.foreach(f => (this.items += f.name.toLowerCase() -> f))
	}
	
	def addCharacter(character: Character*) = {
		character.foreach(f => (this.characters += f.fullName -> f))
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

object Area {
	val noEnemies = "Phiuh! No enemies nearby."
	val noItems =  "You don't see any items lying around."
}