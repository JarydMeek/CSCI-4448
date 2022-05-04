//
//  Weather.swift
//  ExtremePlantTracker
//
//  Created by Jaryd Meek on 3/28/22.
//

import SwiftUI

var lat:Double = 0
var lon:Double = 0

//Weather
struct Weather: View {
    @Binding var loaded:Bool
    
    //NSDate to String converter
    func getDate(date: Int)-> String {
        let formatter = DateFormatter()
        formatter.dateFormat = "h:mm"
        return formatter.string(from: NSDate(timeIntervalSince1970: TimeInterval(date)) as Date)
    }
    

    
    var body: some View {
        
        VStack {
            //Make sure there is data to display
            if (loaded == true  && results != nil) {

            ZStack {
                if (results!.current.clouds > 50) {
                    Color(red: 0.53, green: 0.6, blue: 0.7).ignoresSafeArea()
                } else {
                    Color(red: 0.25, green: 0.5, blue: 1.0).ignoresSafeArea()
                }
                VStack {
                    Spacer()
                    let curr_temp = Int((results!.current.temp-273.15)*9/5+32)
                    if (results!.current.clouds > 50) {
                        Image(systemName: "cloud.fill")
                            .resizable()
                            .frame(width:250, height:175)
                            .foregroundColor(.white)
                            .shadow(color:.black.opacity(0.5), radius:10, x:0, y:0)
                    } else {
                        Image(systemName: "sun.max.fill")
                            .resizable()
                            .frame(width:250, height:250)
                            .foregroundColor(.yellow)
                            .shadow(color:.black.opacity(0.5), radius:10, x:0, y:0)
                    }
                    Text(String(curr_temp) + "°F, " + String(results!.current.weather[0].description)).foregroundColor(.white)
                    Text("Wind Speed: " + String(results!.current.wind_speed) + "mph").foregroundColor(.white)
                    Spacer()
                    if (curr_temp < 40) {
                        Text("It's getting chilly out, consider taking less hardy plants indoors!")
                            .font(.system(size: 15))
                            .multilineTextAlignment(.center)
                        Spacer()
                    }
                    if (results!.current.wind_speed > 25) {
                        Text("It's quite windy, make sure your plants don't get knocked over!")
                            .font(.system(size: 15))
                            .multilineTextAlignment(.center)
                        Spacer()
                    }
                    // begin hstack
                    HStack{
                        VStack{
                            Text("Sunrise")
                            Image(systemName: "sunrise.fill")
                                .foregroundColor(.yellow)
                                .frame(height:10)
                            Text(getDate(date: results!.current.sunrise) + "AM")
                        }.foregroundColor(.white)
                        VStack{
                            Text("Sunset")
                            Image(systemName: "sunset.fill")
//                                .renderingMode(.original)
                                .foregroundColor(.yellow)
                                .frame(height:10)
                            Text(getDate(date: results!.current.sunset) + "PM")
                        }.foregroundColor(.white)
                    }
                    Spacer()
                    // end hstack
                    
                }
                
            }
            
            
        } else {
            ZStack {
                Color(red: 0.53, green: 0.6, blue: 0.7).ignoresSafeArea()
                Text("Loading Weather...")
                    .foregroundColor(.white)
            }
            }
        }
    }

}
