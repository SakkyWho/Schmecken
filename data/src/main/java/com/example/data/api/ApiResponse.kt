package com.example.data.api

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val _links: Links,
    val count: Int,
    val from: Int,
    val hits: List<Hit>,
    val to: Int
)

class Links

data class Hit(
    val _links: LinksX,
    val recipe: Recipe
)

data class LinksX(
    val self: Self
)

data class Recipe(
    val calories: Double,
    val cautions: List<String>,
    val cuisineType: List<String>,
    val dietLabels: List<String>,
    val digest: List<Digest>,
    val dishType: List<String>,
    val healthLabels: List<String>,
    val image: String,
    val images: Images,
    val ingredientLines: List<String>,
    val ingredients: List<Ingredient>,
    val label: String,
    val mealType: List<String>,
    val shareAs: String,
    val source: String,
    val totalDaily: TotalDaily,
    val totalNutrients: TotalNutrients,
    val totalTime: Int,
    val totalWeight: Double,
    val uri: String,
    val url: String,
    val yield: Double
)

data class Self(
    val href: String,
    val title: String
)

data class Digest(
    val daily: Double,
    val hasRDI: Boolean,
    val label: String,
    val schemaOrgTag: String,
    val sub: List<Sub>,
    val tag: String,
    val total: Double,
    val unit: String
)

data class Images(
    val LARGE: LARGE,
    val REGULAR: REGULAR,
    val SMALL: SMALL,
    val THUMBNAIL: THUMBNAIL
)

data class Ingredient(
    val foodCategory: String,
    val foodId: String,
    val image: String,
    val text: String,
    val weight: Double
)

data class TotalDaily(
    val CA: CA,
    val CHOCDF: CHOCDF,
    val CHOLE: CHOLE,
    val ENERC_KCAL: ENERCKCAL,
    val FASAT: FASAT,
    val FAT: FAT,
    val FE: FE,
    val FIBTG: FIBTG,
    val FOLDFE: FOLDFE,
    val K: K,
    val MG: MG,
    val NA: NA,
    val NIA: NIA,
    val P: P,
    val PROCNT: PROCNT,
    val RIBF: RIBF,
    val THIA: THIA,
    val TOCPHA: TOCPHA,
    val VITA_RAE: VITARAE,
    val VITB12: VITB12,
    val VITB6A: VITB6A,
    val VITC: VITC,
    val VITD: VITD,
    val VITK1: VITK1,
    val ZN: ZN
)

data class TotalNutrients(
    val CA: CA,
    val CHOCDF: CHOCDFX,
    @SerializedName("CHOCDF.net")
    val CHOCDFnet: CHOCDFNet,
    val CHOLE: CHOLEX,
    val ENERC_KCAL: ENERCKCALX,
    val FAMS: FAMS,
    val FAPU: FAPU,
    val FASAT: FASATX,
    val FAT: FATX,
    val FATRN: FATRN,
    val FE: FEX,
    val FIBTG: FIBTGX,
    val FOLAC: FOLACX,
    val FOLDFE: FOLDFEX,
    val FOLFD: FOLFD,
    val K: KX,
    val MG: MGX,
    val NA: NAX,
    val NIA: NIAX,
    val P: PX,
    val PROCNT: PROCNTX,
    val RIBF: RIBFX,
    val SUGAR: SUGAR,
    @SerializedName("SUGAR.added")
    val SUGARadded: SUGARAdded,
    val THIA: THIAX,
    val TOCPHA: TOCPHAX,
    val VITA_RAE: VITARAEX,
    val VITB12: VITB12X,
    val VITB6A: VITB6AX,
    val VITC: VITCX,
    val VITD: VITDX,
    val VITK1: VITK1X,
    val WATER: WATER,
    val ZN: ZNX
)

data class FOLACX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class TOCPHAX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class THIAX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class RIBFX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class PROCNTX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class PX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class NIAX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class NAX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class MGX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class KX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FOLDFEX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FIBTGX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FEX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FATX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FASATX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITARAEX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class ENERCKCALX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class CHOLEX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITB12X(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITB6AX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITCX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITDX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITK1X(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class ZNX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class CHOCDFX(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class Sub(
    val daily: Double,
    val hasRDI: Boolean,
    val label: String,
    val schemaOrgTag: String,
    val tag: String,
    val total: Double,
    val unit: String
)

data class LARGE(
    val height: Int,
    val url: String,
    val width: Int
)

data class REGULAR(
    val height: Int,
    val url: String,
    val width: Int
)

data class SMALL(
    val height: Int,
    val url: String,
    val width: Int
)

data class THUMBNAIL(
    val height: Int,
    val url: String,
    val width: Int
)

data class CA(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class CHOCDF(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class CHOLE(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class ENERCKCAL(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FASAT(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FAT(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FE(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FIBTG(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FOLDFE(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class K(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class MG(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class NA(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class NIA(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class P(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class PROCNT(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class RIBF(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class THIA(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class TOCPHA(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITARAE(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITB12(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITB6A(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITC(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITD(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class VITK1(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class ZN(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class CHOCDFNet(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FAMS(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FAPU(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FATRN(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class FOLAC(
    val label: String,
    val quantity: Int,
    val unit: String
)

data class FOLFD(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class SUGAR(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class SUGARAdded(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class WATER(
    val label: String,
    val quantity: Double,
    val unit: String
)
data class FourthInfo(
    val Dishid : Int,
    val imageUrl: String,
    val label: String,
    val calories: Double
)