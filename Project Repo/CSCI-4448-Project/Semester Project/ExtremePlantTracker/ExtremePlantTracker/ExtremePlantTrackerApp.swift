//
//  ExtremePlantTrackerApp.swift
//  ExtremePlantTracker
//
//  Created by Jaryd Meek on 3/28/22.
//

import SwiftUI
import CoreData



@main
struct ExtremePlantTrackerApp: App {
    //SINGLETON
    //By adding the persistence controller by shared, it is a singleton
    let persistenceController = PersistenceController.shared
    var body: some Scene {
        WindowGroup {
            //Main view
            Main()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
