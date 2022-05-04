//
//  Main.swift
//  ExtremePlantTracker
//
//  Created by Jaryd Meek on 3/28/22.
//

import SwiftUI
import MapKit
import CoreLocation



var plantSpeciesFactory = PlantSpeciesFactory()
var NetworkCommands = [LoadSpecies(), LoadWeather()]
var results:Result?



struct Main: View {
    @State var loaded:Bool = false
    let locationManager = CLLocationManager()
    
    //Grab user's current location and convert to coords for api
    func getLocation(_ manager: CLLocationManager) {
        guard let locValue: CLLocationCoordinate2D = manager.location?.coordinate else { return }
        lat = locValue.latitude
        lon = locValue.longitude
    }
    

    //Get permission for location when app opens, and save new coords
    init() {
        self.locationManager.requestWhenInUseAuthorization()
        
        if CLLocationManager.locationServicesEnabled() {
            locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
            locationManager.startUpdatingLocation()
        }
        getLocation(locationManager)
    }

    //Main tabbar, loads sub views
    var body: some View {
        TabView {
            Weather(loaded: $loaded)
                .tabItem {
                    Label("Weather", systemImage: "thermometer")
                }
            MyPlants()
                .tabItem {
                    Label("My Plants", systemImage: "leaf")
                }
        }.onAppear {
            let appearance = UITabBarAppearance()
            UITabBar.appearance().scrollEdgeAppearance = appearance
        }
        .task {
            
            //RUN COMMAND PATTERN
            for x in NetworkCommands {
                await x.execute()
            }
            loaded = true
            
        }
    }
}

struct Main_Previews: PreviewProvider {
    static var previews: some View {
        Main()
    }
}
