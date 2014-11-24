package o1.characters

import o1.game._

abstract class Character(val name: String, val relationShip: String, val sex: String) {
	
	def ask(name: String): String
}