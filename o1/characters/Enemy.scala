package o1.characters

import o1.game._

class Enemy(fullName: String, var hp: Int, startingArea: Area, relationShip: String, sex: String) extends Character(fullName, relationShip, sex) {

	var hostage: Option[Relative] = None

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
	
	def hit(): String = {
		
		""
	}

	protected override def getCorrectGrammatic(sex: String): String = {
		super.getCorrectGrammatic(sex)
	}

}