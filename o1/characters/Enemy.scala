package o1.characters

import o1.game._
import o1.items._

class Enemy(fullName: String, var hp: Int, startingArea: Area, relationShip: String, sex: String) extends Character(fullName, relationShip, sex) {

	var hostage: Option[Relative] = None
	val weapon: Option[Weapon] = Some(new Weapon("Sword", "Description of Sword", 10))

	def setHostage(relative: Relative) = {
		relative.hostagedBy = Some(this)
		this.hostage = Some(relative)
	}

	def freeHostage(relative: Relative): Option[Relative] = {
		this.hostage.filter(_ == relative).foreach(_.hostagedBy = None)
		this.hostage
	}

	def ask(): String = {
		var answer = "\n"
		if (this.hostage.isDefined) {
			val hostage = this.hostage.get
			answer += "Muhahah! I have your relative " + hostage.firstName + " captured\n" +
				"and I won't free " + getCorrectGrammatic(hostage.sex) + " without a FIGHT!"
		} else {
			answer += "I have no relatives of yours captured. Now get lost!"
		}
		answer
	}
	
	def hit(player: Player): Option[String] = {
		var result: Option[String] = None
		if (weapon.isDefined) {
			result = player.tryToKill(weapon.get.effectivity)
		}
		result				
	}
	
	def tryToKill(effectivity: Int): Option[String] = {
		this.hp -= effectivity
		Some(this.toString())
	}
	
	private def isAlive(): Boolean = {
		this.hp > 0
	}
	
	override def toString(): String = {
		var description = this.fullName
		if (this.isAlive())
			description += " has still " + this.hp + " hp."
		else
			description += " is dead. You killed " + this.getCorrectGrammatic(this.sex) + "."
		description
		
	}

	protected override def getCorrectGrammatic(sex: String): String = {
		super.getCorrectGrammatic(sex)
	}

}