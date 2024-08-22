import SwiftUI
import app

@main
struct iOSApp: App {
    init(){
        KoinKt.doInitKoin()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
