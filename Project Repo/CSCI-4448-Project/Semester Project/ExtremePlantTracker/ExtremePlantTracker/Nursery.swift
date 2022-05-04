//
//  Nursery.swift
//  ExtremePlantTracker
//
//  Created by Jaryd Meek on 4/13/22.
//

import Foundation

//Species Storage

var speciesStorage = [PlantSpecies]()

//Plant Species
class PlantSpecies: NSObject, NSCopying  {
    
    //Initializer
    init(sciName: String, commonName: String, waterDays: Int, pic: String, light: String, soil: String) {
        self.sciName = sciName
        self.commonName = commonName
        self.waterDays = waterDays
        self.pic = pic
        self.light = light
        self.soil = soil
    }
    
    //Vars
    private var sciName:String
    private var commonName:String
    private var waterDays:Int
    private var pic:String
    private var light:String
    private var soil:String
    
    //Normal functions
    func getSciName()->String {return sciName}
    func getName()->String {return commonName}
    func getWaterDays()->Int {return waterDays}
    func getLight()->String {return light}
    func getSoil()->String {return soil}
    
    //Overloaded Functions
    func getType()->String {return ""}
    func getIndoor()->Bool {return false}
    func getAlert()->String { return "" }
    
    //Copy
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = PlantSpecies(sciName: sciName, commonName: commonName, waterDays: waterDays, pic: pic, light: light, soil: soil)
        return copy
    }
    
    //Overload ==
    static func == (lhs: PlantSpecies, rhs: PlantSpecies) -> Bool {
        return lhs.sciName == rhs.sciName
    }
    
}

//Subclasses
class IndoorPlant: PlantSpecies {
    override func getIndoor() -> Bool { return true }
}

class Succulent: IndoorPlant {
    override func getType()->String { return "Succulent"}
    override func getAlert() -> String {
        return "Be careful not to overwater me! I need less water than you might think."
    }
}

class IndoorFlower: IndoorPlant {
    override func getType()->String { return "Indoor Flower"}
    override func getAlert() -> String {
        return "Don't leave the blinds drawn, I need lots of light!"
    }
}

class Houseplant: IndoorPlant {
    override func getType()->String { return "Houseplant"}
    override func getAlert() -> String {
        return "Try to leave me somewhere warm."
    }
}

class OutdoorPlant: PlantSpecies {
    override func getIndoor() -> Bool { return false }
    override func getAlert() -> String {
        return "I'll always need more water than you might think."
    }
}

class Vegetable: OutdoorPlant {
    override func getType()->String { return "Vegetable"}
    override func getAlert() -> String {
        return "Consistent watering provides much nicer crops."
    }
}

class Tree: OutdoorPlant {
    override func getType()->String { return "Tree"}
    override func getAlert() -> String {
        return "I can manage without water for a bit, just give me extra when it's hot."
    }
}

class OutdoorFlower: OutdoorPlant {
    override func getType()->String { return "Outdoor Flower"}
    override func getAlert() -> String {
        return "If I'm wilting it's a good sign I need water!"
    }
}



//Local Plants
class Plant: Identifiable, Equatable {
    //Initializer
    init(name: String, species: PlantSpecies, location:String, dateLastWatered: Date) {
        self.name = name
        self.species = species.copy() as! PlantSpecies
        self.location = location
        self.dateLastWatered = dateLastWatered
    }
    
    //Vars
    private var name:String
    private var species:PlantSpecies
    private var location:String
    private var dateLastWatered:Date
    var id = UUID()
    
    
    //Getters/setters
    func getName() -> String {return name}
    func getSpecies() -> PlantSpecies {return species}
    func getLocation() -> String {return location}
    func getLastWatered() -> Date {return dateLastWatered}
    
    func setDate(date: Date) {
        dateLastWatered = date;
    }
    
    static func == (lhs: Plant, rhs: Plant) -> Bool {
        return lhs.name == rhs.name && lhs.id == rhs.id
    }
}



/* ------------ */
/* FACTORY CLASS*/
/* ------------ */

class PlantSpeciesFactory {
    //sciName, commonName, type, waterDays, pic, light, soil
    func createSpecies(type: String, sciName: String, commonName: String, waterDays: Int, pic: String, light: String, soil: String) -> PlantSpecies? {
        if type == "Succulent" {
            return Succulent(sciName: sciName, commonName: commonName, waterDays: waterDays, pic: pic, light: light, soil: soil)
        }else if type == "Indoor Flower" {
            return IndoorFlower(sciName: sciName, commonName: commonName, waterDays: waterDays, pic: pic, light: light, soil: soil)
        }else if type == "Houseplant" {
            return Houseplant(sciName: sciName, commonName: commonName, waterDays: waterDays, pic: pic, light: light, soil: soil)
        } else if type == "Vegetable" {
            return Vegetable(sciName: sciName, commonName: commonName, waterDays: waterDays, pic: pic, light: light, soil: soil)
        }else if type == "Tree" {
            return Tree(sciName: sciName, commonName: commonName, waterDays: waterDays, pic: pic, light: light, soil: soil)
        }else if type == "Outdoor Flower" {
            return OutdoorFlower(sciName: sciName, commonName: commonName, waterDays: waterDays, pic: pic, light: light, soil: soil)
        }
        return IndoorPlant(sciName: sciName, commonName: commonName, waterDays: waterDays, pic: pic, light: light, soil: soil)
    }
}
