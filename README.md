# Social Cleaning Control Project


* [How to build the project](#how-to-build-the-project)
* [Inspiration](#inspiration)
* [Description](#description)
* [How it is built](#how-it-is-built)
* [What's next](#whats-next)
* [Benefits](#benefits)
* [Video Demo](#video-demo)

## How to build the project

To build and run the project need to:
- Add the project to a firebase account and generate for it the **google-services.json** to add under the **app** directory.
- Add the maps api key to the **local.properties** file following this format **MAPS_API_KEY=$MAPS_API_KEY** ([get api key](https://developers.google.com/maps/documentation/android-sdk/get-api-key)). 
  
## Inspiration

Living in a city, I've always believed that a clean environment contributes to a happier community. Witnessing the negative effects of litter and uncleanliness motivated me to explore ways to harness the power of collective responsibility.

Many communities struggle with waste management and maintaining a clean environment.  I believe that by fostering a sense of shared ownership for our city's well-being, we can create a positive change. 


##  Description

The project aims to create a social control system to keep cities clean through active citizen participation. An app will allow anyone to report dirty areas in the city. The app will allow users to:

* **Mark on map**: Users can identify and mark dirty spots using geolocation.
* **Take photo**: Add a photo to document the state of dirty areas.
* **Submit reports**: Complete a comprehensive report including photos and a description of the problem and its location to share with local waste management companies.
* **Mark locations cleaned**: Mark locations that are already cleaned.
* **View locations**: View the map with locations of dirty areas and those already resolved.
* **Gain points**: Users will receive points for the location marked as dirty or cleaned.

Initially, locations will be stored locally on the device and the user will have the possibility to report dirty and cleaned areas and also share reports by email to the local waste management companies. A gamification system will award points for reports and confirmation of cleaning locations.


##  How it is built

Social Cleaning Control is an Android application that leverages the modern Jetpack Compose framework for a smooth and intuitive user experience. It integrates the Google Maps SDK for seamless location reporting and utilizes a Room database for efficient local data storage. To ensure accurate reporting, the app employs the Geocoder API to automatically retrieve the corresponding address for any raw location data captured. Initially, users can share reports of dirty locations via email.


## What's next

- User account system will be created. 
- A shared database will centralize locations, avoiding duplicate reports and allowing users to mark already cleaned areas notified by other users.
- The gamification will be improved with badges and City leaderboards to reward the most active citizens in keeping their city clean.
- Integration of a direct communication system with local waste management companies for efficient routing of reports and feedback on the app's impact.


## Benefits

- Increase civic sense and responsibility for keeping the city clean.
- Improve efficiency in waste management and urban cleaning.
- Promotion of a social network of active citizens for environmental protection.
- Creation of a cleaner and more livable urban environment for all.

  
## Video Demo

  [![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/o0jVtU84vq4/0.jpg)](https://www.youtube.com/watch?v=o0jVtU84vq4)
