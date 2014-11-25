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
	
	def getCurrentLocation(): Area = {
		this.currentLocation
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
	
	def ask(): String = {
		val enemy = this.currentLocation.getEnemy()
		if(enemy.isDefined)
			enemy.get.ask()
		else
			"No " + name.capitalize + " here."
	}
	
	def exits(): String = {
		this.currentLocation.getExitDescription()
	}
	
	def areainfo(): String = {
		this.currentLocation.getAreaInfo()
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
	
	def fight(): String = {
		if(!this.getCurrentLocation().getEnemy().isDefined)
			Area.noEnemies
		else	if (!this.itemsInPossession.exists(_._2.isInstanceOf[Weapon]))
			Player.noWeapons
		else
			Game.battleFieldCommand
	}
	
	def hit(weaponName: String, enemy: Enemy): Option[String] = {
		val weapon = this.itemsInPossession.find(_._1.toLowerCase().equals(weaponName)).map(_._2)
		var result: Option[String] = None
		if (weapon.isDefined) {
			result = enemy.tryToKill(weapon.get.effectivity)
		}
		result				
	}
	
	def tryToKill(effectivity: Int): Option[String] = {
		this.hp -= effectivity
		Some(this.toString())
	}
	
	def isAlive(): Boolean = {
		this.hp > 0
	}
	
	override def toString(): String = {
		var description = "You "
		if (this.isAlive())
			description += "have still " + this.hp + " hp. Whouhuuu!"
		else
			description += "are dead. Game over!"
		description
	}
}

object Player {
	val noWeapons = "You don't have any weapons.Try to\nfind some before going to fight."
}