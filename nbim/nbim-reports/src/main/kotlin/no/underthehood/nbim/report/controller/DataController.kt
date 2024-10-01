package no.underthehood.nbim.report.controller

import no.underthehood.nbim.model.EntityYear
import no.underthehood.nbim.model.NBIMPosition
import no.underthehood.nbim.report.NBIMEquityDao
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@CrossOrigin(origins = ["*"])
@RestController
class DataController(val nbimEquityDao: NBIMEquityDao) {


    @GetMapping("/data", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAll(): List<NBIMPosition> {
        return nbimEquityDao.getAll()
    }

    @GetMapping("/byCountryYear/{country}/{year}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByCountryAndYear(
        @PathVariable country: String,
        @PathVariable year: Int,): List<NBIMPosition> {
        return nbimEquityDao.getByCountryAndYear(country, year)
    }

    @GetMapping("/byIndustryYear/{industry}/{year}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByIndustryAndYear(
        @PathVariable industry: String,
        @PathVariable year: Int,): List<NBIMPosition> {
        return nbimEquityDao.getByCountryAndYear(industry, year)
    }

    @GetMapping("/countryDevelopment/{country}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByIndustryAndYear(
        @PathVariable country: String,): List<EntityYear> {
        return nbimEquityDao.getCountryDevelopment(country)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @GetMapping("/companyDevelopment/{company}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByCompanyAndYear(
        @PathVariable company: String,): List<EntityYear> {
        val decoded = String(Base64.decode(company.toByteArray())).trim()
        println("$company --> $decoded")
        return nbimEquityDao.getCompanyDevelopment(decoded)
    }

    @GetMapping("/distinctCountries", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun distinctCountries(): List<String> {
        return nbimEquityDao.getDistinctCountries()
    }

    @GetMapping("/distinctyears", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun distinctYears(): List<Int> {
        return nbimEquityDao.getDistinctYears()
    }

    @GetMapping("/distinctcompanies", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun distinctCompanies(): List<String> {
        return nbimEquityDao.getDistinctCompanies()
    }


}