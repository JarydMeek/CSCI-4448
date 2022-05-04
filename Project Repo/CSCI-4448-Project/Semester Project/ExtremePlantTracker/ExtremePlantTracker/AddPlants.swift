//
//  AddPlants.swift
//  ExtremePlantTracker
//
//  Created by Jaryd Meek on 3/28/22.
//

import SwiftUI
import CoreData


//Add plants to storage

struct AddPlants: View {
    @Environment(\.presentationMode) var presentationMode

    @Environment(\.managedObjectContext) var context
    
    @State var name:String = ""
    @State var selection:PlantSpecies = PlantSpecies(sciName: "", commonName: "", waterDays: 0, pic: "", light: "", soil: "")
    @State var location:String = ""
    var body: some View {
        NavigationView {
            VStack{
                VStack{
                    Text("Plant Name:")
                    TextField("Name", text: $name)
                    Text("Plant Type:")
                    Picker(selection: $selection, label: Text("Select Plant Type")) {
                        ForEach(speciesStorage, id: \.self) { species in
                            Text("\(species.getName())")
                        }
                    } .pickerStyle(.wheel)
                    Text("Location:")
                    TextField("Location", text: $location)
                }.frame(width:300)
                
                Spacer()
                Button(action: {
                    let newPlant = PlantSaved(context: context)
                    newPlant.name = name
                    newPlant.location = location
                    newPlant.species = selection.getName()
                    newPlant.dateLastWatered = Date()
                    newPlant.id = UUID()
                    
                    do {
                        try context.save()
                    } catch {
                        // Replace this implementation with code to handle the error appropriately.
                        // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                        let nsError = error as NSError
                        fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
                    }
                    presentationMode.wrappedValue.dismiss()
                }) {
                    Text("Add Plant!")
                }
                Spacer()
            }
        }.navigationTitle("Add Plant")
    }
}
