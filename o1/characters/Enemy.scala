package o1.characters

import o1.game._
import o1.items._

class Enemy(fullName: String, var hp: Int, val location: Area, relationShip: String, sex: String) extends Character(fullName, relationShip, sex) {

	var hostage: Option[Relative] = None
	val weapon: Option[Weapon] = Some(new Weapon("Sword", "Description of Sword", 10))

	def setHostage(relative: Relative) = {
		relative.hostagedBy = Some(this)
		this.hostage = Some(relative)
	}

	def freeHostage(): (Option[Relative], String) = {
		var description = ""
		if (this.hostage.isDefined) {
			val freedHostage = this.hostage
			this.hostage = None
			description += "You saved your relative " + freedHostage.get.fullName + " and\nkilled evil " +
						this.fullName + ". Congratulations!"
			(hostage, description)
		} else {
			description += "No hostages to save."
			(None, description)
		}
	}

	def ask(): String = {
		var answer = "\n"
		if (this.hostage.isDefined) {
			val hostage = this.hostage.get
			answer += "Muhahah! I have your relative " + hostage.firstName + " captured\n" +
				"and I won't free " + getCorrectGrammatic(hostage.sex) + " without a FIGHT!"
		} else {
			answer += Enemy.noRelativesCaptured
		}
		answer
	}
	
	def hit(player: Player): String = {
		var result = ""
		if (weapon.isDefined) {
			result = player.tryToKill(weapon.get.effectivity)
		}
		result				
	}
	
	def tryToKill(effectivity: Int): String = {
		if(this.hp >= effectivity) {
			this.hp -= effectivity
		} else {
			this.hp = 0
		}
		this.toString()
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
	val noHostages = "This enemy has no hostages. No need to start a fight."
}