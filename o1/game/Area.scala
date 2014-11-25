package o1.game

import scala.collection.mutable.Map
import o1.items._
import o1.characters._
import o1.game._

class Area(var areaName: String, var areaDescription: String) {
	
	private val neighbors = Map[String, Area]()
	private val items = Map[String, Item]()
	private var enemy: Option[Enemy] = None
	private var relative: Option[Relative] = None
	
	def fullDescription() = {
		val areaInfo = getAreaInfo()
		val itemDescription =getItemDescription()
		val enemyListDescription = getEnemyDescription()
		val exitDescription = getExitDescription()
		
		val separationLines = "-" * 30
		
		areaInfo + "\n" + separationLines + "\n" + itemDescription + "\n" +
			separationLines + "\n" + enemyListDescription + "\n" + exitDescription
	}
	
	def getAreaInfo(): String = {
		"\nYou are now in " + this.areaName + ".\n" + this.areaDescription
	}
	
	def getExitDescription(): String = {
		var exitList = "\nPlaces you can go to:"
		this.neighbors.foreach(f => exitList += "\n" + f._2.areaName + " (" + f._1.capitalize + ")")
		exitList
	}
	
	private def getEnemyDescription(): String = {
		var enemyDescription = ""
		var enemy = this.enemy
		if (enemy.isDefined) {
			var fullName = enemy.get.fullName
			var firstName = enemy.get.firstName
			enemyDescription = "Watch out! Your enemy " + fullName + " is here.\n" +
						"He might have captured one of your relatives.\n" + 
						"Ask " + firstName + " about this by typing 'Ask " + firstName + "'."
		} else {
			enemyDescription = Area.noEnemies
		}
		enemyDescription
	}

	def getEnemy(): Option[Enemy] = {
		this.enemy
	}
	
	def getRelative(): Option[Relative] = {
		this.relative
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
	
	def addEnemy(enemy: Option[Enemy]) = {
		this.enemy = enemy
	}
	
	def addRelative(relative: Option[Relative]) = {
		this.relative = relative
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