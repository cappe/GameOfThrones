package o1.characters

import o1.game._

abstract class Character(val fullName: String, val relationShip: String, val sex: String) {
	
	val firstName = this.fullName.takeWhile(_ != ' ')
	
	def ask(): String

	protected def getCorrectGrammatic(sex: String): String = {
		sex match {
			case Game.male => "him"
			case Game.female => "her"
			case default => "them"
		}
	}
}