package src.characters

import src.game._
import src.items._

class Enemy(fullName: String, var hp: Int, val location: Area, relationShip: String, sex: String) extends Character(fullName, relationShip, sex) {

	var hostage: Option[Relative] = None
	val weapon: Option[Weapon] = Some(new Weapon("Sword", "Description of Sword", 10))

	/* Sets a hostage to this enemy. Max 1 hostage. */
	def setHostage(relative: Relative) = {
		relative.hostagedBy = Some(this)
		this.hostage = Some(relative)
	}

	/* Frees hostage. Is called only when !this.enemy.isAlive() */
	def freeHostage(): (Option[Relative], String) = {
		var description = "\n"
		if (this.hostage.isDefined) {
			this.hostage.map(_.hostagedBy = None)
			val freedHostage = this.hostage
			this.hostage = None
			description += "You saved your relative " + freedHostage.get.fullName + " and\nkilled evil " +
				this.fullName + ". Congratulations!"
			(freedHostage, description)
		} else {
			description += "No hostages to save."
			(None, description)
		}
	}

	def ask(): String = {
		var answer = ""
		if (this.hostage.isDefined) {
			val hostage = this.hostage.get
			answer += "Muhahah! I have your relative " + hostage.firstName + " captured\n" +
				"and I won't free " + getCorrectGrammatic(hostage.sex) + " without a FIGHT!"
		} else {
			answer += Enemy.noRelativesCaptured
		}
		answer
	}

	/* Hits the player with a fixed damage effect */
	def hit(player: Player): String = {
		var result = ""
		if (weapon.isDefined) {
			result = player.tryToKill(weapon.get.effectivity)
		}
		result
	}

	/* Lets the player try to kill this enemy */
	def tryToKill(effectivity: Int): String = {
		var description = "\n"
		if (this.hp >= effectivity) {
			this.hp -= effectivity
			description += "You made " + effectivity + " hp damage. Nice!"
		} else {
			this.hp = 0
		}
		description + "\n" + this.toString()
	}

	def isAlive(): Boolean = {
		this.hp > 0
	}

	override def toString(): String = {
		var description = this.fullName
		if (this.isAlive())
			description += " has still " + this.hp + " hp."
		else
			description += " died. You killed " + this.getCorrectGrammatic(this.sex) + "."
		description

	}

	protected override def getCorrectGrammatic(sex: String): String = {
		super.getCorrectGrammatic(sex)
	}

}

object Enemy {
	val noRelativesCaptured = "I have no relatives of yours captured. Now get lost!"
	val noHostages = "This enemy has no hostages. No need to start a fight here."
}