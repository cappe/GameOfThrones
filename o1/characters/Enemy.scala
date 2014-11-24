package o1.characters

import o1.game._

class Enemy(name: String, var hp: Int, startingArea: Area, relationShip: String, var hostage: Option[Relative], sex: String) extends Character(name, relationShip, sex) {

	def freeHostage(relative: Relative): Option[Relative] = {
		var freeHostage = this.hostage
		this.hostage = None
		freeHostage
	}
	
	def ask(name: String): String = {
		var answer = ""
		if(this.hostage.isDefined) {
			val hostage = this.hostage.get
			answer += "Muhahah! I have your relative " + hostage.name.takeWhile(_ != ' ') + " captured\n" +
					"and I won't free " + getCorrectGrammatic() + " without a FIGHT!"
				
		} else {
			answer = "I have no relatives of yours captured. Now get lost!"
		}
		answer
	}

	private def getCorrectGrammatic(): String = {
		this.hostage.get.sex match {
			case Game.male => "him"
			case Game.female => "her"
			case default => "them"
		}
	}
	
}