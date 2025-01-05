# MercadoLibre Challenge
The purpose of this application is to show a cocktail application that allows user to look for a drink and check its ingredients and their measure.

### 📱 App Information
When opening the app, it will show a Home screen with the option to enter a drink on a search bar in order to look for information about it.
After successfuly retrieving a cocktail list, user can tap any of them and a detail screen will be shown.
On this detail screen, a bigger image will appear, an indication if it is an alcoholic drink and the ingredients to prepare it.

### 👩‍💻 Developer Documentation
1. When opening the app, a Home screen will be shown.
2. If user enters a word in the SearchBar, a network call to [TheCocktailDB](https://www.thecocktaildb.com/api.php) will be done and if it succeeds, a cocktail list will be shown on the Home screen.
3. The GET request to obtain that list will depend if user's input was only a letter or a bigger word.
4. In case user finds the cocktail he/she was looking for, he/she has the possibility to press one of them and a Detail screen will be shown. 
5. When Detail screen is initializing, a new request is done using the cocktail's id provided by the previous screen.

#### 🔧 Implementation
Project is organized taking care of Clean Architecture organization through `Presentation`, `Domain` and `Data` layers:
- **Presentation**: This layer will respect MVI pattern, where each screen has an `Intent` and a `State`. 
`StateFlows` were implemented with the purpose that screens react when data inside `ViewModel` changes. 
Shimmer effect was implemented using an external library ([Shimmer for Jetpack Compose & Compose Multiplatform](https://github.com/valentinilk/compose-shimmer))
- **Domain**: The only purpose of this layer is to map network models to `View` ones.
- **Data**: This layer will include a `Repository` where the communication to the API is stablished. 
`RemoteDataSources` were not implemented due to the fact that they will create boilerplate to this project.
