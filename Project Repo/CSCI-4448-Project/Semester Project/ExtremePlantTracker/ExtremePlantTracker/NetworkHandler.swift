//
//  NetworkHandler.swift
//  ExtremePlantTracker
//
//  Created by Jaryd Meek on 4/13/22.
//

import Foundation
import PostgresClientKit
/* ------------- */
/* COMMAND CLASS */
/* ------------- */

class ToLoadData {
    func execute() async {}
}

//Load data from our Postgres Database
class LoadSpecies: ToLoadData {
    override func execute() async {
        do {
            //Uses following framework and example
            //https://github.com/codewinsdotcom/PostgresClientKit
            var configuration = PostgresClientKit.ConnectionConfiguration()
            configuration.host = "ec2-3-223-213-207.compute-1.amazonaws.com"
            configuration.database = "ddv0rcqrgn9g8h"
            configuration.user = "metnhjqpqtvokb"
            configuration.credential = .md5Password(password: "1a1def84baf0dc727334dcdb27b06fad1c2681e5c1790bb247b035a705065e83")
            
            let connection = try PostgresClientKit.Connection(configuration: configuration)
            defer { connection.close() }
            
            let text = "SELECT * FROM plantbase RIGHT JOIN planttype ON planttype.type = plantbase.type;"
            let statement = try connection.prepareStatement(text: text)
            defer { statement.close() }
            
            let cursor = try statement.execute()
            defer { cursor.close() }
            
            for row in cursor {
                let columns = try row.get().columns
                let sciName = try columns[0].string()
                let commonName = try columns[1].string()
                let type = try columns[2].string()
                let waterDays = try columns[3].int()
                var pic:String
                if !columns[4].isNull {
                    pic = try columns[4].string()
                } else {
                    pic = "None"
                }
                let light = try columns[6].string()
                let soil = try columns[7].string()
                speciesStorage.append(plantSpeciesFactory.createSpecies(type: type, sciName: sciName, commonName: commonName, waterDays: waterDays, pic: pic, light: light, soil: soil)!)
                
            }
        } catch {
            print(error) // better error handling goes here
        }
        speciesStorage.append(plantSpeciesFactory.createSpecies(type: "Other", sciName: "Custom", commonName: "Other", waterDays: 3, pic: "Custom", light: "Custom", soil: "Custom")!)
    }
    
}

//Load data from weather API
class LoadWeather: ToLoadData {
    override func execute() async {
        guard let url = URL(string: "https://api.openweathermap.org/data/2.5/onecall?lat=\(lat)&lon=\(lon)&appid=402682ca66a2ddc6db3d9ed0ed222b3f") else {
            print("invalid url")
            return
        }
        
        do {
            let (data, _) = try await URLSession.shared.data(from: url)
            //print("JSON String: \(String(data: data, encoding: .utf8))")
            results = try JSONDecoder().decode(Result.self, from: data)
//            loaded = true
        } catch {
            print("invalid data")
        }
    }
}
