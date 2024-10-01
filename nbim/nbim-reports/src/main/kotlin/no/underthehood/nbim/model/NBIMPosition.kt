package no.underthehood.nbim.model

data class NBIMPosition (
    val region: String,  // "EUROPE", "ASIA", "NORTH AMERICA", "SOUTH AMERICA", "AFRICA", "OCEANIA"
    val country: String,
    val name: String,
    val industry: String,
    val marketValueNOK: Double,
    val marketValueUSD: Double,
    val voting: Double,
    val ownership: Double,
    val incorporationCountry: String,
    val reportingYear: Int,
)

data class EntityYear(val entity: String, val sum: Long, val year: Int)

