//
//  WeatherStruct.swift
//  ExtremePlantTracker
//
//  Created by Noah Nguyen on 4/13/22.
//

import Foundation


//This file contains all the relevant data structures for the JSON decode of return of results from api


struct Result: Codable {
    let lat: Double
    let lon: Double
    let timezone: String
    let current: Current
    var hourly: [Hourly]
    var daily: [Daily]
    
    
    mutating func sortHourlyArray() {
        hourly.sort { (hour1: Hourly, hour2: Hourly) -> Bool in
            return hour1.dt < hour2.dt
        }
    }
    
    mutating func sortDailyArray() {
        daily.sort { (day1, day2) -> Bool in
            return day1.dt < day2.dt
        }
    }
}

struct Current: Codable {
    let dt: Int
    let sunrise: Int
    let sunset: Int
    let temp: Double
    let feels_like: Double
    let pressure: Int
    let humidity: Int
    let dew_point: Double
    let uvi: Double
    let clouds: Int
    let wind_speed: Double
    let wind_deg: Int
    let weather: [WeatherStruct]
}

struct WeatherStruct: Codable {
    let id: Int
    let main: String
    let description: String
    let icon: String
}

struct Hourly: Codable {
    let dt: Int
    let temp: Double
    let feels_like: Double
    let pressure: Int
    let humidity: Int
    let dew_point: Double
    let clouds: Double
    let wind_speed: Double
    let wind_deg: Int
    let weather: [WeatherStruct]
    var id = UUID()
}

struct Daily: Codable {
    let dt: Int
    let sunrise: Int
    let sunset: Int
    let temp: Temperature
    let feels_like: Feels_Like
    let pressure: Int
    let humidity: Int
    let dew_point: Double
    let wind_speed: Double
    let wind_deg: Int
    let weather: [WeatherStruct]
    let clouds: Int
    let uvi: Double
}

struct Temperature: Codable {
    let day: Double
    let min: Double
    let max: Double
    let night: Double
    let eve: Double
    let morn: Double
}

struct Feels_Like: Codable {
    let day: Double
    let night: Double
    let eve: Double
    let morn: Double
}
