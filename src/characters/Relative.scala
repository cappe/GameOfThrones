package src.characters

import src.game._

class Relative(fullName: String, relationShip: String, sex: String) extends Character(fullName, relationShip, sex) {

	var hostagedBy: Option[Enemy] = None

	def ask(): String = {
		var answer = ""
		if (this.hostagedBy.isDefined) {
			val hostagedBy = this.hostagedBy.get
			answer += "Help, help me Arya! Evil " + hostagedBy.fullName + " has\ncaptured me." +
				" Kill " + getCorrectGrammatic(hostagedBy.sex) + " before it's too late."
		} else {
			answer += "Dear Arya, thank you for saving my life!"
		}
		answer
	}

	protected override def getCorrectGrammatic(sex: String): String = {
		super.getCorrectGrammatic(sex)
	}

}