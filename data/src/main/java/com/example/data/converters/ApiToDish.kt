package com.example.data.converters

import com.example.data.api.Digest
import com.example.data.api.Images
import com.example.data.api.Ingredient
import com.example.data.api.Recipe
import com.example.data.api.Sub
import com.example.data.api.TotalDaily
import com.example.data.api.TotalNutrients
import com.example.data.database.dish.BasicInfo
import com.example.data.database.dish.Dish
import com.example.data.database.dish.Nutrient
import com.example.data.database.dish.Nutrition
import com.example.data.database.dish.OtherInfo
import com.example.data.database.dish.dishDigest
import com.example.data.database.dish.dishImage
import com.example.data.database.dish.dishIngredient
import com.example.data.database.dish.dishSub
import kotlin.math.roundToInt

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
        yield = recipe.yield.roundToInt(),
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
fun totalDailyToMap(totalDaily: TotalDaily?): Map<String, Nutrient?> {
    return mapOf(
        "CA" to (totalDaily?.CA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "CHOCDF" to (totalDaily?.CHOCDF?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "CHOLE" to (totalDaily?.CHOLE?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "ENERC_KCAL" to (totalDaily?.ENERC_KCAL?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FASAT" to (totalDaily?.FASAT?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FAT" to (totalDaily?.FAT?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FE" to (totalDaily?.FE?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FIBTG" to (totalDaily?.FIBTG?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FOLDFE" to (totalDaily?.FOLDFE?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "K" to (totalDaily?.K?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "MG" to (totalDaily?.MG?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "NA" to (totalDaily?.NA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "NIA" to (totalDaily?.NIA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "P" to (totalDaily?.P?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "PROCNT" to (totalDaily?.PROCNT?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "RIBF" to (totalDaily?.RIBF?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "THIA" to (totalDaily?.THIA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "TOCPHA" to (totalDaily?.TOCPHA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITA_RAE" to (totalDaily?.VITA_RAE?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITB12" to (totalDaily?.VITB12?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITB6A" to (totalDaily?.VITB6A?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITC" to (totalDaily?.VITC?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITD" to (totalDaily?.VITD?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITK1" to (totalDaily?.VITK1?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "ZN" to (totalDaily?.ZN?.let { createNutrient(it.label, it.quantity, it.unit) })
    )
}

fun totalNutrientsToMap(totalNutrients: TotalNutrients?): Map<String, Nutrient?> {
    return mapOf(
        "CA" to (totalNutrients?.CA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "CHOCDF" to (totalNutrients?.CHOCDF?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "CHOCDF.net" to (totalNutrients?.CHOCDFnet?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "CHOLE" to (totalNutrients?.CHOLE?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "ENERC_KCAL" to (totalNutrients?.ENERC_KCAL?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FAMS" to (totalNutrients?.FAMS?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FAPU" to (totalNutrients?.FAPU?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FASAT" to (totalNutrients?.FASAT?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FAT" to (totalNutrients?.FAT?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FATRN" to (totalNutrients?.FATRN?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FE" to (totalNutrients?.FE?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FIBTG" to (totalNutrients?.FIBTG?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FOLAC" to (totalNutrients?.FOLAC?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FOLDFE" to (totalNutrients?.FOLDFE?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "FOLFD" to (totalNutrients?.FOLFD?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "K" to (totalNutrients?.K?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "MG" to (totalNutrients?.MG?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "NA" to (totalNutrients?.NA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "NIA" to (totalNutrients?.NIA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "P" to (totalNutrients?.P?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "PROCNT" to (totalNutrients?.PROCNT?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "RIBF" to (totalNutrients?.RIBF?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "SUGAR" to (totalNutrients?.SUGAR?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "SUGAR.added" to (totalNutrients?.SUGARadded?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "THIA" to (totalNutrients?.THIA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "TOCPHA" to (totalNutrients?.TOCPHA?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITA_RAE" to (totalNutrients?.VITA_RAE?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITB12" to (totalNutrients?.VITB12?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITB6A" to (totalNutrients?.VITB6A?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITC" to (totalNutrients?.VITC?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITD" to (totalNutrients?.VITD?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "VITK1" to (totalNutrients?.VITK1?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "WATER" to (totalNutrients?.WATER?.let { createNutrient(it.label, it.quantity, it.unit) }),
        "ZN" to (totalNutrients?.ZN?.let { createNutrient(it.label, it.quantity, it.unit) }),
    )
}

fun createNutrient(name: String?, quantity: Double?, unit: String?): Nutrient? {
    if (name == null || quantity == null || unit == null) {
        return null
    }
    return Nutrient(
        name = name,
        quantity = quantity,
        unit = unit
    )
}


fun convertToDishIngredients(recipeIngredients: List<Ingredient?>): List<dishIngredient?> {
    return recipeIngredients.map { ingredient ->
        ingredient?.let {
            dishIngredient(
                foodCategory = it.foodCategory,
                foodId = it.foodId,
                image = it.image,
                text = it.text,
                weight = it.weight
            )
        }
    }
}

fun convertToDishSub(recipeSub: Sub?): dishSub? {
    return recipeSub?.let {
        dishSub(
            label = it.label,
            tag = it.tag,
            schemaOrgTag = it.schemaOrgTag,
            total = it.total,
            hasRDI = it.hasRDI,
            daily = it.daily,
            unit = it.unit
        )
    }
}

fun convertToDishDigest(recipeDigest: Digest?): dishDigest? {
    return recipeDigest?.let {
        dishDigest(
            label = it.label,
            tag = it.tag,
            schemaOrgTag = it.schemaOrgTag,
            total = it.total,
            hasRDI = it.hasRDI,
            daily = it.daily,
            unit = it.unit,
            sub = it.sub?.map(::convertToDishSub) ?: emptyList()
        )
    }
}


fun convertToDishImage(url: String?, width: Int?, height: Int?): dishImage? {
    if (url == null || width == null || height == null) {
        return null
    }
    return dishImage(
        url = url,
        width = width,
        height = height
    )
}

fun convertToDishImages(recipeImages: Images?): Map<String, dishImage?> {
    return mapOf(
        "LARGE" to (recipeImages?.LARGE?.let { convertToDishImage(it.url, it.width, it.height) }),
        "REGULAR" to (recipeImages?.REGULAR?.let { convertToDishImage(it.url, it.width, it.height) }),
        "SMALL" to (recipeImages?.SMALL?.let { convertToDishImage(it.url, it.width, it.height) }),
        "THUMBNAIL" to (recipeImages?.THUMBNAIL?.let { convertToDishImage(it.url, it.width, it.height) })
    )
}




