//
//  MyPlants.swift
//  ExtremePlantTracker
//
//  Created by Jaryd Meek on 3/28/22.
//

import SwiftUI
import CoreData

//Handles Main Plant View, Plant List, and Single Plant View


//ROW FOR LIST ON MAIN VIEW
struct PlantRow: View {
    let plant:PlantSaved
    func getWaterDays() -> Int {
        for x in speciesStorage {
            if x.getName() == plant.species {
                return x.getWaterDays()
            }
        }
        return 5
    }
    var body: some View {
        HStack{
            if (Calendar.current.dateComponents([.day], from: plant.dateLastWatered ?? Date(), to: Date()).day! > getWaterDays()) {
                Image(systemName: "drop").foregroundColor(.red)
            } else if (Calendar.current.dateComponents([.day], from: plant.dateLastWatered ?? Date(), to: Date()).day! > 0) {
                Image(systemName: "drop").foregroundColor(.orange)
            } else  {
                Image(systemName: "drop").foregroundColor(.green)
            }
            Text(plant.name ?? "Error")
        }
    }
}


//Convert date to string
func getDate(date: Date)->String {
    let dateformat = DateFormatter()
    dateformat.dateFormat = "h:mm a - EEEE, MMM d, yyyy"
    return dateformat.string(from: date)
}


//SINGLE PLANT VIEW
struct PlantView: View {
    @Environment(\.presentationMode) var presentationMode
    @Environment(\.managedObjectContext) var context
    @FetchRequest(
        sortDescriptors: [NSSortDescriptor(keyPath: \PlantSaved.dateLastWatered, ascending: true)],
        animation: .default)
    private var savedPlants: FetchedResults<PlantSaved>
    let toShow:Plant
    
    func getAlert (toShow: Plant) -> String {
        return speciesStorage[speciesStorage.firstIndex(where: { $0.getName() == toShow.getSpecies().getName() })!].getAlert()
    }
    
    var body: some View {
        ZStack() {
            VStack {
                Spacer().frame(height:50)
                Text(toShow.getName())
                    .font(.largeTitle)
                Spacer()
                Group{
                    Text("Last Watered:")
                    Text(getDate(date: toShow.getLastWatered()))
                    Text("Location:")
                    Text(toShow.getLocation())
                    Spacer().frame(height:35)
                    Text(getAlert(toShow: toShow))
                }
                Spacer()
                Group {
                    Text("Species Information")
                        .font(.title)
                    Text("Species Common Name: " + (toShow.getSpecies().getName()))
                    Text("Species Scientific Name: " + (toShow.getSpecies().getSciName()))
                    
                    Text("Days Between Watering: " + String(toShow.getSpecies().getWaterDays()))
                    Text("Number of Days Since Watering: " + String(Calendar.current.dateComponents([.day], from: toShow.getLastWatered(), to: Date()).day!))
                    Text("Amount of Light Required: " + String(toShow.getSpecies().getLight()))
                    Text("Type of Soil Required: " + String(toShow.getSpecies().getSoil()))
                }
                Spacer()
                HStack{
                    Button(action: {
                        for x in 0..<savedPlants.count {
                            if toShow.getName() == savedPlants[x].name {
                                savedPlants[x].dateLastWatered = Date()
                            }
                        }
                        try? context.save()
                        presentationMode.wrappedValue.dismiss()
                    }, label: {
                        Text("Water Plant")
                    })
                    .padding(15)
                    .foregroundColor(.white)
                    .background(Color.accentColor)
                    .clipShape(Capsule())
                    Button(action: {
                        presentationMode.wrappedValue.dismiss()
                        for x in 0..<savedPlants.count {
                            if toShow.getName() == savedPlants[x].name {
                                context.delete(savedPlants[x])
                            }
                        }
                        
                        try? context.save()
                        
                    }, label: {
                        Text("Remove Plant")
                    })
                    .padding(15)
                    .foregroundColor(.white)
                    .background(Color.red)
                    .clipShape(Capsule())
                }
                Spacer()
                    .frame(height:15)
            }
            VStack{
            HStack{
            Button(action: { presentationMode.wrappedValue.dismiss() }) {
                ZStack(alignment: .topLeading) {
                    HStack{
                        Image(systemName: "chevron.left")
                            .foregroundColor(.blue)
                            .imageScale(.large)
                        Text("Back")
                            .font(.title3)
                            .foregroundColor(.blue)
                    }
                }
            }.frame(width:100, height:35)
                Spacer()
            }
                Spacer()
            }
            
        }
        .navigationTitle(Text(""))
        .navigationBarHidden(true)
        .statusBar(hidden: true)
    }
}




//LIST VIEW OF MY PLANTS
struct MyPlants: View {
    
    @Environment(\.managedObjectContext) var context
    @FetchRequest(
        sortDescriptors: [NSSortDescriptor(keyPath: \PlantSaved.dateLastWatered, ascending: true)],
        animation: .default)
    private var savedPlants: FetchedResults<PlantSaved>
    
    func savedToPlant (toConvert: PlantSaved) -> Plant {
        return Plant(name: toConvert.name!, species: speciesStorage[speciesStorage.firstIndex(where: { $0.getName() == toConvert.species })!], location: toConvert.location!, dateLastWatered: toConvert.dateLastWatered!)
    }
    
    
    var body: some View {
        NavigationView{
            ZStack {
                VStack{
                    Text("My Plants")
                        .font(.title)
                    List(savedPlants) { plant in
                        NavigationLink(destination: PlantView(toShow: savedToPlant(toConvert: plant))) {
                            PlantRow(plant: plant)
                        }
                    }
                    VStack {
                        Spacer()
                            .frame(height: 20)
                        NavigationLink(destination: AddPlants()) {
                            Text("Add New Plant")
                        }
                        .padding(15)
                        .foregroundColor(.white)
                        .background(Color.accentColor)
                        .clipShape(Capsule())
                        Spacer()
                            .frame(height:15)
                    }
                    
                }.navigationBarTitle("")
                    .navigationBarHidden(true)
            }
        }
    }
}

struct MyPlants_Previews: PreviewProvider {
    static var previews: some View {
        MyPlants()
    }
}
