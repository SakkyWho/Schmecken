package com.example.data

fun recipeToDish(recipe: Recipe): Dish {
    val basicInfo = BasicInfo(
        uri = recipe.uri,
        label = recipe.label,
        image = recipe.image,
        images = convertToDishImages(recipe.images),
        dietLabels = recipe.dietLabels,
        healthLabels = recipe.healthLabels,
        cautions = recipe.cautions,
        cuisinType = recipe.cuisineType,
        totalTime = recipe.totalTime,
        mealType = recipe.mealType,
        dishType = recipe.dishType
    )

    val otherInfo = OtherInfo(
        source = recipe.source,
        url = recipe.url,
        shareas = recipe.shareAs,
        yield = recipe.yield,
        ingredientLines = recipe.ingredientLines,
        ingredients = convertToDishIngredients(recipe.ingredients),
        totalTime = recipe.totalTime,
        digest = recipe.digest.map(::convertToDishDigest)
    )

    val nutrition = Nutrition(
        calories = recipe.calories,
        totalWeight = recipe.totalWeight,
        totalDaily = totalDailyToMap(recipe.totalDaily),
        nutrients = totalNutrientsToMap(recipe.totalNutrients)
    )

    return Dish(
        basicinfo = basicInfo,
        otherinfo = otherInfo,
        nutrition = nutrition
    )


}
fun totalDailyToMap(totalDaily: TotalDaily): Map<String, Nutrient> {
    return mapOf(
        "CA" to createNutrient(totalDaily.CA.label, totalDaily.CA.quantity,totalDaily.CA.unit),
        "CHOCDF" to createNutrient(totalDaily.CHOCDF.label, totalDaily.CHOCDF.quantity,totalDaily.CHOCDF.unit),
        "CHOLE" to createNutrient(totalDaily.CHOLE.label, totalDaily.CHOLE.quantity,totalDaily.CHOLE.unit),
        "ENERC_KCAL" to createNutrient(totalDaily.ENERC_KCAL.label, totalDaily.ENERC_KCAL.quantity,totalDaily.ENERC_KCAL.unit),
        "FASAT" to createNutrient(totalDaily.FASAT.label, totalDaily.FASAT.quantity,totalDaily.FASAT.unit),
        "FAT" to createNutrient(totalDaily.FAT.label, totalDaily.FAT.quantity,totalDaily.FAT.unit),
        "FE" to createNutrient(totalDaily.FE.label, totalDaily.FE.quantity,totalDaily.FE.unit),
        "FIBTG" to createNutrient(totalDaily.FIBTG.label, totalDaily.FIBTG.quantity,totalDaily.FIBTG.unit),
        "FOLDFE" to createNutrient(totalDaily.FOLDFE.label, totalDaily.FOLDFE.quantity,totalDaily.FOLDFE.unit),
        "K" to createNutrient(totalDaily.K.label, totalDaily.K.quantity,totalDaily.K.unit),
        "MG" to createNutrient(totalDaily.MG.label, totalDaily.MG.quantity,totalDaily.MG.unit),
        "NA" to createNutrient(totalDaily.NA.label, totalDaily.NA.quantity,totalDaily.NA.unit),
        "NIA" to createNutrient(totalDaily.NIA.label, totalDaily.NIA.quantity,totalDaily.NIA.unit),
        "P" to createNutrient(totalDaily.P.label, totalDaily.P.quantity,totalDaily.P.unit),
        "PROCNT" to createNutrient(totalDaily.PROCNT.label, totalDaily.PROCNT.quantity,totalDaily.PROCNT.unit),
        "RIBF" to createNutrient(totalDaily.RIBF.label, totalDaily.RIBF.quantity,totalDaily.RIBF.unit),
        "THIA" to createNutrient(totalDaily.THIA.label, totalDaily.THIA.quantity,totalDaily.THIA.unit),
        "TOCPHA" to createNutrient(totalDaily.TOCPHA.label, totalDaily.TOCPHA.quantity,totalDaily.TOCPHA.unit),
        "VITA_RAE" to createNutrient(totalDaily.VITA_RAE.label, totalDaily.VITA_RAE.quantity,totalDaily.VITA_RAE.unit),
        "VITB12" to createNutrient(totalDaily.VITB12.label, totalDaily.VITB12.quantity,totalDaily.VITB12.unit),
        "VITB6A" to createNutrient(totalDaily.VITB6A.label, totalDaily.VITB6A.quantity,totalDaily.VITB6A.unit),
        "VITC" to createNutrient(totalDaily.VITC.label, totalDaily.VITC.quantity,totalDaily.VITC.unit),
        "VITD" to createNutrient(totalDaily.VITD.label, totalDaily.VITD.quantity,totalDaily.VITD.unit),
        "VITK1" to createNutrient(totalDaily.VITK1.label, totalDaily.VITK1.quantity,totalDaily.VITK1.unit),
        "ZN" to createNutrient(totalDaily.ZN.label, totalDaily.ZN.quantity,totalDaily.ZN.unit)
    )
}
fun totalNutrientsToMap(totalNutrients: TotalNutrients): Map<String, Nutrient> {
    return mapOf(
        "CA" to createNutrient(totalNutrients.CA.label, totalNutrients.CA.quantity,totalNutrients.CA.unit),
        "CHOCDF" to createNutrient(totalNutrients.CHOCDF.label, totalNutrients.CHOCDF.quantity,totalNutrients.CHOCDF.unit),
        "CHOCDF.net" to createNutrient(totalNutrients.CHOCDFnet.label, totalNutrients.CHOCDFnet.quantity,totalNutrients.CHOCDFnet.unit),
        "CHOLE" to createNutrient(totalNutrients.CHOLE.label, totalNutrients.CHOLE.quantity,totalNutrients.CHOLE.unit),
        "ENERC_KCAL" to createNutrient(totalNutrients.ENERC_KCAL.label, totalNutrients.ENERC_KCAL.quantity,totalNutrients.ENERC_KCAL.unit),
        "FAMS" to createNutrient(totalNutrients.FAMS.label, totalNutrients.FAMS.quantity,totalNutrients.FAMS.unit),
        "FAPU" to createNutrient(totalNutrients.FAPU.label, totalNutrients.FAPU.quantity,totalNutrients.FAPU.unit),
        "FASAT" to createNutrient(totalNutrients.FASAT.label, totalNutrients.FASAT.quantity,totalNutrients.FASAT.unit),
        "FAT" to createNutrient(totalNutrients.FAT.label, totalNutrients.FAT.quantity,totalNutrients.FAT.unit),
        "FATRN" to createNutrient(totalNutrients.FATRN.label, totalNutrients.FATRN.quantity,totalNutrients.FATRN.unit),
        "FE" to createNutrient(totalNutrients.FE.label, totalNutrients.FE.quantity,totalNutrients.FE.unit),
        "FIBTG" to createNutrient(totalNutrients.FIBTG.label, totalNutrients.FIBTG.quantity,totalNutrients.FIBTG.unit),
        "FOLAC" to createNutrient(totalNutrients.FOLAC.label, totalNutrients.FOLAC.quantity,totalNutrients.FOLAC.unit),
        "FOLDFE" to createNutrient(totalNutrients.FOLDFE.label, totalNutrients.FOLDFE.quantity,totalNutrients.FOLDFE.unit),
        "FOLFD" to createNutrient(totalNutrients.FOLFD.label, totalNutrients.FOLFD.quantity,totalNutrients.FOLFD.unit),
        "K" to createNutrient(totalNutrients.K.label, totalNutrients.K.quantity,totalNutrients.K.unit),
        "MG" to createNutrient(totalNutrients.MG.label, totalNutrients.MG.quantity,totalNutrients.MG.unit),
        "NA" to createNutrient(totalNutrients.NA.label, totalNutrients.NA.quantity,totalNutrients.NA.unit),
        "NIA" to createNutrient(totalNutrients.NIA.label, totalNutrients.NIA.quantity,totalNutrients.NIA.unit),
        "P" to createNutrient(totalNutrients.P.label, totalNutrients.P.quantity,totalNutrients.P.unit),
        "PROCNT" to createNutrient(totalNutrients.PROCNT.label, totalNutrients.PROCNT.quantity,totalNutrients.PROCNT.unit),
        "RIBF" to createNutrient(totalNutrients.RIBF.label, totalNutrients.RIBF.quantity,totalNutrients.RIBF.unit),
        "SUGAR" to createNutrient(totalNutrients.SUGAR.label, totalNutrients.SUGAR.quantity,totalNutrients.SUGAR.unit),
        "SUGAR.added" to createNutrient(totalNutrients.SUGARadded.label, totalNutrients.SUGARadded.quantity,totalNutrients.SUGARadded.unit),
        "THIA" to createNutrient(totalNutrients.THIA.label, totalNutrients.THIA.quantity,totalNutrients.THIA.unit),
        "TOCPHA" to createNutrient(totalNutrients.TOCPHA.label, totalNutrients.TOCPHA.quantity,totalNutrients.TOCPHA.unit),
        "VITA_RAE" to createNutrient(totalNutrients.VITA_RAE.label, totalNutrients.VITA_RAE.quantity,totalNutrients.VITA_RAE.unit),
        "VITB12" to createNutrient(totalNutrients.VITB12.label, totalNutrients.VITB12.quantity,totalNutrients.VITB12.unit),
        "VITB6A" to createNutrient(totalNutrients.VITB6A.label, totalNutrients.VITB6A.quantity,totalNutrients.VITB6A.unit),
        "VITC" to createNutrient(totalNutrients.VITC.label, totalNutrients.VITC.quantity,totalNutrients.VITC.unit),
        "VITD" to createNutrient(totalNutrients.VITD.label, totalNutrients.VITD.quantity,totalNutrients.VITD.unit),
        "VITK1" to createNutrient(totalNutrients.VITK1.label, totalNutrients.VITK1.quantity,totalNutrients.VITK1.unit),
        "WATER" to createNutrient(totalNutrients.WATER.label, totalNutrients.WATER.quantity,totalNutrients.WATER.unit),
        "ZN" to createNutrient(totalNutrients.ZN.label, totalNutrients.ZN.quantity,totalNutrients.ZN.unit)
    )
}

