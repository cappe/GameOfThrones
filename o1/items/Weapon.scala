package o1.items

class Weapon(name: String, description: String, effectivity: Int) extends Item(name, description, effectivity) {

	override def toString(): String = {
		this.name + " (does " + this.effectivity + " hp damage)"
	}
	
}