package o1.game

class Area(var areaName: String, var areaDescription: String) {
	
	def fullDescription = {
		var itemList = ""
//		if(!this.items.isEmpty) {
//			itemList = "\nYou see here: " + this.items.keys.mkString(" ")
//		}
//		val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
//		this.description + itemList + exitList
		itemList
	}

}