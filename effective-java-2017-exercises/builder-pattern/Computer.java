/**  Create a Computer class for a custom PC builder website.
Scenario: Customers want to build custom computers. There are several required components and many optional upgrades. Some combinations don't make sense (validation needed). The computer configuration should be immutable once created.
Requirements:

Required parameters:

String cpu (must be set)
int ramGB (must be at least 4)
int storageGB (must be at least 128)


Optional parameters:

String gpu (default: "Integrated Graphics")
boolean ssdStorage (default: false)
String cpuCooler (default: "Stock Cooler")
int powerSupplyWatts (default: 500)
String caseType (default: "Standard ATX")
boolean rgbLighting (default: false)



Implementation Requirements:

Make Computer immutable
Static nested Builder class
Builder constructor validates required parameters (throw IllegalArgumentException if invalid)
In the build() method, add validation:

If GPU is not "Integrated Graphics", power supply must be at least 600W
If ramGB > 32, throw an exception suggesting "Are you sure? That's a lot of RAM!"


Override toString() to display the configuration
Add a method double estimatePrice() that calculates a rough price based on specs

Main Method Requirements:

Create a budget computer (minimal specs)
Create a gaming computer (high-end GPU, lots of RAM, RGB)
Try to create an invalid computer (high-end GPU with low power supply) and catch the exception
*/


public class Computer {

}
