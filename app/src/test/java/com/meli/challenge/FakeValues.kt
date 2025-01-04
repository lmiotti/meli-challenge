package com.meli.challenge

import com.meli.challenge.data.network.model.CocktailApiResponse
import com.meli.challenge.data.network.model.CocktailListApiResponse
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.models.NetworkError

object FakeValues {

    const val fakeId: String = "11007"
    const val fakeName: String = "Margarita"
    val fakeFirstLetter: String = fakeName.first().toString()
    val fakeCocktail = Cocktail(
        id = fakeId,
        name = fakeName,
        thumbnail = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
        ingredients = listOf("Tequila", "Triple sec", "Lime juice", "Salt"),
        isAlcoholic = true
    )
    val fakeCocktailList = listOf(fakeCocktail, fakeCocktail)
    val fakeError = NetworkError(code = 400, message = "Not Found")

    val fakeCocktailApiResponse = listOf(
        CocktailApiResponse(
            id = "11007",
            name = "Margarita",
            alternativeName = null,
            tags = "IBA,ContemporaryClassic",
            video = null,
            category = "Ordinary Drink",
            iba = "Contemporary Classics",
            alcoholic = "Alcoholic",
            glass =  "Cocktail glass",
            instructions = "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten only the outer rim and sprinkle the salt on it. The salt should present to the lips of the imbiber and never mix into the cocktail. Shake the other ingredients with ice, then carefully pour into the glass.",
            instructionsEs = "Frota el borde del vaso con la rodaja de lima para que la sal se adhiera a él. Procure humedecer sólo el borde exterior y espolvorear la sal sobre él. La sal debe presentarse en los labios del imbibidor y nunca mezclarse en el cóctel. Agite los demás ingredientes con hielo y viértalos con cuidado en el vaso.",
            instructionsDe = "Reiben Sie den Rand des Glases mit der Limettenscheibe, damit das Salz daran haftet. Achten Sie darauf, dass nur der äußere Rand angefeuchtet wird und streuen Sie das Salz darauf. Das Salz sollte sich auf den Lippen des Genießers befinden und niemals in den Cocktail einmischen. Die anderen Zutaten mit Eis schütteln und vorsichtig in das Glas geben.",
            instructionsFr = "Frotter le bord du verre avec la tranche de citron vert pour faire adhérer le sel. Veillez à n'humidifier que le bord extérieur et à y saupoudrer le sel. Le sel doit se présenter aux lèvres du buveur et ne jamais se mélanger au cocktail. Secouez les autres ingrédients avec de la glace, puis versez-les délicatement dans le verre.",
            instructionsIt = "Strofina il bordo del bicchiere con la fetta di lime per far aderire il sale.\r\nAvere cura di inumidire solo il bordo esterno e cospargere di sale.\r\nIl sale dovrebbe presentarsi alle labbra del bevitore e non mescolarsi mai al cocktail.\r\nShakerare gli altri ingredienti con ghiaccio, quindi versarli delicatamente nel bicchiere.",
            instructionsZhHans = null,
            instructionsZhHant = null,
            thumbnail = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
            ingredient1 = "Tequila",
            ingredient2 = "Triple sec",
            ingredient3 = "Lime juice",
            ingredient4 = "Salt",
            ingredient5 = null,
            ingredient6 = null,
            ingredient7 = null,
            ingredient8 = null,
            ingredient9 = null,
            ingredient10 = null,
            ingredient11 = null,
            ingredient12 = null,
            ingredient13 = null,
            ingredient14 = null,
            ingredient15 = null,
            measure1= "1 1/2 oz ",
            measure2 = "1/2 oz ",
            measure3 = "1 oz ",
            measure4 = null,
            measure5 = null,
            measure6 = null,
            measure7 = null,
            measure8 = null,
            measure9 = null,
            measure10 = null,
            measure11 = null,
            measure12 = null,
            measure13 = null,
            measure14 = null,
            measure15 = null,
            imageSource = "https://commons.wikimedia.org/wiki/File:Klassiche_Margarita.jpg",
            imageAttribution = "Cocktailmarler",
            creativeCommonsConfirmed = "Yes",
            dateModified = "2015-08-18 14:42:59"
        )
    )

    val fakeCocktailListApiResponse = CocktailListApiResponse(fakeCocktailApiResponse)
}