fun createNutrient(name: String, quantity: Double, unit: String): Nutrient {
    return Nutrient(
        name = name,
        quantity = quantity,
        unit = unit
    )
}

fun convertToDishIngredients(recipeIngredients: List<Ingredient>): List<dishIngredient> {
    return recipeIngredients.map { Ingredient ->
        dishIngredient(
            foodCategory = Ingredient.foodCategory,
            foodId = Ingredient.foodId,
            image = Ingredient.image,
            text = Ingredient.text,
            weight = Ingredient.weight
        )
    }
}
fun convertToDishSub(recipeSub: Sub): dishSub {
    return dishSub(
        label = recipeSub.label,
        tag = recipeSub.tag,
        schemaOrgTag = recipeSub.schemaOrgTag,
        total = recipeSub.total,
        hasRDI = recipeSub.hasRDI,
        daily = recipeSub.daily,
        unit = recipeSub.unit
    )
}

fun convertToDishDigest(recipeDigest: Digest): dishDigest {
    return dishDigest(
        label = recipeDigest.label,
        tag = recipeDigest.tag,
        schemaOrgTag = recipeDigest.schemaOrgTag,
        total = recipeDigest.total,
        hasRDI = recipeDigest.hasRDI,
        daily = recipeDigest.daily,
        unit = recipeDigest.unit,
        sub = recipeDigest.sub.map(::convertToDishSub)
    )
}
fun convertToDishImage(url: String, width: Int, height: Int): dishImage {
    return dishImage(
        url = url,
        width = width,
        height = height
    )
}

fun convertToDishImages(recipeImages: Images): Map<String, dishImage> {
    return mapOf(
        "LARGE" to convertToDishImage(recipeImages.LARGE.url, recipeImages.LARGE.width, recipeImages.LARGE.height),
        "REGULAR" to convertToDishImage(recipeImages.REGULAR.url, recipeImages.REGULAR.width, recipeImages.REGULAR.height),
        "SMALL" to convertToDishImage(recipeImages.SMALL.url, recipeImages.SMALL.width, recipeImages.SMALL.height),
        "THUMBNAIL" to convertToDishImage(recipeImages.THUMBNAIL.url, recipeImages.THUMBNAIL.width, recipeImages.THUMBNAIL.height)
    )
}




