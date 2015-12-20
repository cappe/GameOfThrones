package src.characters

import src.game._
import src.items._
import src.characters._
import scala.util.Random

import scala.collection.mutable.Map

class Player(val name: String, var hp: Int, var startingArea: Area) {

	private val home = startingArea
	private val maxHp = hp
	private var currentLocation = home
	private var quitCommandGiven = false
	var exitCommandGiven = false
	private val itemsInPossession = Map[String, Item]()
	private val relativesRescued = Map[String, Relative]()

	def location: Area = this.currentLocation

	/* Moves the player to given direction */
	def go(direction: String): String = {
		val destination = this.location.neighbor(direction)
		this.currentLocation = destination.getOrElse(this.currentLocation)
		if (destination.isDefined) "You go to " + destination.get.areaName + "." else "You can't go there."
	}

	/* Refills player's health points */
	def refill(): String = {
		var description = ""
		if (this.currentLocation.equals(home)) {
			this.hp = this.maxHp
			description += "You refilled your Health Points.\n" + this.getHp()
		} else {
			description += "Refill not possible. This is not your home."
		}
		description
	}

	/* Tells your home */
	def getHome(): String = {
		"Your home is " + this.home.areaName + "."
	}

	def getCurrentLocation(): Area = {
		this.currentLocation
	}

	/* Picks up items from the current area */
	def get(itemName: String): String = {
		var description = ""
		if (this.currentLocation.contains(itemName)) {
			val itemInHands = this.currentLocation.removeItem(itemName)
			if (itemInHands.isDefined) {
				this.itemsInPossession += itemInHands.get.name.toLowerCase() -> itemInHands.get
				description = "You picked up the " + itemInHands.get.toString() + "."
			}
		} else {
			description = "There is no " + itemName + " here to pick up."
		}
		description
	}

	/* Already rescued relatives */
	def rescuedRelative(relativeName: String): Boolean = {
		this.relativesRescued.contains(relativeName)
	}

	def addRelative(relative: Relative) = {
		this.relativesRescued += relative.fullName -> relative
	}

	def hasItem(itemName: String): Boolean = {
		this.itemsInPossession.contains(itemName)
	}

	/* Gives game tips */
	def tips(): String = {
		val tip1 = "Go home to " + this.home.areaName + " to refill your Health Points.\n"
		val tip2 = "Use 'help' to see available commands.\n"
		val tip3 = "Exit during the fight by typing 'exit' and go home to refill your Health Points."
		val tips = Vector(tip1, tip2, tip3)
		tips(Random.nextInt(3))
	}

	def getHp(): String = {
		var description = ""
		if (this.hp == this.maxHp) {
			description += "You have now maximum amount of\nHealth Points. " +
				"That is " + this.maxHp + " hp."
		} else {
			description += "You have only " + this.hp + " hp left." +
				"\nGo back home to " + this.home.areaName + " to refill your Health Points."
		}
		description
	}

	/* Lists all the commands available */
	def getHelp(): String = {
		var helpList = ""
		Action.commands.foreach(f => helpList += "\n\n" + f._1 + ": " + f._2)
		helpList
	}

	/* Lists all the items player is carrying and
	 * the relatives player has already saved
	 */
	def makeInventory(): String = {
		getRelativesRescued() + "\n" + getItemsInPossession()
	}

	private def getRelativesRescued(): String = {
		var description = ""
		if (this.relativesRescued.isEmpty)
			description += "No relatives saved - yet."
		else {
			description += "You have saved:"
			this.relativesRescued.foreach(description += "\n" + _._2.fullName)
		}
		description
	}

	private def getItemsInPossession(): String = {
		var description = "\n"
		if (this.itemsInPossession.isEmpty)
			description += "You are not carrying any items."
		else {
			description += "You are carrying:"
			this.itemsInPossession.foreach(description += "\n" + _._2.toString())
		}
		description
	}

	def ask(name: String): String = {
		val currentEnemy = this.currentLocation.getEnemy()
		var answer = ""

		var enemy = currentEnemy.find(_.firstName.toLowerCase() equals (name))
		var enemyAnswer = enemy.map(_.ask())

		if (enemyAnswer.isDefined)
			answer = enemyAnswer.get
		else if (currentEnemy.isDefined && currentEnemy.get.hostage.isDefined) {
			var hostage = currentEnemy.get.hostage.get
			if (hostage.firstName.toLowerCase().equals(name)) {
				answer = hostage.ask()
			}
		} else
			answer = Player.noOnePresent
		answer
	}

	def exits(): String = {
		this.currentLocation.getExitDescription()
	}

	def areainfo(): String = {
		this.currentLocation.getAreaInfo()
	}

	def examine(itemName: String): String = {
		this.itemsInPossession.find(_._1.equals(itemName)).map(_._2.description).getOrElse(Player.noItem)
	}

	def itemsInThisArea(): String = {
		this.currentLocation.getItemDescription()
	}

	def enemiesInThisArea(): String = {
		this.currentLocation.getEnemyDescription()
	}

	def drop(itemName: String): String = {
		if (this.hasItem(itemName)) {
			this.currentLocation.addItem(this.itemsInPossession.get(itemName).get)
			"You drop the " + this.itemsInPossession.remove(itemName).get.name + "."
		} else {
			Player.noItem
		}
	}

	def quit(): String = {
		this.quitCommandGiven = true
		""
	}

	def hasQuit() = this.quitCommandGiven

	def getWeapons(): Vector[Item] = {
		this.itemsInPossession.filter(_._2.isInstanceOf[Weapon]).map(_._2).toVector
	}

	/* Enters to the battlezone with an enemy */
	def fight(): String = {
		this.exitCommandGiven = false
		val possibleEnemy = this.getCurrentLocation().getEnemy()
		if (!possibleEnemy.isDefined)
			Area.noEnemies
		else if (!possibleEnemy.get.hostage.isDefined)
			Enemy.noHostages
		else if (!this.itemsInPossession.exists(_._2.isInstanceOf[Weapon]))
			Player.noWeapons
		else
			Game.battleFieldCommand
	}

	/* Hits enemy with fixed damage effect */
	def hit(weaponName: String, enemy: Enemy): Option[String] = {
		val weapon = this.itemsInPossession.find(_._1.toLowerCase().equals(weaponName)).map(_._2)
		var result: Option[String] = None
		if (weapon.isDefined) {
			result = Some(enemy.tryToKill(weapon.get.effectivity))
		}
		result
	}

	/* Possible to exit in the middle of a fight */
	def exitFight(): Option[String] = {
		this.exitCommandGiven = true
		Some("Exiting fight.")
	}

	/* Enemy uses this method when they try to kill this player */
	def tryToKill(effectivity: Int): String = {
		if (this.hp > effectivity) {
			this.hp -= effectivity
			"\nEnemy made " + effectivity + " hp damage. Autch!" +
				"\n" + this.toString()
		} else {
			val oldHp = this.hp
			this.hp = 0
			"\nYou had " + oldHp + " hp. Enemy killed you."
		}
	}

	def isAlive(): Boolean = {
		this.hp > 0
	}

	override def toString(): String = {
		var description = ""
		if (this.isAlive())
			description += "You have still " + this.hp + " hp. Whouhuuu!"
		description
	}
}

object Player {
	val noWeapons = "You don't have any weapons. Try to\nfind some before going to fight."
	val noItem = "You don't have that item!"
	val noOnePresent = "There is no one here."
}